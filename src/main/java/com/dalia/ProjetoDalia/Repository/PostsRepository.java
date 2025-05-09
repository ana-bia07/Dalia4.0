package com.dalia.ProjetoDalia.Repository;

import com.dalia.ProjetoDalia.Entity.Posts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<Posts, String> {


}