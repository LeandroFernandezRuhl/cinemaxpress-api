# CinemaXpress API

CinemaXpress is a RESTful API built with Spring Boot, designed to provide a robust backend for cinema management operations. This API enables seamless ticket reservations, movie catalog management, showtime scheduling, and sales analytics for both clients and administrators.

## Features

- **Ticket Reservation:** Clients can search for movies, view available showtime schedules, and conveniently book desired seats with the option for refunds.
- **Movie Catalog Management:** Administrators can add and remove movies from the lineup, manually enter movie details (title, synopsis, duration), and integrate with TMDB, an external movie database, for easy addition of new films.
- **Showtime Scheduling:** Administrators can schedule movie showtimes across various cinema halls, assign screening times, define hall capacities, and manage available seats for each showtime.
- **Sales Analytics:** Administrators can access comprehensive statistics and metrics on ticket sales, including sales figures and revenue generated within specific date ranges or for individual movies.

## Technologies Used

- **Spring Boot:** The core framework for building the RESTful API.
- **Spring Web:** Provides support for building web applications and RESTful APIs following the MVC pattern.
- **Spring Data JPA:** Simplifies data access and manipulation in a relational database using the Java Persistence API (JPA).
- **Hibernate:** An Object-Relational Mapping (ORM) tool for mapping Java objects to relational database tables.
- **PostgreSQL:** The relational database management system used for data persistence.
- **Swagger:** Integrated for interactive API documentation, authentication handling, and a user-friendly interface for developers and clients.
- **JSON Web Tokens (JWT):** Implemented for robust authentication and authorization mechanisms, leveraging Spring Security for secure access control and data protection.

## Getting Started

To get a local copy of the project up and running, follow these steps:

1. Clone the repository.
2. Configure the database connection properties in the `application.properties` file.
3. Get a TMDB API key from [https://developer.themoviedb.org/reference/intro/getting-started](themoviedb.org) and add it in `application.properties`.
4. Build and run the application using Gradle with `./gradlew build`.

## Documentation

Detailed API documentation is available through Swagger UI once the application is running. Access the documentation by navigating to [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) in your web browser.
