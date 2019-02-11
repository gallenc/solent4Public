/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com600.example.journeyplanner.jpadao;

import java.util.List;
import org.solent.com600.example.journeyplanner.model.SysUser;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.solent.jpa.test.Employee;
import org.solent.com600.example.journeyplanner.model.SysUserDAO;

/**
 *
 * @author cgallen
 */
public class SysUserDAOJpaImpl implements SysUserDAO {

    EntityManager entityManager = null;

    public SysUserDAOJpaImpl(EntityManager em) {
        entityManager = em;
    }

    @Override
    public SysUser create(SysUser user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        //entityManager.close();
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer retrieve(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SysUser> retrieveAll() {
        Query q = entityManager.createQuery("select s from SysUser s");
        List<SysUser> sysUserList = q.getResultList();
        return sysUserList;
    }

    @Override
    public SysUser retrieveMatching(SysUser template) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SysUser update(SysUser entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
