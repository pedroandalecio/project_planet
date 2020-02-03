package br.com.planet.services.planet;

import java.util.List;
import org.springframework.data.domain.Pageable;

import br.com.planet.dto.PlanetCreateDTO;
import br.com.planet.dto.PlanetDTO;
import br.com.planet.dto.PlanetUpdateDTO;

public interface PlanetService {

	List<PlanetDTO> retrievePlanets(Pageable pageable);

	PlanetCreateDTO createPlanet(PlanetCreateDTO planetDTO);

	PlanetDTO findById(Integer id);

	void updatePlanet(PlanetUpdateDTO planetDTO);
	
	void delete(Integer id);
}
