package com.dalia.ProjetoDalia.Controller.Posts;

import com.dalia.ProjetoDalia.DTOS.Posts.PostsDTO;
import com.dalia.ProjetoDalia.Entity.Posts;
import com.dalia.ProjetoDalia.Services.Posts.PostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Posts")
@RequestMapping("/posts")
@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostsDTO postsDTO) {
        Posts createdPost = postsService.createPost(postsDTO.toEntity());
        return ResponseEntity.created(URI.create("/posts/" + createdPost.getId())).body("Post criado com sucesso.");
    }

    @GetMapping("/{idPosts}")
    public ResponseEntity<PostsDTO> getPost(@PathVariable String idPosts) {
        return postsService.getPostById(idPosts)
                .map(post -> ResponseEntity.ok(new PostsDTO(post.getIdUsers(), post.getTitle(), post.getContent(), post.getLikes(), post.getCreatedAt(), post.getComments())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PostsDTO>> getAllPosts() {
        List<PostsDTO> postsDTOs = postsService.getAllPosts().stream()
                .map(post -> new PostsDTO(post.getIdUsers(), post.getTitle(), post.getContent(), post.getLikes(), post.getCreatedAt(), post.getComments()))
                .toList();
        return ResponseEntity.ok(postsDTOs);
    }

    @PutMapping("/{idPosts}")
    public ResponseEntity<PostsDTO> updatePost(@PathVariable String idPosts, @RequestBody PostsDTO postsDTO) {
        return postsService.updatePost(idPosts, postsDTO.toEntity())
                .map(updatedPost -> ResponseEntity.ok(new PostsDTO(updatedPost.getIdUsers(), updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getLikes(), updatedPost.getCreatedAt(), updatedPost.getComments())))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idPosts}")
    public ResponseEntity<Void> deletePost(@PathVariable String idPosts) {
        postsService.deletePost(idPosts);
        return ResponseEntity.noContent().build();
    }
}
