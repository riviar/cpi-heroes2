/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.List;

/**
 * Tool Attributes
 * @author Rafal Kural
 * @version 1.03
 */
public class ToolAttributes {
    
    /**
     * Name of the attribute
     */
    private String name;
    /**
     * Type of the input that will decide behavior connected to this attribute
     */
    private EToolParamType inputType;
    
    //Single attribute can possibly hold both single value and array of values, inputType is used to decide which one to use
    /**
     * Attribute single value
     */
    private String value;
    /**
     * Attribute array of values
     */
    private String[] values;
    private String paramHelp;
    
    /**
     * Holds values to display in dropdown list if EToolParamType favours this type of input
     */
    private List<DropDownParamStruct> dropdownList;

    /**
     * Constructor for textfield-input attribute
     * @param name attribute name
     * @param value attribute default value
     * @param paramHelp help text
     */
    public ToolAttributes(String name, String value, String paramHelp) {
        this.name = name;
        this.inputType = EToolParamType.TEXTFIELD;
        this.value = value;
        this.values = null;
        this.dropdownList = null;
        this.paramHelp = paramHelp;
    }
    
    /**
     * More flexible constructor. Allows choosing inputType for attribute and specify drop-down list contents
     * @param name attribute name
     * @param inputType attribute input type
     * @param value attribute default value
     * @param dropdownList attribute drop-down list contents (can be null for TEXTFIELD type)
     * @param paramHelp help text
     */
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
     * Constructor for attribute that holds array of values (MULTIPLE type)
     * @param name attribute name
     * @param inputType attribute input type (should be only MULTIPLE for now, consider removing it from constructor)
     * @param values default attribute values
     * @param dropdownList attribute drop-down list contents (not used for MULTIPLE type, can be null, consider removing from constructor)
     * @param paramHelp help text
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
