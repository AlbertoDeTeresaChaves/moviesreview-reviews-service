package com.moviesreview.reviews.controller;

import com.moviesreview.reviews.dto.ErrorResponseDto;
import com.moviesreview.reviews.dto.ReviewRequestDto;
import com.moviesreview.reviews.dto.ReviewResponseDto;
import com.moviesreview.reviews.dto.ReviewStatsDto;
import com.moviesreview.reviews.service.IReviewService;
import com.moviesreview.reviews.service.IReviewStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "CRUD REST API for Reviews Microservice")
@RestController
@RequestMapping(path = "/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewsService;
    private final IReviewStatsService reviewStatsService;

    @Operation(summary = "Create a new review", description = "Creates a review for a movie by an account/user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review successfully created",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error in input fields",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto createdReview = reviewsService.createReview(reviewRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @Operation(summary = "Get reviews by movie Slug", description = "Retrieves a paginated list of reviews associated with a specific movie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/movies/{slug}")
    public ResponseEntity<Page<ReviewResponseDto>> fetchReviewByMovie(@PathVariable String slug, Pageable pageable){
        Page<ReviewResponseDto> reviewsPage = reviewsService.fetchReviewsByMovie(slug, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(reviewsPage);
    }

    @Operation(summary = "Get a specific review by account and movie", description = "Retrieves a single review written by a specific user account for a specific movie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review successfully retrieved",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/user/{username}/movie/{slug}")
    public ResponseEntity<ReviewResponseDto> fetchReview(@PathVariable String username, @PathVariable String slug){
        ReviewResponseDto review = reviewsService.fetchReview(username, slug);
        return ResponseEntity.status(HttpStatus.OK).body(review);
    }

    @Operation(summary = "Get review stats of movie", description = "Retrieves the review stats for a specific movie.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review stats successfully retrieved",
                    content = @Content(schema = @Schema(implementation = ReviewStatsDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/movies/{movieId}/stats")
    public ResponseEntity<ReviewStatsDto> fetchReviewStats(@PathVariable String movieId){
        ReviewStatsDto reviewStats = reviewStatsService.getReviewStatsForMovie(movieId);

        return ResponseEntity.status(HttpStatus.OK).body(reviewStats);
    }


    @Operation(summary = "Update an existing review", description = "Updates the contents, rating, or watched date of a review by its ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review successfully updated",
                    content = @Content(schema = @Schema(implementation = ReviewResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Validation error in input fields",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable String reviewId, @Valid @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto updatedReview = reviewsService.updateReview(reviewId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReview);
    }

    @Operation(summary = "Delete a review", description = "Deletes a review by its unique review ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Review not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable String reviewId){
        reviewsService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
