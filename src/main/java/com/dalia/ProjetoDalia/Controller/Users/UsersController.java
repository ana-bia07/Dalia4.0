package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.Model.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Model.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Model.Entity.Users.Search;
import com.dalia.ProjetoDalia.Model.Entity.Users.Users;
import com.dalia.ProjetoDalia.Services.Users.SearchService;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Usuários")
@Controller
public class UsersController {

    private final UsersServices usersService;
    private final SearchService searchService;

    public UsersController(UsersServices usersServices, SearchService searchService) {
        this.usersService = usersServices;
        this.searchService = searchService;

    }

    @GetMapping("/")
    public String redirectToLandingPage() {
        return "redirect:/LandingPage/LandingPage.html";
    }

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("users", new UsersDTO(null, null, null, null, null, null, null));
        return "cadastro";
    }


    @PostMapping("/criarUsuario")
    @Operation(summary = "Cria um usuário", description = "Rota para criar um usuário via formulário HTML")
    public String createUserForm(@ModelAttribute("users") UsersDTO user, @RequestParam(required = false) String passconfirmation, HttpSession session ,Model model) {
        if (!user.password().equals(passconfirmation)) {
            model.addAttribute("error", "As senhas não coincidem.");
            return "cadastro";
        }

        try {
            UsersDTO newUser = usersService.createUser(user);

            session.setAttribute("idUser",newUser.toEntity().getId());
            return "redirect:/search";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/login")
    public String MostraLogin(){
        return "Login";
    }

    @PostMapping("/RealizaLogin")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession Session) {
        Optional<UsersDTO> optionalUser = usersService.getByEmail(email);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get().toEntity();
            if (user.getPassword().equals(password)) {
                Session.setAttribute("idUser", user.getId());

                if (user.getSearch() != null) {
                    Session.setAttribute("search", user.getSearch());
                }
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
        return "perguntas";
    }

    @PostMapping("/salvar-respostas")
    public String processarFormulario(@ModelAttribute("search") SearchDTO search, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        searchService.saveOrUpdateSearchForUser(idUser, search);
        session.setAttribute("search", search.toEntity());

        return "redirect:/home";
    }
}