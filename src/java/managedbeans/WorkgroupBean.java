/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Projects;
import entitybeans.Users;
import entitybeans.Workgroups;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessionbeans.AccountSessionFacade;
import sessionbeans.ProjectSessionFacade;
import sessionbeans.WorkGroupSessionFacade;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class WorkgroupBean {

    private Workgroups workgroup;
    private Users user;
    private Projects project;
    private String newProjectName;

    @EJB
    WorkGroupSessionFacade workgroupFacade;
    @EJB
    ProjectSessionFacade projectFacade;
//    @EJB
//    AuthenticationBean authBean;
    @ManagedProperty(value="#{utilityBean}")
    private UtilityBean utilityBean;
    //Stores ID of the workgroup
    @ManagedProperty(value="#{param.selectedWorkgroup}")
    private String selectedWorkgroup;
    //Stored ID of the project
    @ManagedProperty(value="#{param.selectedProject}")
    private String selectedProject;

    public void setSelectedProject(String selectedProject) {
        this.selectedProject = selectedProject;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public void setSelectedWorkgroup(String selectedWorkgroup) {
        this.selectedWorkgroup = selectedWorkgroup;
    }

    public String getNewProjectName() {
        return newProjectName;
    }

    public void setNewProjectName(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    /**
     * Creates a new instance of WorkgroupBean
     */
    public WorkgroupBean() {

        //TODO: code taken from AuthenticationBean - should call it there instead!
        // get current session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // set user attribute of session
        user = (Users) session.getAttribute("user");

        workgroup = new Workgroups();
        
        project = new Projects();
        
    }

    /**
     * Returns a collection containing all projects in current workgroup
     *
     * @return Collection containing all projects in current workgroup
     */
    public Collection<Projects> getProjectsInWorkgroup() {
        
        if (workgroup == null) {
            return new ArrayList();
        } else {
            return workgroup.getProjectsCollection();
        }
    }

    /**
     * Returns a collection containing all users in current workgroup
     *
     * @return Collection containing all users in current workgroup
     */
    public Collection<Users> getUsersInWorkgroup() {
        workgroup = utilityBean.getSelectedWorkgroup();
        if (workgroup == null) {
            return new ArrayList();
        } else {
            for (Users nextuser : workgroup.getUsersCollection()) {
                System.out.println(nextuser);
            }
            return workgroup.getUsersCollection();
        }
    }

    public String addUserToWorkgroup() {
        if (user == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Users> users = workgroup.getUsersCollection();
            users.add(user);
            workgroup.setUsersCollection(users);
        }
        return "workgroupspage";
    }

    public String removeUserFromWorkgroup() {
        if (user == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Users> users = workgroup.getUsersCollection();
            users.remove(user);
            workgroup.setUsersCollection(users);
        }
        return "workgroupspage";
    }
    
    public String addProjectToWorkgroup() {
        project = new Projects();
        project.setProjectname(newProjectName);
        if (project == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.add(project);
            workgroup.setProjectsCollection(projects);
        }
        return "workgroupspage";
    }

    public String removeProjectFromWorkgroup() {
        if (project == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.add(project);
            workgroup.setProjectsCollection(projects);
        }
        return "workgroupspage";
    }

    public String createWorkgroup() {
        if (workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            workgroupFacade.createWorkgroup(workgroup);
        }
        return "workgroupspage";
    }

    public String removeWorkgroup() {
        if (workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            workgroupFacade.removeWorkgroup(workgroup);
        }
        return "workgroupspage";
    }

    public Collection<Workgroups> getWorkgroupsForUser() {

        if (utilityBean.getUser() == null) {
            return new ArrayList();
        } else {
            return workgroupFacade.workgroupsForUser(utilityBean.getUser());
        }
    }

    public Workgroups getNewWorkgroup() {
        workgroup.setOwner(user);       
        ArrayList<Users> users = new ArrayList();
        users.add(user);
        workgroup.setUsersCollection(users);
        return workgroup;
    }
    
    /**
     * Selects workgroup and redirects to its page
     * @return 
     */
    public String selectWorkgroup() {
        utilityBean.setSelectedWorkgroup(workgroupFacade.retrieveWorkgroupById(Integer.valueOf(selectedWorkgroup)));
        return "workgroup";
    }

    /**
     * Selects projects and redirects to its page
     * @return 
     */
    public String selectProject() {
        utilityBean.setSelectedProject(projectFacade.retrieveProjectById(Integer.valueOf(selectedProject)));
        return "project";
    }
}
