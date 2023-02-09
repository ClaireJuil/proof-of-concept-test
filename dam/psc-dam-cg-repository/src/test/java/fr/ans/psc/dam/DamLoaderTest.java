package fr.ans.psc.dam;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import fr.ans.psc.DamApiApplication;
import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = DamApiApplication.class)
@DirtiesContext
@ActiveProfiles("test-mongo")
@Slf4j
class DamLoaderTest {

	@Autowired
	private DamLoader loader;

	@Autowired
	GenericRepository psRepository;

	@DisplayName(value = "loading file into Map and loading data into MongoDB")
	@Test
	void testLoadMapFromFile() throws Exception {
		File damFile = new File(Thread.currentThread().getContextClassLoader().getResource("data-mongo/dam-cg-distinct-test-13lignes.csv").getPath());

		loader.loadMapFromFile(damFile);
		// nombre PS en BDD
		assertEquals(11, loader.getPsMap().size());
		// PS avec 3 DAMs
		assertEquals(3, loader.getPsMap().get("0999103138").getDams().size());
		//verif DAM un PS avec unique DAM
		assertEquals(1, loader.getPsMap().get("899900007776").getDams().size());
		assertEquals("6", loader.getPsMap().get("899900007776").getDams().get(0).getCodeTypeIdentifiant());
		assertEquals("85", loader.getPsMap().get("899900007776").getDams().get(0).getCodeZoneTarifaire());
		assertEquals("01-01-2000",
				loader.getPsMap().get("899900007776").getDams().get(0).getDateDebutValidite());
		assertEquals("20-02-2022",
				loader.getPsMap().get("899900007776").getDams().get(0).getDateFinValidite());
		assertEquals("001", loader.getPsMap().get("899900007776").getDams().get(0).getHabilitationFse());
		
//		 vider les données existantes
		if (loader.getMongoTemplate().collectionExists(Ps.class)) {
				loader.getMongoTemplate().dropCollection(Ps.class);
		} 
		loader.loadDb();
		Ps ps = psRepository.findByNationalId("899900007776");
		assertEquals(1, ps.getDams().size());
		assertEquals("85", ps.getDams().get(0).getCodeZoneTarifaire());
	
//		Ps cpe = psRepository.findByNationalId("10B102332000/CPET0001");
//		assertEquals(1, cpe.getDams().size());
	}
	
	@Test
	void testLoadMapFromFileWithDateNull() throws Exception {
		File damFile = new File(Thread.currentThread().getContextClassLoader().getResource("data-mongo/dam-cg-distinct-test-13lignes-DateNull.csv").getPath());

		loader.loadMapFromFile(damFile);
		// nombre PS en BDD
		assertEquals(11, loader.getPsMap().size());
	
		//verif DAM un PS avec unique DAM
		assertEquals(1, loader.getPsMap().get("899900007776").getDams().size());
		assertEquals("6", loader.getPsMap().get("899900007776").getDams().get(0).getCodeTypeIdentifiant());
		assertEquals("85", loader.getPsMap().get("899900007776").getDams().get(0).getCodeZoneTarifaire());
		log.info("... {}", loader.getPsMap().get("899900007776").getDams().get(0).getDateDebutValidite());
		assertNull(loader.getPsMap().get("899900007776").getDams().get(0).getDateDebutValidite());
		assertNull(loader.getPsMap().get("899900007776").getDams().get(0).getDateDebutValidite());

		assertEquals("001", loader.getPsMap().get("899900007776").getDams().get(0).getHabilitationFse());

		if (loader.getMongoTemplate().collectionExists(Ps.class)) {
			loader.getMongoTemplate().dropCollection(Ps.class);
	} 
		loader.loadDb();
		Ps ps = psRepository.findByNationalId("899900007776");
		assertEquals(1, ps.getDams().size());
		assertEquals("85", ps.getDams().get(0).getCodeZoneTarifaire());
	}


}
