/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.List;

/**
 * Tools entity class
 * @author Fox
 */
public class Tool {
    private String name;
    private EToolType toolType;
    private String description;
    private String path;
    private List<String> inputList;
    private List<String> parameterList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EToolType getToolType() {
        return toolType;
    }

    public void setToolType(EToolType toolType) {
        this.toolType = toolType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<String> parameterList) {
        this.parameterList = parameterList;
    }
    
    public Tool(String name, EToolType toolType, String description, 
            String path, List<String> inputList, List<String> parametersList) {
        this.name = name;
        this.toolType = toolType;
        this.description = description;
        this.path = path;
        this.inputList = inputList;
        this.parameterList = parametersList;
    }
}
