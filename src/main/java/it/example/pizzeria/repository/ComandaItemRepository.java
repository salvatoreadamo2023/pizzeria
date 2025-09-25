package it.example.pizzeria.repository;

import it.example.pizzeria.model.ComandaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComandaItemRepository extends JpaRepository<ComandaItem, Long> {

	List<ComandaItem> findByComanda_Id(Long comandaId);

}
