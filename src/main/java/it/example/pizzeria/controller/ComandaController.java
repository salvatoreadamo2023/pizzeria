package it.example.pizzeria.controller;

import it.example.pizzeria.model.Comanda;
import it.example.pizzeria.model.ComandaItem;
import it.example.pizzeria.model.Prodotto;
import it.example.pizzeria.service.ComandaService;
import it.example.pizzeria.service.ProdottoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comande")
public class ComandaController {

    private final ComandaService comandaService;
    private final ProdottoService prodottoService;

    public ComandaController(ComandaService comandaService, ProdottoService prodottoService) {
        this.comandaService = comandaService;
        this.prodottoService = prodottoService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("comande", comandaService.findAll());
        return "comande";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("comanda", new Comanda());
        model.addAttribute("prodotti", prodottoService.findAll());
        return "comanda-form";
    }

    @PostMapping("/nuova")
    public String saveComanda(@ModelAttribute Comanda comanda,
                              @RequestParam Map<String,String> requestParams) {

        comanda.setDataOra(LocalDateTime.now());
        comanda.setStato("In preparazione");

        List<ComandaItem> items = new ArrayList<>();
        double totale = 0.0;

        for (String key : requestParams.keySet()) {
            if (key.startsWith("items[") && key.endsWith("].prodottoId")) {
                String index = key.substring(6, key.indexOf("]"));
                Long prodId = Long.parseLong(requestParams.get(key));
                int quantita = 1;
                String qtyKey = "items[" + index + "].quantita";
                if (requestParams.containsKey(qtyKey)) {
                    quantita = Integer.parseInt(requestParams.get(qtyKey));
                }

                Prodotto p = prodottoService.findById(prodId)
                        .orElseThrow(() -> new IllegalArgumentException("Prodotto non trovato: " + prodId));

                ComandaItem item = ComandaItem.builder()
                        .prodotto(p)
                        .comanda(comanda) // importante
                        .quantita(quantita)
                        .prezzoUnitario(p.getPrezzo())
                        .build();

                items.add(item);
                totale += p.getPrezzo() * quantita;
            }
        }

        // ⚠ Aggiorna la lista degli items nella comanda
        comanda.setItems(items);
        comanda.setTotale(totale);

        comandaService.save(comanda); // salva con cascata

        return "redirect:/comande";
    }






    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        comandaService.findById(id).ifPresent(comanda -> model.addAttribute("comanda", comanda));
        model.addAttribute("prodotti", prodottoService.findAll());
        return "comanda-edit";
    }


    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
    	
        comandaService.findById(id).ifPresent(comanda -> model.addAttribute("comanda", comanda));
        return "comanda-view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        comandaService.deleteById(id);
        return "redirect:/comande";
    }
    @GetMapping("/print/{id}")
    public String print(@PathVariable Long id, Model model) {
        comandaService.findById(id).ifPresent(comanda -> model.addAttribute("comanda", comanda));
        return "comanda-ricetta"; // Thymeleaf template
    }

}
