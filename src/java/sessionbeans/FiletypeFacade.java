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
 *
 * @author lestelles
 */
@Stateless
public class FiletypeFacade extends AbstractFacade<Filetype> {

    @PersistenceContext(unitName = "RNAseqPU")
    private EntityManager em;
    
    public FiletypeFacade() {
        super(Filetype.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
        
}
