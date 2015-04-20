/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import entitybeans.Projects;
import entitybeans.Users;
import entitybeans.Workgroups;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import outputview.GenericOutput;
import toolstuff.util.Tool;

/**
 * Bean used to store information about session
 *
 * @author Rafal Kural
 */
@ManagedBean
@SessionScoped
public class UtilityBean {

    /**
     * Currently logged in user
     */
    private Users user;
    /**
     * Currently selected user
     */
    private Users selectedUser;
    /**
     * Currently selected workgroup
     */
    private Workgroups selectedWorkgroup;
    /**
     * Currently selected project
     */
    private Projects selectedProject;
    /**
     * Currently selected tool
     */
    private Tool selectedTool;
    /**
     * new job
     */
    private Jobhistory newJob;
    /**
     * Currently selected job
     */
    private Jobhistory selectedJob;
    /**
     * Currently selected output
     */
    private GenericOutput selectedOutput;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Workgroups getSelectedWorkgroup() {
        return selectedWorkgroup;
    }

    public void setSelectedWorkgroup(Workgroups selectedWorkgroup) {
        this.selectedWorkgroup = selectedWorkgroup;
    }

    public Projects getSelectedProject() {
        //System.out.println("Selected Project " + selectedProject);
        return selectedProject;
    }

    public void setSelectedProject(Projects selectedProject) {
        this.selectedProject = selectedProject;
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }

    public void setSelectedTool(Tool selectedTool) {
        this.selectedTool = selectedTool;
    }

    public Users getSelectedUser() {
        System.out.println("utilbean,getselecteduser called");
        return selectedUser;
    }
    public void setSelectedUser(Users selectedUser) {
        System.out.println("utilbean.setselecteduser called");
        this.selectedUser = selectedUser;
    }

    public Jobhistory getSelectedJob() {
        return selectedJob;
    }

    public void setSelectedJob(Jobhistory selectedJob) {
        this.selectedJob = selectedJob;
    }

    public GenericOutput getSelectedOutput() {
        return selectedOutput;
    }

    public void setSelectedOutput(GenericOutput selectedOutput) {
        this.selectedOutput = selectedOutput;
    }
    
    
    
    /**
     * Creates a new instance of UtilityBean
     */
    public UtilityBean() {
        this.selectedTool = null;
        this.selectedProject = null;
        this.selectedWorkgroup = null;
        this.user = null;
        this.newJob = null;
    }

    /**
     * @return the newJob
     */
    public Jobhistory getNewJob() {
        return newJob;
    }

    /**
     * @param newJob the newJob to set
     */
    public void setNewJob(Jobhistory newJob) {
        this.newJob = newJob;
    }

}
