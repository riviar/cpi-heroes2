/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Jobhistory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * Facade to manage jobs in the database
 * @author Asier Gonzalez
 */

@Stateful
public class JobHistoryFacade extends AbstractFacade<Jobhistory> {

    /**
     * The job to be persisted
     */
    private Jobhistory newJob;
    
    /**
     * Object that controls the table entities 
     */
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
          return em;      
    }
    
    /**
     * Empty constructor needed by the EJB to initialise the facade 
     */
    public JobHistoryFacade() {
        super(Jobhistory.class);
    }
    
    
    /**
     * It retrieves all the user jobs from the history
     * @return List with all the jobs 
     */
    public List<Jobhistory> getAllJobs() {
        Query q = em.createNamedQuery("Jobhistory.findAll");
        return q.getResultList();
    }
    
    /**
     * It retrieves all the jobs in the history related to the selected project
     * @return List wit all the jobs of the selected project
     */
    public List<Jobhistory> getProjectJobs(int projectid) {
        List<Jobhistory> list = new ArrayList();
        
        Query q = em.createNamedQuery("Jobhistory.findByProjectid");
        
        q.setParameter("projectid", projectid);
                
        return q.getResultList();
        
    }
    
    
    /**
     * It retrieves the PID of a job given its name
     * @param currentJob Name of the job whose PID is searched
     * @return Operating system level process identifier of the job
     */
    public int getJobPID(String currentJob){
        
        Query q = em.createNamedQuery("Jobhistory.findByJobname");
        q.setParameter("jobname", currentJob);
        return Integer.parseInt(q.getResultList().get(0).toString());        
    }

    /**
     * Adds new job to database
     * @param newJob Job to be added
     */
    public void addJob(Jobhistory newJob){
        if(newJob == null){
            System.out.println("Malo!");
            this.newJob = new Jobhistory("Manually entered job");
        }else{
            this.newJob = newJob;
        }
        System.out.println(newJob.getCommandused());
        create(newJob);
      
    }
    
   /**
    * Checks if exists a job with the given name
    * @param jobname Name of the searched job
    * @return <code>True</code> if the job exists and <code>FALSE</code> otherwise
    */
    public boolean jobExists(String jobname) {
        Query q = em.createQuery("SELECT j FROM Jobhistory j WHERE j.jobname=:jobname");
        q.setParameter("username", jobname);
        try {
            // return true if any record matching username is found in database
            // can getSingleResult ever return null and not throw NoResultException?
            if (q.getSingleResult() != null) {
                return true;
            }
        } catch (NoResultException ex) {
            return false;
        }
        // should never reach here, so trap with assert, but return true anyway as IDE complains otherwise
        assert false;
        return true;
    }
    
    /**
     * Retrieves Job item by id
     * @param id Database-level identifier of the searched job
     * @return Matching job
     */
    public Jobhistory findJobHistoryById(int id) {
        Query q = em.createNamedQuery("Jobhistory.findByIdjobs");
        q.setParameter("idjobs", id);
        try {
            return (Jobhistory) q.getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}




