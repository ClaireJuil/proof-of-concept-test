package fr.ans.psc.dam.api.called;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import fr.ans.psc.dam.api.exception.ThrowDamException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "with.gravitee", havingValue = "false", matchIfMissing = true)
public class PscTokenValidatorImpl implements PscTokenValidator {

	@Value("${psc.url}")
	private String pscUrl;

	@Value("${psc.clientID}")
	private String pscClientID;

	@Value("${psc.clientSecret}")
	private String pscSecret;

	// champs reponse intropestion PSC
	public static final String TOKEN_ACTIVE_FIELD = "active";
	public static final String TOKEN_ACTIVE_FALSE = "false";
	public static final String TOKEN_ACTIVE_TRUE = "true";
	public static HttpHeaders headers = new HttpHeaders();

	private URI uri = null;

	private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

	@Autowired
	RestTemplate restTemplate;

	@PostConstruct
	public void setup() {
		String adminuserCredentials = pscClientID + ":" + pscSecret;
		String encodedCredentials = null;
		try {
			encodedCredentials = encodeBase64(adminuserCredentials);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("Execption UnsupportedEncodingException in setup of PscTokenValidator class");
		}
		headers.clear(); // for test
		headers.add("Authorization", "Basic " + encodedCredentials);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		try {
			uri = new URI(pscUrl);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			log.error("Execption URISyntaxException in setup of PscTokenValidator class");
		}
	}

//	@Override
//	public Boolean withGravitee() {
//		return false;
//	}

	public String isTokenActive(String accessToken) {

		// payload
		// MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.clear();
		params.add("token", accessToken);
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, requestEntity, String.class);

		log.debug("Appel ProSanteConnect Response: StatusCode {} \n body: {}", result.getStatusCode(),
				result.getBody());
		return result.getBody();
	}

	public static final String encodeBase64(String stringToEncode) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(stringToEncode.getBytes("UTF-8"));
	}

	@Override
	public void tokenValidator(String accessToken) {

		// vérification du token
		String pscResponse = null;
		log.debug(" appel ProSante Connect avec token: {} ....", accessToken);
		try {
			pscResponse = isTokenActive(accessToken);
		} catch (Exception e) {
			log.error("** Erreur sur appel Prosante Connect: {} \n .. {}", e.getCause(), e.getMessage());
			ThrowDamException.throwExceptionRequestError("Erreur technique sur appel de ProSante Connect",
					HttpStatus.SERVICE_UNAVAILABLE);
		}
		parsePSCresponse(pscResponse);
	}

	public static void parsePSCresponse(String reponsePSC) {
		ObjectNode node = null;
		try {
			node = new ObjectMapper().readValue(reponsePSC, ObjectNode.class);
		} catch (Exception e) {
			log.error("Erreur technique durant le parse de la reponse d'intropection } ", e.getMessage());
			log.debug(e.toString());
			ThrowDamException.throwExceptionRequestError(
					"Erreur technique sur la lecture de la réponse ProSante Connect (introspection) ",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (node.has(TOKEN_ACTIVE_FIELD)) {
			String token_active_field = node.get(TOKEN_ACTIVE_FIELD).asText();
			if (token_active_field.equalsIgnoreCase(TOKEN_ACTIVE_TRUE)) {
				log.debug("token actif OK");
				return;
			} else {
				log.debug("Token ProSante Connect non actif {}: ", reponsePSC);
				ThrowDamException.throwExceptionRequestError("Token ProSante Connect non actif : " + reponsePSC,
						HttpStatus.UNAUTHORIZED);
			}
		} else {
			log.error("Reponse invalide introspection PSC: champ {} non trouvé", TOKEN_ACTIVE_FIELD);
			ThrowDamException.throwExceptionRequestError(
					"Reponse invalide introspection PSC: champ {} non trouvé " + TOKEN_ACTIVE_FIELD,
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
