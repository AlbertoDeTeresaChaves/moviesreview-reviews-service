package com.moviesreview.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Schema(
        name = "ReviewRequest",
        description = "Schema to hold review request information"
)
public record ReviewRequestDto(

        @NotBlank(message = "Account ID cannot be blank")
        @Schema(description = "ID of the account/user writing the review", example = "123e4567-e89b-12d3-a456-426614174000")
        String accountId,

        @NotBlank(message = "Movie ID cannot be blank")
        @Schema(description = "ID of the movie being reviewed", example = "6abac1592637fc27c36901b8")
        String movieId,

        @Size(max = 1000, message = "Review text cannot exceed 1000 characters")
        @Schema(description = "Text content of the review", example = "Amazing movie with a great plot twist!")
        String review,

        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 10, message = "Rating cannot exceed 10")
        @Schema(description = "Rating given to the movie", example = "9")
        Integer rating,

        @PastOrPresent(message = "Watched date cannot be in the future")
        @Schema(description = "Date when the movie was watched", example = "2026-06-06T15:30:00Z")
        Instant watchedDate
) {
}
