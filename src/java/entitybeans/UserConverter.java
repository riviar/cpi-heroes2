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
import sessionbeans.AccountSessionFacade;

@FacesConverter(value = "userConverter", forClass = Users.class)

/**
 *
 * @author user
 */
public class UserConverter implements Converter {

    @EJB
    AccountSessionFacade accountFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String userTextID) {
        userTextID = userTextID.replace("entitybeans.Users[ idusers=", "");
        userTextID = userTextID.replace(" ]", "");
        int userID = Integer.valueOf(userTextID);
        Users user = accountFacade.find(userID);
        return user;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null) return null;
        return value.toString();
    }
    
}
