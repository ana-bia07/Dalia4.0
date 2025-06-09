package com.dalia.ProjetoDalia.Controller.Users;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Services.Users.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class CicloController {

    private final SearchService searchService;

    public CicloController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/ciclo5dias/{userId}")
    public List<Map<String, String>> getEventosCiclo(@PathVariable String userId) {
        Optional<SearchDTO> searchOpt = searchService.getSearchByidUsers(userId);
        if (searchOpt.isEmpty()) {
            return List.of(); // retorna lista vazia se não achar
        }

        SearchDTO search = searchOpt.get();
        LocalDate ultimaMenstruacao = search.toEntity().getLastMenstruationDay();
        int ciclo = search.toEntity().getCycleDuration();

        List<Map<String, String>> eventos = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (int offset = -2; offset <= 2; offset++) {
            LocalDate data = hoje.plusDays(offset);
            String dataStr = data.toString();

            String status = null;
            if (searchService.isMenstruacao(data, ultimaMenstruacao, ciclo)) {
                status = "menstruacao";
            } else if (searchService.isPeriodoFertil(data, ultimaMenstruacao)) {
                status = "periodo_fertil";
            } else if (searchService.isOvulacao(data, ultimaMenstruacao)) {
                status = "ovulacao";
            }

            if (status != null) {
                Map<String, String> evento = new HashMap<>();
                evento.put("data", dataStr);
                evento.put("status", status);
                eventos.add(evento);
            }
        }

        return eventos;
    }
}
