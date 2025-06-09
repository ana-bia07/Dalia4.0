package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.SearchDTO;
import com.dalia.ProjetoDalia.Entity.Users.Search;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UsersRepository usersRepository;

    public String createSearch(String idUsers, SearchDTO searchData) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return "Usuário não encontrado";

        Users user = userOpt.get();
        Search search = searchData.toEntity();

        user.setSearch(search);
        usersRepository.save(user);

        return "Search cadastrada com sucesso para o usuário " + idUsers;
    }

    public Optional<SearchDTO> updateSearch(String idUsers, SearchDTO searchData) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return Optional.empty();

        Users user = userOpt.get();
        Search search = searchData.toEntity();

        user.setSearch(search);
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

    public Optional<SearchDTO> getSearchByidUsers(String idUsers) {
        return usersRepository.findById(idUsers)
                .map(Users::getSearch)
                .map(search -> new SearchDTO(
                        search.getAge(),
                        search.isRegularMenstruation(),
                        search.isUseContraceptive(),
                        search.getContraceptiveType(),
                        search.getLastMenstruationDay(),
                        search.getCycleDuration(),
                        search.getMenstruationDuration()
                ));
    }


    // Verifica se a data está dentro do período da menstruação (5 dias a partir da última menstruação)
    public boolean isMenstruacao(LocalDate data, LocalDate ultimaMenstruacao, int ciclo) {
        LocalDate fimMenstruacao = ultimaMenstruacao.plusDays(4); // 5 dias (0 a 4)
        return !data.isBefore(ultimaMenstruacao) && !data.isAfter(fimMenstruacao);
    }

    // Verifica se a data está no período fértil (dias -5 a 0 antes da ovulação)
    public boolean isPeriodoFertil(LocalDate data, LocalDate ultimaMenstruacao) {
        LocalDate ovulacao = calcularDiaOvulacao(ultimaMenstruacao);
        LocalDate inicioFertil = ovulacao.minusDays(5);
        return !data.isBefore(inicioFertil) && !data.isAfter(ovulacao);
    }

    // Verifica se a data é o dia da ovulação
    public boolean isOvulacao(LocalDate data, LocalDate ultimaMenstruacao) {
        LocalDate ovulacao = calcularDiaOvulacao(ultimaMenstruacao);
        return data.isEqual(ovulacao);
    }

    public LocalDate calcularDiaOvulacao(LocalDate ultimaMenstruacao) {
        return ultimaMenstruacao.plusDays(14);
    }
}
