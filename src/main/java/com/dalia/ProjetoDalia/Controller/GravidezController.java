package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.Model.DTOS.Users.PregnancyMonitoringDTO;
import com.dalia.ProjetoDalia.Model.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Model.Entity.Comments;
import com.dalia.ProjetoDalia.Model.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Model.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class GravidezController {
    private final UsersServices usersService;
    private final UsersRepository usersRepository;

    @GetMapping("/Gravidez/pesquisa")
    public String mostrarPesquisa(Model model, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");

        if(idUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("idUser", idUser);
        model.addAttribute("pregnancyMonitoring", new PregnancyMonitoring());
        return "/pesquisagravidez";
    }

    @PostMapping("/salvar-pesquisa")
    public String salvarPesquisa(@ModelAttribute("pregnancyMonitoring") PregnancyMonitoringDTO pregnancyMonitoring, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        Optional<UsersDTO> userOpt = usersService.getUserById(idUser);
        if(userOpt.isPresent()) {
            Comments.Users existingUser = userOpt.get().toEntity();
            UsersDTO dto = new UsersDTO(
                    existingUser.getId(),
                    existingUser.getName(),
                    existingUser.getSurname(),
                    existingUser.getEmail(),
                    existingUser.getPassword(),
                    existingUser.getSearch(),
                    pregnancyMonitoring.toEntity()
            );
            usersService.updateUser(idUser, dto);
            session.setAttribute("pregnancy", pregnancyMonitoring);
        }
        return "redirect:/homeGravidez";
    }
}
