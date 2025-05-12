package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.PregnancyMonitoringDTO;
import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PregnancyMonitoringService {

    private final UsersRepository usersRepository;

    public String createPregnancyMonitoring(String idUsers, PregnancyMonitoringDTO dto) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return "Usuário não encontrado";

        Users user = userOpt.get();

        if (user.getPregnancyMonitorings() == null) {
            user.setPregnancyMonitorings(new ArrayList<>());
        }

        user.getPregnancyMonitorings().add(toEntity(dto));
        usersRepository.save(user);

        return "Gravidez registrada com sucesso";
    }

    public Optional<PregnancyMonitoringDTO> getPregnancyByidUsers(String idUsers) {
        return usersRepository.findById(idUsers)
                .map(Users::getPregnancyMonitorings)
                .filter(pregnancyMonitorings -> !pregnancyMonitorings.isEmpty())
                .map(pregnancyMonitorings -> pregnancyMonitorings.stream()
                        .map(this::toDTO)
                        .collect(Collectors.toList()))
                .map(dtoList -> dtoList.get(0));
    }


    public Optional<PregnancyMonitoringDTO> updatePregnancy(String idUsers, PregnancyMonitoringDTO dto) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return Optional.empty();

        Users user = userOpt.get();

        if (user.getPregnancyMonitorings() == null) {
            user.setPregnancyMonitorings(new ArrayList<>());
        }

        user.getPregnancyMonitorings().clear();
        user.getPregnancyMonitorings().add(toEntity(dto));
        usersRepository.save(user);
        return Optional.of(dto);
    }

    public void deletePregnancy(String idUsers) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        userOpt.ifPresent(user -> {
            user.setPregnancyMonitorings(null);
            usersRepository.save(user);
        });
    }

    private PregnancyMonitoring toEntity(PregnancyMonitoringDTO dto) {
        return new PregnancyMonitoring(
                dto.isPregnant(),
                dto.lastMenstruationDay(),
                dto.gestationWeeks(),
                dto.expectedBirthDate(),
                dto.symptoms(),
                dto.consultations()
        );
    }

    private PregnancyMonitoringDTO toDTO(PregnancyMonitoring entity) {
        return new PregnancyMonitoringDTO(
                entity.isPregnant(),
                entity.getLastMenstruationDay(),
                entity.getGestationWeeks(),
                entity.getExpectedBirthDate(),
                entity.getSymptoms(),
                entity.getConsultations()
        );
    }
}
