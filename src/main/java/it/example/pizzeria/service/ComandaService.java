package it.example.pizzeria.service;

import it.example.pizzeria.model.Comanda;
import it.example.pizzeria.repository.ComandaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComandaService {

    private final ComandaRepository comandaRepository;

    public ComandaService(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    public List<Comanda> findAll() {
        return comandaRepository.findAll();
    }

    public Optional<Comanda> findById(Long id) {
        return comandaRepository.findById(id);
    }

    public Comanda save(Comanda comanda) {
        return comandaRepository.save(comanda);
    }

    public void deleteById(Long id) {
        comandaRepository.deleteById(id);
    }
}
