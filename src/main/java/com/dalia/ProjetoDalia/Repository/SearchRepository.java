package com.dalia.ProjetoDalia.Repository;

import com.dalia.ProjetoDalia.Entity.Users.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchRepository extends MongoRepository<Search, String> {
}