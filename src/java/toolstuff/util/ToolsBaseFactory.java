/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fox
 */
public class ToolsBaseFactory {
    
    private ToolsBaseFactory(){}
    
    //return ToolsBase instance populated with tools
    public static ToolsBase initializeToolsBase() {
        //initializing variables
        Tool tool;
        List<String> inputs = new ArrayList();
        List<String> parameters = new ArrayList();
        List<Tool> fullToolsList = new ArrayList();
        
        //starting creating tools
        inputs.clear();
        inputs.add("Fasta file");
        parameters.clear();
        tool = new Tool("FastQC", EToolType.PREPROCESSING, "Performs initial analysis of fastqc quality", "There should be path I don't remember", inputs, parameters);
        fullToolsList.add(tool);
        
        inputs.clear();
        inputs.add("Fasta file");
        parameters.clear();
        tool = new Tool("Trimmomatric", EToolType.PREPROCESSING, "Performs trimming and matric and stuff", "There should be path I don't remember", inputs, parameters);
        fullToolsList.add(tool);
        
        inputs.clear();
        inputs.add("Fasta file");
        parameters.clear();
        tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        fullToolsList.add(tool);
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
