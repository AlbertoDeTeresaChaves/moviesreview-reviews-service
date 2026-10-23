package com.moviesreview.reviews.service.client;

import com.moviesreview.reviews.dto.MovieSummaryDto;
import org.bson.types.ObjectId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "movies")
public interface MoviesClient {

    @GetMapping("/api/movies/id/{id}")
    Optional<MovieSummaryDto> getMovie(@PathVariable("id") String id);
}
