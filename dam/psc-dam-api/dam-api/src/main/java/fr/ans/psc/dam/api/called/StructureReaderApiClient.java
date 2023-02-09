package fr.ans.psc.dam.api.called;

import org.springframework.beans.factory.annotation.Value;

import fr.ans.psc.api.client.structure.reader.invoker.ApiClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StructureReaderApiClient extends ApiClient {

	public StructureReaderApiClient(@Value("${structurereader.url}") String structureReaderUrl) {

		log.info("StructureReaderApiClient   structurereader.url= {}", structureReaderUrl);
		setBasePath(structureReaderUrl);
		log.info("StructureReaderApiClient setBasePath done!");
	}

}
