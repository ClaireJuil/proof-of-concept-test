package fr.ans.psc.dam.api.exception;

import org.springframework.http.HttpStatus;

public class ThrowDamException {

	private ThrowDamException () {};
	
	public static void throwExceptionRequestError(String msg, HttpStatus status) {
		var erreur = new fr.ans.psc.dam.model.Error();
		erreur.setCode(status.toString());
		erreur.setMessage(msg);
		throw new DamRequestException(erreur, status);
	}
}
