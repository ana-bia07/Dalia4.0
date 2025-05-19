package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users.Search;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.SearchRepository;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Tag(name = "Usuários")
@Controller
public class UsersController {

    private final UsersServices usersServices;
    private final UsersRepository usersRepository;
    private final SearchRepository searchRepository;

    public UsersController(UsersServices usersServices, UsersRepository usersRepository, SearchRepository searchRepository) {
        this.usersServices = usersServices;
        this.usersRepository = usersRepository;
        this.searchRepository = searchRepository;
    }

    @GetMapping("/")
    public String redirectToLandingPage() {
        return "redirect:/LandingPage/LandingPage.html"; // Redireciona para a landing page
    }

    @GetMapping("/users")
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public String findAll(Model model) {
        try {
            model.addAttribute("users", usersServices.getAll());
            return "users/list"; // Exemplo de página Thymeleaf com lista de usuários
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao buscar usuários: " + e.getMessage());
            return "error"; // Exemplo de página de erro
        }
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro/cadastro";
    }


    @PostMapping("/criaUsuario")
    @Operation(summary = "Cria um usuário", description = "Rota para criar um usuário via formulário HTML")
    public String createUserForm(@RequestParam String name, @RequestParam String surname,
                                 @RequestParam String email, @RequestParam String password,
                                 @RequestParam(required = false) String passconfirmation, Model model) {

        if (!password.equals(passconfirmation)) {
            model.addAttribute("error", "As senhas não coincidem.");
            return "cadastro"; // Retorna à página de cadastro se as senhas não coincidirem
        }

        Users user = new Users();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);

        // Defina valores default para os campos opcionais
        user.setBirthDate(null);
        user.setSearch(Collections.emptyList());
        user.setPregnancyMonitorings(Collections.emptyList());

        usersRepository.save(user);

        // Redireciona para a página de login após o cadastro bem-sucedido
        return "redirect:/search"; // Ajuste a URL de redirecionamento
    }

    @GetMapping("/login")
    public String MostraLogin(){
        return "Login/Login";
    }

    @PostMapping("/RealizaLogin")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                model.addAttribute("user", user);
                return "redirect:/Home/index.html";  // Página inicial após login
            }
        }
        model.addAttribute("error", "E-mail ou senha inválidos!");
        return "Login/Login";  // Retorna para a página de login
    }

    @GetMapping("/search")
    public String mostrarFormulario(Model model) {
        model.addAttribute("search", new Search());
        return "perguntas"; // Nome do arquivo Thymeleaf: resources/templates/pesquisa.html
    }

    @PostMapping("/salvar-respostas")
    public String processarFormulario(@ModelAttribute("search") Search search) {
        searchRepository.save(search);
        return "redirect:/Home/home.html"; // Redireciona para a home após salvar
    }

    @GetMapping("/{idUsers}")
    @Operation(summary = "Busca um usuário pelo Id", description = "Rota para buscar um usuário pelo Id")
    public ResponseEntity<Users> findById(@PathVariable String idUsers) {
        Optional<Users> exist = usersServices.getById(idUsers);
        return exist.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idUsers}")
    @Operation(summary = "Deleta um usuário pelo Id", description = "Rota para deletar um usuário pelo Id")
    public ResponseEntity<Object> deleteById(@PathVariable String idUsers) {
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
        Optional<Users> optionalUser = usersRepository.findById(idUsers);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Users existingUser = optionalUser.get();
        updateUserFields(existingUser, usersDTO);
        usersRepository.save(existingUser);
        return ResponseEntity.ok(existingUser);
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
