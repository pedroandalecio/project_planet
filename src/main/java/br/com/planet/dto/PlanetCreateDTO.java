package br.com.planet.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class PlanetCreateDTO {

	@JsonIgnore
	private Integer id;
	private String description;
	private String name;
}
