package com.dalia.ProjetoDalia.Controller.Posts;

import com.dalia.ProjetoDalia.DTOS.Posts.CommentsDTO;
import com.dalia.ProjetoDalia.Entity.Posts;
import com.dalia.ProjetoDalia.Services.Posts.CommentsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "Comments")
@RequestMapping("/comments")
@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping("/{idPosts}")
    public ResponseEntity<String> addCommentToPost(@PathVariable String idPosts, @RequestBody CommentsDTO commentsDTO) {
        commentsService.addComment(idPosts, commentsDTO.toEntity());
        return ResponseEntity.created(URI.create("/comments/" + idPosts)).body("Comentário adicionado com sucesso.");
    }
}
