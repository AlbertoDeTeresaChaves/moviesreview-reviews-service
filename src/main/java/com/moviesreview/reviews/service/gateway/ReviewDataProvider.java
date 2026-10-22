package com.moviesreview.reviews.service.gateway;

import com.moviesreview.reviews.model.Author;
import com.moviesreview.reviews.model.MovieSummary;

import java.util.UUID;

public interface ReviewDataProvider {
    Author getAuthor(UUID accountId);
    MovieSummary getMovie(String movieId);
}
