package com.dalia.ProjetoDalia.Model.DTOS.Users;

import com.dalia.ProjetoDalia.Model.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Model.Entity.Users.Search;
import com.dalia.ProjetoDalia.Model.Entity.Users.Users;


public record UsersDTO(
        String id,
        String name,
        String surname,
        String email,
        String password,
        Search search,
        PregnancyMonitoring pregnancyMonitoring
) {

    public UsersDTO() {
        this(null, "", "", "", "", null, null);
    }

    public UsersDTO(Users users){
        this(
                users.getId(),
                users.getName(),
                users.getSurname(),
                users.getEmail(),
                users.getPassword(),
                users.getSearch(),
                users.getPregnancyMonitoring()
        );
    }


    public Users toEntity(){
        Users user = new Users();
        user.setId(this.id());
        user.setName(this.name());
        user.setSurname(this.surname());
        user.setEmail(this.email());
        user.setPassword(this.password());
        user.setSearch(this.search());
        user.setPregnancyMonitoring(this.pregnancyMonitoring());
        return user;
    }
}
