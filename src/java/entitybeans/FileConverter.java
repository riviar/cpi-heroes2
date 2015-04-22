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
import sessionbeans.FilesFacade;

@FacesConverter(value = "fileConverter", forClass = Files.class)

/**
 *
 * @author user
 */
public class FileConverter implements Converter {

    @EJB
    FilesFacade fileFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String fileTextID) {
        fileTextID = fileTextID.replace("entitybeans.Files[ idresources=", "");
        fileTextID = fileTextID.replace(" ]", "");
        int fileID = Integer.valueOf(fileTextID);
        Files file = fileFacade.find(fileID);
        return file;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return null;
        return value.toString();
    }
    
}
