package br.com.planet.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import br.com.planet.entities.Planet;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<Planet, Integer> {

}
