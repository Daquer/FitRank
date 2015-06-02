package br.com.fitrank.persistencia;

import br.com.fitrank.modelo.Fitness;

public class FitnessDAO extends GenericDAO<Fitness>{
	
	public FitnessDAO() {
		super(Fitness.class);
	}
	
//	private final EntityManager em;
//
//	public FitnessDAO(EntityManager em){
//		this.em=em;
//	}
//
//	//Persiste o novo usuario do facebook
//	public Fitness persisteUsuario(Fitness fitness){
//		em.getTransaction().begin();
//		fitness = em.merge(fitness);
//	    em.getTransaction().commit();
//	    
//	    return fitness;
//	}
}
