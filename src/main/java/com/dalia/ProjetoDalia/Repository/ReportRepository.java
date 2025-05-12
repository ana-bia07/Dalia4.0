package com.dalia.ProjetoDalia.Repository;

import com.dalia.ProjetoDalia.Entity.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    List<Report> findByIdUsers(String idUsers);
}
