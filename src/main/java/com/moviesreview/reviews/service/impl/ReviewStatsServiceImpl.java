package com.moviesreview.reviews.service.impl;

import com.moviesreview.reviews.dto.ReviewStatsDto;
import com.moviesreview.reviews.service.IReviewStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewStatsServiceImpl implements IReviewStatsService {

    private final MongoTemplate mongoTemplate;

    @Override
    public ReviewStatsDto getReviewStatsForMovie(String movieId) {
        ObjectId movieObjectId = new ObjectId(movieId);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("movie_id").is(movieObjectId)),
                Aggregation.group("movie_id")
                        .avg("rating").as("averageRating")
                        .count().as("reviewCount")
                        .sum(ConditionalOperators.when(Criteria.where("rating").ne(null))
                                .then(1).otherwise(0)).as("totalRating")
                        .sum(ConditionalOperators.when(Criteria.where("watchedDate").ne(null))
                                .then(1).otherwise(0)).as("watchedCount")
        );

        AggregationResults<ReviewStatsDto> results = mongoTemplate.aggregate(
                aggregation,"reviews", ReviewStatsDto.class
        );
        return results.getUniqueMappedResult() != null?
                results.getUniqueMappedResult()
                : new ReviewStatsDto(0.0,0,0,0);
    }
}
