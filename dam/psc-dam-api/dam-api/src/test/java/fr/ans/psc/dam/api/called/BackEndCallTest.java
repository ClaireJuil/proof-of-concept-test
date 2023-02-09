package fr.ans.psc.dam.api.called;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import fr.ans.psc.AppTest;
import fr.ans.psc.dam.api.ApiExecutor;
import fr.ans.psc.dam.api.exception.DamRequestException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@ActiveProfiles("test-withgravitee")
@Import(AppTest.class) // bean RestTemplate
@DirtiesContext
@Slf4j
public class BackEndCallTest {

	@Autowired
	ConsumedWsConfiguration conf;

	@Autowired
	ApiExecutor apiExecutor;

	@Test
	public void connexionParams() {

		assertTrue(conf.getDamReaderUrl().contentEquals("http://localhostxxx:8080/dam-reader-api"));
		assertTrue(conf.getStructureReaderUrl().contentEquals("http://localhostxxx:8084/dam-structure-api"));
	}

	@Test
	public void CG_WebserviceOffTest() {
		try {
			apiExecutor.get_dams("peuImporte...");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(
					e.getClass().getCanonicalName().contentEquals("fr.ans.psc.dam.api.exception.DamRequestException"));
			DamRequestException damExcept = (DamRequestException) e;
			assertEquals(damExcept.getErreur().getCode(), "503 SERVICE_UNAVAILABLE");
		}
	}

	@Test
	public void RASS_WebserviceOffTest() {
		try {
			apiExecutor.readIdLieuDeTravail("peuImporte...");
			assertTrue(false);
		} catch (Exception e) {
			assertTrue(
					e.getClass().getCanonicalName().contentEquals("fr.ans.psc.dam.api.exception.DamRequestException"));
			DamRequestException damExcept = (DamRequestException) e;
			assertEquals(damExcept.getErreur().getCode(), "503 SERVICE_UNAVAILABLE");
		}
	}
	
}
