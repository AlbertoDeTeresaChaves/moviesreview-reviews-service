package com.moviesreview.reviews.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.Instant;


@Document(collection = "reviews")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Review {

    @Id
    private String id;

    @Indexed
    @Field("account_id")
    private String accountId;

    @Field(name = "movie_id", targetType = FieldType.OBJECT_ID)
    private String movieId;
    private Author author;
    private MovieSummary movie;
    private String review;
    private Double rating;
    private Instant watchedDate;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;

    public void update(String newReviewText,Double newRating,Instant newWatchedDate){
        this.review = newReviewText;
        this.rating = newRating;
        this.watchedDate = newWatchedDate;
    }
}
