package com.moviesreview.reviews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovieSummary {
    private String slug;
    private String title;
    private Integer releaseYear;
    private String posterUrl;
}
