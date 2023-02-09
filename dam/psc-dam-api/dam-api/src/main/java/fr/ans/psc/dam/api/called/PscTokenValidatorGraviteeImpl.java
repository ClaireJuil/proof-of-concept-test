package fr.ans.psc.dam.api.called;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fr.ans.psc.dam.api.exception.ThrowDamException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "with.gravitee", havingValue="true", matchIfMissing=false)
public class PscTokenValidatorGraviteeImpl  implements PscTokenValidator{
	
	@Override
	public void tokenValidator(String accessToken) {
		String msg = "Erreur technique sur la vérification du token psc";
		log.error(msg + " => méthode non implémenté pour un fonctionnement avec gravitee! ");
		ThrowDamException.throwExceptionRequestError(msg,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
