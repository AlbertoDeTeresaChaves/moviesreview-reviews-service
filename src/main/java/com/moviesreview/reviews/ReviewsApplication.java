package com.moviesreview.reviews;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "Reviews microservice Rest API Documentation",
				description = "Reviews microservice Rest API Documentation",
				version = "0.1",
				contact = @Contact(
						name = "Alberto de Teresa Chaves",
						email = "alberto.trabajo.es@gmail.com",
						url = "https://x.com/Beto_rvss"
				)
		)
)
@EnableFeignClients
@EnableConfigurationProperties
@SpringBootApplication
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}
