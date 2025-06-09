package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;

import java.time.LocalDate;
import java.util.List;

public class PregnancyMonitoringDTO {

    private Boolean isPregnant;
    private LocalDate dayPregnancy;
    private int gestationWeeks;
    private LocalDate expectedBirthDate;
    private List<String> consultations;

    public PregnancyMonitoringDTO() {}

    public PregnancyMonitoringDTO(Boolean isPregnant, LocalDate dayPregnancy, int gestationWeeks,
                                  LocalDate expectedBirthDate, List<String> consultations) {
        this.isPregnant = isPregnant;
        this.dayPregnancy = dayPregnancy;
        this.gestationWeeks = gestationWeeks;
        this.expectedBirthDate = expectedBirthDate;
        this.consultations = consultations;
    }

    // Getters e Setters
    public Boolean getIsPregnant() {
        return isPregnant;
    }

    public void setIsPregnant(Boolean isPregnant) {
        this.isPregnant = isPregnant;
    }

    public LocalDate getDayPregnancy() {
        return dayPregnancy;
    }

    public void setDayPregnancy(LocalDate dayPregnancy) {
        this.dayPregnancy = dayPregnancy;
    }

    public int getGestationWeeks() {
        return gestationWeeks;
    }

    public void setGestationWeeks(int gestationWeeks) {
        this.gestationWeeks = gestationWeeks;
    }

    public LocalDate getExpectedBirthDate() {
        return expectedBirthDate;
    }

    public void setExpectedBirthDate(LocalDate expectedBirthDate) {
        this.expectedBirthDate = expectedBirthDate;
    }

    public List<String> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<String> consultations) {
        this.consultations = consultations;
    }

    // Converter para Entity
    public PregnancyMonitoring toEntity() {
        return new PregnancyMonitoring(
                this.isPregnant,
                this.dayPregnancy,
                this.gestationWeeks,
                this.expectedBirthDate,
                this.consultations
        );
    }
}
