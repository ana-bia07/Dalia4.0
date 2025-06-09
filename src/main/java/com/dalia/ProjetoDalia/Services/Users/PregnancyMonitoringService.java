package com.dalia.ProjetoDalia.Services.Users;

import com.dalia.ProjetoDalia.DTOS.Users.PregnancyMonitoringDTO;
import com.dalia.ProjetoDalia.Entity.Users.PregnancyMonitoring;
import com.dalia.ProjetoDalia.Entity.Users.Users;
import com.dalia.ProjetoDalia.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PregnancyMonitoringService {

    private final UsersRepository usersRepository;

    public String createPregnancyMonitoring(String idUsers, PregnancyMonitoringDTO dto) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return "Usuário não encontrado";

        Users user = userOpt.get();
        PregnancyMonitoring pregnancyMonitoring = dto.toEntity();

        user.setPregnancyMonitoring(pregnancyMonitoring);
        usersRepository.save(user);

        return "Gravidez registrada com sucesso";
    }

    public Optional<PregnancyMonitoringDTO> getPregnancyByidUsers(String idUsers) {
        return usersRepository.findById(idUsers)
                .map(Users::getPregnancyMonitoring)
                .map(this::toDTO);
    }

    public Optional<PregnancyMonitoringDTO> updatePregnancy(String idUsers, PregnancyMonitoringDTO dto) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        if (userOpt.isEmpty()) return Optional.empty();

        Users user = userOpt.get();
        user.setPregnancyMonitoring(dto.toEntity());
        usersRepository.save(user);

        return Optional.of(dto);
    }

    public void deletePregnancy(String idUsers) {
        Optional<Users> userOpt = usersRepository.findById(idUsers);
        userOpt.ifPresent(user -> {
            user.setPregnancyMonitoring(null);
            usersRepository.save(user);
        });
    }

    private PregnancyMonitoringDTO toDTO(PregnancyMonitoring entity) {
        return new PregnancyMonitoringDTO(
                entity.getIsPregnant(),
                entity.getDayPregnancy(),
                entity.getGestationWeeks(),
                entity.getExpectedBirthDate(),
                entity.getConsultations()
        );
    }
}
