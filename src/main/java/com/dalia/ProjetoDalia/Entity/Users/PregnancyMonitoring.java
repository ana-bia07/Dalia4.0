package com.dalia.ProjetoDalia.Entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PregnancyMonitoring {

    private boolean isPregnant;
    @Field(name = "lastMentruationDay")
    private Instant lastMentruationDay;
    private int gestationWeeks;
    @Field(name = "expectedBirthDate")
    private Instant expectedBirthDate;
    private List<String> symptoms;
    private List<Consultations> consultations;
}
