package fr.ans.psc.dam.api;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fr.ans.psc.dam.api.called.PscTokenValidator;
import fr.ans.psc.dam.api.exception.ThrowDamException;
import fr.ans.psc.dam.model.PsDAMs;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DamsApiImpl implements DamsApiDelegate {

	public static final String WITH_GRAVITEE_TRUE = "true";
	public static final String HEADER_NAME_AUTHORIZATION = "Authorization";
	public static final String TOKEN_HEADER_PREFIX_BEARER = "Bearer";

	@Value("${with.gravitee:false}")
	private String withGravitee;

	@Autowired
	ApiExecutor exec;

	@Autowired
	PscTokenValidator psc;

	@Override
	public ResponseEntity<PsDAMs> dams(String idNational, Boolean dontFermes, String idTechniqueStructure,
			String modeExercice) {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					String exampleString = "{ \"nationalId\" : \"nationalId\", \"dams\" : [ { \"modeExercice\" : \"modeExercice\", \"habilitationLot\" : true, \"codeSpecialite\" : \"codeSpecialite\", \"specialite\" : \"specialite\", \"numAssuranceMaladie\" : \"numAssuranceMaladie\", \"agrement3\" : \"agrement3\", \"agrement1\" : \"agrement1\", \"agrement2\" : \"agrement2\", \"raisonSociale\" : \"raisonSociale\", \"codeTypeIdentifiant\" : \"codeTypeIdentifiant\", \"codeZoneIK\" : \"codeZoneIK\", \"identifiantLieuDeTravail\" : \"identifiantLieuDeTravail\", \"dateFinValidite\" : \"dateFinValidite\", \"dateDebutValidite\" : \"dateDebutValidite\", \"indicateurFacturation\" : \"indicateurFacturation\", \"codeZoneTarifaire\" : \"codeZoneTarifaire\", \"codeAgrement2\" : \"codeAgrement2\", \"codeAgrement3\" : \"codeAgrement3\", \"codeAgrement1\" : \"codeAgrement1\", \"codeModeExercice\" : \"codeModeExercice\", \"zoneTarifaire\" : \"zoneTarifaire\", \"codeConventionnel\" : \"codeConventionnel\", \"zoneIK\" : \"zoneIK\", \"typeIdentifiant\" : \"typeIdentifiant\", \"habilitationFse\" : true, \"codeIndicateurFacturation\" : \"codeIndicateurFacturation\", \"numActivite\" : \"numActivite\", \"conventionnement\" : \"conventionnement\" }, { \"modeExercice\" : \"modeExercice\", \"habilitationLot\" : true, \"codeSpecialite\" : \"codeSpecialite\", \"specialite\" : \"specialite\", \"numAssuranceMaladie\" : \"numAssuranceMaladie\", \"agrement3\" : \"agrement3\", \"agrement1\" : \"agrement1\", \"agrement2\" : \"agrement2\", \"raisonSociale\" : \"raisonSociale\", \"codeTypeIdentifiant\" : \"codeTypeIdentifiant\", \"codeZoneIK\" : \"codeZoneIK\", \"identifiantLieuDeTravail\" : \"identifiantLieuDeTravail\", \"dateFinValidite\" : \"dateFinValidite\", \"dateDebutValidite\" : \"dateDebutValidite\", \"indicateurFacturation\" : \"indicateurFacturation\", \"codeZoneTarifaire\" : \"codeZoneTarifaire\", \"codeAgrement2\" : \"codeAgrement2\", \"codeAgrement3\" : \"codeAgrement3\", \"codeAgrement1\" : \"codeAgrement1\", \"codeModeExercice\" : \"codeModeExercice\", \"zoneTarifaire\" : \"zoneTarifaire\", \"codeConventionnel\" : \"codeConventionnel\", \"zoneIK\" : \"zoneIK\", \"typeIdentifiant\" : \"typeIdentifiant\", \"habilitationFse\" : true, \"codeIndicateurFacturation\" : \"codeIndicateurFacturation\", \"numActivite\" : \"numActivite\", \"conventionnement\" : \"conventionnement\" } ] }";
					ApiUtil.setExampleResponse(request, "application/json", exampleString);
					break;
				}
			}
		});
		log.debug(
				"DamsApiImpl::dams Demande pour:\n\t idNational: {} \n\t idTechStrcu: {} \n\t modeExercice {} \n\t fermes {}",
				idNational, idTechniqueStructure, modeExercice, dontFermes);

		// récupération du token PSC dans les headers
		String pscToken = extractPscTokenFromHeaders();
		// si le ws n'est pas derrière gravitee => il faut vérifier la validité du token
		if (!withGravitee.equalsIgnoreCase(WITH_GRAVITEE_TRUE)) {
			log.debug("Appel introspection de PSC pour vérifier la validité du token ...");
			psc.tokenValidator(pscToken);
		}
		else
		{
			log.debug("Fonctionnement avec Gravitee => pas de vérification de la validité du token PSC transmis");
		}

		PsDAMs psDAMs = exec.getDAMs(/* accessToken, */ idNational, dontFermes, idTechniqueStructure, modeExercice);
		return new ResponseEntity<PsDAMs>(psDAMs, HttpStatus.OK);
	}

	public String extractPscTokenFromHeaders() {
		List<String> tmp = new ArrayList<String>();
		Enumeration<String> tokens = getHttpRequest().getHeaders(HEADER_NAME_AUTHORIZATION);
		while (tokens.hasMoreElements()) {
			log.debug("Au moins un header 'Authorization' trouvé ");
			String token = tokens.nextElement();
			if (token.startsWith(TOKEN_HEADER_PREFIX_BEARER)) {
			//	tmp.add(StringUtils.deleteWhitespace(token).substring(TOKEN_HEADER_PREFIX_BEARER.length()));
				tmp.add(token.replaceAll("\\s", "").substring(TOKEN_HEADER_PREFIX_BEARER.length()));
				log.debug("token 'Bearer' trouvé dans un header 'Authorization': {} ", token);
			}
		}
		if ((tmp.size() == 0) || (tmp.size() > 1)) {
			ThrowDamException.throwExceptionRequestError("Token non trouvé dans les headers de la requête (ou plusieurs token)",
					HttpStatus.BAD_REQUEST);
		}
		log.debug("accessToken received (without prefix): {}", tmp.get(0));

		return tmp.get(0);
	}

	public HttpServletRequest getHttpRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return attrs.getRequest();
	}
}
