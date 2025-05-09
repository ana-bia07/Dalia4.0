package com.dalia.ProjetoDalia.Repository;

import com.dalia.ProjetoDalia.Entity.Users.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {
    @Query("{ 'email' : ?0 }") // Busca o usuário pelo e-mail no MongoDB
    Optional<Users> findByEmail(String email);
}


