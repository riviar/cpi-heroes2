/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Tools;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessionbeans.ToolsSidebarFacade;

/**
 *
 * @author Fox
 */
@ManagedBean
@RequestScoped
public class ToolsSidebarBean {

    //private List<Tools> tools;
    @EJB
    private ToolsSidebarFacade toolsSidebarFacade;
    
    /**
     * Creates a new instance of TollsSidebarBean
     */
    public ToolsSidebarBean() {
    }
    
    public List<String> getToolNames() {
        List<String> list = new ArrayList();
        List<Tools> tools = toolsSidebarFacade.getAllTools();
        for (Tools tool:tools) {
            list.add(tool.getDescription());
        }
        return list;
    }
}
