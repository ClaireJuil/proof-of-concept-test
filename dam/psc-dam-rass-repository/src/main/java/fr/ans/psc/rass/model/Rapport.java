package fr.ans.psc.rass.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Rapport de chargement du fichier dans mongoDB.
 */
@Getter
@Setter
@EqualsAndHashCode
@ApiModel(description = "Rapport de chargement du fichier dans mongoDB.")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-17T15:39:39.535933500+01:00[Europe/Paris]")
public class Rapport   {
	
  @JsonProperty("valid")
  private Boolean valid;

  @JsonProperty("numberTechStruture")
  private String numberTechStruture;

}

