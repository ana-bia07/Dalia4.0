package com.dalia.ProjetoDalia.Controller.Users;


import com.dalia.ProjetoDalia.Entity.Users.Search;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.*;

@Controller
public class CallendarController {

    @GetMapping("/calendar")
    public String exibirCalendario(HttpSession session, Model model) {
        return "calendario";
    }

    @GetMapping("/calendar-data")
    @ResponseBody
    public Map<String, List<String>> getCalendarData(HttpSession session) {
        Search search = (Search) session.getAttribute("search");

        Map<String, List<String>> response = new HashMap<>();

        if (search == null) {
            response.put("error", List.of("Usuário não respondeu a pesquisa"));
            return response;
        }

        LocalDate ultimaMenstruacao = search.getLastMenstruationDay();
        int duracaoCiclo = search.getCycleDuration();
        int duracaoMenstruacao = search.getMenstruationDuration();

        List<String> menstruacao = new ArrayList<>();
        List<String> fertil = new ArrayList<>();
        List<String> ovulacao = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LocalDate inicioCiclo = ultimaMenstruacao.plusDays((long) i * duracaoCiclo);

            for (int j = 0; j < duracaoMenstruacao; j++) {
                menstruacao.add(inicioCiclo.plusDays(j).toString());
            }

            LocalDate diaOvulacao = inicioCiclo.plusDays(duracaoCiclo - 14);
            ovulacao.add(diaOvulacao.toString());

            for (int j = -5; j <= 1; j++) {
                fertil.add(diaOvulacao.plusDays(j).toString());
            }
        }

        response.put("menstruacao", menstruacao);
        response.put("fertil", fertil);
        response.put("ovulacao", ovulacao);

        return response;
    }
}