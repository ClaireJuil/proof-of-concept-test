package fr.ans.psc.api.client.structure.reader;

import fr.ans.psc.api.client.structure.reader.invoker.ApiClient;

import fr.ans.psc.api.client.structure.reader.model.Error;
import fr.ans.psc.api.client.structure.reader.model.StructureIds;

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

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-02-25T10:58:18.928Z[GMT]")@Component("fr.ans.psc.api.client.structure.reader.StructureApi")
public class StructureApi {
    private ApiClient apiClient;

    public StructureApi() {
        this(new ApiClient());
    }

    @Autowired
    public StructureApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * get ids endpoint
     * return an StructureIds Object for the TechnicalStructureId extracted from UserInfo
     * <p><b>200</b> - OK
     * <p><b>409</b> - Conflict. Pas d&#x27;identifiant structure ou plusieurs identifiants trouvés en base de données
     * <p><b>503</b> - Base de données inaccessible
     * @param structureTechnicalId Structure Technical ID (required)
     * @return StructureIds
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public StructureIds getIds(String structureTechnicalId) throws RestClientException {
        return getIdsWithHttpInfo(structureTechnicalId).getBody();
    }

    /**
     * get ids endpoint
     * return an StructureIds Object for the TechnicalStructureId extracted from UserInfo
     * <p><b>200</b> - OK
     * <p><b>409</b> - Conflict. Pas d&#x27;identifiant structure ou plusieurs identifiants trouvés en base de données
     * <p><b>503</b> - Base de données inaccessible
     * @param structureTechnicalId Structure Technical ID (required)
     * @return ResponseEntity&lt;StructureIds&gt;
     * @throws RestClientException if an error occurs while attempting to invoke the API
     */
    public ResponseEntity<StructureIds> getIdsWithHttpInfo(String structureTechnicalId) throws RestClientException {
        Object postBody = null;
        // verify the required parameter 'structureTechnicalId' is set
        if (structureTechnicalId == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Missing the required parameter 'structureTechnicalId' when calling getIds");
        }
        // create path and map variables
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("structureTechnicalId", structureTechnicalId);
        String path = UriComponentsBuilder.fromPath("/ids/get/{structureTechnicalId}").buildAndExpand(uriVariables).toUriString();
        
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

        ParameterizedTypeReference<StructureIds> returnType = new ParameterizedTypeReference<StructureIds>() {};
        return apiClient.invokeAPI(path, HttpMethod.GET, queryParams, postBody, headerParams, formParams, accept, contentType, authNames, returnType);
    }
}
