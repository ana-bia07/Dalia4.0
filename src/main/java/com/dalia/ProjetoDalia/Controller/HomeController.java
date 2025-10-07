package com.dalia.ProjetoDalia.Controller;

import com.dalia.ProjetoDalia.Model.Calendario;
import com.dalia.ProjetoDalia.Model.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Model.Entity.Comments;
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
import java.time.format.TextStyle;
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

        Comments.Users usuario = userService.getByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")).toEntity();
        model.addAttribute("userId", usuario.getId());
        return "index";
    }

    @GetMapping("/calendario/{idUser}")
    public String mostrarCalendario(@PathVariable String idUser, Model model) {
        Optional<SearchDTO> searchDto = searchService.getSearchByIdUsers(idUser);
        if (searchDto.isEmpty()) {
            return Collections.emptyList().toString();
        }

        SearchDTO search = searchDto.get();
        LocalDate hoje = LocalDate.now();
        LocalDate ultimaMenstruacao = search.toEntity().getLastMenstruationDay();
        int cicloCurto = search.toEntity().getMinCycleDuration();
        int cicloLongo = search.toEntity().getMaxCycleDuration();

        List<Calendario> dayMonth = new ArrayList<>();

        //começa no dia 1 do mes atual
        LocalDate dataAtual = hoje.withDayOfMonth(1);

        //loop que preencher os dia dos mes
        while (dataAtual.getMonth() == hoje.getMonth()) {
            String fase;
            if (searchService.isMenstruacao(dataAtual, ultimaMenstruacao)){
                fase = "menstruação";
            } else if (searchService.isOvulacao(dataAtual, ultimaMenstruacao, cicloLongo)){
                fase = "ovulacao";
            } else if (searchService.isPeriodoFertil(dataAtual, ultimaMenstruacao, cicloCurto, cicloLongo)){
                fase = "fertil";
            } else {
                fase = "sem evento";
            }

            dayMonth.add(new Calendario(dataAtual, fase));
            dataAtual = dataAtual.plusDays(1);
        }

        model.addAttribute("dayMonth", dayMonth);
        model.addAttribute("nomeMes", hoje.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));

        return "calendario";
    }
}
