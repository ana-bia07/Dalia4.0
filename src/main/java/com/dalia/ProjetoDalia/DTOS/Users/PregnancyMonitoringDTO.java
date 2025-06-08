package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record PregnancyMonitoringDTO(
    Boolean isPregnant,
    LocalDate dayPregnancy,
    int gestationWeeks,
    LocalDate expectedBirthDate,
    List<String> consultations
) {
    public PregnancyMonitoring toEntity() {
        return new PregnancyMonitoring(
                isPregnant,
                dayPregnancy,
                gestationWeeks,
                expectedBirthDate,
                consultations);
    }
}
