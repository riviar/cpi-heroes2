/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import entitybeans.Projects;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import sessionbeans.FilesFacade;
import sessionbeans.JobHistoryFacade;
import sessionbeans.ProjectSessionFacade;
import toolstuff.RNAseqJob;

/**
 * Managed bean for running tool jobs
 * @author Rafal Kural
 */
@ManagedBean//(name = "TestToolBean")
@RequestScoped
public class ToolBean {

    private Projects project;

    private Jobhistory newJob = new Jobhistory();
    private String newJobName = "";

    private String outputFile = "none";
    
    @EJB
    RNAseqJob job;

    @EJB
    ProjectSessionFacade projectFacade;

    @EJB
    private JobHistoryFacade jobHistoryFacade;

    @EJB
    private FilesFacade filesFacade;
//    @EJB
//    AuthenticationBean authBean;
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    @ManagedProperty(value = "#{param.selectedProject}")
    private String selectedProject;

    /**
     * Creates a new instance of WorkgroupBean
     */
    public ToolBean() {
        project = new Projects();
    }

    public void setNewJob(String newJobName) {
        this.newJob = new Jobhistory(newJobName);
    }

    /**
     * Creates new job instance and executes it using selectedTool (UtilityBean)
     *
     * @return redirect string to go back to project page
     */
    public String runJob() {
        //System.out.println(utilityBean.getSelectedTool().getName());
        //RNAseqJob job = new RNAseqJob(newJobName);
        //RNAseqJob job = new RNAseqJob(utilityBean, jobHistoryFacade, filesFacade, projectFacade, newJobName);
        
        job.init(utilityBean.getSelectedProject(), utilityBean.getSelectedTool(), newJobName);
        //job.init(utilityBean.getSelectedProject(), utilityBean.getSelectedTool(), utilityBean.getUser().getEmail(), newJobName);
        job.execute();
        job.process();
        //System.out.println("Yes");
        return "project?faces-redirect=true";
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

    public Jobhistory getNewJob() {
        return newJob;
    }

    public void setNewJob(Jobhistory newJob) {
        this.newJob = newJob;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public void setProjectFacade(ProjectSessionFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    /**
     * @return the utilityBean
     */
    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    /**
     * @return the selectedProject
     */
    public String getSelectedProject() {
        return selectedProject;
    }

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }

}
