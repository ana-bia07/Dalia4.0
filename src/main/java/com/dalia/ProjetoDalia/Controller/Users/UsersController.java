package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Entity.Users.Search;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.SessionTrackingMode;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Usuários")
@Controller
public class UsersController {

    private final UsersServices usersServices;
    private final UsersRepository usersRepository;

    public UsersController(UsersServices usersServices, UsersRepository usersRepository) {
        this.usersServices = usersServices;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String redirectToLandingPage() {
        return "redirect:/LandingPage/LandingPage.html";
    }

    @GetMapping("/users")
    @Operation(summary = "Busca todos os usuários", description = "Busca todos os usuários")
    public String findAll(Model model) {
        try {
            model.addAttribute("users", usersServices.getAll());
            return "users/list";
        } catch (Exception e) {
            model.addAttribute("error", "Erro ao buscar usuários: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }


    @PostMapping("/criaUsuario")
    @Operation(summary = "Cria um usuário", description = "Rota para criar um usuário via formulário HTML")
    public String createUserForm(@RequestParam String name, @RequestParam String surname,
                                 @RequestParam String email, @RequestParam String password,
                                 @RequestParam(required = false) String passconfirmation, Model model,
                                 HttpSession session) {

        if (!password.equals(passconfirmation)) {
            model.addAttribute("error", "As senhas não coincidem.");
            return "cadastro";
        }

        Users user = new Users();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthDate(null);
        user.setSearch(new Search());
        user.setPregnancyMonitoring(new PregnancyMonitoring());

        usersRepository.save(user);

        session.setAttribute("idUser", user.getId());
        return "redirect:/search";
    }

    @GetMapping("/login")
    public String MostraLogin(){
        return "Login";
    }

    @PostMapping("/RealizaLogin")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession Session) {
        Optional<Users> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                Session.setAttribute("idUser", user.getId());
                model.addAttribute("user", user);
                return "redirect:/home";
            }
        }
        model.addAttribute("error", "E-mail ou senha inválidos!");
        return "Login";
    }

    @GetMapping("/search")
    public String mostrarFormulario(Model model,HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");

        if(idUser == null) {
            return "redirect:/cadastro";
        }

        model.addAttribute("idUser", idUser);
        model.addAttribute("search", new Search());
        return "perguntas"; // Nome do arquivo Thymeleaf: resources/templates/pesquisa.html
    }

    @PostMapping("/salvar-respostas")
    public String processarFormulario(@ModelAttribute("search") Search search, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        Optional<Users> userOpt = usersRepository.findById(idUser);
        if (userOpt.isPresent()) {
            Users existingUser = userOpt.get();
            UsersDTO dto = new UsersDTO(
                    existingUser.getName(),
                    existingUser.getSurname(),
                    existingUser.getEmail(),
                    existingUser.getPassword(),
                    existingUser.getBirthDate(),
                    search,
                    existingUser.getPregnancyMonitoring()
            );
            usersServices.updateUser(idUser, dto);
            session.setAttribute("search", search);
        }
        return "redirect:/home";
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
        if (usersDTO.pregnancyMonitoring() != null) existingUser.setPregnancyMonitoring(usersDTO.pregnancyMonitoring());
    }
}