package com.dalia.ProjetoDalia.Model.Repository;

import com.dalia.ProjetoDalia.Model.Entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Comments.Users, String> {

    Optional<Comments.Users> findByEmail(String email);

    Optional<Comments.Users> findByUsername(String username);

    Optional<Comments.Users> findById(String id);
}


