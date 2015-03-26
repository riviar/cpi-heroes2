/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Files;
import entitybeans.Projects;
import entitybeans.Users;
import entitybeans.Workgroups;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public void addFileToProject(Files file, Projects project) {
        try {
            Collection<Files> files = project.getFilesCollection();
            files.add(file);
            project.setFilesCollection(files);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or file does not exist!"));

        }
    }

    public void removeFileFromProject(Files file, Projects project) {
        try {
            Collection<Files> files = project.getFilesCollection();
            files.remove(file);
            project.setFilesCollection(files);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or file does not exist!"));

        }
    }

    public Collection<Projects> getUserVisibleProjects(Users user) {
        Collection<Projects> userVisibleProjects = new HashSet();
        Query q = em.createNamedQuery("Projects.findInUsersWorkgroup", Projects.class);
        q.setParameter(1, user.getIdusers());
        userVisibleProjects.addAll(q.getResultList());
        userVisibleProjects.addAll(getUserOwnedProjects(user));
        userVisibleProjects.addAll(getPublicProjects(user));
        return userVisibleProjects;
    }

    public Collection<Projects> getPublicProjects(Users user) {
        try {
            Query q = em.createNamedQuery("Projects.findByVisibility", Projects.class);
            q.setParameter("visibility", "PUBLIC");
            return q.getResultList();
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or file does not exist!"));
        }
        return null;
    }

    public Collection<Projects> getUserOwnedProjects(Users user) {
        try {
            Query q = em.createNamedQuery("Projects.findByOwner", Projects.class);
            q.setParameter("user", user);
            return q.getResultList();
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or file does not exist!"));
        }
        return null;
    }
    
    public Projects retrieveProjectById(int id) {
        try {
            Query q = em.createNamedQuery("Projects.findByIdprojects");
            q.setParameter("idprojects", id);
            return (Projects) q.getSingleResult();
        } catch (NoResultException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Project doesn't exist!"));   
        }
        return null;
    }
}
