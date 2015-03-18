/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Projects;
import entitybeans.Workgroups;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Facade for managing projects
 *
 * @author Matthew Robinson
 */
@Stateful
public class ProjectSessionFacade extends AbstractFacade<Projects> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectSessionFacade() {
        super(Projects.class);
    }

    public void renameProject(String newname, Projects project) {
        try {
            project.setProjectname(newname);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot rename project!"));
        }
    }

    public void createProject(Projects project) {
        create(project);
    }

    public void deleteProject(Projects project) {
        remove(project);
    }

    public void addProjectToWorkgroup(Projects project, Workgroups workgroup) {
        try {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.add(project);
            workgroup.setProjectsCollection(projects);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - workgroup or project does not exist!"));

        }
    }

    public void removeProjectFromWorkgroup(Projects project, Workgroups workgroup) {
        try {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.remove(project);
            workgroup.setProjectsCollection(projects);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - workgroup or project does not exist!"));

        }
    }    
}
