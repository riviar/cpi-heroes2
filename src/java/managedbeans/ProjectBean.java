/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import entitybeans.Projects;
import entitybeans.Workgroups;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessionbeans.ProjectSessionFacade;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class ProjectBean {

    private Projects project;
    private Files file;
    private String newProjectName;
    private Workgroups newProjectWorkgroup;
    private String newProjectVisibility;

    @EJB
    ProjectSessionFacade projectFacade;

    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    public String getNewProjectName() {
        return newProjectName;
    }

    public void setNewProjectName(String newProjectName) {
        this.newProjectName = newProjectName;
    }

    public String getNewProjectVisibility() {
        return newProjectVisibility;
    }

    public void setNewProjectVisibility(String newProjectVisibility) {
        this.newProjectVisibility = newProjectVisibility;
    }

    public Workgroups getNewProjectWorkgroup() {
        return newProjectWorkgroup;
    }

    public void setNewProjectWorkgroup(Workgroups newProjectWorkgroup) {
        this.newProjectWorkgroup = newProjectWorkgroup;
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    /**
     * Creates a new instance of ProjectBean
     */
    public ProjectBean() {

    }

    public String addFileToProject() {
        if (file == null || project == null) {
            return "errorpage";
        } else {
            projectFacade.addFileToProject(file, project);
        }
        return "projectpage?faces-redirect=true";
    }

    public String removeFileFromProject() {
        if (file == null || project == null) {
            return "errorpage?faces-redirect=true";
        } else {
            projectFacade.removeFileFromProject(file, project);
        }
        return "projectpage?faces-redirect=true";
    }

    public Collection<Projects> getUserVisibleProjects() {
        return projectFacade.getUserVisibleProjects(utilityBean.getUser());
    }

    public Collection<Projects> getUserOwnedProjects() {
        return projectFacade.getUserOwnedProjects(utilityBean.getUser());
    }

    public Collection<Projects> getAllPublicProjects() {
        return projectFacade.getAllPublicProjects();
    }

    public Collection<Projects> getUserPublicProjects() {
        return projectFacade.getUsersPublicProjects(utilityBean.getUser());
    }

    public Collection<Projects> getProtectedProjects() {
        return projectFacade.getUsersProtectedProjects(utilityBean.getUser());
    }

    public Collection<Projects> getPrivateProjects() {
        return projectFacade.getUsersPrivateProjects(utilityBean.getUser());
    }

    public String createProject() {
        Projects project = new Projects();
        project.setProjectname(newProjectName);
        project.setOwner(utilityBean.getUser());
        project.setWorkgroup(newProjectWorkgroup);
        project.setVisibility(newProjectVisibility);
        projectFacade.create(project);
        return "projects_menu?faces-redirect=true";
    }
}
