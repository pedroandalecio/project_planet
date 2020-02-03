package br.com.planet.entities;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity(name = "planet")
@DynamicUpdate
public class Planet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_planet")
    @SequenceGenerator(sequenceName = "seq_planet", allocationSize = 1, name = "seq_planet")
	private Integer id;
	
	@Length(min = 3, max = 60)
	private String name;
	
	@Length(min = 5, max = 255)
	private String description;
	
	@Column(name = "date_register")
	private LocalDate dateRegister;
	
	private Boolean status;
}
