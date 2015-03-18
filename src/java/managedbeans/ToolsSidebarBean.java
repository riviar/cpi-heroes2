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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import sessionbeans.ToolsSidebarFacade;
import toolstuff.util.EToolType;
import toolstuff.util.ToolsBase;
import toolstuff.util.ToolsBaseFactory;

/**
 *
 * @author Fox
 */
@ManagedBean
@RequestScoped
public class ToolsSidebarBean {

    //private List<Tools> tools;
//    @EJB
//    private ToolsSidebarFacade toolsSidebarFacade;
    private ToolsBase tools;
    
    @ManagedProperty(value="#{param.tool}")
    private String selectedToolName;
    
    @ManagedProperty(value="#{utilityBean}")
    private UtilityBean utilityBean;

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }
    
    public void setSelectedToolName(String selectedToolName) {
        this.selectedToolName = selectedToolName;
    }

    /**
     * Creates a new instance of TollsSidebarBean
     */
    public ToolsSidebarBean() {
        tools = ToolsBaseFactory.initializeToolsBase();
    }

    public List<String> getToolNames(EToolType type) {
        return tools.getToolsNames(type);
    }
    
    public List<String> getPreprocessingToolNames() {
        return getToolNames(EToolType.PREPROCESSING);
    }
    
    public String selectTool() {
        utilityBean.setSelectedTool(tools.getToolByName(selectedToolName));
        return "new_job";
    }
}
