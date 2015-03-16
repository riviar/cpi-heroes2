/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Experiments;
import entitybeans.Files;
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
 * Facade for managing experiments
 *
 * @author Matthew Robinson
 */
@Stateful
public class ExperimentSessionFacade extends AbstractFacade<Experiments> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExperimentSessionFacade() {
        super(Experiments.class);
    }

    public void renameExperiment(String newname, Experiments experiment) {
        try {
            experiment.setExperimentname(newname);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Cannot rename experiment!"));
        }
    }

    public void createExperiment(Experiments experiment) {
        create(experiment);
    }

    public void deleteExperiment(Experiments experiment) {
        remove(experiment);
    }

    public void addExperimentToProject(Experiments experiment, Projects project) {
        try {
            Collection<Experiments> experiments = project.getExperimentsCollection();
            experiments.add(experiment);
            project.setExperimentsCollection(experiments);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or experiment does not exist!"));

        }
    }

    public void removeExperimentFromWorkgroup(Experiments experiment, Projects project) {
        try {
            Collection<Experiments> experiments = project.getExperimentsCollection();
            experiments.remove(experiment);
            project.setExperimentsCollection(experiments);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - project or experiment does not exist!"));

        }
    }

    public void addFileToExperiment(Files file, Experiments experiment) {
        try {
            Collection<Files> files = experiment.getFilesCollection();
            files.add(file);
            experiment.setFilesCollection(files);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - experiment or file does not exist!"));

        }
    }

    public void removeFileFromExperiment(Files file, Experiments experiment) {
        try {
            Collection<Files> files = experiment.getFilesCollection();
            files.remove(file);
            experiment.setFilesCollection(files);
        } catch (NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Error - experiment or file does not exist!"));

        }
    }
}
