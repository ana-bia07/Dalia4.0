package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.Users.Search;

import java.time.LocalDate;

public record SearchDTO(
        int age,
        boolean regularMenstruation,
        boolean useContraceptive,
        String contraceptiveType,
        LocalDate lastMenstruationDay,
        int cycleDuration
) {
    public Search toEntity() {
        return new Search(
                age,
                regularMenstruation,
                useContraceptive,
                contraceptiveType,
                lastMenstruationDay,
                cycleDuration
        );
    }
}
