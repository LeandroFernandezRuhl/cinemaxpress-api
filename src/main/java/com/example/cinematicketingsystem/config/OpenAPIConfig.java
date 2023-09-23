package com.example.cinematicketingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        /* Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment"); */
        Contact contact = new Contact();
        contact.setEmail("leandro.feruhl@gmail.com");
        contact.setName("Leandro Fernandez");
        // contact.setUrl("https://www.bezkoder.com");

        // License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Cinema Management API")
                // .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints for managing movies, cinema rooms, and showtimes. Regular users can purchase and refund tickets, while the manager can access analytics such as sold tickets."); //.termsOfService("https://www.bezkoder.com/terms")

        // .license(mitLicense);

        return new OpenAPI().info(info); // .servers(List.of(devServer, prodServer));
    }
}
