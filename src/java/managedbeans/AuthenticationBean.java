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
 * Managed bean dealing with registering and logging users in/out
 * @author Rafal Kural
 * @version 1.0
 */
@ManagedBean
@RequestScoped
public class AuthenticationBean {

    @EJB
    AccountSessionFacade accountFacade;
    
    @ManagedProperty(value="#{utilityBean}")
    private UtilityBean utilityBean;
    
    /**
     * Variable for confirming correct password in registering
     */
    private String passwordConfirm;

    public Collection<Users> getAllUsers(){
        return accountFacade.findAll();
    }
    
    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    

    private Users newUser = new Users();

    /**
     * Creates a new instance of AuthenticationBean
     */
    public AuthenticationBean() {
    }

    public String getUserNameByID(String userTextID) {
        // this is horrible, wrong, and wants replacing with a converter class
        userTextID = userTextID.replace("entitybeans.Users[ idusers=", "");
        userTextID = userTextID.replace(" ]", "");
        int userID = Integer.valueOf(userTextID);
        Users user = accountFacade.find(userID);
        return user.getUsername();
    }
    
    /**
     * Try to log user in
     * @return 
     */
    public String loginUser() {
        //attempt login and get redirect page
        String redirectpage = loginUser(newUser.getUsername(), newUser.getPassword());
        return redirectpage;
    }

    /**
     * Try to log user in with provided credentials and set user on session if successful
     * @param username username
     * @param password password
     * @return redirect string to home if credentials are correct, to index if incorrect
     */
    public String loginUser(String username, String password) {
        // lookup username/password combination in database
        Users user = accountFacade.checkUserLogin(username, password);
        // if no matching user found, show error message
        if (user == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Incorrect login or password"));
            return "index?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Logged In Successfully!"));
            // set user attribute in utilitybean
            utilityBean.setUser(user);
            newUser = user;
            return "home?faces-redirect=true";
        }
    }


    public Users getNewUser() {
        return newUser;
    }

    /**
     * Try to register new user
     * @return redirect string to index if registered correctly, to register if there was some problem
     */
    public String registerNewUser() {
         //check if user with specified login already exists
        if (accountFacade.userExists(newUser.getUsername())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("This username is already registered!"));
            return "register?faces-redirect=true";
        } else if (!newUser.getPassword().equals(passwordConfirm)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Password doesn't match!"));
            return "register?faces-redirect=true";
        } else {
            accountFacade.registerUser(newUser);
        }
        return "index?faces-redirect=true";
    }
    
    /**
     * Invalidate session and log out
     * @return 
     */
    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
        session.invalidate();
        return "index?faces-redirect=true";
    }
}
