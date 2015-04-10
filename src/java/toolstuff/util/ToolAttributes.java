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
    
    /**
     * Single value
     */
    private String value;
    /**
     * Array of values - if type allows
     */
    private String[] values;
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
        this.values = null;
        this.dropdownList = null;
        this.paramHelp = paramHelp;
    }
    
    public ToolAttributes(String name, EToolParamType inputType,
            String value, List<DropDownParamStruct> dropdownList, String paramHelp) {
        this.name = name;
        this.inputType = inputType;
        this.value = value;
        this.values = null;
        this.dropdownList = dropdownList;
        this.paramHelp = paramHelp;
    }
    /**
     * Constructor for attribute with array of values
     * @param name
     * @param inputType
     * @param values
     * @param dropdownList
     * @param paramHelp 
     */
    public ToolAttributes(String name, EToolParamType inputType,
            String[] values, List<DropDownParamStruct> dropdownList, String paramHelp) {
        this.name = name;
        this.inputType = inputType;
        this.values = values;
        this.value = null;
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

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
    
    
    
}
