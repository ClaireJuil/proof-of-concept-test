package fr.ans.psc.rass.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import fr.ans.psc.rass.model.StructureIds;
import fr.ans.psc.rass.oracle.StructureIdsRowMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@ConditionalOnProperty(name = "database.type", havingValue = "oracle",matchIfMissing = false)
public class OracleStructureIdsRepositoryImpl implements OracleStructureIdsRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private static String QUERY = "SELECT i.ID_METIER " 
			+ "FROM M_ID_PM_RASS i "
			+ "JOIN M_SITE s on i.M_ID_PM_RASS_ID = s.M_ID_PM_RASS_EG_ID "
			+ "JOIN V_ID_STRUCT_EG v on v.M_SITE_ID = s.M_SITE_ID " 
			+ "where v.IDENTIFIANT_STRUCTURE = ? ";

	@Override
	public StructureIds findByStructureTechnicalId(String technicalStructureId) {
		log.info("Using Oracle Repository. technicalStructureId: {}}", technicalStructureId);
		List<StructureIds> ids = jdbcTemplate.query(QUERY, new StructureIdsRowMapper(), technicalStructureId);
		log.info("List<StructureIds> OK.  size: {} ", ids == null ? 0 : ids.size());
		if (ids.size()==1) {
			ids.get(0).setStructureTechnicalId(technicalStructureId);
			return ids.get(0);
		} else {
			//TODO exception pour httpStatus 410
			return null;
		}

	}
}
