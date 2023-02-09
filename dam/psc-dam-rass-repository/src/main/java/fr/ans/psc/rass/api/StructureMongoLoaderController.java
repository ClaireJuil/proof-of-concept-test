package fr.ans.psc.rass.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.univocity.parsers.common.DataProcessingException;

import fr.ans.psc.rass.RassLoader;
import fr.ans.psc.rass.model.Rapport;
import fr.ans.psc.rass.model.StructureIds;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@ConditionalOnProperty(name = "database.type", havingValue = "mongo",matchIfMissing = false)
public class StructureMongoLoaderController {

	private static final Path TMP_PATH = Paths.get(System.getProperty("java.io.tmpdir"));


	private Map<String, StructureIds> map = new HashMap<>();
	
	@Autowired
	RassLoader loader;


	@PostMapping(value = "/mongo/load")
	public ResponseEntity<Rapport> loadMongo(@RequestParam MultipartFile file) {
		log.info("Reload Mongo DB Request IN ..");
			log.info("StructureMongoLoaderController  Request IN .. with file: {}, size {}: ", file.getName(), file.getSize());
		File fileToLoad = null;
		fileToLoad = getMultiPartFile(file);	
		map.clear();
		
		
		try {
			loader.loadMapFromFile(fileToLoad);
		} catch (DataProcessingException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		
		//chargement des nouvelles données
		loader.loadDb();
		Rapport rapport = new Rapport();
		rapport.setNumberTechStruture(Integer.toString(loader.getListe().size()));
		rapport.setValid(true);
		return new ResponseEntity<Rapport>(rapport, HttpStatus.OK);

	}

	public File multipartFileToFile(MultipartFile multipart) throws IOException {

		String unsafeFileName = multipart.getOriginalFilename();
		String pureFilename = (new File(unsafeFileName)).getName();

		Path tempFile = Files.createTempFile(TMP_PATH, pureFilename, null);
		log.debug(" multipart.getOriginalFilename: {}. Use {} as name", unsafeFileName, pureFilename);
		log.debug("tempFile {}", tempFile.getFileName().toString());
		multipart.transferTo(tempFile);
		return tempFile.toFile();

	}

public File getMultiPartFile(MultipartFile file) {
		File fileToLoad = null;
		try {
			fileToLoad = multipartFileToFile(file);
			log.debug("file received: isFile: {} \t name: {} \t length {}", fileToLoad.isFile(), fileToLoad.getName(),
					fileToLoad.length());
		} catch (IOException e) {

		}
		return fileToLoad;
	}
}
