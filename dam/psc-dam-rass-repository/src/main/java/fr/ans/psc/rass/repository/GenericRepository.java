package fr.ans.psc.rass.repository;

import fr.ans.psc.rass.model.StructureIds;

public interface GenericRepository {

	public StructureIds findByStructureTechnicalId(String technicalStructureId);
}
