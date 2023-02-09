package fr.ans.psc.rass.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Identifiants métier et techniques des structures
 */
@EqualsAndHashCode
@Getter
@Setter
@Document(collection = "structureIds")
public class StructureIds {

	@Id
	private String _id;
	
	@JsonProperty("identifiantMetier")
	@NotNull(message = "identifiantMetier should not be null")
	private String identifiantMetier;
	
	@JsonProperty("structureTechnicalId")
	@NotNull(message = "structureTechnicalId should not be null")
	@Indexed(unique = true)
	private String structureTechnicalId;

	public StructureIds() {

	}

	public StructureIds(String[] items) {
		structureTechnicalId = items[0];
		identifiantMetier = items[1];		
	}
}
