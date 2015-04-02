/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    
    @ManagedProperty(value = "#{param.selectedJob}")
    private String selectedJobId;
    
    public void setcurrentJob(String currentJob) {
        this.setCurrentJob(currentJob);
    }
    
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public String getSelectedJobId() {
        return selectedJobId;
    }

    public void setSelectedJobId(String selectedJobId) {
        this.selectedJobId = selectedJobId;
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
    
    public int getJobPID(String jobName){
         System.out.println(jobName);
        return jobHistoryFacade.getJobPID(getCurrentJob());
    }
    
    public String getJobRunningTime(int PID){
        
        List<String> commandList = new ArrayList(5);
        commandList.add("ps");
        commandList.add("-p");
        commandList.add(Integer.toString(PID));
        commandList.add("-o");
        commandList.add("etime");
        ProcessBuilder pb = new ProcessBuilder().command(commandList);
        //ProcessBuilder pb = new ProcessBuilder().command("pwd").redirectErrorStream(true);
        Process p = null;
        StringBuffer output = new StringBuffer();
        try {
            p = pb.start();  
            p.waitFor();
            
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if(!line.contains("ELAPSED")){
                    output.append(line);
                }
                
            }
            
            
            /*StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");

            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

            // start gobblers
            outputGobbler.start();
            errorGobbler.start();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        
        return output.toString();
    }
    
    /*public String getJobPID(){
        return Integer.toString(jobHistoryFacade.getJobPID(currentJob));
    }*/

    /**
     * @param currentJob the currentJob to set
     */
    public void setCurrentJob(String currentJob) {
        System.out.println("Constructor: " + currentJob);
        this.currentJob = currentJob;
    }
    
    /**
     * Sets selected jobHistory item in utility bean and redirects to output page
     * @return 
     */
    public String selectJobHistoryItem() {
        System.out.println("Looking for job with id " + selectedJobId);
        Jobhistory selectedJobHistoryItem = jobHistoryFacade.findJobHistoryById(Integer.valueOf(selectedJobId));
        System.out.println("Found job with name " + selectedJobHistoryItem.getJobname());
        utilityBean.setSelectedJob(selectedJobHistoryItem);
        return "job_output";

    /**
     * @return the currentJob
     */
    public String getCurrentJob() {
        return currentJob;
    }
   
}
