package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.Users.Search;
import java.time.LocalDate;

public record SearchDTO(
        int age,
        boolean regularMenstruation,
        boolean useContraceptive,
        String contraceptiveType,
        LocalDate lastMenstruationDay,
        int cycleDuration,
        Integer menstruationDuration  // adicionado aqui
) {
    public Search toEntity() {
        return new Search(
                age,
                regularMenstruation,
                useContraceptive,
                contraceptiveType,
                lastMenstruationDay,
                menstruationDuration != null ? menstruationDuration : 5,  // valor padrão
                cycleDuration);
    }
}
