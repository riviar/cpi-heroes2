/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

/**
 * Param class that holds different label and value states
 * @author Rafal Kural
 */
public class DropDownParamStruct {
    /**
     * Param label
     */
    private String label;
    /**
     * Param value
     */
    private String value;

    /**
     * Constructor
     * @param label param label
     * @param value param value
     */
    public DropDownParamStruct(String label, String value) {
        this.label = label;
        this.value = value;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
