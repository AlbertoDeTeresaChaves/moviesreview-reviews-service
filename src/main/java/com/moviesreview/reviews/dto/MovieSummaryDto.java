package com.moviesreview.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "MovieSummary",
        description = "Schema to hold movie summary information"
)
public record MovieSummaryDto(
        @Schema(description = "Unique slug identifier of the movie", example = "drive-2011")
        String slug,

        @Schema(description = "Title of the movie", example = "Drive")
        String title,

        @Schema(description = "Release year of the movie", example = "2011")
        Integer releaseYear,

        @Schema(description = "URL of the movie poster image", example = "https://cdn.example.com/movie/drive-2011.jpg")
        String posterUrl
) {}
