/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import entitybeans.Projects;
import entitybeans.Workgroups;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import sessionbeans.FilesFacade;
import sessionbeans.ProjectSessionFacade;
import sessionbeans.WorkGroupSessionFacade;

/**
 *
 * @author user
 */
@ManagedBean
@ViewScoped
public class ProjectBean {

    private Projects project;
    private Files file;
    private String newProjectName;
    private Workgroups newProjectWorkgroup;
    private String newProjectVisibility;
    private List<Projects> userVisibleProjects;
    
    @EJB
    ProjectSessionFacade projectFacade;
    @EJB
    WorkGroupSessionFacade workgroupFacade;
    @EJB
    FilesFacade fileFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
//    @ManagedProperty(value = "#{param.selectedProject}")
    private Projects selectedProject;
//    @ManagedProperty(value = "#{param.fakeProperty}")

    public Projects getSelectedProject() {
        System.err.println("PB.getSelectedProject = " + selectedProject);
        return selectedProject;
    }

    public void setSelectedProject(Projects selectedProject) {       
        this.selectedProject = selectedProject;
        System.err.println("PB.setSelectedProject = " + selectedProject);
    }
    
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

    public List<Projects> getUserVisibleProjects() {
        if(userVisibleProjects == null) {
            userVisibleProjects = (List<Projects>) projectFacade.getUserVisibleProjects(utilityBean.getUser());
        }
        return userVisibleProjects;
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

    public String crateProjectWG() {
        createProject();
        return "workgroup?faces-redirect=true";
    }
    
    public String createProject() {
        Projects project = new Projects();
        project.setProjectname(newProjectName);
        project.setOwner(utilityBean.getUser());
        project.setWorkgroup(newProjectWorkgroup);
        project.setVisibility(newProjectVisibility);
        Date today = Date.from(Instant.now());
        project.setCreationdate(today);
        projectFacade.create(project);
        return "projects_menu?faces-redirect=true";
    }
    
    /**
     * Deletes the project currently represented by bean parameter <code>selectedProject</code>.
     * Removes associations between workgroups/files and deleted workgroup, but 
     * does not delete the associated workgroups/files themselves.
     * @return String "projects_menu"
     */
    
    public String deleteProject() {
        Projects project = selectedProject;
        // update workgroup associated with deleted project
        Workgroups workgroup = project.getWorkgroup();
        // test for non-null workgroup rather than project visibility
        // in case of future security model changes
        if(workgroup != null) {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.remove(project);
            workgroup.setProjectsCollection(projects);
            workgroupFacade.updateWorkgroup(workgroup);
        }
        // update files associatee with deleted project
        for(Files file: project.getFilesCollection()) {
            Collection<Projects> projects = file.getProjectsCollection();
            projects.remove(project);
            file.setProjectsCollection(projects);
            fileFacade.edit(file);
        }
        // delete project, which should now have no foreign keys associated
        projectFacade.remove(project);
        return "projects_menu?faces-redirect=true";
    }
    
     /**
     * Selects projects and redirects to its page
     * @return
     */
    public String selectProject() {
        System.err.println("PB.selectProject = " + selectedProject.toString());
        utilityBean.setSelectedProject(selectedProject);
        return "project?faces-redirect=true";
    }
    
}
