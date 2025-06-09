package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Services.Users.SearchService;
import com.dalia.ProjetoDalia.Services.Users.UsersServices;
import com.mongodb.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final SearchService searchService;
    private final UsersServices userService;

    @GetMapping("/home")
    public String homePage(Model model, @Nullable Principal principal) {
        if (principal == null) {
            model.addAttribute("userId", "visitante");
            return "index";
        }

        Users usuario = userService.getByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        model.addAttribute("userId", usuario.getId());
        return "index";
    }

    @ResponseBody
    @GetMapping("/api/ciclo5dias-home/{idUser}")
    public List<Map<String, String>> getEventosCiclo5Dias(@PathVariable String idUser) {
        Optional<SearchDTO> searchOpt = searchService.getSearchByidUsers(idUser);
        if (searchOpt.isEmpty()) {
            return Collections.emptyList();
        }

        SearchDTO search = searchOpt.get();
        LocalDate ultimaMenstruacao = search.toEntity().getLastMenstruationDay();
        int ciclo = search.toEntity().getCycleDuration();

        LocalDate hoje = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<Map<String, String>> eventos = new ArrayList<>();

        for (int offset = -2; offset <= 2; offset++) {
            LocalDate data = hoje.plusDays(offset);

            String status = null;
            if (searchService.isMenstruacao(data, ultimaMenstruacao, ciclo)) {
                status = "menstruacao";
            } else if (searchService.isPeriodoFertil(data, ultimaMenstruacao)) {
                status = "periodo_fertil";
            } else if (searchService.isOvulacao(data, ultimaMenstruacao)) {
                status = "ovulacao";
            }

            Map<String, String> evento = new HashMap<>();
            evento.put("data", data.format(formatter)); // Garantido: yyyy-MM-dd
            if (status != null) {
                evento.put("status", status);
            }
            eventos.add(evento);
        }

        return eventos;
    }
}
