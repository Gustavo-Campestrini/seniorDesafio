package com.example.desafio_hotel_senior.api.hospede;

import com.example.desafio_hotel_senior.hospede.HospedeControler;
import com.example.desafio_hotel_senior.hospede.HospedeEntity;
import com.example.desafio_hotel_senior.hospede.HospedeRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HospedeControllerTest {

    @Mock
    private HospedeRepository hospedeRepository;

    @InjectMocks
    private HospedeControler hospedeController;

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(hospedeRepository);
    }

    @Test
    void getAll() {
        HospedeEntity hospedeMock = mock(HospedeEntity.class);
        List<HospedeEntity> expectedList = List.of(hospedeMock);

        when(hospedeRepository.findAll()).thenReturn(expectedList);

        List<HospedeEntity> responseList = hospedeController.getAll();

        verify(hospedeRepository).findAll();
        assertEquals(expectedList, responseList);
    }

    @Test
    void getById() {
        UUID id = UUID.randomUUID();
        HospedeEntity hospedeMock = mock(HospedeEntity.class);

        when(hospedeRepository.findById(id)).thenReturn(Optional.of(hospedeMock));

        HospedeEntity response = hospedeController.getById(id);

        verify(hospedeRepository).findById(id);
        assertEquals(hospedeMock, response);
    }

    @Test
    void getByIdHospedeNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(hospedeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> hospedeController.getById(nonExistentId));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        verify(hospedeRepository).findById(nonExistentId);
    }

    @Test
    void create() {
        HospedeEntity hospedeMock = mock(HospedeEntity.class);

        when(hospedeRepository.save(hospedeMock)).thenReturn(hospedeMock);

        HospedeEntity response = hospedeController.create(hospedeMock);

        verify(hospedeRepository).save(hospedeMock);
        assertEquals(hospedeMock, response);
    }

    @Test
    void update() {
        UUID id = UUID.randomUUID();
        HospedeEntity hospedeMock = mock(HospedeEntity.class);

        when(hospedeRepository.findById(id)).thenReturn(Optional.of(hospedeMock));
        when(hospedeRepository.save(hospedeMock)).thenReturn(hospedeMock);

        when(hospedeMock.getNome()).thenReturn(RandomString.make(10));

        HospedeEntity updatedHospede = hospedeController.update(id, hospedeMock);

        verify(hospedeRepository).findById(id);
        verify(hospedeRepository).save(hospedeMock);
        assertEquals(hospedeMock.getNome(), updatedHospede.getNome());
    }

    @Test
    void updateHospedeNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(hospedeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,//
                () -> hospedeController.update(nonExistentId, new HospedeEntity()));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        verify(hospedeRepository).findById(nonExistentId);
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        HospedeEntity hospedeMock = mock(HospedeEntity.class);

        when(hospedeRepository.findById(id)).thenReturn(Optional.of(hospedeMock));

        hospedeController.delete(id);

        verify(hospedeRepository).findById(id);
        verify(hospedeRepository).deleteById(id);
    }

    @Test
    void deleteHospedeNotFound() {
        UUID nonExistentId = UUID.randomUUID();
        when(hospedeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class,
                () -> hospedeController.delete(nonExistentId));

        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
        verify(hospedeRepository).findById(nonExistentId);
    }
}
