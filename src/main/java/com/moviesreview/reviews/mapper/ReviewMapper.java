package com.moviesreview.reviews.mapper;

import com.moviesreview.reviews.dto.AuthorDto;
import com.moviesreview.reviews.dto.MovieSummaryDto;
import com.moviesreview.reviews.dto.ReviewRequestDto;
import com.moviesreview.reviews.dto.ReviewResponseDto;
import com.moviesreview.reviews.model.Author;
import com.moviesreview.reviews.model.MovieSummary;
import com.moviesreview.reviews.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public static ReviewResponseDto toDto (Review review){
        return new ReviewResponseDto(
                review.getId(),
                review.getAccountId(),
                review.getMovieId(),
                AuthorToDto(review.getAuthor()),
                MovieSummaryToDto(review.getMovie()),
                review.getReview(),
                review.getRating(),
                review.getWatchedDate()
        );
    }

    public static Review toEntity (ReviewRequestDto requestDto,Author author, MovieSummary movieSummary){
        return Review.builder()
                .accountId(requestDto.accountId())
                .movieId(requestDto.movieId())
                .author(author)
                .movie(movieSummary)
                .review(requestDto.review())
                .rating(requestDto.rating().doubleValue() /2)
                .watchedDate(requestDto.watchedDate()).build();
    }

    public static AuthorDto AuthorToDto (Author author){
        return new AuthorDto(
                author.getUsername(),
                author.getAvatarUrl()
        );
    }

    public static MovieSummaryDto MovieSummaryToDto (MovieSummary movie){
        return new MovieSummaryDto(
                movie.getSlug(),
                movie.getTitle(),
                movie.getReleaseYear(),
                movie.getPosterUrl()
        );
    }
}
