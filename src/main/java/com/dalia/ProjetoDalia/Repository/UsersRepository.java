package com.dalia.ProjetoDalia.Repository;

import com.dalia.ProjetoDalia.Entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<Users, String> {


}
