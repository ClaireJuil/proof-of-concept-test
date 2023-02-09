package fr.ans.psc.dam.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import fr.ans.psc.dam.model.SimpleDam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DamRowMapper implements RowMapper<SimpleDam> {
	

	@Override
	public SimpleDam mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.debug("DamRowMapper: rowNum {}:", rowNum);
		
		// WARNING: ResultSet fisrt column is '1' (not '0')
		SimpleDam dam = new SimpleDam();


		dam.setIdentifiantLieuDeTravail(rs.getString("IDENTIFIANT_LIEU_DE_TRAVAIL"));	
		dam.setCodeTypeIdentifiant(rs.getString("CODE_TYPE_IDENTIFIANT"));
		dam.setRaisonSociale(rs.getString("RAISON_SOCIALE"));
		dam.setCodeModeExercice(rs.getString("CODE_MODE_EXERCICE"));
		dam.setNumActivite(rs.getString("NUM_ACTIVITE"));
		dam.setNumAssuranceMaladie(rs.getString("NUM_ASSURANCE_MALADIE"));
		dam.setDateDebutValidite( rs.getString("DATE_DEBUT_VALIDITE"));
		dam.setDateFinValidite( rs.getString("DATE_FIN_VALIDITE"));
		dam.setCodeSpecialite(rs.getString("CODE_SPECIALITE"));
		dam.setCodeConventionnel(rs.getString("CODE_CONVENTIONNEL"));
		dam.setCodeIndicateurFacturation(rs.getString("INDICATEUR_FACTURATION"));
		dam.setCodeZoneIK(rs.getString("CODE_ZONE_IK"));

		dam.setCodeZoneTarifaire(rs.getString("CODE_ZONE_TARIFAIRE"));
		dam.setCodeAgrement1(rs.getString("CODE_AGREMENT_1"));

		dam.setCodeAgrement2(rs.getString("CODE_AGREMENT_2"));

		dam.setCodeAgrement3(rs.getString("CODE_AGREMENT_3"));

		dam.setHabilitationFse(rs.getString("HABILITATION_FSE"));
		dam.setHabilitationLot(rs.getString("HABILITATION_LOT"));

		return dam;

	}

}
