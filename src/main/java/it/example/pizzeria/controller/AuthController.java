package it.example.pizzeria.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.example.pizzeria.model.Comanda;
import it.example.pizzeria.model.Prodotto;
import it.example.pizzeria.repository.ComandaRepository;
import it.example.pizzeria.repository.ProdottoRepository;

@Controller
public class AuthController {

	private final ProdottoRepository prodottoRepository;
	private final ComandaRepository comandaRepository;

	public AuthController(ProdottoRepository prodottoRepository, ComandaRepository comandaRepository) {
		this.prodottoRepository = prodottoRepository;
		this.comandaRepository = comandaRepository;
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // Thymeleaf template: login.html
	}

	@GetMapping("/logout-success")
	public String logout() {
		return "login";
	}

	@GetMapping({ "/", "/dashboard" })
	public String dashboard(Model model) {
		LocalDate oggi = LocalDate.now();
		LocalDate fine = oggi;
		LocalDate inizio = oggi.minusDays(6);

		model.addAttribute("prodottiTotali", prodottoRepository.count());
		model.addAttribute("comandeOggi", comandaRepository.countByToday(oggi));
		model.addAttribute("incasso", comandaRepository.totaleIncassoOggi(oggi));
		model.addAttribute("giorniSettimana", List.of("Lun", "Mar", "Mer", "Gio", "Ven", "Sab", "Dom"));
		model.addAttribute("comandeSettimanali", comandaRepository.comandeUltimaSettimana(inizio, fine));

		return "dashboard";
	}

}
