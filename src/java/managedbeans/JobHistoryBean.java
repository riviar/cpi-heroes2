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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import sessionbeans.JobHistoryFacade;
import toolstuff.TestToolBean;

/**
 *
 * @author pitas
 */
@ManagedBean
@RequestScoped
public class JobHistoryBean {

    private String jobName;
        
    //private Jobhistory newJob;// = new Jobhistory("New Job 2");
    private Jobhistory newJob = new Jobhistory("New Job 2");

    public Jobhistory getNewJob() {
        return newJob;
    }

    public void setNewJob(Jobhistory newJob) {
        this.newJob = newJob;
    }
    
    @EJB
    JobHistoryFacade jobHistoryFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    @ManagedProperty(value = "#{param.currentJob}")
    private String currentJob;
    
    public void setcurrentJob(String currentJob) {
        this.setCurrentJob(currentJob);
    }
    
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }
    
    /**
     * Creates a new instance of JobHistoryBean
     */
    public JobHistoryBean() {
        //this.newJob = new Jobhistory();
        //jobHistoryFacade = new JobHistoryFacade();
    }
    
    public JobHistoryBean(Jobhistory newJob) {
        this.newJob = newJob;
    }
    
    public String addJob2History() {
        //check if user with specified login already exists
        /*if (jobHistoryFacade.jobExists(newJob.getJobname())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("This job already exists!"));
        } else {*/
        System.out.println("Job Name "+newJob.getJobname());
        //jobHistoryFacade = new JobHistoryFacade();
        if(jobHistoryFacade != null){
            jobHistoryFacade.addJob(newJob);
        }else{
            jobHistoryFacade = new JobHistoryFacade();
            jobHistoryFacade.addJob(newJob);
        }
        
        
        
        //}
        return "project";
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
        //List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getProjectJobs(utilityBean.getSelectedProject().getIdprojects());
        List<Jobhistory> list = new ArrayList(jobs.size());
        //List<Jobhistory> jobs = jobHistoryFacade.getProjectJobs(151);
        for (int i = jobs.size()-1; i>=0; i--) {
            //System.out.println(job.getProjectid().getIdprojects());
            //System.out.println(Integer.getInteger(selectedProject));
            //if(job.getProjectid().getIdprojects()==Integer.getInteger(selectedProject)){
                 //list.add(job);
            //}
            list.add(jobs.get(i));
           
        }
        //return jobs;
        return list;             
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
