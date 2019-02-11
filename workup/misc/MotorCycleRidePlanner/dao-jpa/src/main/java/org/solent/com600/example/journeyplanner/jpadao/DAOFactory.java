package org.solent.com600.example.journeyplanner.jpadao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

public class DAOFactory {

    private static final String PERSISTENCE_UNIT_NAME = "motorcyclePersistence";
    private static EntityManagerFactory factory;
    private static EntityManager em;
    private static SysUserDAO sysUserDAO;
    
    static {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
        sysUserDAO = new SysUserDAOJpaImpl(em);
    }

    public static SysUserDAO getSysUserDAO() {
        return sysUserDAO;
    }

}
