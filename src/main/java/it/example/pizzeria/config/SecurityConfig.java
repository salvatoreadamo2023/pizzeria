package it.example.pizzeria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import it.example.pizzeria.model.User;
import it.example.pizzeria.repository.UserRepository;

import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
        	    .requestMatchers("/public/**", "/login").permitAll()
        	    .requestMatchers("/dashboard", "/").authenticated() // proteggi dashboard
        	    .anyRequest().authenticated()
        	)
            .formLogin(form -> form
                .loginPage("/login")     // usa la pagina login personalizzata
                .defaultSuccessUrl("/", true) // redirige qui dopo il login
                .permitAll()
            )
            .logout(logout -> logout.permitAll())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> {
            User utente = repo.findByUsername(username)
                              .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

            var authorities = utente.getRoles().stream()
                                    .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getNome()))
                                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails.User(
                    utente.getUsername(),
                    utente.getPassword(),
                    utente.isEnabled(),
                    true, true, true,
                    authorities
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
