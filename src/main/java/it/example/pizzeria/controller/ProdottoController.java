package it.example.pizzeria.controller;

import it.example.pizzeria.model.Prodotto;
import it.example.pizzeria.service.ProdottoService;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/prodotti")
public class ProdottoController {

	private final ProdottoService prodottoService;

	public ProdottoController(ProdottoService prodottoService) {
		this.prodottoService = prodottoService;
	}

	@GetMapping
	public String list(Model model) {
		var prodotti = prodottoService.findAll();
		prodotti.forEach(p -> System.out.println(p.getNome()));
		model.addAttribute("prodotti", prodotti);
		return "prodotti";
	}

	@GetMapping("/new")
	public String createForm(Model model) {
		model.addAttribute("prodotto", new Prodotto());
		return "prodotto-form";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute Prodotto prodotto) {
		prodottoService.save(prodotto);
		return "redirect:/prodotti";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		prodottoService.findById(id).ifPresent(prodotto -> model.addAttribute("prodotto", prodotto));
		return "prodotto-form";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    try {
	        prodottoService.deleteById(id);
	        redirectAttributes.addFlashAttribute("successMessage", "Prodotto eliminato correttamente.");
	    } catch (DataIntegrityViolationException e) {
	        redirectAttributes.addFlashAttribute("errorMessage", "Impossibile eliminare il prodotto: è ancora presente in una comanda.");
	    }
	    return "redirect:/prodotti";
	}

}
