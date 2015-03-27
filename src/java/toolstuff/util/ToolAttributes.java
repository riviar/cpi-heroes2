/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

/**
 * Tool Attributes
 * @author Fox
 */
public class ToolAttributes {
    
    private String name;
    private EToolParamType inputType;
    
    private String value;
    
    private String dropdownList;

    public ToolAttributes(String name, String value) {
        this.name = name;
        this.value = value;
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

    public String getDropdownList() {
        return dropdownList;
    }

    public void setDropdownList(String dropdownList) {
        this.dropdownList = dropdownList;
    }

    public EToolParamType getInputType() {
        return inputType;
    }

    public void setInputType(EToolParamType inputType) {
        this.inputType = inputType;
    }
    
}
