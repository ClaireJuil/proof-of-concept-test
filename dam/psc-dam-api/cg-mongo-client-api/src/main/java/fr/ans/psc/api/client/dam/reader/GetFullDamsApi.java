package fr.ans.psc.api.client.dam.reader;

import fr.ans.psc.api.client.dam.reader.invoker.ApiClient;

import fr.ans.psc.api.client.dam.reader.model.SimpleDams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-03-01T10:44:01.736Z[GMT]")@Component("fr.ans.psc.api.client.dam.reader.GetFullDamsApi")
public class GetFullDamsApi {
    private ApiClient apiClient;

    public GetFullDamsApi() {
        this(new ApiClient());
    }

    @Autowired
    public GetFullDamsApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Lecture des DAMs d&#x27;un PS
     * Renvoie la liste des DAMs d&#x27;un PS (nationaId)
     * <p><b>200</b> - OK
     * <p><b>410</b> - PS non trouvé ou PS sans DAMs
     * <p><b>503</b> - service indisponible
     * @param nationalId Identifiant National du PS (required)
     * @return SimpleDams
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public SimpleDams lectureDesDAMs(String nationalId) throws RestClientException {
        return lectureDesDAMsWithHttpInfo(nationalId).getBody();
    }

    /**
     * Lecture des DAMs d&#x27;un PS
     * Renvoie la liste des DAMs d&#x27;un PS (nationaId)
     * <p><b>200</b> - OK
     * <p><b>410</b> - PS non trouvé ou PS sans DAMs
     * <p><b>503</b> - service indisponible
     * @param nationalId Identifiant National du PS (required)
     * @return ResponseEntity&lt;SimpleDams&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<SimpleDams> lectureDesDAMsWithHttpInfo(String nationalId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'nationalId' is set
        if (nationalId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'nationalId' when calling lectureDesDAMs");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("nationalId", nationalId);
        String path = UriComponentsBuilder.fromPath("/dams/get_dams/{nationalId}").buildAndExpand(uriVariables).toUriString();
        
        final MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
        final HttpHeaders headerParams = new HttpHeaders();
        final MultiValueMap<String, Object> formParams = new LinkedMultiValueMap<String, Object>();

        final String[] accepts = { 
            "application/json"
         };
        final List<MediaType> accept = apiClient.selectHeaderAccept(accepts);
        final String[] contentTypes = {  };
        final MediaType contentType = apiClient.selectHeaderContentType(contentTypes);

        String[] authNames = new String[] {  };

        ParameterizedTypeReference<SimpleDams> returnType = new ParameterizedTypeReference<SimpleDams>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
