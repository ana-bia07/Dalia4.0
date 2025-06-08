package com.dalia.ProjetoDalia.DTOS.Posts;

import com.dalia.ProjetoDalia.Entity.Comments;
import com.dalia.ProjetoDalia.Entity.Posts;

import java.time.Instant;

public record CommentsDTO(
        String idUsers,
        String comment,
        Instant createdAt
){
    public Comments toEntity() {
        return new Comments(
                idUsers,
                comment,
                createdAt);

    }
}