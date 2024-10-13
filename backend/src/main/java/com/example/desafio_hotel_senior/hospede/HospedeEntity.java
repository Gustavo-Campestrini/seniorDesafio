package com.example.desafio_hotel_senior.hospede;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity(name = "hospede")
@Table(name = "hospede")
@Getter
@Setter
public class HospedeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String nome;

    @Column
    private String telefone;

    @Column
    private String documento;
}
