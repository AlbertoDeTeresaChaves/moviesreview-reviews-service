package com.moviesreview.reviews.service.gateway;

import com.moviesreview.reviews.dto.AuthorDto;
import com.moviesreview.reviews.dto.MovieSummaryDto;
import com.moviesreview.reviews.exceptions.ResourceNotFoundException;
import com.moviesreview.reviews.model.Author;
import com.moviesreview.reviews.model.MovieSummary;
import com.moviesreview.reviews.service.client.AccountsClient;
import com.moviesreview.reviews.service.client.MoviesClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeignReviewDataProvider implements ReviewDataProvider{

    private final AccountsClient accountsClient;
    private final MoviesClient moviesClient;

    @Override
    public Author getAuthor(UUID accountId) {
        AuthorDto authorDto = accountsClient.getAccount(accountId).orElseThrow(
                () -> new ResourceNotFoundException("AccountClient","accountId",accountId.toString())
        );

        return Author.builder()
                .username(authorDto.username())
                .avatarUrl(authorDto.avatarUrl()).build();
    }

    @Override
    public MovieSummary getMovie(String movieId) {
        MovieSummaryDto movieSummaryDto = moviesClient.getMovie(movieId).orElseThrow(
                () -> new ResourceNotFoundException("MovieClient","movieId",movieId)
        );
        return MovieSummary.builder()
                .slug(movieSummaryDto.slug())
                .title(movieSummaryDto.title())
                .releaseYear(movieSummaryDto.releaseYear())
                .posterUrl(movieSummaryDto.posterUrl())
                .build();
    }
}
