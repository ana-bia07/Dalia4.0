package com.dalia.ProjetoDalia.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class Calendario {
    private final LocalDate date;
    private final String fase;

    public Calendario(LocalDate date, String fase) {
        this.date = date;
        this.fase = fase;
    }
}