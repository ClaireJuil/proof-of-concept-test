package fr.ans.psc.dam.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import fr.ans.psc.dam.model.Ps;

@ConditionalOnProperty(name = "database.type", havingValue = "oracle",matchIfMissing = false)
public interface OraclePsRepository extends GenericRepository {

	Ps findByNationalId(String nationalId);
}
