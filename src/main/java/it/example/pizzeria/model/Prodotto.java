package it.example.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="prodotti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prodotto {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descrizione;
    private double prezzo;
    private boolean disponibile = true;
    
   
}

