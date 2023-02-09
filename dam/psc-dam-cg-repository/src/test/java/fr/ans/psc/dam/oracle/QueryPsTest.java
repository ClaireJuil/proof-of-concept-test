package fr.ans.psc.dam.oracle;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import fr.ans.psc.DamApiApplication;
import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = DamApiApplication.class)
@DirtiesContext
@ActiveProfiles("test-oracle")
@Slf4j
public class QueryPsTest {

	@Autowired
	GenericRepository repo;

//	@Disabled("Nécessite une connexion à une base ...")
	@Test
	public void getDAMsTest() {

		Ps ps = repo.findByNationalId("0336138649"); 
		assertNotNull(ps);

		log.info("ps.dams size:  {}", ps.getDams().size());
		log.info("Raison Sociale Elt 0:  {}", ps.getDams().get(0).getRaisonSociale());
	}

	@Test
	public void getCpeDAMsTest() {

		Ps ps = repo.findByNationalId("538237299300511/0000003571"); 
		assertNotNull(ps);

		log.info("ps.dams size:  {}", ps.getDams().size());
		log.info("Raison Sociale Elt 0:  {}", ps.getDams().get(0).getRaisonSociale());
	}
}
