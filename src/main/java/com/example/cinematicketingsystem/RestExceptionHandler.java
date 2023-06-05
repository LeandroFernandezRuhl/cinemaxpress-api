package com.example.cinematicketingsystem;

import com.example.cinematicketingsystem.apierror.ApiError;
import com.example.cinematicketingsystem.exception.EntityNotFoundException;
import com.example.cinematicketingsystem.exception.MovieApiException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URISyntaxException;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Builds a ResponseEntity that wraps an ApiError.
     * @param apiError the ApiError
     * @return the ApiError object wrapped in a ResponseEntity
     */
    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Handles for MissingServletRequestParameterException.
     * Triggered when a 'required' request parameter is missing.
     * @param ex the MissingServletRequestParameterException to be handled
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiError(status, error, ex));
    }


    /**
     * Handles HttpMediaTypeNotSupportedException. Triggered when a client POSTs,
     * PUTs, or PATCHes content of a type not supported by request handler.
     * @param ex the HttpMediaTypeNotSupportedException to be handled
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(status, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handles MethodArgumentNotValidException. Triggered when validation on an argument annotated with @Valid fails.
     * @param ex the MethodArgumentNotValidException to be handled
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ApiError apiError = new ApiError(status);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles HttpMessageNotReadableException. Triggered when the request has a malformed or invalid body.
     * @param ex the HttpMessageNotReadableException to be handled
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(status, error, ex));
    }

    /**
     * Handles HttpMessageNotWritableException. Triggered if the server encounters
     * an error while serializing an object into the desired format, such as JSON or XML.
     * @param ex the HttpMessageNotWritableException to be handled
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(new ApiError(status, error, ex));
    }

    /**
     * Handles NoHandlerFoundException. Triggered if the requested URL does not match any defined
     * routes or mappings in the web application or there is a missing or misconfigured handler or controller.
     * @param ex the NoHandlerFoundException to be handled
     * @param headers the headers to use for the response
     * @param status the status code to use for the response
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ApiError apiError = new ApiError(status);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     * @param ex the ConstraintViolationException to be handled
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            jakarta.validation.ConstraintViolationException ex) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles jakarta.persistence.EntityNotFoundException. Triggered when an operation attempts to find an entity
     * by a certain identifier or criteria, but the entity is not found in the data store.
     * @param ex the EntityNotFoundException to be handled
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(jakarta.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(jakarta.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex));
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than jakarta.persistence.EntityNotFoundException.
     * @param ex the EntityNotFoundException to be handled
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles DataIntegrityViolationException, inspects the cause for different DB causes.
     * @param ex the DataIntegrityViolationException to be handled
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handles MethodArgumentTypeMismatchException. Thrown when a method argument fails type conversion.
     * @param ex the MethodArgumentTypeMismatchException to be handled
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles URISyntaxException. Triggered when a URI syntax error occurs.
     * @param ex the URISyntaxException to be handled
     * @param request the current request
     * @return the ApiError object wrapped in a ResponseEntity
     */
    @ExceptionHandler(URISyntaxException.class)
    protected ResponseEntity<Object> handleURISyntaxException(
            URISyntaxException ex,
            WebRequest request) {
        ApiError apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage("Invalid URI syntax");
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(MovieApiException.class)
    protected ResponseEntity<Object> handleMovieApiException(MovieApiException ex) {
        if (ex.isMovieNotFound()) {
            ApiError apiError = new ApiError(NOT_FOUND);
            apiError.setMessage(ex.getMessage());
            return buildResponseEntity(apiError);
        }
        ApiError apiError = new ApiError(INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
}
