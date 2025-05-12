package com.dalia.ProjetoDalia.Entity;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private String id;

    private String name;
    private String surname;
    private String email;
    private String password;
    @Field(name = "birthDate")
    private LocalDate birthDate; //troquei para localdate pois é melhor recomendado ja que aniversario não precisa de hora
    private Search search; //alterei ppois estava dando erro no Service do Search...
    private PregnancyMonitoring pregnancyMonitoring;
}
