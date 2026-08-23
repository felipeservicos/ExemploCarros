package br.com.tjrj.exemplocarros.carro;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/carro")
@AllArgsConstructor
public class CarroController {

    private final CarroService carroService;

    @GetMapping(value = "/")
    public List<Carro> findAll() {
        return carroService.findAll();
    }

    @PostMapping(value = "/popula")
    public void save() {

        Carro carro1 = new Carro();
        carro1.setModelo("Gol");

        carroService.save(carro1);

        Carro carro2 = new Carro();
        carro2.setModelo("Uno");

        carroService.save(carro2);

    }
}
