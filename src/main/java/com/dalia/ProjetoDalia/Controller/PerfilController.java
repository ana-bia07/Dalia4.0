package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.Model.DTOS.Users.PregnancyMonitoringDTO;
import com.dalia.ProjetoDalia.Model.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Model.Entity.Users.Search;
import com.dalia.ProjetoDalia.Model.Entity.Comments;
import com.dalia.ProjetoDalia.Model.Entity.Users.Users;
import com.dalia.ProjetoDalia.Model.Repository.UsersRepository;
import com.dalia.ProjetoDalia.Services.EmailService;
import com.dalia.ProjetoDalia.Services.Users.PregnancyMonitoringService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class PerfilController {
    private final UsersRepository usersRepository;
    private EmailService emailService;
    private PregnancyMonitoringService pregnancyService;

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        String modo = (String) session.getAttribute("modoAtual");
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        Optional<PregnancyMonitoringDTO> dtoP = pregnancyService.getPregnancyByIdUser(idUser);

        if(dtoP.isPresent() == true){
            modo = "gravidez";
        } else {
            modo = "menstruação";
        }
        model.addAttribute("modoAtual", modo);

        Optional<Users> userOpt = usersRepository.findById(idUser);
        if (userOpt.isPresent()) {
            Users user = userOpt.get();
            if (user.getSearch() == null) user.setSearch(new Search());
            UsersDTO dto = new UsersDTO(user);
            model.addAttribute("userDTO", dto);
            return "perfilerrocalendar";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/Home/perfil")
    public String atualizarPerfil(@ModelAttribute("userDTO") UsersDTO userDTO, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        Optional<Users> userOriginalOpt = usersRepository.findById(idUser);
        if (userOriginalOpt.isPresent()) {
            Users user = userOriginalOpt.get();

            user.setName(userDTO.name());
            user.setSurname(userDTO.surname());
            user.setEmail(userDTO.email());
            if (userDTO.password() == null || userDTO.password().isBlank()) {
            } else {
                user.setPassword(userDTO.password());
            }
            if (user.getSearch() == null) {
                user.setSearch(new Search());
            }
            user.getSearch().setAge(userDTO.search().getAge());
            user.getSearch().setUseContraceptive(userDTO.search().isUseContraceptive());
            user.getSearch().setContraceptiveType(userDTO.search().getContraceptiveType());

            usersRepository.save(user);
            return "redirect:/perfil";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/enviar-denuncia")
    public String enviarDenuncia(@RequestParam("mensagem") String mensagem) {
    emailService.enviarDenuncia(mensagem);

    return "redirect:/perfil?denuncia=enviada";
    }
}
