package fr.ans.psc.dam.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.openapitools.jackson.nullable.JsonNullable;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SimpleDam
 */
@Getter
@Setter
@EqualsAndHashCode
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-24T14:15:02.502813300+01:00[Europe/Paris]")
public class SimpleDam {
	@JsonProperty("identifiantLieuDeTravail")
	private String identifiantLieuDeTravail;

	@JsonProperty("codeTypeIdentifiant")
	private String codeTypeIdentifiant;

	@JsonProperty("raisonSociale")
	private String raisonSociale;

	@JsonProperty("codeModeExercice")
	private String codeModeExercice;

	@JsonProperty("numActivite")
	private String numActivite;

	@JsonProperty("numAssuranceMaladie")
	private String numAssuranceMaladie;

	@JsonProperty("dateDebutValidite")
	private String dateDebutValidite;

	@JsonProperty("dateFinValidite")
	private String dateFinValidite;

	@JsonProperty("codeSpecialite")
	private String codeSpecialite;
	
	@JsonProperty("codeConventionnel")
	private String codeConventionnel;

	@JsonProperty("codeIndicateurFacturation")
	private String codeIndicateurFacturation;

	@JsonProperty("codeZoneIK")
	private String codeZoneIK;

	@JsonProperty("codeZoneTarifaire")
	private String codeZoneTarifaire;

	@JsonProperty("codeAgrement1")
	private String codeAgrement1;

	@JsonProperty("codeAgrement2")
	private String codeAgrement2;

	@JsonProperty("codeAgrement3")
	private String codeAgrement3;

	@JsonProperty("habilitationFse")
	private String habilitationFse;

	@JsonProperty("habilitationLot")
	private String habilitationLot;

	public SimpleDam identifiantLieuDeTravail(String identifiantLieuDeTravail) {
		this.identifiantLieuDeTravail = identifiantLieuDeTravail;
		return this;
	}

	public SimpleDam() {
	}

	public SimpleDam(String[] items) {
		identifiantLieuDeTravail = items[1];
		codeTypeIdentifiant = items[2];
		raisonSociale = items[3];
		codeModeExercice = items[4];
		numActivite = items[5];
		numAssuranceMaladie = items[6];
		dateDebutValidite = items[7];
		dateFinValidite = items[8];
		codeSpecialite = items[9];
		codeConventionnel = items[10];
		codeIndicateurFacturation = items[11];
		codeZoneIK = items[12];
		codeZoneTarifaire = items[13];
		codeAgrement1 = items[14];
		codeAgrement2 = items[15];
		codeAgrement3 = items[16];
		habilitationFse = items[17];
		habilitationLot = items[18];
	}
}
