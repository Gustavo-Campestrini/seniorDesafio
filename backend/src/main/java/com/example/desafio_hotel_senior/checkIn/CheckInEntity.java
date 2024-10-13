package com.example.desafio_hotel_senior.checkIn;

import com.example.desafio_hotel_senior.hospede.HospedeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "check_in")
@Table(name = "check_in")
@Getter
@Setter
public class CheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "hospede_id")
    private HospedeEntity hospede;

    @Column(name = ("data_entrada"))
    private LocalDateTime dataEntrada;

    @Column(name = ("data_saida"))
    private LocalDateTime dataSaida;

    @Column(name = ("adicional_veiculo"))
    private Boolean adicionalVeiculo;

    @Transient
    private Double valorTotal;

    @Transient
    private Double valorUltimaHospedagem;


    @JsonGetter
    public Double getValorTotal() {
        return valorTotal;
    }

    @JsonSetter
    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @JsonGetter
    public Double getValorUltimaHospedagem() {
        return valorUltimaHospedagem;
    }

    @JsonSetter
    public void setValorUltimaHospedagem(Double valorUltimaHospedagem) {
        this.valorUltimaHospedagem = valorUltimaHospedagem;
    }
}
