/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Tools;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Fox
 */
@Stateless
public class ToolsSidebarFacade extends AbstractFacade<Tools> {

    
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    public ToolsSidebarFacade() {
        super(Tools.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Tools> getAllTools() {
        Query q = em.createNamedQuery("Tools.findAll");
        return q.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
