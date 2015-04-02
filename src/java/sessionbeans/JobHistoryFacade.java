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
 *
 * @author pitas
 */

//@Stateless
@Stateful
public class JobHistoryFacade extends AbstractFacade<Jobhistory> {

    private Jobhistory newJob;
    
    
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
   /* @ManagedProperty(value = "#{param.currentJob}")
    private String currentJob;*/
  
    @Override
    protected EntityManager getEntityManager() {
        /*if(em == null){
             System.out.println("Yes, it's null");
             EntityManagerFactory emf = Persistence.createEntityManagerFactory("RNAseqPU");
             EntityManager ecm = emf.createEntityManager(); 
             return ecm;
        }else{*/
            return em;
        //}        
    }
    
    public JobHistoryFacade() {
        super(Jobhistory.class);
    }
    
        
    /*public void setcurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }*/
    
    /**
     * It retrieves all the user jobs from the history
     * @return 
     */
    public List<Jobhistory> getAllJobs() {
        Query q = em.createNamedQuery("Jobhistory.findAll");
        return q.getResultList();
    }
    
    /**
     * It retrieves all the jobs in the history related to the selected project
     * @return 
     */
    public List<Jobhistory> getProjectJobs(int projectid) {
        List<Jobhistory> list = new ArrayList();
        
        Query q = em.createNamedQuery("Jobhistory.findByProjectid");
        //Query q = em.createNamedQuery("Jobhistory.findAll");
        //System.out.println("Project: " + projectid);
        
        q.setParameter("projectid", projectid);
        //System.out.println(q.getResultList().size());
        /*List<Jobhistory> jobs = q.getResultList();
        for (Jobhistory job:jobs) {
            System.out.println(job.getProjectid().getIdprojects());
            System.out.println(Integer.parseInt(projectid));
            if(job.getProjectid().getIdprojects()==Integer.parseInt(projectid)){
                 list.add(job);
            }
        }*/
        
        return q.getResultList();
        //    return list;
    }
    
    
    /**
     * It retrieves the PID of a job given its name
     * @param currentJob
     * @return 
     */
    public int getJobPID(String currentJob){
       // System.out.println(currentJob);
        
        Query q = em.createNamedQuery("Jobhistory.findByJobname");
        q.setParameter("jobname", currentJob);
        return Integer.parseInt(q.getResultList().get(0).toString());        
    }

    /**
     * Add new job to database
     * @param newJob 
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
        //System.out.println(this.newJob.getJobname());
        //System.out.println(this.newJob.getProcessid());
        //System.out.println(this.newJob.getCommandused());
        //create(new Jobhistory("Manually entered job"));
        
            
        
    }
    
   
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
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}




