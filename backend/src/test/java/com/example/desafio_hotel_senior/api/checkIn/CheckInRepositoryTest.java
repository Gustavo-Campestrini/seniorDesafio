package com.example.desafio_hotel_senior.api.checkIn;

import com.example.desafio_hotel_senior.checkIn.CheckInEntity;
import com.example.desafio_hotel_senior.checkIn.CheckInRepository;
import com.example.desafio_hotel_senior.hospede.HospedeEntity;
import com.example.desafio_hotel_senior.hospede.HospedeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EnableAutoConfiguration
@ActiveProfiles("test")
public class CheckInRepositoryTest {

    @Autowired
    private CheckInRepository repository;

    @Autowired
    private HospedeRepository hospedeRepository;

    @Test
    void searchByNameDocumentOrPhone() {
        HospedeEntity hospede = createAndSaveHospede();

        CheckInEntity checkIn = new CheckInEntity();
        checkIn.setId(UUID.randomUUID());
        checkIn.setHospede(hospede);
        checkIn.setDataEntrada(LocalDateTime.now().minusDays(5));
        checkIn.setDataSaida(LocalDateTime.now().minusDays(1));

        repository.save(checkIn);

        List<CheckInEntity> results = repository.searchByNameDocumentOrPhone("Gustavo");
        assertThat(results).isNotEmpty();
    }

    private HospedeEntity createAndSaveHospede() {
        HospedeEntity hospede = new HospedeEntity();
        hospede.setId(UUID.randomUUID());
        hospede.setNome("Gustavo Silva");
        hospede.setDocumento("123456789");
        hospede.setTelefone("987654321");

        return hospedeRepository.save(hospede);
    }

    @Test
    void getLastStay() {
        HospedeEntity hospede = createAndSaveHospede();

        CheckInEntity checkIn1 = new CheckInEntity();
        checkIn1.setId(UUID.randomUUID());
        checkIn1.setHospede(hospede);
        checkIn1.setDataEntrada(LocalDateTime.now().minusDays(5));
        checkIn1.setDataSaida(LocalDateTime.now().minusDays(1));

        CheckInEntity checkIn2 = new CheckInEntity();
        checkIn2.setId(UUID.randomUUID());
        checkIn2.setHospede(hospede);
        checkIn2.setDataEntrada(LocalDateTime.now().minusDays(3));
        checkIn2.setDataSaida(LocalDateTime.now().plusDays(2));

        repository.save(checkIn1);
        repository.save(checkIn2);

        CheckInEntity lastStay = repository.getLastStay(hospede.getId(), UUID.randomUUID());
        assertThat(lastStay).isNotNull();
    }

    @Test
    void findCheckedOutHospedes() {
        HospedeEntity hospede = createAndSaveHospede();

        CheckInEntity checkIn1 = new CheckInEntity();
        checkIn1.setId(UUID.randomUUID());
        checkIn1.setHospede(hospede);
        checkIn1.setDataEntrada(LocalDateTime.now().minusDays(5));
        checkIn1.setDataSaida(LocalDateTime.now().minusDays(1));

        repository.save(checkIn1);

        List<CheckInEntity> checkedOutHospedes = repository.findCheckedOutHospedes(LocalDateTime.now());
        assertThat(checkedOutHospedes).hasSize(1);
    }

    @Test
    void findCheckedInHospedes() {
        HospedeEntity hospede = createAndSaveHospede();

        CheckInEntity checkIn = new CheckInEntity();
        checkIn.setId(UUID.randomUUID());
        checkIn.setHospede(hospede);
        checkIn.setDataEntrada(LocalDateTime.now().minusDays(3));
        checkIn.setDataSaida(LocalDateTime.now().plusDays(2));

        repository.save(checkIn);

        List<CheckInEntity> checkedInHospedes = repository.findCheckedInHospedes(LocalDateTime.now());
        assertThat(checkedInHospedes).hasSize(1);
    }
}
