package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
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
    private final UsersServices usersServices;
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
    public String salvarPesquisa(@ModelAttribute("pregnancyMonitoring") PregnancyMonitoring pregnancyMonitoring, @RequestParam String idUser){
        System.out.println("ID RECEBIDO:" + idUser);
        Optional<Users> userOpt = usersRepository.findById(idUser);
        if(userOpt.isPresent()) {
            Users existUser = userOpt.get();
            UsersDTO dto = new UsersDTO(
                    existUser.getName(),
                    existUser.getSurname(),
                    existUser.getEmail(),
                    existUser.getPassword(),
                    existUser.getBirthDate(),
                    existUser.getSearch(),
                    pregnancyMonitoring
            );
            usersServices.updateUser(idUser, dto);
        }
        return "redirect:/homeGravidez";
    }
}
