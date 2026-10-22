package com.moviesreview.reviews.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "Author",
        description = "Schema to hold author information"
)
public record AuthorDto(
        @Schema(description = "Username of the author", example = "johndoe")
        String username,

        @Schema(description = "URL of the author's avatar image", example = "https://example.com/avatar.jpg")
        String avatarUrl
) {}
