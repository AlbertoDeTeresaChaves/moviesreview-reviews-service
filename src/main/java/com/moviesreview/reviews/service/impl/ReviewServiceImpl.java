package com.moviesreview.reviews.service.impl;

import com.moviesreview.reviews.dto.ReviewRequestDto;
import com.moviesreview.reviews.dto.ReviewResponseDto;
import com.moviesreview.reviews.exceptions.ResourceNotFoundException;
import com.moviesreview.reviews.mapper.ReviewMapper;
import com.moviesreview.reviews.model.Author;
import com.moviesreview.reviews.model.MovieSummary;
import com.moviesreview.reviews.model.Review;
import com.moviesreview.reviews.repository.ReviewsRepository;
import com.moviesreview.reviews.service.IReviewService;
import com.moviesreview.reviews.service.gateway.ReviewDataProvider;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements IReviewService {

    private final ReviewsRepository reviewsRepository;
    private final ReviewDataProvider reviewDataProvider;

    @Override
    public ReviewResponseDto createReview(ReviewRequestDto requestDto) {
        log.info("Trying to create review with the account {}",requestDto.accountId());

        validateAccountHasNotReviewedMovie(requestDto.accountId(),requestDto.movieId());

        Author author = reviewDataProvider.getAuthor(UUID.fromString(requestDto.accountId()));
        MovieSummary movieSummary = reviewDataProvider.getMovie(requestDto.movieId());

        Review newReview = ReviewMapper.toEntity(requestDto,author,movieSummary);
        Review savedReview = reviewsRepository.save(newReview);

        log.info("Review successfully saved with ID: {}", savedReview.getId());
        return ReviewMapper.toDto(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> fetchReviewsByMovie(String slug, Pageable pageable) {
        log.info("Trying to fetch all reviews of the movie {}",slug);
        Page<Review> reviewPage = reviewsRepository.findByMovieSlug(slug,pageable);

        log.info("Page of reviews successfully obtained of the movie {}",slug);
        return reviewPage.map(ReviewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto fetchReview(String username, String slug) {
        log.info("Trying to fetch review for user {} in the movie {}",username,slug);
        return reviewsRepository.findByAuthorUsernameAndMovieSlug(username,slug)
                .map(ReviewMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review","username and slug",username + "," + slug)
                );
    }


    @Override
    public ReviewResponseDto updateReview(String reviewId, ReviewRequestDto requestDto) {
        log.info("Trying to update review  {} ",reviewId);

        Review oldReview = reviewsRepository.findById(reviewId).orElseThrow(
                ()-> new ResourceNotFoundException("Review","reviewId",reviewId)
        );

        oldReview.update(requestDto.review(),requestDto.rating().doubleValue() / 2,requestDto.watchedDate());

        Review updatedReview = reviewsRepository.save(oldReview);
        log.info("Review successfully updated with the id {} ",reviewId);

        return ReviewMapper.toDto(updatedReview);
    }

    @Override
    public void deleteReview(String reviewId) {
        log.info("Trying to delete review with the id {}",reviewId);
        Review review = reviewsRepository.findById(reviewId).orElseThrow(
                () -> new ResourceNotFoundException("Review","reviewId",reviewId)
        );

        reviewsRepository.delete(review);
        log.info("Review successfully deleted with the id {} ",reviewId);
    }

    private void validateAccountHasNotReviewedMovie(String accountId, String movieId){
        if(reviewsRepository.existsByAccountIdAndMovieId(accountId, movieId)){
            throw new DuplicateRequestException("The account already write the review of this movie");
        }
    }
}
