package fr.ans.psc.dam.api.called;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.Base64;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import fr.ans.psc.AppTest;
import fr.ans.psc.dam.api.exception.DamRequestException;

@SpringBootTest
@ActiveProfiles("test-withoutgravitee")
@Import(AppTest.class) // bean RestTemplate
@DirtiesContext
public class ProSanteConnectTest {

	@Autowired
	PscTokenValidatorImpl psc;

	@Value("${psc.url}")
	private String pscUrl;

	@Value("${psc.clientID}")
	private String pscClientID;

	@Value("${psc.clientSecret}")
	private String pscSecret;


	public static String AUTHORISATION = "Authorization";

	@Test
	public void connexionParams() throws UnsupportedEncodingException {
		// test setup methode

		// les paramètres de connexion sont bien ceux du properties de test
		assertTrue(pscClientID.equals("clientID-test"));
		assertTrue(pscSecret.equals("secret-client-test"));
		assertTrue(pscUrl.equals("https://serveur.fr/path/introspect"));

		String adminuserCredentials = pscClientID + ":" + pscSecret;
		assertNotNull(psc);
		assertNotNull(psc.headers);

		assertTrue(psc.headers.getAccept().contains(MediaType.APPLICATION_JSON));
		assertEquals(psc.headers.getContentType(), MediaType.APPLICATION_FORM_URLENCODED);
		String authorisation = psc.headers.get(AUTHORISATION).toString();
		assertTrue(authorisation.contains("Basic"));
		String encodedAuth = authorisation.substring(7, authorisation.length() - 1);
		String authen = new String(Base64.getDecoder().decode(encodedAuth), "UTF-8");
	//	assertEquals(authen, pscClientID + ":" + pscSecret);
		assertEquals(authen, adminuserCredentials);

	}

	@Test
	public void encode64Test() throws UnsupportedEncodingException {
		String encoded = psc.encodeBase64("clientID-test:secret-client-test");
		assertEquals(encoded, "Y2xpZW50SUQtdGVzdDpzZWNyZXQtY2xpZW50LXRlc3Q=");
	}

	@Test
	public void parsePSCResponseTest() throws IOException {
		// non parsable
		String reponsePSC = "raiseExceptionExpexted";
		DamRequestException ee;
		try {
			psc.parsePSCresponse(reponsePSC);
			assertTrue(false, "Une exception aurait dû levée, reponse PSC non parsable");
		} catch (Exception e) {

			assertEquals(e.getClass().getCanonicalName(), "fr.ans.psc.dam.api.exception.DamRequestException");
			ee = (DamRequestException) e;
			assertEquals(ee.getStatusARetourner(), HttpStatus.INTERNAL_SERVER_ERROR);
			assertTrue(
					ee.getErreur().getMessage()
							.contains("Erreur technique sur la lecture de la réponse ProSante Connect (introspection)"),
					"le message d'erreur n'est pas celui attendu");
			assertEquals(ee.getErreur().getCode(), "500 INTERNAL_SERVER_ERROR",
					"le code d'erreur n'est pas celui attendu");
		}

		// token non actif
		reponsePSC = Files.readString(new ClassPathResource("PSC/PSC200_activefalse.json").getFile().toPath());
		try {
			psc.parsePSCresponse(reponsePSC);
			assertTrue(false, "Une exception aurait dû levée, token non actif");
		} catch (Exception e) {
			assertEquals(e.getClass().getCanonicalName(), "fr.ans.psc.dam.api.exception.DamRequestException");
			ee = (DamRequestException) e;
			assertEquals(ee.getStatusARetourner(), HttpStatus.UNAUTHORIZED);
			assertTrue(ee.getErreur().getMessage().contains("Token ProSante Connect non actif"),
					"le message d'erreur n'est pas celui attendu");
			assertEquals(ee.getErreur().getCode(), "401 UNAUTHORIZED", "le code d'erreur n'est pas celui attendu");
		}

		// token actif
		reponsePSC = Files.readString(
				new ClassPathResource("PSC/PSC200_activetrue_medecin_899700218896.json").getFile().toPath());
		try {
			psc.parsePSCresponse(reponsePSC);
		} catch (Exception e) {
			assertTrue(false, "une excpetion n'aurait pas dû être levée pour un token actif");
		}
	}
}
