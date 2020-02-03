package br.com.planet.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.planet.entities.Planet;
import lombok.Data;

@Data
public class PlanetDTO {

	private Integer id;
	private String description;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dateRegister;
	private String name;
	private Boolean status;
	
	public static List<PlanetDTO> getList(Page<Planet> page) {

        List<Planet> list = page.getContent();
        List<PlanetDTO> listDTO = new ArrayList<>();
        if(!list.isEmpty()) {
        	list.forEach(item -> {
            	listDTO.add(new PlanetDTO(item));
    		});
        }
        return listDTO;
    }

	public PlanetDTO(Planet planet) {
		this.id = planet.getId();
		this.description = planet.getDescription();
		this.dateRegister = planet.getDateRegister();
		this.status = planet.getStatus();
		this.name = planet.getName();
	}
	
	public PlanetDTO() {
		
	}
}
