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
import sessionbeans.WorkGroupSessionFacade;

@FacesConverter(value = "workgroupConverter", forClass = Workgroups.class)

/**
 *
 * @author user
 */
public class WorkgroupConverter implements Converter {

    @EJB
    WorkGroupSessionFacade workgroupFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String workgroupTextID) {
        workgroupTextID = workgroupTextID.replace("entitybeans.Workgroups[ idworkgroups=", "");
        workgroupTextID = workgroupTextID.replace(" ]", "");
        int workgroupID = Integer.valueOf(workgroupTextID);
        Workgroups workgroup = workgroupFacade.find(workgroupID);
        return workgroup;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
    }
    
}
