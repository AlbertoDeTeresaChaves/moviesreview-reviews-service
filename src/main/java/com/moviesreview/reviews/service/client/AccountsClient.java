package com.moviesreview.reviews.service.client;

import com.moviesreview.reviews.dto.AuthorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "accounts-service",url = "http://localhost:8070")
public interface AccountsClient {

    @GetMapping("/api/accounts/{id}")
    Optional<AuthorDto> getAccount (@PathVariable("id") UUID id);
}
