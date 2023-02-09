package fr.ans.psc.dam.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Professionnel de santé
 */
@EqualsAndHashCode
@Getter
@Setter
@Document(collection = "psDams")
public class Ps {

	@Id
	private String _id;

	@JsonProperty("nationalId")
	@Indexed(unique = true)
	@NotNull(message = "nationalId should not be null")
	private String nationalId;

	@JsonProperty("dams")
	@Valid
	private List<SimpleDam> dams = null;

	public Ps(String[] items) {
		nationalId = items[0];
		addDamsItem(new SimpleDam(items));
	}

	public Ps() {

	}

	public Ps addDamsItem(SimpleDam damsItem) {
		if (this.dams == null) {
			this.dams = new ArrayList<SimpleDam>();
		}
		this.dams.add(damsItem);
		return this;
	}
}
