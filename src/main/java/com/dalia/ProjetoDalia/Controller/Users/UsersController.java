package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@Tag(name = "Usuários")
@RequiredArgsConstructor //add para tirar a linha 26 do codigo
@RestController //fiz a alteração de controller para restcontroller que é mais recomendado. Deem uam pesquisada
public class UsersController {
    private final UsersServices usersServices;
    private final UsersRepository usersRepository;

    // public UsersController(UsersServices usersServices) {this.usersServices = usersServices;}  O @RequiredArgsConstructor faz isso automatico

    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Rota para criar um usuário")
    public ResponseEntity<Users> createUser(@RequestBody UsersDTO usersDTO){
        var created = usersServices.createUser(usersDTO);
        return ResponseEntity.created(URI.create("/usuarios/" + created.toString())).build();
    }

    @GetMapping("/{idUsers}")
    @Operation(summary = "Busca um usuário pelo Id", description = "Rota para buscar um usuário pelo Id")
    public ResponseEntity<Users> findById(@PathVariable String idUsers){
        var exist = usersServices.getById(idUsers);
        return exist.isPresent()
                ? ResponseEntity.ok(exist.get())
                : ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Busca todos os serviços", description = "Busca todos os serviços")
    public ResponseEntity<List<Users>> findAll(){
        var listServices = usersServices.getAll();
        return ResponseEntity.ok(listServices);
    }

    @DeleteMapping("/{idUsers}")
    @Operation(summary = "Deleta um usuário pelo Id", description = "Rota para Deletar um usuário pelo Id")
    public ResponseEntity<Void> deleteById(@PathVariable String idUsers){
        usersServices.delete(idUsers);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsers}")
    @Operation(summary = "Atualiza um usuário pelo Id", description = "Rota para atualizar um usuário pelo Id")
    public ResponseEntity<Users> updateUser(@PathVariable String idUsers, @RequestBody UsersDTO usersDTO) {
        Optional<Users> optionalUser = usersRepository.findById(idUsers);

        return optionalUser.map(existingUser -> {
            Optional<Users> updatedUser = updateUserFields(existingUser, usersDTO);
            return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<Users> updateUserFields(Users existingUser, UsersDTO usersDTO) {
        existingUser.setName(StringUtils.hasText(usersDTO.name())
                ? usersDTO.name()
                : existingUser.getName());

        existingUser.setSurname(StringUtils.hasText(usersDTO.surname())
                ? usersDTO.surname()
                : existingUser.getSurname());

        existingUser.setEmail(StringUtils.hasText(usersDTO.email())
                ? usersDTO.email()
                : existingUser.getEmail());

        existingUser.setPassword(StringUtils.hasText(usersDTO.password())
                ? usersDTO.password()
                : existingUser.getPassword());

        existingUser.setBirthDate(usersDTO.birthdate() != null
                ? usersDTO.birthdate()
                : existingUser.getBirthDate());

        existingUser.setSearch(usersDTO.search() != null
                ? usersDTO.search()
                : existingUser.getSearch());

        existingUser.setPregnancyMonitoring(usersDTO.pregnancyMonitoring() != null
                ? usersDTO.pregnancyMonitoring()
                : existingUser.getPregnancyMonitoring());

        usersRepository.save(existingUser);
        return Optional.of(existingUser);
    }

}
