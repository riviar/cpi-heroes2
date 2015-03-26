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
    private ETool toolEnum;
    private EToolType toolType;
    private String description;
    private String path;
    private List<ToolAttributes> inputList;
    private List<ToolAttributes> parameterList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ETool getToolEnum() {
        return toolEnum;
    }

    public void setToolEnum(ETool toolEnum) {
        this.toolEnum = toolEnum;
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

    public List<ToolAttributes> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ToolAttributes> parameterList) {
        this.parameterList = parameterList;
    }

    public List<ToolAttributes> getInputList() {
        return inputList;
    }

    public void setInputList(List<ToolAttributes> inputList) {
        this.inputList = inputList;
    }
    
    
    
    public Tool(String name, ETool toolEnum, EToolType toolType, String description, 
            String path, List<ToolAttributes> inputList, List<ToolAttributes> parametersList) {
        this.name = name;
        this.toolEnum = toolEnum;
        this.toolType = toolType;
        this.description = description;
        this.path = path;
        this.inputList = inputList;
        this.parameterList = parametersList;
    }
}
