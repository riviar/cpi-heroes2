/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

/**
 * Class that allow to hold different label and value states
 * @author Fox
 */
public class DropDownParamStruct {
    private String label;
    private String value;

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
