package fr.ans.psc.dam.util;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class Startup implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(final ApplicationReadyEvent event) {
		log.info("Chargement des nomenclatures");
		// type structure (RASS)
		Nom.N_TYP_ID_PM.put("0", "N° ADELI");
		Nom.N_TYP_ID_PM.put("1", "Id Cabinet ADELI / N° de registre");
		Nom.N_TYP_ID_PM.put("3", "FINESS / N° de registre");
		Nom.N_TYP_ID_PM.put("4", "SIREN / N° de registre");
		Nom.N_TYP_ID_PM.put("5", "SIRET / N° de registre");
		Nom.N_TYP_ID_PM.put("6", "Id Cabinet RPPS / N° de registre");
		Nom.N_TYP_ID_PM.put("8", "N° RPPS");
		Nom.N_TYP_ID_PM.put("10", "Adresse MSSante");

		// zone IK (RASS)
		Nom.N_CODE_ZONE_IK.put("00", "Pas d'indemnité kilométrique");
		Nom.N_CODE_ZONE_IK.put("01", "Indemnités kilométriques plaine");
		Nom.N_CODE_ZONE_IK.put("02", "Indemnités kilométriques montagne");
		Nom.N_CODE_ZONE_IK.put("03", "Indemnités kilométriques spéciales");
		Nom.N_CODE_ZONE_IK.put("09", "Non définie");
		Nom.N_CODE_ZONE_IK.put("99", "Libellé Code Indemnités kilométriques 99");
		Nom.N_CODE_ZONE_IK.put("0", "Libellé Code Indemnités kilométriques 0");
		Nom.N_CODE_ZONE_IK.put("1", "Libellé Code Indemnités kilométriques 1");

		// N_CODE_AGREMENT (RASS)
		Nom.N_CODE_AGREMENT.put("0", "Pas d'agrément");
		Nom.N_CODE_AGREMENT.put("1", "Agrément D ou agrément DDASS");
		Nom.N_CODE_AGREMENT.put("2", "Agréments A, B, C, E et F");
		Nom.N_CODE_AGREMENT.put("3", "Agréments G, H et J");
		Nom.N_CODE_AGREMENT.put("4", "Agrément K");
		Nom.N_CODE_AGREMENT.put("5", "Agrément L");
		Nom.N_CODE_AGREMENT.put("6", "Agrément M");
		Nom.N_CODE_AGREMENT.put("99", "Non défini");
		Nom.N_CODE_AGREMENT.put("9", "Libellé Code Agréments 9");

		// N_CODE_CONVENTIONNEL (RASS)
		Nom.N_CODE_CONVENTIONNEL.put("0", "Non conventionné");
		Nom.N_CODE_CONVENTIONNEL.put("1", "Conventionné");
		Nom.N_CODE_CONVENTIONNEL.put("2", "Conventionné avec dépassement");
		Nom.N_CODE_CONVENTIONNEL.put("3", "Conventionné avec honoraires libres");
		Nom.N_CODE_CONVENTIONNEL.put("9", "Etablissement ou Centre de Santé");
		Nom.N_CODE_CONVENTIONNEL.put("4", "Libellé code convention 4");

		// N_CODE_SPECIALITE (RASS)
		Nom.N_CODE_SPECIALITE.put("01", "Médecine générale");
		Nom.N_CODE_SPECIALITE.put("02", "Anesthésiologie-réanimation chirurgicale");
		Nom.N_CODE_SPECIALITE.put("03", "Pathologie cardio-vasculaire");
		Nom.N_CODE_SPECIALITE.put("04", "Chirurgie générale");
		Nom.N_CODE_SPECIALITE.put("05", "Dermatologie et Vénéréologie");
		Nom.N_CODE_SPECIALITE.put("06", "Radiodiagnostic et Imagerie médicale");
		Nom.N_CODE_SPECIALITE.put("07", "Gynécologie obstétrique");
		Nom.N_CODE_SPECIALITE.put("08", "Gastro-Entérologie et Hépathologie");
		Nom.N_CODE_SPECIALITE.put("09", "Médecine interne");
		Nom.N_CODE_SPECIALITE.put("10", "Neuro-chirurgien");
		Nom.N_CODE_SPECIALITE.put("11", "Oto-Rhino-Laryngologiste");
		Nom.N_CODE_SPECIALITE.put("12", "Pédiatre");
		Nom.N_CODE_SPECIALITE.put("13", "Pneumologie");
		Nom.N_CODE_SPECIALITE.put("14", "Rhumatologie");
		Nom.N_CODE_SPECIALITE.put("15", "Ophtalmologie");
		Nom.N_CODE_SPECIALITE.put("16", "Chirurgie urologique");
		Nom.N_CODE_SPECIALITE.put("17", "Neuro-psychiatrie");
		Nom.N_CODE_SPECIALITE.put("18", "Stomatologie");
		Nom.N_CODE_SPECIALITE.put("19", "Chirurgie dentaire");
		Nom.N_CODE_SPECIALITE.put("20", "Réanimation médicale");
		Nom.N_CODE_SPECIALITE.put("21", "Sage-Femme");
		Nom.N_CODE_SPECIALITE.put("24", "Infirmier");
		Nom.N_CODE_SPECIALITE.put("26", "Masseur-Kinésithérapeute");
		Nom.N_CODE_SPECIALITE.put("27", "Pédicure");
		Nom.N_CODE_SPECIALITE.put("28", "Orthophoniste");
		Nom.N_CODE_SPECIALITE.put("29", "Orthoptiste");
		Nom.N_CODE_SPECIALITE.put("30", "Laboratoire d'analyses médicales");
		Nom.N_CODE_SPECIALITE.put("31", "Médecine physique et de réadaptation");
		Nom.N_CODE_SPECIALITE.put("32", "Neurologie");
		Nom.N_CODE_SPECIALITE.put("33", "Psychiatrie générale");
		Nom.N_CODE_SPECIALITE.put("34", "Gériatrie");
		Nom.N_CODE_SPECIALITE.put("35", "Néphrologie");
		Nom.N_CODE_SPECIALITE.put("36", "Chirurgie dentaire (spéc. O.D.F.)");
		Nom.N_CODE_SPECIALITE.put("37", "Anatomie-cytologie-pathologiques");
		Nom.N_CODE_SPECIALITE.put("38", "Médecin biologiste");
		Nom.N_CODE_SPECIALITE.put("39", "Laboratoire polyvalent");
		Nom.N_CODE_SPECIALITE.put("40", "Laboratoire anatomo-pathologiste");
		Nom.N_CODE_SPECIALITE.put("41", "Chirurgie orthopédique et Traumatologie");
		Nom.N_CODE_SPECIALITE.put("42", "Endocrinologie et métabolismes");
		Nom.N_CODE_SPECIALITE.put("43", "Chirurgie infantile");
		Nom.N_CODE_SPECIALITE.put("44", "Chirurgie maxillo-faciale");
		Nom.N_CODE_SPECIALITE.put("45", "Chirurgie maxillo-faciale et Stomatologie");
		Nom.N_CODE_SPECIALITE.put("46", "Chirurgie plastique reconstructrice et esthétique");
		Nom.N_CODE_SPECIALITE.put("47", "Chirurgie thoracique et cardio-vasculaire");
		Nom.N_CODE_SPECIALITE.put("48", "Chirurgie vasculaire");
		Nom.N_CODE_SPECIALITE.put("49", "Chirurgie viscérale et digestive");
		Nom.N_CODE_SPECIALITE.put("50", "Pharmacien");
		Nom.N_CODE_SPECIALITE.put("51", "Pharmacien mutualiste");
		Nom.N_CODE_SPECIALITE.put("53", "Chirurgien dentiste spécialité Chirurgie orale");
		Nom.N_CODE_SPECIALITE.put("54", "Chirurgien dentiste spécialité Médecine bucco-dentaire");
		Nom.N_CODE_SPECIALITE.put("69", "Chirurgie orale");
		Nom.N_CODE_SPECIALITE.put("70", "Gynécologie médicale");
		Nom.N_CODE_SPECIALITE.put("71", "Hématologie");
		Nom.N_CODE_SPECIALITE.put("72", "Médecine nucléaire");
		Nom.N_CODE_SPECIALITE.put("73", "Oncologie médicale");
		Nom.N_CODE_SPECIALITE.put("74", "Oncologie radiothérapique");
		Nom.N_CODE_SPECIALITE.put("75", "Psychiatrie de l'enfant et de l'adolescent");
		Nom.N_CODE_SPECIALITE.put("76", "Radiothérapie");
		Nom.N_CODE_SPECIALITE.put("77", "Obstétrique");
		Nom.N_CODE_SPECIALITE.put("78", "Génétique médicale");
		Nom.N_CODE_SPECIALITE.put("79", "Gynécologie obstétrique et gynécologie médicale");
		Nom.N_CODE_SPECIALITE.put("80", "Santé publique et médecine sociale");
		Nom.N_CODE_SPECIALITE.put("99", "Non définie");
		Nom.N_CODE_SPECIALITE.put("25", "Libellé Code Spécialité 25");

		// N_CODE_ZONE_TARIFAIRE (RASS)
		Nom.N_CODE_ZONE_TARIFAIRE.put("40", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("50", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("60", "Zone C");
		Nom.N_CODE_ZONE_TARIFAIRE.put("70", "Zone D");
		Nom.N_CODE_ZONE_TARIFAIRE.put("10", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("11", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("20", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("22", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("23", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("24", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("25", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("30", "Zone C");
		Nom.N_CODE_ZONE_TARIFAIRE.put("10", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("20", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("80", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("81", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("82", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("83", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("84", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("85", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("30", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("31", "Zone A");
		Nom.N_CODE_ZONE_TARIFAIRE.put("32", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("33", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("34", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("35", "Zone B");
		Nom.N_CODE_ZONE_TARIFAIRE.put("99", "Non définie");
		Nom.N_CODE_ZONE_TARIFAIRE.put("1", "Libellé Code Zone 1");
		Nom.N_CODE_ZONE_TARIFAIRE.put("2", "Libellé Code Zone 2");
		Nom.N_CODE_ZONE_TARIFAIRE.put("01", "Libellé Code Zone 01");
		Nom.N_CODE_ZONE_TARIFAIRE.put("02", "Libellé Code Zone 02");
		Nom.N_CODE_ZONE_TARIFAIRE.put("28", "Libellé Code Zone 28");

		// N_HABILITATION_FSE (RASS)
		Nom.N_HABILITATION_FSE.put("000", "Non");
		Nom.N_HABILITATION_FSE.put("001", "Oui");

		// N_HABILITATION_LOT (RASS)
		Nom.N_HABILITATION_LOT.put("000", "Non");
		Nom.N_HABILITATION_LOT.put("001", "Oui");

		// N_INDICATEUR_FACTURATION (RASS)
		Nom.N_INDICATEUR_FACTURATION.put("0", "Libellé indicateur facturation 0");
		Nom.N_INDICATEUR_FACTURATION.put("1", "Libellé indicateur facturation 1");
		Nom.N_INDICATEUR_FACTURATION.put("2", "Libellé indicateur facturation 2");
		Nom.N_INDICATEUR_FACTURATION.put("3", "Libellé indicateur facturation 3");
		Nom.N_INDICATEUR_FACTURATION.put("4", "Libellé indicateur facturation 4");
		Nom.N_INDICATEUR_FACTURATION.put("5", "Libellé indicateur facturation 5");
		Nom.N_INDICATEUR_FACTURATION.put("9", "Libellé indicateur facturation 9");

		//CODE_S_MODE_EXERCICE
		Nom.CODE_S_MODE_EXERCICE.put("0", "Libéral" );
		Nom.CODE_S_MODE_EXERCICE.put("1", "Salarié" );
			
		
		log.info("\t N_TYP_ID_PM.size {}: ", Nom.N_TYP_ID_PM.size());
		log.info("\t N_CODE_SPECIALITE.size {}: ", Nom.N_CODE_SPECIALITE.size());
		log.info("\t ...");
		log.info("Fin de chargement des nomenclatures:");
		
		return;
	}

}
