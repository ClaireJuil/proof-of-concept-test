package fr.ans.psc.rass.oracle;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import fr.ans.psc.rass.model.StructureIds;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StructureIdsRowMapper implements RowMapper<StructureIds> {
	
	
	@Override
	public StructureIds mapRow(ResultSet rs, int rowNum) throws SQLException {
		log.debug("DamRowMapper: rowNum {}:", rowNum);
		
		StructureIds ids = new StructureIds();
		ids.setIdentifiantMetier(rs.getString("ID_METIER"));
		return ids;

	}
}
