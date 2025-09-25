package it.example.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="ruoli")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String nome; // es. ROLE_ADMIN, ROLE_USER

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
}
