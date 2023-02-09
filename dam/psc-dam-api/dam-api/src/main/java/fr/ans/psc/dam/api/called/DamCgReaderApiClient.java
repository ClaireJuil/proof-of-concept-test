package fr.ans.psc.dam.api.called;

import org.springframework.beans.factory.annotation.Value;

import fr.ans.psc.api.client.dam.reader.invoker.ApiClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DamCgReaderApiClient extends ApiClient {

	public DamCgReaderApiClient(@Value("${damreader.url}") String damReaderUrl) {

		log.info("DamCgReaderApiClient  damreader.url= {}", damReaderUrl);
		setBasePath(damReaderUrl);
		log.info("DamCgReaderApiClient setBasePath done!");
	}

}
