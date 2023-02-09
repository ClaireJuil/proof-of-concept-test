package fr.ans.psc.dam.mongo;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.jupiter.tools.spring.test.mongo.annotation.MongoDataSet;

import fr.ans.psc.DamApiApplication;
import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ContextConfiguration(classes = DamApiApplication.class)
@DirtiesContext
@ActiveProfiles("test-mongo")
@Slf4j

public class MongoQueryTest {
	
	@Autowired
	GenericRepository repo;

	
	@MongoDataSet(value = "/data-mongo/ps_test_899900007776_1dam.json", cleanBefore = true, cleanAfter = true)
//	@Test //=> TODO LOAD DataSet...
	public void getDAMsTest() {	
		
		
		//log.info("Nbre de doc en base: {}", );
		Ps ps = repo.findByNationalId("899900007776");
		assertNotNull(ps);
		log.info("Mongo ps.dams size:  {}", ps.getDams().size());		
		log.info("identifiantLieuDeTravail:  {}", ps.getDams().get(0).getIdentifiantLieuDeTravail());		
	}
}
