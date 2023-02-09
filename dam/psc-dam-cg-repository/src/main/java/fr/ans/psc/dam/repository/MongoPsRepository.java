package fr.ans.psc.dam.repository;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

import fr.ans.psc.dam.model.Ps;

@ConditionalOnProperty(name = "database.type", havingValue = "mongo",matchIfMissing = false)
public interface MongoPsRepository extends GenericRepository, MongoRepository<Ps, String> {

	public Ps findByNationalId(String nationalId);
}
