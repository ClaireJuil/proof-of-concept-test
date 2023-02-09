package fr.ans.psc.dam.bdd.oracle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import fr.ans.psc.RassStructureIDApiApplication;
import fr.ans.psc.rass.model.StructureIds;
import fr.ans.psc.rass.repository.OracleStructureIdsRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = RassStructureIDApiApplication.class)
@DirtiesContext
@ActiveProfiles("test-oracle")
@Slf4j
public class OracleQueryStructureIdsTest {

	@Autowired
	OracleStructureIdsRepository repo;

	@Disabled
	@Test
	public void geIdsTest() {

		StructureIds ids = repo.findByStructureTechnicalId("F38000415001072019"); 
		assertNotNull(ids);
		assertEquals("F38000415001072019", ids.getStructureTechnicalId());
		assertEquals("380004150", ids.getIdentifiantMetier());
		log.info("ids value:  {}", ids.getIdentifiantMetier());
	}

}
