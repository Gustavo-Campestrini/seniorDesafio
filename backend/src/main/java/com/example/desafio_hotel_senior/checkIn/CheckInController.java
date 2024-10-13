package com.example.desafio_hotel_senior.checkIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("checkin")
public class CheckInController {

    public static final double PRECO_DIARIA_FINAL_DE_SEMANA = 150.0;
    public static final double PRECO_ADICIONAL_VEICULO_FINAL_DE_SEMANA = 20.0;
    public static final double PRECO_DIARIA_SEMANA = 120.0;
    public static final double PRECO_ADICIONAL_VEICULO_SEMANA = 15.0;

    @Autowired
    private CheckInRepository repository;

    @GetMapping("/search")
    public List<CheckInEntity> searchByHospede(@RequestParam(name = "q") String q) {
        return repository.searchByNameDocumentOrPhone(q);
    }

    @PostMapping
    public CheckInEntity create(@RequestBody CheckInEntity entity) {
        return repository.save(entity);
    }

    @GetMapping
    public List<CheckInEntity> list() {
        return repository.findAll();
    }

    @GetMapping("/checkedout")
    public List<CheckInEntity> getCheckedOutHospedes() {
        List<CheckInEntity> checkedOutHospedes = repository.findCheckedOutHospedes(LocalDateTime.now());

        return calculatePrices(checkedOutHospedes);
    }

    public List<CheckInEntity> calculatePrices(List<CheckInEntity> checkedOutHospedes) {
        return checkedOutHospedes.stream().peek(item -> {
            item.setValorTotal(calculateTotalValue(item));

            CheckInEntity lastStay = repository.getLastStay(item.getHospede().getId(), item.getId());

            if (lastStay != null) {
                item.setValorUltimaHospedagem(calculateTotalValue(lastStay));
            }
        }).toList();
    }

    @GetMapping("/checkedin")
    public List<CheckInEntity> getCheckedInHospede() {
        List<CheckInEntity> checkedInHospedes = repository.findCheckedInHospedes(LocalDateTime.now());
        return calculatePrices(checkedInHospedes);
    }


    public double calculateTotalValue(CheckInEntity checkIn) {
        LocalDateTime entrada = checkIn.getDataEntrada();
        LocalDateTime saida = checkIn.getDataSaida();
        boolean adicionalVeiculo = checkIn.getAdicionalVeiculo();

        double total = 0.0;
        LocalDateTime currentDate = entrada;

        while (!currentDate.isAfter(saida)) {
            double valorDiaria;

            if (isWeekend(currentDate)) {
                valorDiaria = PRECO_DIARIA_FINAL_DE_SEMANA;
                if (adicionalVeiculo) {
                    valorDiaria += PRECO_ADICIONAL_VEICULO_FINAL_DE_SEMANA;
                }
            } else {
                valorDiaria = PRECO_DIARIA_SEMANA;
                if (adicionalVeiculo) {
                    valorDiaria += PRECO_ADICIONAL_VEICULO_SEMANA;
                }
            }

            total += valorDiaria;

            currentDate = currentDate.plusDays(1);
        }

        if (saida.toLocalTime().isAfter(LocalTime.of(16, 30))) {
            total += (saida.getDayOfWeek() == DayOfWeek.SATURDAY || saida.getDayOfWeek() == DayOfWeek.SUNDAY)
                    ? PRECO_DIARIA_FINAL_DE_SEMANA
                    : PRECO_DIARIA_SEMANA;
        }

        return total;
    }


    private boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}
