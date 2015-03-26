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
import java.util.List;
import javax.ejb.Stateful;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Facade for managing workgroups
 *
 * @author Matthew Robinson
 */
@Stateful
public class WorkGroupSessionFacade extends AbstractFacade<Workgroups> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WorkGroupSessionFacade() {
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

    public void removeWorkgroup(Workgroups workgroup) {
        remove(workgroup);
    }

    public void updateWorkgroup(Workgroups workgroup) {
        edit(workgroup);
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

    public Collection<Workgroups> workgroupsOwnedByUser(Users user) {

        Query q = em.createNamedQuery("Workgroups.findByUsersOwner", Workgroups.class);
        q.setParameter("users", user);
        System.out.println("WGFacade - " + q.getResultList().size() + " owner results!");
        return q.getResultList();
    }

    public Collection<Workgroups> workgroupsWithUserMember(Users user) {

        Query q = em.createNamedQuery("Workgroups.findByUsersMember", Workgroups.class);
        q.setParameter(1, user.getIdusers());
        return q.getResultList();
    }

    public Workgroups retrieveWorkgroupById(int id) {

        Query q = em.createNamedQuery("Workgroups.findByIdworkgroups");
        q.setParameter("idworkgroups", id);
        return (Workgroups) q.getSingleResult();
    }

    public Users retrieveUserById(int id) {
        Query q = em.createNamedQuery("Users.findByIdusers");
        q.setParameter("idusers", id);
        return (Users) q.getSingleResult();
    }

}
