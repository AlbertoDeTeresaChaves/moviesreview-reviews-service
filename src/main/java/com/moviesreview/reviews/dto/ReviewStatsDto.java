package com.moviesreview.reviews.dto;

public record ReviewStatsDto(
        Double averageRating,
        Integer totalRating,
        Integer reviewCount,
        Integer watchedCount
) {
}
