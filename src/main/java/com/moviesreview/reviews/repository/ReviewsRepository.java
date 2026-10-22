package com.moviesreview.reviews.repository;

import com.moviesreview.reviews.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ReviewsRepository extends MongoRepository<Review,String> {

    boolean existsByAccountIdAndMovieId(String accountId, String movieId);
    Optional<Review> findByAuthorUsernameAndMovieSlug(String accountId, String slug);
    Page<Review> findByMovieSlug(String slug, Pageable pageable);

}
