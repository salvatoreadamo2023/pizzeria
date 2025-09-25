package it.example.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comanda_dettagli")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComandaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "prodotto_id")
    private Prodotto prodotto;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "comanda_id")
    private Comanda comanda;

    private int quantita;
    private double prezzoUnitario;
}
