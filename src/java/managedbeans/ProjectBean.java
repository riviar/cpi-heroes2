/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import entitybeans.Projects;
import entitybeans.Users;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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
    private final Users user;
    
    @EJB
    ProjectSessionFacade projectFacade;

    /**
     * Creates a new instance of ProjectBean
     */
    public ProjectBean() {
                //TODO: code taken from AuthenticationBean - should call it there instead!
        // get current session
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        // set user attribute of session
        user = (Users) session.getAttribute("user");
    }

    public String addFileToProject() {
        if (file == null || project == null) {
            return "errorpage";
        } else {
            projectFacade.addFileToProject(file, project);
        }
        return "projectpage";
    }

    public String removeFileFromProject() {
        if (file == null || project == null) {
            return "errorpage";
        } else {
            projectFacade.removeFileFromProject(file, project);
        }
        return "projectpage";
    }
    
    public Collection<Projects> getUserVisibleProjects() {      
        return projectFacade.getUserVisibleProjects(user);
    }

    public Collection<Projects> getUserOwnedProjects() {      
        return projectFacade.getUserOwnedProjects(user);
    }

}
