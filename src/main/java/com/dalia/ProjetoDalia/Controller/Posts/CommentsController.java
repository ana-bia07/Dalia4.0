package com.dalia.ProjetoDalia.Controller.Posts;

import com.dalia.ProjetoDalia.DTOS.Posts.CommentsDTO;
import com.dalia.ProjetoDalia.Entity.Comments;
import com.dalia.ProjetoDalia.Services.Posts.CommentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Comments")
@RequestMapping("/posts/{postId}/comments")
@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<String> addCommentToPost(@PathVariable String postId, @RequestBody CommentsDTO commentsDTO) {
        Comments comment = commentsDTO.toEntity();
        commentsService.addComment(postId, comment);
        return ResponseEntity.created(URI.create("/posts/" + postId + "/comments"))
                .body("Comentário adicionado com sucesso.");
    }


    @GetMapping
    public ResponseEntity<List<Comments>> getCommentsByPostId(@PathVariable String postId) {
        List<Comments> comments = commentsService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
