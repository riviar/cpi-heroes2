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
        
        //starting creating tools
        inputs.clear();
        inputs.add(new ToolAttributes("Fasta file", "fasta"));
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
        inputs.add(new ToolAttributes("Right", "fasta"));
        inputs.add(new ToolAttributes("Left", "fasta"));
        parameters.clear();
        parameters.add(new ToolAttributes("Window Size", "4")); //should set default values
        parameters.add(new ToolAttributes("Required Quality", "21"));
        //tool = new Tool("Trimmomatric", EToolType.PREPROCESSING, "Performs trimming and matric and stuff", "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trimmomatic", 
                ETool.TRIMMOMATIC, 
                EToolType.PREPROCESSING, 
                "Performs trimming and matric and stuff", 
                "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", "fasta"));
        inputs.add(new ToolAttributes("Left", "fasta"));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer Count", "15"));
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
        inputs.add(new ToolAttributes("Right", "fasta"));
        inputs.add(new ToolAttributes("Left", "fasta"));
        parameters.clear();
        parameters.add(new ToolAttributes("Sequence Type", "?"));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trinity", 
                ETool.TRINITY, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "/home/lestelles/Desktop/do_trinity.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        inputs.clear();
        inputs.add(new ToolAttributes("Right", "fasta"));
        inputs.add(new ToolAttributes("Left", "fasta"));
        parameters.clear();
        parameters.add(new ToolAttributes("Sequence Type", "?"));
        parameters.add(new ToolAttributes("Kmer Count", "15"));
        parameters.add(new ToolAttributes("Distance between two paired ends", "0"));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Velvet", 
                ETool.TRINITY, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "bash_scripts/do_velvet.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters)));
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
