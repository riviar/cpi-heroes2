/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import entitybeans.Projects;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import managedbeans.UtilityBean;
import sessionbeans.AccountSessionFacade;
import sessionbeans.JobHistoryFacade;
import sessionbeans.ProjectSessionFacade;
import sessionbeans.WorkGroupSessionFacade;

/**
 *
 * @author Fox
 */
@ManagedBean//(name = "TestToolBean")
@RequestScoped
public class TestToolBean {

    private Projects project;
        
    private Jobhistory newJob;// = new Jobhistory();

    private String newJobName = "";
    private String inputPath="";
    private String inputPath2="";
    private String windowSize ="";
    private String qualityth ="";
    private String outputFile = "none";

    private String kmerCount = "";

    private String seqType="";
    private String kmer="";
    private String insLen="";
    private String jobid="";
    
    @EJB
    ProjectSessionFacade projectFacade;
//    @EJB
//    AuthenticationBean authBean;
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    @ManagedProperty(value = "#{param.selectedProject}")
    private String selectedProject;
    
    public Jobhistory getNewJob() {
        return newJob;
    }

    public void setNewJob(Jobhistory newJob) {
        this.newJob = newJob;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    public void setProjectFacade(ProjectSessionFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }
    
    
        /**
     * Creates a new instance of WorkgroupBean
     */
    public TestToolBean() {

        //TODO: code taken from AuthenticationBean - should call it there instead!
        // get current session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // set user attribute of session

        project = new Projects();
        newJob = new Jobhistory();
        
    }

    
    public void setNewJob(String newJobName) {
        System.out.println("Non expected");
        this.newJob = new Jobhistory(newJobName);
    }
    
    public String doVelvet(){
        getUtilityBean().setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));
        return "new_job_velvet"; 
    }
    
    public String runVelvet() {
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));   
        //inputPath are left reads and inputPath2 are rightReads
        AbstractJob job = new VelvetJob(getSeqType(), getInputPath(), getInputPath(), getKmer(), getInsLen(), getJobid());
        job.execute();
        return "project";
    }
        
    public String doTrinity(){
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));
        return "new_job_trinity"; 
    }
    
    public String runTrinity() {
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));   
        //inputPath are left reads and inputPath2 are rightReads
        AbstractJob job = new TrinityJob(getSeqType(), getInputPath(), getInputPath(), getJobid());
        job.execute();
        return "project";
    }

    public String getSeqType() {
        return seqType;
    }

        
        
    /**
     * @return the utilityBean
     */
    public UtilityBean getUtilityBean() {
        return utilityBean;
    }



    /**
     * @return the selectedProject
     */
    public String getSelectedProject() {
        return selectedProject;
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the inputPath2
     */
    public String getInputPath2() {
        return inputPath2;
    }

    /**
     * @param inputPath2 the inputPath2 to set
     */
    public void setInputPath2(String inputPath2) {
        this.inputPath2 = inputPath2;        
    }
    
    public String getKmerCount() {
        return kmerCount;
    }
    
  
    public void setKmerCount(String kmerCount) {
        this.kmerCount = kmerCount;
    }

    /**
     * @return the windowSize
     */
    public String getWindowSize() {
        return windowSize;
    }

    /**
     * @param windowSize the windowSize to set
     */
    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * @return the qualityth
     */
    public String getQualityth() {
        return qualityth;
    }

    /**
     * @param qualityth the qualityth to set
     */
    public void setQualityth(String qualityth) {
        this.qualityth = qualityth;
    }

    /**
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

   
    
    public void runFastQC() {
        AbstractJob job = new FastQCJob(inputPath);
        job.execute();
        outputFile = inputPath.substring(0, inputPath.lastIndexOf("."));
    }
 
    public void runSeecer() {
        AbstractJob job = new SeecerJob(getInputPath(), getInputPath2(), getKmerCount());
        job.execute();
        outputFile = inputPath.substring(0, inputPath.lastIndexOf("."));
    }
    
    public String doTrimmomatic() {
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));
        return "new_job_trimmomatic";
    }

    @EJB
    JobHistoryFacade jobHistoryFacade;
    
    public String runTrimmomatic() {
        System.out.println("runTrimmomatic");
        //System.out.println(getNewJobName() + " " + getInputPath() + " " + getInputPath2() + " " + getWindowSize() + " " + getQualityth());
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(getSelectedProject())));
        AbstractJob job = new TrimmomaticJob(jobHistoryFacade, newJob.getJobname(), getInputPath(), getInputPath2(), getWindowSize(), getQualityth());
        //AbstractJob job = new TrimmomaticJob(getInputPath(), getInputPath2(), getWindowSize(), getQualityth());
        job.execute();
        
        //System.out.println(newJob.getJobname());
        jobHistoryFacade.addJob(newJob);
        
        
        return "project";
    }

    /**
     * @return the kmer
     */
    public String getKmer() {
        return kmer;
    }

    /**
     * @param kmer the kmer to set
     */
    public void setKmer(String kmer) {
        this.kmer = kmer;
    }

    /**
     * @return the insLen
     */
    public String getInsLen() {
        return insLen;
    }

    /**
     * @param insLen the insLen to set
     */
    public void setInsLen(String insLen) {
        this.insLen = insLen;
    }

    /**
     * @return the jobid
     */
    public String getJobid() {
        return jobid;
    }

    /**
     * @param jobid the jobid to set
     */
    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    /**
     * @return the newJobName
     */
    public String getNewJobName() {
        return newJobName;
    }

    /**
     * @param newJobName the newJobName to set
     */
    public void setNewJobName(String newJobName) {
        this.newJobName = newJobName;
    }

}
