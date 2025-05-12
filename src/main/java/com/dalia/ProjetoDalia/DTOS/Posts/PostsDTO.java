package com.dalia.ProjetoDalia.DTOS.Posts;

import com.dalia.ProjetoDalia.Entity.Comments;
import com.dalia.ProjetoDalia.Entity.Posts;

import java.time.Instant;
import java.util.List;

public record PostsDTO (
    String idUsers,
    String title,
    String content,
    int likes,
    Instant createdAt,
    List<Comments> comments
) {
    public Posts toEntity() {
        return new Posts(
                null,
                idUsers,
                title,
                content,
                likes,
                createdAt,
                comments);
    }
}
