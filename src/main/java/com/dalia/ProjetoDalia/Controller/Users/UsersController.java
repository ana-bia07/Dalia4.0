package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@Tag(name = "Usuários")
@RestController
public class UsersController {
    private final UsersServices usersServices;
    private final UsersRepository usersRepository;

    public UsersController(UsersServices usersServices, UsersRepository usersRepository) {
        this.usersServices = usersServices;
        this.usersRepository = usersRepository;
    }

    @Controller
    public class LandingPageController {

        @GetMapping("/")
        public String redirectToLandingPage() {
            return "redirect:/LandingPage/LandingPage.html"; // Redireciona para a landing page
        }


    }


    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Rota para criar um usuário")
    public ResponseEntity<Object> createUser(@RequestBody UsersDTO usersDTO){
        try {
            var created = usersServices.createUser(usersDTO);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Usuário criado com sucesso!");

            return ResponseEntity.created(URI.create("/users/" + created.toString())).body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Erro ao criar usuário: " + e.getMessage()));
        }
    }

    @PostMapping("/Login/Login.html")
    @Operation(summary = "Realiza login", description = "Autenticação do usuário")
    public ResponseEntity<Object> loginUser(@RequestBody UsersDTO usersDTO) {
        try {
            Optional<Users> user = usersRepository.findByEmail(usersDTO.email());

            if (user.isEmpty() || !user.get().getPassword().equals(usersDTO.password())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "E-mail ou senha inválidos"));
            }

            return ResponseEntity.ok(Map.of("message", "Login bem-sucedido", "userId", user.get().getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Erro ao processar login"));
        }
    }

    @GetMapping("/{idUsers}")
    @Operation(summary = "Busca um usuário pelo Id", description = "Rota para buscar um usuário pelo Id")
    public ResponseEntity<Users> findById(@PathVariable String idUsers){
        try {
            Optional<Users> exist = usersServices.getById(idUsers); // Busca o usuário no serviço
            // Verifica se o usuário foi encontrado e retorna ele, senão retorna Not Found
            return exist.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Caso ocorra algum erro, retorna uma mensagem de erro
            return ResponseEntity.badRequest().body(null); // Pode também retornar null ou um objeto de erro.
        }
    }
    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public ResponseEntity<Object> findAll(){
        try {
            var listUsers = usersServices.getAll();
            return ResponseEntity.ok(listUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    @DeleteMapping("/{idUsers}")
    @Operation(summary = "Deleta um usuário pelo Id", description = "Rota para deletar um usuário pelo Id")
    public ResponseEntity<Object> deleteById(@PathVariable String idUsers){
        try {
            usersServices.delete(idUsers);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{idUsers}")
    @Operation(summary = "Atualiza um usuário pelo Id", description = "Rota para atualizar um usuário pelo Id")
    public ResponseEntity<Object> updateUser(@PathVariable String idUsers, @RequestBody UsersDTO usersDTO) {
        try {
            Optional<Users> optionalUser = usersRepository.findById(idUsers);

            if (optionalUser.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Users existingUser = optionalUser.get();
            updateUserFields(existingUser, usersDTO);
            usersRepository.save(existingUser);

            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    private void updateUserFields(Users existingUser, UsersDTO usersDTO) {
        if (StringUtils.hasText(usersDTO.name())) existingUser.setName(usersDTO.name());
        if (StringUtils.hasText(usersDTO.surname())) existingUser.setSurname(usersDTO.surname());
        if (StringUtils.hasText(usersDTO.email())) existingUser.setEmail(usersDTO.email());
        if (StringUtils.hasText(usersDTO.password())) existingUser.setPassword(usersDTO.password());
        if (usersDTO.birthdate() != null) existingUser.setBirthDate(usersDTO.birthdate());
        if (usersDTO.search() != null) existingUser.setSearch(usersDTO.search());
        if (usersDTO.pregnancyMonitorings() != null) existingUser.setPregnancyMonitorings(usersDTO.pregnancyMonitorings());
    }
}
