/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.List;

/**
 * Tools entity class. Not from database
 * @author Rafal Kural
 */
public class Tool {
    private String name;
    /**
     * Tool enum (fastqc, seecer, etc)
     */
    private ETool toolEnum;
    /**
     * Defines tool family it belongs to (preprocessing, assembly, etc)
     */
    private EToolType toolType;
    /**
     * Tool description
     */
    private String description;
    /**
     * Path to executable
     */
    private String path;
    /**
     * List of files inputs
     */
    private List<ToolAttributes> inputList;
    /**
     * List of parameter inputs
     */
    private List<ToolAttributes> parameterList;
    /**
     * Tool help text
     */
    private String toolHelp;

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
    
    /**
     * pls help
     * @return the help
     */
    public String getToolHelp() {
        return toolHelp;
    }
    
    /**
     * @param help the help to set
     */
    public void setToolHelp(String help) {
        this.toolHelp = help;
    }

    /**
     * Constructor for tool entity
     * @param name tool name
     * @param toolEnum tool enum
     * @param toolType tool family enum
     * @param description description
     * @param path path to executable
     * @param inputList list of files inputs
     * @param parametersList list of paramerers inputs
     * @param help help text
     */
    public Tool(String name, ETool toolEnum, EToolType toolType, String description, 
            String path, List<ToolAttributes> inputList, List<ToolAttributes> parametersList, String help) {
        this.name = name;
        this.toolEnum = toolEnum;
        this.toolType = toolType;
        this.description = description;
        this.path = path;
        this.inputList = inputList;
        this.parameterList = parametersList;
        this.toolHelp = help;
    }
}
