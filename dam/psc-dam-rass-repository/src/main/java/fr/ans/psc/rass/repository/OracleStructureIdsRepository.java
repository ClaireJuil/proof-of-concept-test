package fr.ans.psc.rass.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import fr.ans.psc.rass.model.StructureIds;

@ConditionalOnProperty(name = "database.type", havingValue = "oracle",matchIfMissing = false)
public interface OracleStructureIdsRepository extends GenericRepository{

	StructureIds findByStructureTechnicalId(String technicalStructureId);
}
