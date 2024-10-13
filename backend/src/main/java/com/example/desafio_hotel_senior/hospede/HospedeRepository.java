package com.example.desafio_hotel_senior.hospede;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HospedeRepository extends JpaRepository<HospedeEntity, UUID> {


}
