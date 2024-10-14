package com.example.desafio_hotel_senior.hospede;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotEmpty(message = "Nome é um campo obrigatório!")
    private String nome;

    @Column
    @NotEmpty(message = "Telefone é um campo obrigatório!")
    private String telefone;

    @Column
    @NotEmpty(message = "Documento é um campo obrigatório!")
    private String documento;
}
