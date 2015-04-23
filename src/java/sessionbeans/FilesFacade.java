/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Files;
import entitybeans.Filetype;
import entitybeans.Projects;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Facade to manage files in the database
 * @author Lucia Estelles Lopez
 * @author Asier Gonzalez
 */
@Stateful
public class FilesFacade extends AbstractFacade<Files> {

    /**
     * Object that controls the table entities 
     */
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
    /**
     * Empty constructor needed by the EJB to initialise the facade 
     */
    public FilesFacade() {
        super(Files.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Gets the files associated to the given project
     * @param idProject Identifier of the project
     * @return List of the project files
     */
    public List<Files> getProjectFiles(int idProject) {
            
        Query q = em.createNamedQuery("Files.findByProj");
        q.setParameter("idprojects", idProject);
        return q.getResultList();
        
    }
    
    /**
     * Gets the files of an specific type
     * @param filetypeid Identifier of the file type
     * @return List of the files with the selected type
     */
    public List<Files> getTypeFiles(int filetypeid) {
            
        Query q = em.createNamedQuery("Files.findByType");
        q.setParameter("filetypeid", new Filetype(filetypeid));
        return q.getResultList();        
    }
    
    /**
     * Gets the files of an particular project and file type
     * @param idProject Identifier of the project
     * @param filetypeid Identifier of the file type
     * @return List of the files matching the search parameters
     */
    public List<Files> getFilesForTool(int idProject, int filetypeid){
        Query q = em.createNamedQuery("Files.findByProjAndType");
        q.setParameter("idprojects", idProject);
        q.setParameter("filetypeid", new Filetype(filetypeid));
        return q.getResultList(); 
    }
    
    /**
     * Remove files from the database
     * @param file File to be deleted
     */
    public void deleteFile(Files file) {
        Collection<Projects> projects = file.getProjectsCollection();
        // remove file from all projects to which it belongs
        for (Projects project: projects) {
            Collection<Files> files = project.getFilesCollection();
            files.remove(file);
            project.setFilesCollection(files);
        }
        // remove database entry for the file itself
        remove(file);
    }
}
