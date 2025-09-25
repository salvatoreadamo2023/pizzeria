package it.example.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comande")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_nome")
    private String cliente;

    @Column(name = "data_ora")
    private LocalDateTime dataOra;

    private double totale;
    private String stato;
    private String note;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComandaItem> items = new ArrayList<>();
}
