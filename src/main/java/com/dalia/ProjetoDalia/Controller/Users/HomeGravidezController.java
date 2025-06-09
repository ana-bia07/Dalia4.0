package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.DTOS.Users.PregnancyMonitoringDTO;
import com.dalia.ProjetoDalia.Services.Users.PregnancyMonitoringService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class HomeGravidezController {

    private final PregnancyMonitoringService pregnancyService;

    @GetMapping("/homeGravidez")
    public String showPregnancyPage(Model model, HttpSession session) {
        String idUser = (String) session.getAttribute("idUser");
        if (idUser == null) {
            return "redirect:/login";
        }

        PregnancyMonitoringDTO dto = pregnancyService.getPregnancyByidUsers(idUser).orElse(new PregnancyMonitoringDTO());
        model.addAttribute("pregnancy", dto);
        model.addAttribute("idUser", idUser);

        // Para mostrar a lista de consultas como string separada por vírgulas (para formulário)
        if (dto.getConsultations() != null && !dto.getConsultations().isEmpty()) {
            String consultationsString = String.join(", ", dto.getConsultations());
            model.addAttribute("consultationsString", consultationsString);
        } else {
            model.addAttribute("consultationsString", "");
        }

        return "gravidez";
    }

    @PostMapping("/save")
    public String savePregnancy(
            @RequestParam String idUser,
            @ModelAttribute("pregnancy") PregnancyMonitoringDTO dto,
            @RequestParam(value = "consultationsString", required = false) String consultationsString) {

        // Converte string separada por vírgulas para List<String>
        if (consultationsString != null && !consultationsString.isBlank()) {
            List<String> consultations = Arrays.stream(consultationsString.split(","))
                    .map(String::trim)
                    .toList();
            dto.setConsultations(consultations);
        } else {
            dto.setConsultations(List.of());
        }

        pregnancyService.updatePregnancy(idUser, dto);

        return "redirect:/Gravidez/homeGravidez";
    }
}
