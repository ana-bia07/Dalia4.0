package com.dalia.ProjetoDalia.Services.Posts;

import com.dalia.ProjetoDalia.Entity.Comments;
import com.dalia.ProjetoDalia.Entity.Posts;
import com.dalia.ProjetoDalia.Repository.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CommentsService {

    @Autowired
    private PostsRepository postsRepository;

    public Optional<Posts> addComment(String postId, Comments comment) {
        Optional<Posts> postOpt = postsRepository.findById(postId);
        if (postOpt.isPresent()) {
            Posts post = postOpt.get();

            if (post.getComments() == null) {
                post.setComments(new ArrayList<>());
            }

            post.getComments().add(comment);
            postsRepository.save(post);
            return Optional.of(post);
        }
        return Optional.empty();
    }
}
