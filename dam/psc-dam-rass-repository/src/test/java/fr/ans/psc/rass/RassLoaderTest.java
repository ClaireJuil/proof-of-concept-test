package fr.ans.psc.rass;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import fr.ans.psc.RassStructureIDApiApplication;
import fr.ans.psc.rass.repository.GenericRepository;

@SpringBootTest
@ContextConfiguration(classes = RassStructureIDApiApplication.class)
@DirtiesContext
@ActiveProfiles("test-mongo")
public class RassLoaderTest {
	

		@Autowired
		private RassLoader loader;

		@Autowired
		GenericRepository psRepository;

		@DisplayName(value = "loading file into Map and loading data into MongoDB")
		@Test
		void testLoadMapFromFile() throws Exception {
			File structureFile = new File(Thread.currentThread().getContextClassLoader().getResource("data-mongo/rass-structure.csv").getPath());

			//chargement du fichier d'extraction du RASS dans une Map
			loader.loadMapFromFile(structureFile);
			// nombre de lignes
			assertEquals(8, loader.getListe().size());
			
			assertEquals(loader.getListe().get(0).getIdentifiantMetier(),"59650306000");
			assertEquals(loader.getListe().get(0).getStructureTechnicalId(),"C59650306000");
			
			assertEquals(loader.getListe().get(7).getIdentifiantMetier(),"10002525375006");
			assertEquals(loader.getListe().get(7).getStructureTechnicalId(),"R10000000553881");
			
			//insertion en BDD
			loader.loadDb();
			
			//relecture/vérif
			assertEquals(psRepository.findByStructureTechnicalId("C59650306000").getIdentifiantMetier(), "59650306000");
			assertEquals(psRepository.findByStructureTechnicalId("R10000000553881").getIdentifiantMetier(), "10002525375006");	
		}
		
	

}
