package fr.ans.psc.dam.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.ans.psc.api.client.dam.reader.model.SimpleDam;
import fr.ans.psc.dam.util.Nom;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * SimpleDam
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2022-02-17T09:54:38.793935200+01:00[Europe/Paris]")
@Getter
@Setter
@EqualsAndHashCode

public class RichDam {
	
	private static String NOMENCLATURE_NON_TROUVEE = "code non trouvé dans la nomeclature";
	
	@JsonProperty("identifiantLieuDeTravail")
	private String identifiantLieuDeTravail;

	@JsonProperty("typeIdentifiant")
	private String typeIdentifiant;

	@JsonProperty("codeTypeIdentifiant")
	private String codeTypeIdentifiant;

	@JsonProperty("raisonSociale")
	private String raisonSociale;

	@JsonProperty("modeExercice")
	private String modeExercice;

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
	
	@JsonProperty("specialite")
	private String specialite;

	@JsonProperty("codeSpecialite")
	private String codeSpecialite;

	@JsonProperty("conventionnement")
	private String conventionnement;

	@JsonProperty("codeConventionnel")
	private String codeConventionnel;

	@JsonProperty("indicateurFacturation")
	private String indicateurFacturation;

	@JsonProperty("codeIndicateurFacturation")
	private String codeIndicateurFacturation;

	@JsonProperty("zoneIK")
	private String zoneIK;

	@JsonProperty("codeZoneIK")
	private String codeZoneIK;

	@JsonProperty("zoneTarifaire")
	private String zoneTarifaire;

	@JsonProperty("codeZoneTarifaire")
	private String codeZoneTarifaire;

	@JsonProperty("agrement1")
	private String agrement1;

	@JsonProperty("codeAgrement1")
	private String codeAgrement1;

	@JsonProperty("agrement2")
	private String agrement2;

	@JsonProperty("codeAgrement2")
	private String codeAgrement2;

	@JsonProperty("agrement3")
	private String agrement3;

	@JsonProperty("codeAgrement3")
	private String codeAgrement3;

	@JsonProperty("habilitationFse")
	private String habilitationFse;

	@JsonProperty("habilitationLot")
	private String habilitationLot;

	public RichDam() {
	}

	public RichDam(SimpleDam dam) {
		this.identifiantLieuDeTravail = dam.getIdentifiantLieuDeTravail();
		this.typeIdentifiant = Nom.N_TYP_ID_PM.getOrDefault(dam.getCodeTypeIdentifiant(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeTypeIdentifiant = dam.getCodeTypeIdentifiant();
		this.raisonSociale = dam.getRaisonSociale();
		this.modeExercice = Nom.CODE_S_MODE_EXERCICE.getOrDefault(dam.getCodeModeExercice(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeModeExercice = dam.getCodeModeExercice();
		this.numActivite = dam.getNumActivite();
		this.numAssuranceMaladie = dam.getNumAssuranceMaladie();
		
		this.dateDebutValidite = dam.getDateDebutValidite();
		this.dateFinValidite = dam.getDateFinValidite();
		this.specialite = Nom.N_CODE_SPECIALITE.getOrDefault(dam.getCodeSpecialite(), 
				NOMENCLATURE_NON_TROUVEE);
		this.codeSpecialite = dam.getCodeSpecialite();
		this.conventionnement = Nom.N_CODE_CONVENTIONNEL.getOrDefault(dam.getCodeConventionnel(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeConventionnel = dam.getCodeConventionnel();
		this.indicateurFacturation = Nom.N_INDICATEUR_FACTURATION.getOrDefault(dam.getCodeIndicateurFacturation(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeIndicateurFacturation = dam.getCodeIndicateurFacturation();
		this.zoneIK = Nom.N_CODE_ZONE_IK.getOrDefault(dam.getCodeZoneIK(), NOMENCLATURE_NON_TROUVEE);
		this.codeZoneIK = dam.getCodeZoneIK();
		this.zoneTarifaire = Nom.N_CODE_ZONE_TARIFAIRE.getOrDefault(dam.getCodeZoneTarifaire(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeZoneTarifaire = dam.getCodeZoneTarifaire();
		this.agrement1 = Nom.N_CODE_AGREMENT.getOrDefault(dam.getCodeAgrement1(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeAgrement1 = dam.getCodeAgrement1();
		this.agrement2 = Nom.N_CODE_AGREMENT.getOrDefault(dam.getCodeAgrement2(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeAgrement2 = dam.getCodeAgrement2();
		this.agrement3 = Nom.N_CODE_AGREMENT.getOrDefault(dam.getCodeAgrement3(),
				NOMENCLATURE_NON_TROUVEE);
		this.codeAgrement3 = dam.getCodeAgrement3();
		this.habilitationFse = Nom.N_HABILITATION_FSE.getOrDefault(dam.getHabilitationFse(),
				NOMENCLATURE_NON_TROUVEE);
		this.habilitationFse = dam.getHabilitationFse();
		this.habilitationLot = Nom.N_HABILITATION_LOT.getOrDefault(dam.getHabilitationLot(),
				NOMENCLATURE_NON_TROUVEE);
		this.habilitationLot = dam.getHabilitationLot();
	}
}
