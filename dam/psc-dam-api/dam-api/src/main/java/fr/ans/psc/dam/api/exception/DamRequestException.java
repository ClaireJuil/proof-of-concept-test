package fr.ans.psc.dam.api.exception;

import org.springframework.http.HttpStatus;
import fr.ans.psc.dam.model.Error;

import lombok.Getter;
import lombok.Setter;

public class DamRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	private Error erreur;

	@Getter
	@Setter
	private HttpStatus statusARetourner = HttpStatus.INTERNAL_SERVER_ERROR;

	public DamRequestException(fr.ans.psc.dam.model.Error erreur, HttpStatus statusARetourner) {
		super();
		this.erreur = erreur;
		this.statusARetourner = statusARetourner;
	}
}
