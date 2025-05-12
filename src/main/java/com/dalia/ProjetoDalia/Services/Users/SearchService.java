package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Entity.Search;
import com.dalia.ProjetoDalia.Entity.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final UsersRepository usersRepository;

    // SearchService.java
    public String createSearch(String idUsers, SearchDTO searchData) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return null;

        Users user = userOpt.get();
        Search search = searchData.toEntity();
        user.setSearch(search);

        usersRepository.save(user);

        return "Search cadastrada com sucesso para o usuário " + idUsers;
    }



    public Optional<SearchDTO> updateSearch(String idUsers, SearchDTO searchData){
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if(userOpt.isEmpty()) return Optional.empty();

        Users user = userOpt.get();
        user.setSearch(searchData.toEntity());
        usersRepository.save(user);

        return Optional.of(searchData);
    }

    public boolean deleteSearch(String idUsers) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if(userOpt.isEmpty()) return false;

        Users user = userOpt.get();
        user.setSearch(null);
        usersRepository.save(user);
        return true;
    }

    public Optional<SearchDTO> getSearchByidUsers(String idUsers) {
        return usersRepository.findById(idUsers)
                .map(Users::getSearch)
                .map(search -> new SearchDTO(
                        search.getAge(),
                        search.isRegularMenstruation(),
                        search.isUseContraceptive(),
                        search.getContraceptiveType(),
                        search.getLastMenstruationDay(),
                        search.getCycleDuration()
                ));
    }
}

