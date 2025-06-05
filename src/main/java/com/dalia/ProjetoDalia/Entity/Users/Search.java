package com.dalia.ProjetoDalia.Entity.Users;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "search")
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
    private Integer menstruationDuration = 5;
    private int cycleDuration;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isRegularMenstruation() {
        return regularMenstruation;
    }

    public void setRegularMenstruation(boolean regularMenstruation) {
        this.regularMenstruation = regularMenstruation;
    }

    public boolean isUseContraceptive() {
        return useContraceptive;
    }

    public void setUseContraceptive(boolean useContraceptive) {
        this.useContraceptive = useContraceptive;
    }

    public String getContraceptiveType() {
        return contraceptiveType;
    }

    public void setContraceptiveType(String contraceptiveType) {
        this.contraceptiveType = contraceptiveType;
    }

    public LocalDate getLastMenstruationDay() {
        return lastMenstruationDay;
    }

    public void setLastMenstruationDay(LocalDate lastMenstruationDay) {
        this.lastMenstruationDay = lastMenstruationDay;
    }

    public int getCycleDuration() {
        return cycleDuration;
    }

    public void setCycleDuration(int cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    public int getMenstruationDuration() {
        return menstruationDuration;
    }

    public void setMenstruationDuration(int menstruationDuration) {
        this.menstruationDuration = menstruationDuration;
    }
}
