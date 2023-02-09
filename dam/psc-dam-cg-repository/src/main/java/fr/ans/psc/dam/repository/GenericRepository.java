package fr.ans.psc.dam.repository;

import fr.ans.psc.dam.model.Ps;

public interface GenericRepository {

	//public List<SimpleDam> findByNationalId(String nationalId);
	public Ps findByNationalId(String nationalId);
	 
}
