package br.com.planet.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.planet.dto.PlanetCreateDTO;
import br.com.planet.dto.PlanetDTO;
import br.com.planet.dto.PlanetUpdateDTO;
import br.com.planet.services.planet.PlanetService;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @PostMapping(value = "/v1/planets")
    public ResponseEntity<PlanetCreateDTO> createPlanet(@RequestBody @Valid final PlanetCreateDTO planetDTO) {
        log.info("Create planet >>> {}", planetDTO);
        PlanetCreateDTO saved = planetService.createPlanet(planetDTO);
        log.info("Planet saved successfully {}", saved);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId()).toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @GetMapping(value = "/v1/planets")
    public ResponseEntity<List<PlanetDTO>> retrievePlanets(Pageable pageable) {
        log.info("Listing all planets.");
        List<PlanetDTO> planets = planetService.retrievePlanets(pageable);
        log.info("Total of planets found >>> {}", planets.size());
        return ResponseEntity.ok(planets);
    }

    @GetMapping(value = "/v1/planets/{id}")
    public ResponseEntity<PlanetDTO> retrievePlanet(@PathVariable("id") Integer id) {
        log.info("Retrieve planet id {}  ", id);
        PlanetDTO result = planetService.findById(id);
        log.info("Planet found >>> {} ", result);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/v1/planets/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable("id") Integer id) {
        log.info("Delete planet with id >>> {}", id);
        planetService.delete(id);
        log.info("Planet with id {} deleted successfully {}", id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/v1/planets/{id}")
    public ResponseEntity<PlanetUpdateDTO> updatePlanet(@RequestBody @Valid final PlanetUpdateDTO planetDTO, @PathVariable("id") Integer id) {
        log.info("Retrieve planet id {}  ", id);
        planetDTO.setId(id);
        planetService.updatePlanet(planetDTO);
        log.info("Planet found >>> {} ", planetDTO);
        return ResponseEntity.ok(planetDTO);
    }
}
