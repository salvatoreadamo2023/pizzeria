package it.example.pizzeria.repository;

import it.example.pizzeria.model.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ComandaRepository extends JpaRepository<Comanda, Long> {

    List<Comanda> findByStato(String stato);

    List<Comanda> findByClienteContainingIgnoreCase(String cliente);

    // --- Comande di oggi ---
    @Query("SELECT COUNT(c) FROM Comanda c WHERE DATE(c.dataOra) = :oggi")
    long countByToday(@Param("oggi") LocalDate oggi);

    // --- Incasso totale oggi ---
    @Query("SELECT COALESCE(SUM(c.totale), 0) FROM Comanda c WHERE DATE(c.dataOra) = :oggi")
    double totaleIncassoOggi(@Param("oggi") LocalDate oggi);

    // --- Comande per gli ultimi 7 giorni (andamento settimanale) ---
    @Query("SELECT COUNT(c) FROM Comanda c WHERE DATE(c.dataOra) BETWEEN :inizio AND :fine GROUP BY DATE(c.dataOra) ORDER BY DATE(c.dataOra)")
    List<Long> comandeUltimaSettimana(@Param("inizio") LocalDate inizio, @Param("fine") LocalDate fine);
}
