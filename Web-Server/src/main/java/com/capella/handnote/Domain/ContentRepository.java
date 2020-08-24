package com.capella.handnote.Domain;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ContentRepository extends MongoRepository<Content, String> {
//    Content findByUserId(String userId);
    // ?0를 넣으면 String 인자가 들어가게 된다.
    @Query("{'userId' : ?0}")
    List<Content> findAllByUserId(String userId);
}
