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
 * @author Asier Gonzalez
 */
@ManagedBean
@RequestScoped
public class ToolBean {

    /**
     * The project the job belongs to
     */
    private Projects project;

    /**
     * New job to be created and run according to the tool selected by the user
     */
    private Jobhistory newJob = new Jobhistory();
    
    /**
     * Job name
     */
    private String newJobName = "";

    /**
     * User selected name for the outcomes
     */
    private String outputFile = "none";
    
    /**
     * Enterprise JavaBean to create, configure and run the job
     */
    @EJB
    RNAseqJob job;

    /**
     * Facade to modify the files list of the projects in the database
     */
    @EJB
    ProjectSessionFacade projectFacade;

    /**
     * Facade to add and modify jobs
     */
    @EJB
    private JobHistoryFacade jobHistoryFacade;

    /**
     * Facade to add the resulting files to the database
     */
    @EJB
    private FilesFacade filesFacade;

    /**
     * Managed bean with session scoped information
     */
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    /**
     * The project the user selected retrieved from the web
     */
    @ManagedProperty(value = "#{param.selectedProject}")
    private String selectedProject;

    /**
     * Creates a new instance of ToolBean
     */
    public ToolBean() {
        project = new Projects();
    }

    /**
     * Sets the name of the job
     * @param newJobName Job name
     */
    public void setNewJob(String newJobName) {
        this.newJob = new Jobhistory(newJobName);
    }

    /**
     * Creates new job instance and executes it using selectedTool (UtilityBean)
     *
     * @return redirect string to go back to project page
     */
    public String runJob() {
        
        job.init(utilityBean.getSelectedProject(), utilityBean.getSelectedTool(), newJobName);
        job.execute();
        job.process();
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
