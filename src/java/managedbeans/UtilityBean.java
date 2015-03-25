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
import toolstuff.util.Tool;

/**
 * Bean used to store information about session
 *
 * @author Fox
 */
@ManagedBean
@SessionScoped
public class UtilityBean {

    private Users user;
    private Users selectedUser;
    private Workgroups selectedWorkgroup;
    private Projects selectedProject;
    private Tool selectedTool;
    private Jobhistory newJob;

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
        return selectedUser;
    }
    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
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
