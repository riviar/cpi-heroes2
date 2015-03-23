/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Jobhistory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedProperty;
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
    
   /* @ManagedProperty(value = "#{param.currentJob}")
    private String currentJob;*/

    public JobHistoryFacade() {
        super(Jobhistory.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
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
    public List<Jobhistory> getProjectJobs(String projectid) {
        List<Jobhistory> list = new ArrayList();
        
        Query q = em.createNamedQuery("Jobhistory.findByProjectid");
        //Query q = em.createNamedQuery("Jobhistory.findAll");
        //System.out.println("Project: " + projectid);
        
        q.setParameter("projectid", Integer.parseInt(projectid));
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
     * @return 
     */
    public int getJobPID(String currentJob){
        //System.out.println(currentJob);
        Query q = em.createNamedQuery("Jobhistory.findJobPID(currentJob)");
        return Integer.parseInt(q.getResultList().get(0).toString());
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}




