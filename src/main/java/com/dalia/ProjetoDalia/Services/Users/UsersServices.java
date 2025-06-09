package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.UsersDTO;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServices {

    private final UsersRepository usersRepository;

    public UsersServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public String createUser(UsersDTO usersDTO) {
        var user = usersDTO.toEntity();
        var savedUser = usersRepository.save(user);
        return savedUser.getId();
    }

    public Optional<Users> getById(String id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public List<Users> getAll() {
        return usersRepository.findAll();
    }

    public void delete(String id) {
        usersRepository.deleteById(id);
    }

    public Optional<Users> updateUser(String id, UsersDTO usersDTO) {
        Users existingUser = usersRepository.findById(id).orElse(null);

        return existingUser != null
                ? updateUserFields(existingUser, usersDTO)
                : Optional.empty();
    }

    private Optional<Users> updateUserFields(Users existingUser, UsersDTO usersDTO) {
        if (StringUtils.hasText(usersDTO.name())) existingUser.setName(usersDTO.name());
        if (StringUtils.hasText(usersDTO.surname())) existingUser.setSurname(usersDTO.surname());
        if (StringUtils.hasText(usersDTO.email())) existingUser.setEmail(usersDTO.email());
        if (StringUtils.hasText(usersDTO.password())) existingUser.setPassword(usersDTO.password());
        if (usersDTO.birthdate() != null) existingUser.setBirthDate(usersDTO.birthdate());
        if (usersDTO.search() != null) existingUser.setSearch(usersDTO.search());
        if (usersDTO.pregnancyMonitoring() != null) existingUser.setPregnancyMonitoring(usersDTO.pregnancyMonitoring());

        usersRepository.save(existingUser);
        return Optional.of(existingUser);
    }
}
