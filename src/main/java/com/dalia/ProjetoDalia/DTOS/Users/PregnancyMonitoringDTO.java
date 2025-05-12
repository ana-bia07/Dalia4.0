package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;

import java.time.Instant;
import java.util.List;

public record PregnancyMonitoringDTO(
    boolean isPregnant,
    Instant lastMenstruationDay,
    int gestationWeeks,
    Instant expectedBirthDate,
    List<String> symptoms,
    List<String> consultations
) {
    public PregnancyMonitoring toEntity() {
        return new PregnancyMonitoring(
                isPregnant,
                lastMenstruationDay,
                gestationWeeks,
                expectedBirthDate,
                symptoms,
                consultations);
    }
}
