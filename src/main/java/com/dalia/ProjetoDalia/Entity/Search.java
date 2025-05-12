package com.dalia.ProjetoDalia.Entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.time.LocalDate;

@Document(collation = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Search {

    private int age;
    private boolean regularMenstruation;
    private boolean useContraceptive;
    private String contraceptiveType;
    @Field(name = "lastMenstruationDay")
    private LocalDate lastMenstruationDay;
    private int cycleDuration;
}
