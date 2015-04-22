/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entitybeans.Filetype;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Facade to manage the <i>Filetype</i> table in the database
 * @author Lucia Estelles Lopez
 */
@Stateless
public class FiletypeFacade extends AbstractFacade<Filetype> {

    /**
     * Object that controls the table entities 
     */
    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
    /**
     * Empty constructor needed by the EJB to initialise the facade 
     */
    public FiletypeFacade() {
        super(Filetype.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
        
}
