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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    private String newUserName;

    @EJB
    WorkGroupSessionFacade workgroupFacade;
    @EJB
    ProjectSessionFacade projectFacade;
    @EJB
    AccountSessionFacade usersFacade;

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;
    @ManagedProperty(value="#{utilityBean}")
    private UtilityBean utilityBean;
    //Stores ID of the workgroup
    @ManagedProperty(value="#{param.selectedWorkgroup}")
    private String selectedWorkgroup;
    //Stored ID of the project
    @ManagedProperty(value="#{param.selectedProject}")
    private String selectedProject;
    @ManagedProperty(value="#{param.selectedUser}")
    private String selectedUser;
    
    /*
    Creates a HashMap to use it in workgroups.xhtml
    */
    private static Map<String,Object> userMap;
    public Map<String,Object> getUsersMap() {
        userMap = new LinkedHashMap<>();
        List<Users> users=usersFacade.findAll();
        for (Users user:users){
            userMap.put(user.getUsername(), user);
        }        
        return userMap;
    }

    public void setAuthenticationBean(AuthenticationBean authBean) {
        this.authenticationBean = authBean;
    }

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

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

    public String getNewUserName() {
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    

    /**
     * Creates a new instance of WorkgroupBean
     */
    public WorkgroupBean() {

        //user = utilityBean.getUser();

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
        System.out.println("adduser called");
        Users user = utilityBean.getSelectedUser();
        if (user == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Users> users = workgroup.getUsersCollection();
            users.add(user);
            workgroup.setUsersCollection(users);
            workgroupFacade.updateWorkgroup(workgroup);
        }
        return "workgroup.xhtml";
    }

    public String removeUserFromWorkgroup() {           
            Collection<Users> users = workgroup.getUsersCollection();
            users.remove(workgroupFacade.retrieveUserById(Integer.valueOf(selectedUser)));
            workgroup.setUsersCollection(users);
            workgroupFacade.updateWorkgroup(workgroup);
        return "workgroup.xhtml";
    }

    public String addProjectToWorkgroup() {
        project = new Projects();
        project.setProjectname(newProjectName);
        project.setOwner(utilityBean.getUser());
        if (project == null || workgroup == null) {
            return "invaliddataerrorpage";
        } else {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.add(project);
            workgroup.setProjectsCollection(projects);
            workgroupFacade.updateWorkgroup(workgroup);
        }
        return "workgroupspage";
    }

    public String removeProjectFromWorkgroup() {

        Collection<Projects> projects = workgroup.getProjectsCollection();
        projects.remove(projectFacade.retrieveProjectById(Integer.valueOf(selectedProject)));
        workgroup.setProjectsCollection(projects);
        workgroupFacade.updateWorkgroup(workgroup);

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
            return workgroupFacade.workgroupsWithUserMember(utilityBean.getUser());
        }
    }

    public Workgroups getNewWorkgroup() {
        user = authenticationBean.getLoggedInUser();
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

    public String selectUser() {
        utilityBean.setSelectedUser(workgroupFacade.retrieveUserById(Integer.valueOf(selectedUser)));
        return "project";
    }
}
