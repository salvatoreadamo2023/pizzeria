package it.example.pizzeria.service;

import it.example.pizzeria.model.Prodotto;
import it.example.pizzeria.repository.ProdottoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoService {

    private final ProdottoRepository prodottoRepository;

    public ProdottoService(ProdottoRepository prodottoRepository) {
        this.prodottoRepository = prodottoRepository;
    }

    public List<Prodotto> findAll() {
        return prodottoRepository.findAll();
    }

    public List<Prodotto> findAvailable() {
        return prodottoRepository.findByDisponibileTrue();
    }

    public Optional<Prodotto> findById(Long id) {
        return prodottoRepository.findById(id);
    }

    public Prodotto save(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public void deleteById(Long id) {
        prodottoRepository.deleteById(id);
    }
}
