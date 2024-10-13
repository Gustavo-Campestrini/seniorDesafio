package com.example.desafio_hotel_senior.hospede;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("hospede")
public class HospedeControler {

    @Autowired
    private HospedeRepository hospedeRepository;

    @GetMapping
    public List<HospedeEntity> getAll() {
        return hospedeRepository.findAll();
    }

    @GetMapping("/{id}")
    public HospedeEntity getById(@PathVariable UUID id) {
        Optional<HospedeEntity> hospede = hospedeRepository.findById(id);
        if (hospede.isPresent()) {
            return hospede.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public HospedeEntity create(@RequestBody HospedeEntity hospede) {
        return hospedeRepository.save(hospede);
    }

    @PutMapping("/{id}")
    public HospedeEntity update(@PathVariable UUID id, @RequestBody HospedeEntity entity) {
        Optional<HospedeEntity> hospedeOptional = hospedeRepository.findById(id);
        if (hospedeOptional.isPresent()) {
            HospedeEntity hospede = hospedeOptional.get();
            hospede.setNome(entity.getNome());
            hospede.setTelefone(entity.getTelefone());
            hospede.setDocumento(entity.getDocumento());
            return hospedeRepository.save(hospede);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        Optional<HospedeEntity> hospede = hospedeRepository.findById(id);
        if (hospede.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        hospedeRepository.deleteById(id);
    }
}
