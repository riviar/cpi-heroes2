/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import sessionbeans.JobHistoryFacade;

/**
 *
 * @author pitas
 */
@ManagedBean
@RequestScoped
public class JobHistoryBean {

    private String jobName;
    
    @EJB
    private JobHistoryFacade jobHistoryFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    private String selectedProject;
    
    @ManagedProperty(value = "#{param.currentJob}")
    private String currentJob;
    
    public void setcurrentJob(String currentJob) {
        this.setCurrentJob(currentJob);
    }
    
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
        selectedProject = utilityBean.getSelectedProject().getIdprojects().toString();
    }
    
    /**
     * Creates a new instance of JobHistoryBean
     */
    public JobHistoryBean() {
    }
    
     /**
     * @param jobName the jobName to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
        //System.out.println(jobName);
    }
    
    public List<Jobhistory> getJobs() {
        //List<String> list = new ArrayList();
        //List<Integer> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        /*for (Jobhistory job:jobs) {
            list.add(job.getJobname());
        }*/
        return jobs;        
    }
    
     public List<Jobhistory> getProjectJobs() {
        //List<Jobhistory> list = new ArrayList();
        //List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getProjectJobs(selectedProject);
        /*for (Jobhistory job:jobs) {
            System.out.println(job.getProjectid().getIdprojects());
            System.out.println(Integer.getInteger(selectedProject));
            if(job.getProjectid().getIdprojects()==Integer.getInteger(selectedProject)){
                 list.add(job);
            }
           
        }*/
        return jobs;        
    }
    
    public List<String> getJobName() {
        List<String> list = new ArrayList();
        //List<Integer> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        for (Jobhistory job:jobs) {
            list.add(job.getJobname());
        }
        return list;        
    }
    
    public List<String> getJobCommand() {
        List<String> list = new ArrayList();
        //List<Integer> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        for (Jobhistory job:jobs) {
            list.add(job.getCommandused());
        }
        return list;        
    }
    
    public int getJobPID(){
        return jobHistoryFacade.getJobPID(currentJob);
    }

    /**
     * @param currentJob the currentJob to set
     */
    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

   
    
}
