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
        List<ToolAttributes> inputs = new ArrayList();
        List<ToolAttributes> parameters = new ArrayList();
        List<Tool> fullToolsList = new ArrayList();
        List<DropDownParamStruct> dropdownList = new ArrayList();
        
        //starting creating tools
        inputs.clear();
        inputs.add(new ToolAttributes("Fasta file", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        //tool = new Tool("FastQC", EToolType.PREPROCESSING, "Performs initial analysis of fastqc quality", "Preprocessing/FastQC/fastqc", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("FastQC", 
                ETool.FASTQC, 
                EToolType.PREPROCESSING, 
                "Performs initial analysis of fastqc quality", 
                "Preprocessing/FastQC/fastqc", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));        
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", EToolParamType.DROPDOWN, "fasta", null));
        inputs.add(new ToolAttributes("Left", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Window Size", EToolParamType.TEXTFIELD, "4", null)); //should set default values
        parameters.add(new ToolAttributes("Required Quality", EToolParamType.TEXTFIELD, "21", null));
        //tool = new Tool("Trimmomatric", EToolType.PREPROCESSING, "Performs trimming and matric and stuff", "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trimmomatic", 
                ETool.TRIMMOMATIC, 
                EToolType.PREPROCESSING, 
                "Performs trimming and matric and stuff", 
                "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", EToolParamType.DROPDOWN, "fasta", null));
        inputs.add(new ToolAttributes("Left", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer Count", EToolParamType.TEXTFIELD, "15", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Seecer", 
                ETool.SEECER, 
                EToolType.PREPROCESSING, 
                "Performs seecering", 
                "Preprocessing/Seecer/Seecer/SEECER/bin/run_seecer.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", EToolParamType.DROPDOWN, "fasta", null));
        inputs.add(new ToolAttributes("Left", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        dropdownList.clear();
        dropdownList.add(new DropDownParamStruct("Fasta file", "fasta"));
        dropdownList.add(new DropDownParamStruct("Fastq file", "fastq"));
        parameters.add(new ToolAttributes("Sequence Type", EToolParamType.DROPDOWN, "fasta", dropdownList));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trinity", 
                ETool.TRINITY, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "/home/lestelles/Desktop/do_trinity.sh", //change on VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", EToolParamType.DROPDOWN, "fasta", null));
        inputs.add(new ToolAttributes("Left", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        dropdownList.clear();
        dropdownList.add(new DropDownParamStruct("Fasta file", "fasta"));
        dropdownList.add(new DropDownParamStruct("Fastq file", "fastq"));
        parameters.add(new ToolAttributes("Sequence Type", EToolParamType.DROPDOWN, "fasta", dropdownList));
        parameters.add(new ToolAttributes("Kmer Count", EToolParamType.TEXTFIELD, "15", null));
        parameters.add(new ToolAttributes("Expected distance between two paired ends", EToolParamType.TEXTFIELD, "0", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Velvet", 
                ETool.VELVET, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "bash_scripts/do_velvet.sh", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", EToolParamType.DROPDOWN, "fasta", null));
        inputs.add(new ToolAttributes("Left", EToolParamType.DROPDOWN, "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer Count", EToolParamType.TEXTFIELD, "15", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Transabyss", 
                ETool.TRANSABYSS, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "temporary, change", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
