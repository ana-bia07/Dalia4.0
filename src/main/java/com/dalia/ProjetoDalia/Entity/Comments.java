package com.dalia.ProjetoDalia.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments {
    private String idUsers;
    private String comment;
    private Instant createdAt;
}
