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

    private Jobhistory newJob = new Jobhistory();
    private String newJobName = "";

    private String outputFile = "none";

    @EJB
    ProjectSessionFacade projectFacade;
//    @EJB
//    AuthenticationBean authBean;
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    @ManagedProperty(value = "#{param.selectedProject}")
    private String selectedProject;

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
    }

    public void setNewJob(String newJobName) {
        this.newJob = new Jobhistory(newJobName);
    }

    /**
     * Creates new job instance and executes it using selectedTool (UtilityBean)
     */
    public void runJob() {
        RNAseqJob job = new RNAseqJob(newJobName);
        job.execute();
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
