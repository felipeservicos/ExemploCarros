package br.com.tjrj.exemplocarros.carro;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CarroService {
    private final CarroRepository carroRepository;

    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    public void save(Carro carro) {
        carroRepository.save(carro);
    }
}
