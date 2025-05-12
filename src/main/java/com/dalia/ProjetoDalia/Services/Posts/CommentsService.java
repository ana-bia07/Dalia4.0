package com.dalia.ProjetoDalia.Services.Posts;

import com.dalia.ProjetoDalia.Entity.Comments;
import com.dalia.ProjetoDalia.Entity.Posts;
import com.dalia.ProjetoDalia.Repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final PostsRepository postsRepository;

    public Optional<Posts> addComment(String idPosts, Comments comment) {
        return postsRepository.findById(idPosts)
                .map(post -> {
                    post.getComments().add(comment);
                    return postsRepository.save(post);
                });
    }
}
