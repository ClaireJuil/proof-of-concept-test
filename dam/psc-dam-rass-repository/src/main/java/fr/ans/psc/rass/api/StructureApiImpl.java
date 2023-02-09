package fr.ans.psc.rass.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import fr.ans.psc.dam.api.ApiUtil;
import fr.ans.psc.dam.api.StructureApiDelegate;
import fr.ans.psc.rass.model.StructureIds;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StructureApiImpl implements StructureApiDelegate {

	@Autowired
	ApiExecutor exec;

	@Override
	public ResponseEntity<StructureIds> getIds(String structureTechnicalId) {
		log.debug("StructureApiImpl::getIds IN structureTechnicalId: {} ", structureTechnicalId);
		getRequest().ifPresent(request -> {

			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					String exampleString = "{ \"structureTechnicalId\" : \"structureTechnicalId\" }";
					ApiUtil.setExampleResponse(request, "application/json", exampleString);
					break;
				}
			}
		});
		log.debug("StructureApiImpl::getIds traitement pour structureTechnicalId: {} ", structureTechnicalId);
		StructureIds ps = exec.getIds(structureTechnicalId);
		if (ps == null) {
			log.debug("StructureApiImpl::getIds -- !! Pas d'Id métier trouvé pour structureTechnicalId: {} ",
					structureTechnicalId);
			return new ResponseEntity<>(HttpStatus.GONE);
		}

		return new ResponseEntity<>(ps, HttpStatus.OK);
	}

}