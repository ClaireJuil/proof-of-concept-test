package fr.ans.psc.dam.repository;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import fr.ans.psc.dam.model.Ps;
import fr.ans.psc.dam.model.SimpleDam;
import fr.ans.psc.dam.oracle.DamRowMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "database.type", havingValue = "oracle", matchIfMissing = false)
public class OraclePsRepositoryImpl implements OraclePsRepository {

	private static final HashSet<Character> cpeIdTypes = new HashSet<Character>();

	static {
		cpeIdTypes.add('1');
		cpeIdTypes.add('3');
		cpeIdTypes.add('4');
		cpeIdTypes.add('5');
		cpeIdTypes.add('6');
	}
	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String QUERY = "SELECT " 
			+ "a.IDENTIFIANT_LIEU_DE_TRAVAIL as IDENTIFIANT_LIEU_DE_TRAVAIL "
			+ ", tst.TYPE_IDENTIFIANT  as CODE_TYPE_IDENTIFIANT  " 
			+ ", ldt.RAISON_SOCIALE_LIEU as RAISON_SOCIALE "
			+ ", a.CODE_S_MODE_EXERCICE as CODE_MODE_EXERCICE " 
			+ ", d.NUM_ACTIVITE " 
			+ ", d.NUM_ASSURANCE_MALADIE "
			+ ", TO_CHAR(d.DATE_DEBUT_DE_VALIDITE, 'DD-MM-YYYY') as DATE_DEBUT_VALIDITE " 
			+ ", TO_CHAR(d.DATE_FIN_DE_VALIDITE, 'DD-MM-YYYY') as DATE_FIN_VALIDITE " 
			+ ", d.CODE_SPECIALITE as CODE_SPECIALITE"
			+ ", d.CODE_CONVENTIONNEL as CODE_CONVENTIONNEL "
			+ ", d.INDICATEUR as INDICATEUR_FACTURATION " 
			+ ", d.CODE_ZONE_IK as CODE_ZONE_IK "
			+ ", d.CODE_ZONE_TARIFAIRE as CODE_ZONE_TARIFAIRE " 
			+ ", d.CODE_AGREMENT_1 as  CODE_AGREMENT_1 "
			+ ", d.CODE_AGREMENT_2 as  CODE_AGREMENT_2 " 
			+ ", d.CODE_AGREMENT_3 as  CODE_AGREMENT_3 "
			+ ", d.HABILITATION_FSE as HABILITATION_FSE " 
			+ ", d.HABILITATION_LOT as HABILITATION_LOT "
			+ "from VERSION_DE_TITULAIRE vdt "
			+ "JOIN ACTIVITE a on a.NUM_INTERNE_TITULAIRE = vdt.NUM_INTERNE_TITULAIRE "
			+ "JOIN DAM d on d.NUM_ACTIVITE = a.NUM_ACTIVITE "
			+ "JOIN LIEU_DE_TRAVAIL ldt on a.IDENTIFIANT_LIEU_DE_TRAVAIL = ldt.IDENTIFIANT_LIEU_DE_TRAVAIL "
			+ "JOIN TYPE_STRUCTURE tst on ldt.TYPE_STRUCTURE =tst.TYPE_STRUCTURE "
			//+ "WHERE (d.DATE_FIN_DE_VALIDITE IS null OR d.DATE_FIN_DE_VALIDITE >= SYSDATE) "
			//DAMs actuel et DAM fermés depuis moins de 3 mois
			+ "WHERE (d.DATE_FIN_DE_VALIDITE IS null OR d.DATE_FIN_DE_VALIDITE >= add_months(sysdate,-3))"			
			+ "AND vdt.IDENTIFIANT_PERSONNEL_IDENT = ? "
			+ "AND (vdt.IDENTIFIANT_STRUCTURE_IDENT = ? "
			+ "OR vdt.IDENTIFIANT_STRUCTURE_IDENT is null)";
	
	private static boolean isCPEtype(Character c) {
		return cpeIdTypes.contains(c);
	}

	@Override
	public Ps findByNationalId(String nationalId) {
		log.info("Using Oracle Repository. nationalId: {}", nationalId);
		char idType = nationalId.charAt(0);
		String personalId = "";
		String structureId = "";
		if (isCPEtype(idType)) {
			String[] ids = nationalId.split("/");
			if (ids.length == 2) {
				structureId = ids[0].substring(1);
				personalId = "R".concat(ids[1]);
			} else {
				personalId = nationalId.substring(1);
			}
		}
		else { // Adeli-RPPS
			personalId = nationalId.substring(1);
		}
		log.info("** personalId {}", personalId);
		log.info("** structureId {}", structureId);
			
		List<SimpleDam> dams = jdbcTemplate.query(QUERY, new DamRowMapper(), personalId, structureId);
		log.info("List<Dam> OK.  size: {} ", dams == null ? 0 : dams.size()); // toujours non nul ...
		if (dams.size() == 0) {
			return null;
		}
		// else
		Ps ps = new Ps();
		ps.setNationalId(nationalId);
		ps.setDams(dams);
		return ps;
	}
}
