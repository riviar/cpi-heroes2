/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Projects;
import entitybeans.Users;
import entitybeans.Workgroups;
import java.util.Collection;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Facade for managing workgroups
 *
 * @author Matthew Robinson
 */
@Stateful
public class ProjectSessionFacade extends AbstractFacade<Workgroups> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectSessionFacade() {
        super(Workgroups.class);
    }

    public void renameWorkgroup(String newname, Workgroups workgroup) {
        try {
            workgroup.setWorkgroupname(newname);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot rename workgroup!"));
        }
    }

    public void createWorkgroup(Workgroups workgroup) {
        create(workgroup);
    }

    public void deleteWorkgroup(Workgroups workgroup) {
        remove(workgroup);
    }

    public void addUserToWorkgroup(Users user, Workgroups workgroup) {
        try {
            Collection<Users> users = workgroup.getUsersCollection();
            users.add(user);
            workgroup.setUsersCollection(users);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - user or workgroup does not exist!"));

        }
    }

    public void removeUserFromWorkgroup(Users user, Workgroups workgroup) {
        try {
            Collection<Users> users = workgroup.getUsersCollection();
            users.remove(user);
            workgroup.setUsersCollection(users);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - user or workgroup does not exist!"));

        }
    }

    public void addProjectToWorkgroup(Projects project, Workgroups workgroup) {
        try {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.add(project);
            workgroup.setProjectsCollection(projects);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or workgroup does not exist!"));

        }
    }

    public void removeProjectFromWorkgroup(Projects project, Workgroups workgroup) {
        try {
            Collection<Projects> projects = workgroup.getProjectsCollection();
            projects.remove(project);
            workgroup.setProjectsCollection(projects);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or workgroup does not exist!"));

        }
    }
}
