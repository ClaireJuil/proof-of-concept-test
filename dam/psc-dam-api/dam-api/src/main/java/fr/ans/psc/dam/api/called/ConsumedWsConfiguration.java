package fr.ans.psc.dam.api.called;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class ConsumedWsConfiguration {

	@Value("${damreader.url}")
	private String damReaderUrl;

	@Value("${structurereader.url}")
	private String structureReaderUrl;
}
