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
import sessionbeans.JobHistoryFacade;

@FacesConverter(value = "jobHistoryConverter", forClass = Jobhistory.class)

/**
 *
 * @author user
 */
public class JobhistoryConverter implements Converter {

    @EJB
    JobHistoryFacade jobHistoryFacade;
    
    @Override
    //entities.Jobhistory[ idjobs=165
    public Object getAsObject(FacesContext context, UIComponent component, String jobHistoryTextID) {
        jobHistoryTextID = jobHistoryTextID.replace("entitybeans.Jobhistory[ idjobs=", "");
        jobHistoryTextID = jobHistoryTextID.replace(" ]", "");
        int jobID = Integer.valueOf(jobHistoryTextID);
        Jobhistory job = jobHistoryFacade.find(jobID);
        return job;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return null;
        return value.toString();
    }
    
}
