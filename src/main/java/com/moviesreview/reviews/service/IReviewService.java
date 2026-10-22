package com.moviesreview.reviews.service;


import com.moviesreview.reviews.dto.ReviewRequestDto;
import com.moviesreview.reviews.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReviewService {
    ReviewResponseDto createReview(ReviewRequestDto requestDto);
    Page<ReviewResponseDto> fetchReviewsByMovie(String movieId, Pageable pageable);
    ReviewResponseDto fetchReview(String accountId,String movieId);
    ReviewResponseDto updateReview(String reviewId, ReviewRequestDto requestDto);
    void deleteReview(String reviewId);

}
