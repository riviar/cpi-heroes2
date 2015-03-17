/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import entitybeans.Projects;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

    @EJB
    ProjectSessionFacade projectFacade;

    /**
     * Creates a new instance of ProjectBean
     */
    public ProjectBean() {
    }

    public String addFileToProject() {
        if (file == null || project == null) {
            return "errorpage";
        } else {
//            projectFacade.addFileToProject(file, project);
        }
        return "projectpage";
    }

    public String removeFileFromProject() {
        if (file == null || project == null) {
            return "errorpage";
        } else {
//            projectFacade.removeFileFromProject(file, project);
        }
        return "projectpage";
    }
    
//    public Collection<Projects> getUserVisibleProjects() {      
//        return projectFacade.getUserVisibleProjects();
//    }
//
//    public Collection<Projects> getUserOwnedProjects() {      
//        return projectFacade.getUserOwnedProjects();
//    }

}
