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
        
        //FastQC
        inputs.clear();
        inputs.add(new ToolAttributes("Fasta file", EToolParamType.DROPDOWN, "fasta", null,null));
        parameters.clear();
        //tool = new Tool("FastQC", EToolType.PREPROCESSING, "Performs initial analysis of fastqc quality", "Preprocessing/FastQC/fastqc", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("FastQC", 
                ETool.FASTQC, 
                EToolType.PREPROCESSING,
                "Performs initial analysis of fastqc quality", 
                //"Preprocessing/FastQC/fastqc", 
                "shell_scripts/do_fastqc.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getFactqcHelp()));        
        
        
        //TRIMMOMATIC-TRIM
        inputs.clear();
        inputs.add(new ToolAttributes("Forward reads", "fasta", null));
        inputs.add(new ToolAttributes("Reverse reads", "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Window Size", "4", ToolHelp.getTrimmomaticWindowSize())); //should set default values
        parameters.add(new ToolAttributes("Required Quality", "15", ToolHelp.getTrimmomaticRequiredQuality()));
        parameters.add(new ToolAttributes("Forward paired file name", "fw_paired.fq", null));
        parameters.add(new ToolAttributes("Forward unpaired file name", "fw_unpaired.fq",null));
        parameters.add(new ToolAttributes("Reverse paired file name", "r_paired.fq", null));
        parameters.add(new ToolAttributes("Reverse unpaired file name", "r_unpaired.fq", null));
        //tool = new Tool("Trimmomatric", EToolType.PREPROCESSING, "Performs trimming and matric and stuff", "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trimmomatic - Trimming", 
                ETool.TRIMMOMATIC_TRIM, 
                EToolType.PREPROCESSING, 
                "Performs trimming and matric and stuff", 
                "shell_scripts/do_trimmomatic-trim.sh", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getTrimmomaticHelp()));
        
        //TRIMMOMATIC-ADAPT
        inputs.clear();
        inputs.add(new ToolAttributes("Forward reads", "fasta", null));
        inputs.add(new ToolAttributes("Reverse reads", "fasta", null));
        parameters.clear();
        dropdownList.clear();
        dropdownList.add(new DropDownParamStruct("TruSeq3-PE", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/TruSeq3-PE.fa"));
        dropdownList.add(new DropDownParamStruct("TruSeq2-PE", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/TruSeq2-PE.fa"));
        dropdownList.add(new DropDownParamStruct("NexteraPE-PE.fa", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/NexteraPE-PE.fa"));
        parameters.add(new ToolAttributes("Adapters", EToolParamType.DROPDOWN, "Select adapters", dropdownList, null));
        parameters.add(new ToolAttributes("Seed mismatches", "2", null));
        parameters.add(new ToolAttributes("Palindrome Clip Thresshold", "30", null));
        parameters.add(new ToolAttributes("Simple Clip Thresshold", "10", null));
        parameters.add(new ToolAttributes("Forward paired file name", "fw_paired.fq", null));
        parameters.add(new ToolAttributes("Forward unpaired file name", "fw_unpaired.fq", null));
        parameters.add(new ToolAttributes("Reverse paired file name", "r_paired.fq", null));
        parameters.add(new ToolAttributes("Reverse unpaired file name", "r_unpaired.fq", null));
        //tool = new Tool("Trimmomatric", EToolType.PREPROCESSING, "Performs trimming and matric and stuff", "/opt/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/trimmomatic.jar", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trimmomatic - Adapters", 
                ETool.TRIMMOMATIC_ADAPT, 
                EToolType.PREPROCESSING, 
                "Performs trimming and matric and stuff", 
                "shell_scripts/do_trimmomatic-adapt.sh", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                null));
        
        //SEECER
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer", "15", ToolHelp.getSeecerKmer()));
        parameters.add(new ToolAttributes("Output file name", "output_from_seecer.fa", null));
        parameters.add(new ToolAttributes("Left corrected reads name", "leftCorrected.fa", null));
        parameters.add(new ToolAttributes("Right corrected reads name", "rightCorrected.fa", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Seecer", 
                ETool.SEECER, 
                EToolType.PREPROCESSING, 
                "Performs seecering", 
                //"Preprocessing/Seecer/Seecer/SEECER/bin/run_seecer.sh", 
                "shell_scripts/do_seecer.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getSeecerHelp()));
        
        //TRINITY
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        dropdownList.clear();
        dropdownList.add(new DropDownParamStruct("FASTQ", "fq"));
        dropdownList.add(new DropDownParamStruct("FASTA", "fa"));
        parameters.add(new ToolAttributes("Sequence type", EToolParamType.DROPDOWN, "FASTQ", dropdownList, null));
        //parameters.add(new ToolAttributes("Sequence Type", "fq"));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Trinity", 
                ETool.TRINITY, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "shell_scripts/do_trinity.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getTrinityHelp() ));
        
        
        //VELVET
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        dropdownList.clear();
        dropdownList.add(new DropDownParamStruct("FASTQ", "fastq"));
        dropdownList.add(new DropDownParamStruct("FASTA", "fasta"));
        parameters.add(new ToolAttributes("Sequence type", EToolParamType.DROPDOWN, "FASTQ", dropdownList, null));
        //parameters.add(new ToolAttributes("Sequence Type", "fastq"));
        parameters.add(new ToolAttributes("Kmer", "31", ToolHelp.getVelvetKmer() ));
        parameters.add(new ToolAttributes("Insert length", "170", ToolHelp.getVelvetInsertLEngth()));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Velvet", 
                ETool.VELVET, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "shell_scripts/do_velvet.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getVelvetHelp() ));
        
        
        //TRANSABYSS
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer", "31", ToolHelp.getTransabyssKmer()));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Transabyss", 
                ETool.TRANSABYSS, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "shell_scripts/do_transabyss.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getTransabyssHelp() ));
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
