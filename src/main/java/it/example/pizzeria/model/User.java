package it.example.pizzeria.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Entity
@Table(name="utenti")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(nullable=false)
    private String password;

    @Column(name="full_name")
    private String fullName;
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name="utenti_ruoli",
        joinColumns = @JoinColumn(name="utente_id"),
        inverseJoinColumns = @JoinColumn(name="ruolo_id")
    )
    private Set<Role> roles;
}
