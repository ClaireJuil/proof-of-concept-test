package fr.ans.psc.rass.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.ans.psc.rass.model.StructureIds;

@ConditionalOnProperty(name = "database.type", havingValue = "mongo",matchIfMissing = false)
public interface MongoStructureIdsRepository extends GenericRepository, MongoRepository<StructureIds, String> {

	StructureIds findByStructureTechnicalId(String structureTechnicalId);
}
