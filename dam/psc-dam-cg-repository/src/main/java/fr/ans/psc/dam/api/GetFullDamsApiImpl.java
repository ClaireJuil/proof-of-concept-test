package fr.ans.psc.dam.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.model.SimpleDam;
import fr.ans.psc.dam.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GetFullDamsApiImpl implements GetFullDamsApiDelegate {

	@Autowired
	GenericRepository repo;

	 public ResponseEntity<List<SimpleDam>> lectureDesDAMs(String nationalId) {
	        getRequest().ifPresent(request -> {
	            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
	                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
	                    String exampleString = "{ \"dateDebutValidite\" : \"dateDebutValidite\", \"habilitationLot\" : \"habilitationLot\", \"codeZoneTarifaire\" : \"codeZoneTarifaire\", \"codeAgrement2\" : \"codeAgrement2\", \"codeAgrement3\" : \"codeAgrement3\", \"codeAgrement1\" : \"codeAgrement1\", \"numAssuranceMaladie\" : \"numAssuranceMaladie\", \"codeModeExercice\" : \"codeModeExercice\", \"codeConventionnel\" : \"codeConventionnel\", \"raisonSociale\" : \"raisonSociale\", \"habilitationFse\" : \"habilitationFse\", \"codeTypeIdentifiant\" : \"codeTypeIdentifiant\", \"codeZoneIK\" : \"codeZoneIK\", \"identifiantLieuDeTravail\" : \"identifiantLieuDeTravail\", \"dateFinValidite\" : \"dateFinValidite\", \"codeIndicateurFacturation\" : \"codeIndicateurFacturation\", \"numActivite\" : \"numActivite\" }";
	                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
	                    break;
	                }
	            }
	        });

		log.debug("Request getFullDams IN with enocdeNationalId: {} ", nationalId);
		String decodedNationalId = null;
		try {
			decodedNationalId = URLDecoder.decode(nationalId, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("Erreur sur le decodage du NationalId {]", nationalId);
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		log.debug("Request getFullDams IN with decodedNationalId: {} ", decodedNationalId);
		Ps ps = repo.findByNationalId(decodedNationalId);
		if (ps == null) {
			log.debug("PS with nationalId: {} not found or no DAMs in DAtabase , decodedNationalId ");
			return new ResponseEntity<>(HttpStatus.GONE);
		} else {
			return new ResponseEntity<List<SimpleDam>>(ps.getDams(), HttpStatus.OK);
		}
	}
}