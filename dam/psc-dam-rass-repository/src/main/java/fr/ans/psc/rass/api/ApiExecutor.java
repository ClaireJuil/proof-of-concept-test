package fr.ans.psc.rass.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import fr.ans.psc.rass.model.StructureIds;
import fr.ans.psc.rass.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@ComponentScan(basePackages = {"fr.ans.psc.rass.repository"})
public class ApiExecutor {

	@Autowired
	GenericRepository repo;

	public StructureIds getIds(String technicalStructureId) {
		return repo.findByStructureTechnicalId(technicalStructureId);
	}
}
