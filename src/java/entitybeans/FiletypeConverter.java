/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitybeans;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessionbeans.FiletypeFacade;

@FacesConverter(value = "filetypeConverter", forClass = Filetype.class)

/**
 *
 * @author user
 */
public class FiletypeConverter implements Converter {

    @EJB
    FiletypeFacade filetypeFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String filetypeTextID) {
        filetypeTextID = filetypeTextID.replace("entitybeans.Filetype[ filetypeid=", "");
        filetypeTextID = filetypeTextID.replace(" ]", "");
        int filetypeID = Integer.valueOf(filetypeTextID);
        Filetype filetype = filetypeFacade.find(filetypeID);
        return filetype;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
