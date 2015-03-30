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
import sessionbeans.ProjectSessionFacade;

@FacesConverter(value = "projectConverter", forClass = Projects.class)

/**
 *
 * @author user
 */
public class ProjectConverter implements Converter {

    @EJB
    ProjectSessionFacade projectFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String projectTextID) {
        projectTextID = projectTextID.replace("entitybeans.Projects[ idprojects", "");
        projectTextID = projectTextID.replace(" ]", "");
        int projectID = Integer.valueOf(projectTextID);
        Projects project = projectFacade.find(projectID);
        return project;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
