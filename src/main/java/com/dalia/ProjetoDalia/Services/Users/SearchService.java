package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Entity.Users.Search;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UsersRepository usersRepository;

    public String createSearch(String idUsers, SearchDTO searchData) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return "Usuário não encontrado";

        Users user = userOpt.get();
        Search search = searchData.toEntity();

        if (user.getSearch() == null) {
            user.setSearch(new ArrayList<>());
        }
        user.getSearch().add(search);
        usersRepository.save(user);

        return "Search cadastrada com sucesso para o usuário " + idUsers;
    }

    public Optional<SearchDTO> updateSearch(String idUsers, SearchDTO searchData) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return Optional.empty();

        Users user = userOpt.get();
        if (user.getSearch() == null) {
            user.setSearch(new ArrayList<>());
        }

        user.getSearch().add(searchData.toEntity());
        usersRepository.save(user);

        return Optional.of(searchData);
    }

    public boolean deleteSearch(String idUsers) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return false;

        Users user = userOpt.get();
        user.setSearch(null);
        usersRepository.save(user);

        return true;
    }

    public Optional<List<SearchDTO>> getSearchByidUsers(String idUsers) {
        return usersRepository.findById(idUsers)
                .map(Users::getSearch)
                .map(searchList -> searchList.stream()
                        .map(search -> new SearchDTO(
                                search.getAge(),
                                search.isRegularMenstruation(),
                                search.isUseContraceptive(),
                                search.getContraceptiveType(),
                                search.getLastMenstruationDay(),
                                search.getCycleDuration()
                        ))
                        .toList());
    }
}
