package com.dalia.ProjetoDalia.DTOS.Users;

import com.dalia.ProjetoDalia.Entity.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Entity.Search;
import com.dalia.ProjetoDalia.Entity.Users;

import java.time.LocalDate;

public record UsersDTO(
        String name,
        String surname,
        String email,
        String password,
        LocalDate birthdate,
        Search search,
        PregnancyMonitoring pregnancyMonitoring
){
    public Users toEntity(){
        return new Users(
                null,
                name,
                surname,
                email,
                password,
                birthdate,
                search,
                pregnancyMonitoring
        );
    }
}
