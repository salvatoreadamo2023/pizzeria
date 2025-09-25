package it.example.pizzeria.repository;

import it.example.pizzeria.model.Prodotto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

    List<Prodotto> findByDisponibileTrue();

    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
}
