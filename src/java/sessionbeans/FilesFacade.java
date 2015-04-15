/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Files;
import entitybeans.Filetype;
import entitybeans.Projects;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author lestelles
 */
//@Stateless
@Stateful
public class FilesFacade extends AbstractFacade<Files> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
    public FilesFacade() {
        super(Files.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Files> getProjectFiles(int idProject) {
            
        Query q = em.createNamedQuery("Files.findByProj");
        q.setParameter("idprojects", idProject);
        //System.out.println("--------idProject: "+idProject);
        return q.getResultList();
        
    }
    
    public List<Files> getTypeFiles(int filetypeid) {
            
        Query q = em.createNamedQuery("Files.findByType");
        q.setParameter("filetypeid", new Filetype(filetypeid));
        //System.out.println("--------idProject: "+idProject);
        return q.getResultList();        
    }
    
    public List<Files> getFilesForTool(int idProject, int filetypeid){
        Query q = em.createNamedQuery("Files.findByProjAndType");
        q.setParameter("idprojects", idProject);
        q.setParameter("filetypeid", new Filetype(filetypeid));
        //System.out.println("--------idProject: "+idProject);
        return q.getResultList(); 
    }
    
}
