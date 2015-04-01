/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Users;
import java.util.Collection;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sessionbeans.AccountSessionFacade;

/**
 *
 * @author user
 */
@ManagedBean
@RequestScoped
public class AuthenticationBean {

    @EJB
    AccountSessionFacade accountFacade;
    
    @ManagedProperty(value="#{utilityBean}")
    private UtilityBean utilityBean;

    public Collection<Users> getAllUsers(){
        return accountFacade.findAll();
    }
    
    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    private Users newUser = new Users();

    /**
     * Creates a new instance of AuthenticationBean
     */
    public AuthenticationBean() {
    }
  
    public String loginUser() {
        //attempt login and get redirect page
        String redirectpage = loginUser(newUser.getUsername(), newUser.getPassword());
        return redirectpage;
    }

    public String loginUser(String username, String password) {
        // lookup username/password combination in database
        Users user = accountFacade.checkUserLogin(username, password);
        // if no matching user found, show error message
        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Incorrect login or password"));
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Logged In Successfully!"));
            // set user attribute in utilitybean
            utilityBean.setUser(user);
            newUser = user;
            return "home";
        }
    }


    public Users getNewUser() {
        return newUser;
    }

    public String registerNewUser() {
         //check if user with specified login already exists
        if (accountFacade.userExists(newUser.getUsername())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("This username is already registered!"));
        } else {
            accountFacade.registerUser(newUser);
        }
        return "index";
    }
    
    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
        session.invalidate();
        return "index";
    }
}
