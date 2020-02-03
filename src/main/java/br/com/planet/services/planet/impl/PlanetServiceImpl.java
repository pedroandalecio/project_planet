package br.com.planet.services.planet.impl;

import static java.util.Objects.isNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.planet.dto.PlanetCreateDTO;
import br.com.planet.dto.PlanetDTO;
import br.com.planet.dto.PlanetUpdateDTO;
import br.com.planet.entities.Planet;
import br.com.planet.exceptions.PlanetException;
import br.com.planet.repositories.PlanetRepository;
import br.com.planet.services.planet.PlanetService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlanetServiceImpl implements PlanetService {

	@Autowired
	private PlanetRepository planetRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PlanetDTO> retrievePlanets(Pageable pageable) {
		log.info("Retrieve Planets ");
		Page<Planet> planets = this.planetRepository.findAll(pageable);
		log.info("Retrieved {} Planets", planets.getTotalElements());
		return PlanetDTO.getList(planets);
	}

	@Override
	public PlanetCreateDTO createPlanet(final PlanetCreateDTO planetDTO) {
		if (isNull(planetDTO)) {
			log.error("planet is null");
			throw new IllegalArgumentException("planet is null");
		}
		log.info("Creating planet {}", planetDTO);
		log.info("Saving planet");

		Planet planet = modelMapper.map(planetDTO, Planet.class);

		planet.setDateRegister(LocalDate.now());
		planet.setStatus(true);
		
		return modelMapper.map(this.planetRepository.save(planet), PlanetCreateDTO.class);
	}

	@Override
	public PlanetDTO findById(final Integer planetId) {
		if (planetId == null) {
			log.error("planetId can not be null");
			throw new IllegalArgumentException("planetId is null");
		}
		Optional<Planet> planet = planetRepository.findById(planetId);
		if (!planet.isPresent()) {
			log.warn("Planet id {}  not found", planetId);
			throw new PlanetException("Planet id [ " + planetId + " ] not found");
		}

		log.info("Planet found {} ", planet.get());

		return modelMapper.map(planet.get(), PlanetDTO.class);
	}

	@Override
	public void updatePlanet(PlanetUpdateDTO planetDTO) {

		Optional<Planet> planetExist = planetRepository.findById(planetDTO.getId());

		if (planetExist.isPresent()) {
			log.info("Updating planet {}", planetExist);
			
			planetExist.get().setDescription(planetDTO.getDescription());
			planetExist.get().setName(planetDTO.getName());
			planetExist.get().setStatus(planetDTO.getStatus());
			
			log.info("Saving planet");
			this.planetRepository.save(planetExist.get());
			log.info("Planet updated {}", planetExist.get());
		} else {
			log.error("planet not updated : ", planetDTO.getId());
			throw new IllegalArgumentException("planet not updated : " + planetDTO.getId());
		}
	}

	@Override
	public void delete(Integer id) {
		Optional<Planet> result = this.planetRepository.findById(id);

		if (!result.isPresent()) {
			throw new EntityNotFoundException("Planet not found");
		}

		Planet planet = result.get();
		log.info("Deleting planet {}", planet);

		this.planetRepository.delete(planet);
		log.info("Planet deleted {}", planet);
	}
}
