package fr.ans.psc.rass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import fr.ans.psc.rass.model.StructureIds;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@EqualsAndHashCode()
@Slf4j
@Getter
@Service
@ConditionalOnProperty(name = "database.type", havingValue = "mongo", matchIfMissing = false)
@EnableMongoRepositories
public class RassLoader {

	private final MongoTemplate mongoTemplate;

	private static final int ROW_LENGTH = 2;

	//private Map<String, StructureIds> map = new HashMap<>();
	private List<StructureIds> liste = new ArrayList<StructureIds>();

	public RassLoader(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;

	}

	public void loadMapFromFile(File file) throws IOException, IllegalArgumentException, DataProcessingException {
		log.info("loading {} into list of StructureIds", file.getName());

		liste.clear();

		// ObjectRowProcessor converts the parsed values and gives you the resulting
		// row.
		ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
			@Override
			public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
				if (objects.length != ROW_LENGTH) {
					throw new IllegalArgumentException();
				}
				String[] items = Arrays.asList(objects).toArray(new String[ROW_LENGTH]);
					StructureIds idsRow = new StructureIds(items);
					liste.add(idsRow);
					
			}

		};

		CsvParserSettings parserSettings = new CsvParserSettings();
		parserSettings.getFormat().setLineSeparator("\n");
		parserSettings.getFormat().setDelimiter(',');
		parserSettings.setProcessor(rowProcessor);
		parserSettings.setHeaderExtractionEnabled(true);
		parserSettings.setNullValue("");

		CsvParser parser = new CsvParser(parserSettings);
		// get file charset to secure data encoding
		InputStream is = new FileInputStream(file);
		try {
			parser.parse(new BufferedReader(new FileReader(file)));
			log.info("loading into List<StructureIds> complete!");
		} catch (IOException e) {
			throw new IOException("Encoding detection failure", e);

		} finally {
			is.close();
		}
		log.info("StructureIds List size: {}", liste.size());
	}

	public void loadDb() {
		if (mongoTemplate.collectionExists(StructureIds.class)) {
			log.info("Drop de la collection structureIds existante");
			mongoTemplate.dropCollection(StructureIds.class);
		} else {
			log.info("La collection StructureIds sera (re)créée");
		}
		log.info("load Map in Mongo DB beginning..");
		liste.parallelStream().forEach(item -> {
		mongoTemplate.save(item);
	});

		log.info("load List<StructureIds> in Mongo DB done!");
	}
}	
	
	
