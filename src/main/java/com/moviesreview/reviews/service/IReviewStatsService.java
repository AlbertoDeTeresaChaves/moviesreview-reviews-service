package com.moviesreview.reviews.service;

import com.moviesreview.reviews.dto.ReviewStatsDto;

public interface IReviewStatsService {

    ReviewStatsDto getReviewStatsForMovie(String movieId);
}
