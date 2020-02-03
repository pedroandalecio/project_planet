package br.com.planet.dto;

import lombok.Data;

@Data
public class PlanetUpdateDTO {

	private Integer id;
	private String description;
	private String name;
	private Boolean status;

}
