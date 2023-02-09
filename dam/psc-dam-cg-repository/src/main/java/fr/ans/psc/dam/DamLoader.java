/*
 * Copyright A.N.S 2021
 */
package fr.ans.psc.dam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.any23.encoding.TikaEncodingDetector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import com.univocity.parsers.common.DataProcessingException;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.model.SimpleDam;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class MapsHandler.
 */
@Getter
@Setter

/**
 * Can equal.
 *
 * @param other the other
 * @return true, if successful
 */
@EqualsAndHashCode()
@Slf4j
@Service
@ConditionalOnProperty(name = "database.type", havingValue = "mongo", matchIfMissing = false)
@EnableMongoRepositories
public class DamLoader {
//	private static final HashSet<Character> cpeIdTypes = new HashSet<Character>();
//
//	static {
//		cpeIdTypes.add('1');
//		cpeIdTypes.add('3');
//		cpeIdTypes.add('4');
//		cpeIdTypes.add('5');
//		cpeIdTypes.add('6');
//	}

	private final MongoTemplate mongoTemplate;
	
	private static final int ROW_LENGTH = 19;


	private Map<String, Ps> psMap = new HashMap<>();

	public DamLoader(MongoTemplate mongoTemplate) {
		super();
		this.mongoTemplate = mongoTemplate;

	}

//
//	private static boolean isCPEtype(Character c) {
//		return cpeIdTypes.contains(c);
//	}
	/**
	 * Load maps from file.
	 *
	 * @param file the file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void loadMapFromFile(File file) throws IOException, IllegalArgumentException, DataProcessingException {
		log.info("loading {} into list of Ps", file.getName());

		psMap.clear();

		// ObjectRowProcessor converts the parsed values and gives you the resulting
		// row.
		ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
			@Override
			public void rowProcessed(Object[] objects, ParsingContext parsingContext) {
				if (objects.length != ROW_LENGTH) {
					throw new IllegalArgumentException();
				}
				String[] items = Arrays.asList(objects).toArray(new String[ROW_LENGTH]);
				// test if exists by nationalId (item 0)
				Ps psMapped = psMap.get(items[0]);
				if (psMapped == null) {
					// create PS and add to map
					Ps psRow = new Ps(items);
					psMap.put(psRow.getNationalId(), psRow);
				} else {
					// if ps exists then add dam.
					SimpleDam dam = new SimpleDam(items);
					psMapped.getDams().add(dam);
				}
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
			Charset detectedCharset = Charset.forName(new TikaEncodingDetector().guessEncoding(is));
			log.info("extract RASS detected encoding is: {} ", detectedCharset.displayName());
			parser.parse(new BufferedReader(new FileReader(file, detectedCharset)));
			log.info("loading into Map complete!");
		} catch (IOException e) {
			throw new IOException("Encoding detection failure", e);

		} finally {
			is.close();
		}
		log.info("PS Map size: {}", psMap.size());
	}

	public void loadDb() {
		log.info("load Map in Mongo DB beginning..");
		Collection<Ps> items = psMap.values();
		items.parallelStream().forEach(item -> {
			mongoTemplate.save(item);
	});

		log.info("load Map in Mongo DB done!");
	}
}
