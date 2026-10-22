package com.moviesreview.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(
        name = "ReviewResponse",
        description = "Schema to hold review response information"
)
public record ReviewResponseDto(
        @Schema(description = "Unique identifier of the review", example = "60c72b2f9b1d8b2f9b1d8b2f")
        String id,

        @Schema(description = "ID of the account/user who wrote the review", example = "123e4567-e89b-12d3-a456-426614174000")
        String accountId,

        @Schema(description = "ID of the movie associated with the review", example = "6abac1592637fc27c36901b8")
        String movieId,

        @Schema(description = "Author details of the review")
        AuthorDto author,

        @Schema(description = "Summary details of the reviewed movie")
        MovieSummaryDto movie,

        @Schema(description = "Text content of the review", example = "Amazing movie with a great plot twist!")
        String review,

        @Schema(description = "Rating given to the movie", example = "4.5")
        Double rating,

        @Schema(description = "Date when the movie was watched", example = "2026-06-06T15:30:00Z")
        Instant watchedDate
){}
