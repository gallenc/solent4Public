package org.solent.examples.com600.jpadao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOFactory {
	
	   private static final EntityManagerFactory emFactoryObj;
	    private static final String PERSISTENCE_UNIT_NAME = "motorcycle-persistence";  
	 
	    static {
	        emFactoryObj = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	    }
	 
	    // This Method Is Used To Retrieve The 'EntityManager' Object
	    public static EntityManager getEntityManager() {
	        return emFactoryObj.createEntityManager();
	    }

}
