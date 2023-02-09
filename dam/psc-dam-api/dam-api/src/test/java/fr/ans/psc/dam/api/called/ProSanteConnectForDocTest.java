package fr.ans.psc.dam.api.called;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
import org.springframework.http.HttpStatus;
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

import fr.ans.psc.AppTest;
import fr.ans.psc.dam.api.DamsApiImpl;
import fr.ans.psc.dam.api.exception.DamRequestException;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class }) // pour restdocs
@SpringBootTest
@ActiveProfiles("test-withoutgravitee")
@AutoConfigureMockMvc
@Import(AppTest.class) // bean RestTemplate
@DirtiesContext
public class ProSanteConnectForDocTest {

	@MockBean
	PscTokenValidatorImpl psc;
	
	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	
	private static final String TOKEN = "Bearer valeurDuTokenbase64";
	
	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;
	
	/*
	 * setUp pour restdocs
	 */
	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) throws IOException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation)).build();

		}

	@Test
	@DisplayName("token non valid")
	public void tokenNonActifTest() throws Exception {

		String nationalID = "899900007776"; 
		fr.ans.psc.dam.model.Error error = new fr.ans.psc.dam.model.Error();
		error.setCode("401");
		error.setMessage("Token ProSante Connect non actif :{\"active\": false}");
		Mockito.doThrow(new DamRequestException(error,HttpStatus.UNAUTHORIZED)).when(psc).tokenValidator(any());
		ResultActions returned = mockMvc
				.perform(MockMvcRequestBuilders.get("/get_dams").accept(APPLICATION_JSON_UTF8)
						.header(DamsApiImpl.HEADER_NAME_AUTHORIZATION, TOKEN)
						.param("idNational", nationalID).param("dontFermes", "true"))
				.andExpect(status().isUnauthorized());
		
		returned.andDo(print());
		returned.andDo(document("DAM_Non_Autorise/401"));
	}
}
