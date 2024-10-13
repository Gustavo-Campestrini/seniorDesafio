package com.example.desafio_hotel_senior.checkIn;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface CheckInRepository extends JpaRepository<CheckInEntity, UUID> {

    @Query("""
            SELECT ci FROM check_in ci JOIN ci.hospede h
            WHERE (LOWER(h.nome) LIKE LOWER(CONCAT('%', :q, '%')) OR h.documento = :q OR h.telefone = :q)
            """)
    List<CheckInEntity> searchByNameDocumentOrPhone(@Param("q") String q);

    @Query(value = """
             SELECT ci FROM check_in ci
              WHERE ci.hospede.id = :hospedeId
                  AND ci.id != :hospedagemAtual
             ORDER BY ci.dataSaida DESC
             LIMIT 1
            """)
    CheckInEntity getLastStay(@Param("hospedeId") UUID hospedeId, @Param("hospedagemAtual") UUID hospedagemAtual);

    @Query("SELECT ci FROM check_in ci WHERE ci.dataSaida < :now")
    List<CheckInEntity> findCheckedOutHospedes(@Param("now") LocalDateTime now);

    @Query("SELECT ci FROM check_in ci WHERE ci.dataSaida > :now")
    List<CheckInEntity> findCheckedInHospedes(@Param("now") LocalDateTime now);
}
