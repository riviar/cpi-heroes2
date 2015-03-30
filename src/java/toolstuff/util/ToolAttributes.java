/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.List;

/**
 * Tool Attributes
 * @author Fox
 */
public class ToolAttributes {
    
    private String name;
    private EToolParamType inputType;
    
    private String value;
    private String paramHelp;
    
    /**
     * Holds values to display in dropdown list if EToolParamType favours this type of input
     */
    private List<DropDownParamStruct> dropdownList;

    //constructor for textfields
    public ToolAttributes(String name, String value, String paramHelp) {
        this.name = name;
        this.inputType = EToolParamType.TEXTFIELD;
        this.value = value;
        this.dropdownList = null;
        this.paramHelp = paramHelp;
    }
    
    public ToolAttributes(String name, EToolParamType inputType,
            String value, List<DropDownParamStruct> dropdownList, String paramHelp) {
        this.name = name;
        this.inputType = inputType;
        this.value = value;
        this.dropdownList = dropdownList;
        this.paramHelp = paramHelp;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<DropDownParamStruct> getDropdownList() {
        return dropdownList;
    }

    public void setDropdownList(List<DropDownParamStruct> dropdownList) {
        this.dropdownList = dropdownList;
    }

    public EToolParamType getInputType() {
        return inputType;
    }

    public void setInputType(EToolParamType inputType) {
        this.inputType = inputType;
    }

    /**
     * @return the paramHelp
     */
    public String getParamHelp() {
        return paramHelp;
    }

    /**
     * @param paramHelp the paramHelp to set
     */
    public void setParamHelp(String paramHelp) {
        this.paramHelp = paramHelp;
    }
    
    
    
}
