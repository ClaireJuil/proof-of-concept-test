package fr.ans.psc.dam.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.ans.psc.AppTest;
import fr.ans.psc.api.client.dam.reader.model.SimpleDam;
import fr.ans.psc.api.client.dam.reader.model.SimpleDams;
import fr.ans.psc.dam.model.PsDAMs;
import lombok.extern.slf4j.Slf4j;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class }) // pour restdocs
@SpringBootTest
@Import(AppTest.class)
@AutoConfigureMockMvc
//@ActiveProfiles("test-psc-off")
@ActiveProfiles("test-withgravitee")
@DirtiesContext
@Slf4j
public class APICasPassantTest {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	
	private static final String TOKEN = "Bearer valeurDuTokenbase64";

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ApiExecutor apiExecutor;

	ObjectMapper mapper = new ObjectMapper();

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation)).build();

		// méthodes non mockée
		Mockito.doCallRealMethod().when(apiExecutor).getDAMs(any(), any(), any(), any());
		Mockito.doCallRealMethod().when(apiExecutor).get_PsDAMs(any(), any(), any(), any());
		Mockito.doCallRealMethod().when(apiExecutor).filterActiveDAM(any());
		Mockito.doCallRealMethod().when(apiExecutor).filterModeExercice(any(), any());
		Mockito.doCallRealMethod().when(apiExecutor).filterIdTechniqueStructure(any(), any());
		Mockito.doCallRealMethod().when(apiExecutor).convertSimpleDAMsToPsDAMs(any(), any());

	}

	@Test
	@DisplayName("Liste complète des DAMS 200")
	public void getFullDAMsTest() throws Exception {

		String nationalID = "899900007776"; // RPPS

		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());
		// curl -X GET
		// "http://localhost:8081/psc-dam-api/get_dams?idNational=1234&dontFermes=true"
		// -H "accept: application/json"
		ResultActions returned = mockMvc
				.perform(MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
						.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "true"))
				.andExpect(status().isOk());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		PsDAMs psDAMs = mapper.readValue(content, PsDAMs.class);

		assertEquals(psDAMs.getNationalId(), nationalID);
		assertEquals(psDAMs.getDams().size(), 2);

		assertEquals(psDAMs.getDams().get(0).getRaisonSociale(), "CABINET DR BIDEHOP0000777");
		assertEquals(psDAMs.getDams().get(1).getRaisonSociale(), "CABINET FERME DR BIDEHOP");

		assertNull(psDAMs.getDams().get(0).getDateFinValidite());
		assertEquals(psDAMs.getDams().get(1).getDateFinValidite(), "01-01-2050");

		assertEquals(psDAMs.getDams().get(0).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(0).getModeExercice(), "Libéral");

		returned.andDo(print());
		returned.andDo(document("DAM_FULL/OK"));
	}

	@Test
	@DisplayName("Liste des DAMS: filtre dontFermés=false")
	public void getCurrentDAMsTest() throws Exception {
		String nationalID = "899900007776"; // RPPS
		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());

		ResultActions returned = mockMvc
				.perform(MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
						.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "false"))
				.andExpect(status().isOk());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		PsDAMs psDAMs = mapper.readValue(content, PsDAMs.class);

		assertEquals(psDAMs.getNationalId(), nationalID);
		assertEquals(psDAMs.getDams().size(), 1);

		assertEquals(psDAMs.getDams().get(0).getRaisonSociale(), "CABINET DR BIDEHOP0000777");
		assertNull(psDAMs.getDams().get(0).getDateFinValidite());

		assertEquals(psDAMs.getDams().get(0).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(0).getModeExercice(), "Libéral");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneTarifaire(), "85");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement1(), "03");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement2(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement3(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeSpecialite(), "08");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneIK(), "0");
		assertEquals(psDAMs.getDams().get(0).getDateDebutValidite(), "01-01-1900");
		assertEquals(psDAMs.getDams().get(0).getNumAssuranceMaladie(), "991029695");
		assertEquals(psDAMs.getDams().get(0).getNumActivite(), "2101053284");
		assertEquals(psDAMs.getDams().get(0).getCodeTypeIdentifiant(), "6");
		assertEquals(psDAMs.getDams().get(0).getIdentifiantLieuDeTravail(), "99900007776001");

		returned.andDo(print());
		returned.andDo(document("DAM_Filtre_Actuel/OK"));

	}

	@Test
	@DisplayName("Liste des DAMS: filtre idTechniqueStructure")
	public void getDAMsFilterIdTechniqueStructureTest() throws Exception {
		String nationalID = "899900007776"; // RPPS
		String idTechniqueStructure = "mocké..";

		// mock lecture des DAMs
		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());

		// mock lecture idTechniqueStructure
		Mockito.doReturn("99900007776001").when(apiExecutor).readIdLieuDeTravail(any());

		ResultActions returned = mockMvc.perform(MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
				.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
				.param("idNational", nationalID).param("dontFermes", "false")
				.param("idTechniqueStructure", idTechniqueStructure)).andExpect(status().isOk());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		PsDAMs psDAMs = mapper.readValue(content, PsDAMs.class);

		assertEquals(psDAMs.getNationalId(), nationalID);
		assertEquals(psDAMs.getDams().size(), 1);

		assertEquals(psDAMs.getDams().get(0).getRaisonSociale(), "CABINET DR BIDEHOP0000777");
		assertNull(psDAMs.getDams().get(0).getDateFinValidite());

		assertEquals(psDAMs.getDams().get(0).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(0).getModeExercice(), "Libéral");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneTarifaire(), "85");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement1(), "03");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement2(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement3(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeSpecialite(), "08");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneIK(), "0");
		assertEquals(psDAMs.getDams().get(0).getDateDebutValidite(), "01-01-1900");
		assertEquals(psDAMs.getDams().get(0).getNumAssuranceMaladie(), "991029695");
		assertEquals(psDAMs.getDams().get(0).getNumActivite(), "2101053284");
		assertEquals(psDAMs.getDams().get(0).getCodeTypeIdentifiant(), "6");
		assertEquals(psDAMs.getDams().get(0).getIdentifiantLieuDeTravail(), "99900007776001");

		returned.andDo(print());
		returned.andDo(document("DAM_Filtre_idTechniqueStructure/OK"));

	}

	@Test
	@DisplayName("Liste des DAMS: filtre ModeExercice")
	public void getDAMsFiltreModeExerciceTest() throws Exception {
		String nationalID = "899900007776"; // RPPS
		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());

		ResultActions returned = mockMvc.perform(
				MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
				.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "true").param("modeExercice", "0"))
				.andExpect(status().isOk());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		PsDAMs psDAMs = mapper.readValue(content, PsDAMs.class);

		assertEquals(psDAMs.getNationalId(), nationalID);
		assertEquals(psDAMs.getDams().size(), 2);

		assertEquals(psDAMs.getDams().get(0).getRaisonSociale(), "CABINET DR BIDEHOP0000777");
		assertNull(psDAMs.getDams().get(0).getDateFinValidite());
		assertEquals(psDAMs.getDams().get(0).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(0).getModeExercice(), "Libéral");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneTarifaire(), "85");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement1(), "03");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement2(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement3(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeSpecialite(), "08");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneIK(), "0");
		assertEquals(psDAMs.getDams().get(0).getDateDebutValidite(), "01-01-1900");
		assertEquals(psDAMs.getDams().get(0).getNumAssuranceMaladie(), "991029695");
		assertEquals(psDAMs.getDams().get(0).getNumActivite(), "2101053284");
		assertEquals(psDAMs.getDams().get(0).getCodeTypeIdentifiant(), "6");
		assertEquals(psDAMs.getDams().get(0).getIdentifiantLieuDeTravail(), "99900007776001");

		assertEquals(psDAMs.getDams().get(1).getRaisonSociale(), "CABINET FERME DR BIDEHOP");
		assertEquals(psDAMs.getDams().get(1).getDateFinValidite(), "01-01-2050");
		assertEquals(psDAMs.getDams().get(1).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(1).getModeExercice(), "Libéral");
		assertEquals(psDAMs.getDams().get(1).getCodeZoneTarifaire(), "80");
		assertEquals(psDAMs.getDams().get(1).getCodeAgrement1(), "01");
		assertEquals(psDAMs.getDams().get(1).getCodeAgrement2(), "01");
		assertEquals(psDAMs.getDams().get(1).getCodeAgrement3(), "02");
		assertEquals(psDAMs.getDams().get(1).getCodeSpecialite(), "08");
		assertEquals(psDAMs.getDams().get(1).getCodeZoneIK(), "1");
		assertEquals(psDAMs.getDams().get(1).getDateDebutValidite(), "01-01-2005");
		assertEquals(psDAMs.getDams().get(1).getNumAssuranceMaladie(), "99102TEST");
		assertEquals(psDAMs.getDams().get(1).getNumActivite(), "210105TEST");
		assertEquals(psDAMs.getDams().get(1).getCodeTypeIdentifiant(), "6");
		assertEquals(psDAMs.getDams().get(1).getIdentifiantLieuDeTravail(), "9990000xyz6001");

		returned.andDo(print());
		returned.andDo(document("DAM_Filtre_ModeExercice/OK"));
	}

	@Test
	@DisplayName("Liste des DAMS: filtre ModeExercice")
	public void getDAMsTousFiltres() throws Exception {
		String nationalID = "899900007776"; // RPPS
		String idTechniqueStructure = "mocké..";

		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());

		// mock lecture idTechniqueStructure
		Mockito.doReturn("99900007776001").when(apiExecutor).readIdLieuDeTravail(any());

		ResultActions returned = mockMvc
				.perform(MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
						.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "false")
						.param("modeExercice", "0").param("idTechniqueStructure", idTechniqueStructure))
				.andExpect(status().isOk());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		PsDAMs psDAMs = mapper.readValue(content, PsDAMs.class);

		assertEquals(psDAMs.getNationalId(), nationalID);
		assertEquals(psDAMs.getDams().size(), 1);

		assertEquals(psDAMs.getDams().get(0).getRaisonSociale(), "CABINET DR BIDEHOP0000777");
		assertNull(psDAMs.getDams().get(0).getDateFinValidite());
		assertEquals(psDAMs.getDams().get(0).getCodeModeExercice(), "0");
		assertEquals(psDAMs.getDams().get(0).getModeExercice(), "Libéral");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneTarifaire(), "85");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement1(), "03");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement2(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeAgrement3(), "00");
		assertEquals(psDAMs.getDams().get(0).getCodeSpecialite(), "08");
		assertEquals(psDAMs.getDams().get(0).getCodeZoneIK(), "0");
		assertEquals(psDAMs.getDams().get(0).getDateDebutValidite(), "01-01-1900");
		assertEquals(psDAMs.getDams().get(0).getNumAssuranceMaladie(), "991029695");
		assertEquals(psDAMs.getDams().get(0).getNumActivite(), "2101053284");
		assertEquals(psDAMs.getDams().get(0).getCodeTypeIdentifiant(), "6");
		assertEquals(psDAMs.getDams().get(0).getIdentifiantLieuDeTravail(), "99900007776001");


		returned.andDo(print());
		returned.andDo(document("DAM_TousFiltres/OK"));
	}

	@Test
	@DisplayName("Liste des DAMS: 410 après filtrage")
	public void getDAMs410ApresFiltrageTest() throws Exception {
		String nationalID = "899900007776"; // RPPS
		SimpleDams simpleDams = backEndReadDamsRetrun("SimpleDams/RPPS_2DAMS_1ferme2050.json");
		Mockito.doReturn(simpleDams).when(apiExecutor).get_dams(any());

		ResultActions returned = mockMvc.perform(
				MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
				.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "true").param("modeExercice", "1"))
				.andExpect(status().isGone());
		assertTrue(returned.andReturn().getResponse().getContentType().contains("application/json"));
		assertTrue(returned.andReturn().getResponse().getContentType().contains("charset=UTF-8"));
		String content = returned.andReturn().getResponse().getContentAsString();
		assertTrue(content.contentEquals(
				"{\"code\":\"410 GONE\",\"message\":\"Liste des DAMs vide après filtrage sur le ModeExercice\"}"));
		returned.andDo(print());
		returned.andDo(document("DAM_Filtre_ModeExercice/410"));
	}

	public SimpleDams backEndReadDamsRetrun(String PathAndFileName) throws IOException {

		String simpleDamsString = Files
				.readString(new ClassPathResource("SimpleDams/RPPS_2DAMS_1ferme2050.json").getFile().toPath());

		List<SimpleDam> simpleDamsList = mapper.readValue(simpleDamsString,
				mapper.getTypeFactory().constructCollectionLikeType(List.class, SimpleDam.class));

		SimpleDams simpleDams = new SimpleDams();
		simpleDams.addAll(simpleDamsList);
		return simpleDams;
	}

}
