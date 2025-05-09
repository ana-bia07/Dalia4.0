package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Entity.Search;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.SearchService;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name="Search")
@RequestMapping("/searcher")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final UsersRepository usersRepository;
    private final UsersServices usersServices;

    @PostMapping("/{idUsers}")
    public ResponseEntity<String> createSearch(@PathVariable String idUsers, @RequestBody SearchDTO searchData){
        String message = searchService.createSearch(idUsers, searchData);
        return ResponseEntity.created(URI.create("/searcher/" + idUsers)).body(message);
    }

    @GetMapping("/{idUsers}")
    public ResponseEntity<SearchDTO> getSearch(@PathVariable String idUsers){
        return searchService.getSearchByidUsers(idUsers)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idUsers}")
    public ResponseEntity<Search> deleteById(@PathVariable String idUsers){
        usersServices.delete(idUsers);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsers}")
    public ResponseEntity<SearchDTO> updateSearch(@PathVariable String idUsers, @RequestBody SearchDTO searchData){
        var update = searchService.updateSearch(idUsers, searchData);
        return update.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
