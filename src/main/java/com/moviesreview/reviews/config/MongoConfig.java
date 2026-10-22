package com.moviesreview.reviews.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
@RequiredArgsConstructor
public class MongoConfig {

    private final MappingMongoConverter converter;

    @PostConstruct
    void removeClassField(){
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }

}
