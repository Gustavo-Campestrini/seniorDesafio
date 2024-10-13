package com.example.desafio_hotel_senior.api.checkIn;

import com.example.desafio_hotel_senior.checkIn.CheckInController;
import com.example.desafio_hotel_senior.checkIn.CheckInEntity;
import com.example.desafio_hotel_senior.checkIn.CheckInRepository;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckInControllerTest {

    @Mock
    private CheckInRepository repository;

    @Spy
    @InjectMocks
    private CheckInController controller;

    @AfterEach
    void afterEach() {
        verifyNoMoreInteractions(repository);
    }

    @Test
    void searchByHospede() {
        final String query = "Gustavo Henrique Campestrini";
        CheckInEntity mock = mock(CheckInEntity.class);

        final List<CheckInEntity> expectedList = List.of(mock);

        when(repository.searchByNameDocumentOrPhone(query)).thenReturn(expectedList);

        List<CheckInEntity> responseList = controller.searchByHospede(query);

        verify(repository).searchByNameDocumentOrPhone(query);
        assertEquals(expectedList, responseList);
    }

    @Test
    void create() {
        final CheckInEntity mockEntity = mock(CheckInEntity.class);

        when(repository.save(mockEntity)).thenReturn(mockEntity);

        CheckInEntity responseEntity = controller.create(mockEntity);

        verify(repository).save(mockEntity);
        assertEquals(mockEntity, responseEntity);
    }

    @Test
    void list() {
        List<CheckInEntity> expectedResponse = List.of(mock(CheckInEntity.class));

        when(repository.findAll()).thenReturn(expectedResponse);

        List<CheckInEntity> response = controller.list();

        verify(repository).findAll();
        assertEquals(expectedResponse, response);
    }

    @Test
    void getCheckedOutHospedes() {
        List<CheckInEntity> checkedOutList = List.of(mock(CheckInEntity.class));

        when(repository.findCheckedOutHospedes(any(LocalDateTime.class))).thenReturn(checkedOutList);

        doReturn(checkedOutList).when(controller).calculatePrices(checkedOutList);

        List<CheckInEntity> responseList = controller.getCheckedOutHospedes();

        assertEquals(checkedOutList, responseList);
    }

    @Test
    void getCheckedInHospede() {
        List<CheckInEntity> checkedInList = List.of(mock(CheckInEntity.class));

        when(repository.findCheckedInHospedes(any(LocalDateTime.class))).thenReturn(checkedInList);

        doReturn(checkedInList).when(controller).calculatePrices(checkedInList);

        List<CheckInEntity> responseList = controller.getCheckedInHospede();

        assertEquals(checkedInList.size(), responseList.size());
    }

    @Test
    void calculateTotalValue() {
        CheckInEntity checkInEntity = new CheckInEntity();
        checkInEntity.setDataEntrada(LocalDateTime.of(2024, 10, 1, 15, 0));
        checkInEntity.setDataSaida(LocalDateTime.of(2024, 10, 8, 17, 0));
        checkInEntity.setAdicionalVeiculo(true);

        double expectedValue = 1270.0;

        double totalValue = controller.calculateTotalValue(checkInEntity);

        assertEquals(expectedValue, totalValue);
    }
}
