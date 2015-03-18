/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Jobhistory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author pitas
 */

@Stateless
public class JobHistoryFacade extends AbstractFacade<Jobhistory> {

    
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    public JobHistoryFacade() {
        super(Jobhistory.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * It retrieves all the user jobs from the history
     * @return 
     */
    public List<Integer> getAllJobs() {
        Query q = em.createNamedQuery("Jobhistory.findAll");
        return q.getResultList();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}




