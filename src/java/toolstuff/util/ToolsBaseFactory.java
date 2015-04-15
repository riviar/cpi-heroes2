/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory class for ToolsBase.
 * @author Rafal Kural
 * @version 1.06
 */
public class ToolsBaseFactory {
    
    private ToolsBaseFactory(){}
    
    /**
     * Returns instance of ToolsBase populated with tools
     * @return populated ToolsBase instance
     */
    public static ToolsBase initializeToolsBase() {
        //initializing variables
        Tool tool;
        List<ToolAttributes> inputs = new ArrayList();
        List<ToolAttributes> parameters = new ArrayList();
        List<Tool> fullToolsList = new ArrayList();
        List<DropDownParamStruct> dropdownList = new ArrayList();
        
        //starting creating tools
        
        
              
         //SOAPdenovo-Trans
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("FASTQ", "fastq"));
        dropdownList.add(new DropDownParamStruct("FASTA", "fasta"));
        parameters.add(new ToolAttributes("Sequence type", EToolParamType.DROPDOWN, "FASTQ", dropdownList, null));
        
        parameters.add(new ToolAttributes("Kmer", "31", null));
        parameters.add(new ToolAttributes("Insert length", "170", null));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        fullToolsList.add(new Tool("SOAPdenovo-Trans", 
                ETool.SOAPdenovo_Trans, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "shell_scripts/do_SOAPdenovo-Trans.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getSOAPdeNovoTransHelp()));
                      
        

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
                ToolHelp.getFastqcHelp()));        
        
        
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
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("TruSeq3-PE", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/TruSeq3-PE.fa"));
        dropdownList.add(new DropDownParamStruct("TruSeq2-PE", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/TruSeq2-PE.fa"));
        dropdownList.add(new DropDownParamStruct("NexteraPE-PE.fa", "/home/vmuser/CPI/tools/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/Trimmomatic-0.32/adapters/NexteraPE-PE.fa"));
        parameters.add(new ToolAttributes("Adapters", EToolParamType.DROPDOWN, "Select adapters", dropdownList, null));
        parameters.add(new ToolAttributes("Seed mismatches", "2", ToolHelp.getTrimmomaticAdapterSeedMismatches()));
        parameters.add(new ToolAttributes("Palindrome Clip Thresshold", "30", ToolHelp.getPalindromeClipTreshold()));
        parameters.add(new ToolAttributes("Simple Clip Thresshold", "10", ToolHelp.getSimpleClipTreshold()));
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
                ToolHelp.getTrimmomaticAdapterHelp()));
        
        //SEECER
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer", "17", ToolHelp.getSeecerKmer()));
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
        dropdownList = new ArrayList();
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
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("FASTQ", "fastq"));
        dropdownList.add(new DropDownParamStruct("FASTA", "fasta"));
        parameters.add(new ToolAttributes("Sequence type", EToolParamType.DROPDOWN, "FASTQ", dropdownList, null));
        //parameters.add(new ToolAttributes("Sequence Type", "fastq"));
        parameters.add(new ToolAttributes("Kmer", "31", ToolHelp.getVelvetKmer() ));
        parameters.add(new ToolAttributes("Insert length", "170", ToolHelp.getVelvetInsertLength()));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Velvet + Oases", 
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
        fullToolsList.add(new Tool("Trans-ABySS", 
                ETool.TRANSABYSS, 
                EToolType.ASSEMBLY, 
                "Help me", 
                "shell_scripts/do_transabyss.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getTransabyssHelp()));
        
        
        //ABUNDANCE ESTIMATION
        inputs.clear();
        inputs.add(new ToolAttributes("Assembled fasta file", "fasta", null));
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        
        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("FASTQ", "fq"));
        dropdownList.add(new DropDownParamStruct("FASTA", "fa"));
        parameters.add(new ToolAttributes("Reads file format", EToolParamType.DROPDOWN, "FASTQ", dropdownList, null));
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("RSEM", "RSEM"));
        dropdownList.add(new DropDownParamStruct("eXpress", "eXpress"));
        parameters.add(new ToolAttributes("Estimation method", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        parameters.add(new ToolAttributes("Percent of top genes to show", "20", null));
        parameters.add(new ToolAttributes("Name for output", "sample.RSEM", null));
        fullToolsList.add(new Tool("Abundance estimation", 
                ETool.ABUNDANCE_ESTIMATION, 
                EToolType.DOWNSTREAM, 
                "Estimate abundance of isoforms using the reads and the assembled fasta file", 
                "shell_scripts/do_abundanceEstimation.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getAbundanceEstimationHelp()));

        //Differential gene expression
        inputs.clear();
        inputs.add(new ToolAttributes("Abundance estimation files (select as many as you want to compare)", EToolParamType.MULTIPLE, "", null, null));

        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("RSEM", "RSEM"));
        dropdownList.add(new DropDownParamStruct("eXpress", "eXpress"));
        parameters.add(new ToolAttributes("Method used for abundance estimation", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        parameters.add(new ToolAttributes("P-value cutoff for FDR", "0.001", null));
        parameters.add(new ToolAttributes("Minimum fold change", "2", null));
        parameters.add(new ToolAttributes("Maximum differentially expressed genes per comparison", "10", null));
        parameters.add(new ToolAttributes("Prefix for output files", "deg_samples", null));
        parameters.add(new ToolAttributes("Abundance estimation files (TEST)", "/home/vmuser/CPI/datasets/1M_READS_sample/Sp_ds.isoforms.results,/home/vmuser/CPI/datasets/1M_READS_sample/Sp_log.isoforms.results,/home/vmuser/CPI/datasets/1M_READS_sample/Sp_hs.isoforms.results,/home/vmuser/CPI/datasets/1M_READS_sample/Sp_plat.isoforms.results", null));


        fullToolsList.add(new Tool("Differential gene expression", 
                ETool.DEG, 
                EToolType.DOWNSTREAM, 
                "", 
                "shell_scripts/do_deg.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getDifferentialGeneExpressionHelp()));
        
        
        //CLUSTERS
        inputs.clear();
        inputs.add(new ToolAttributes("Select RDATA file", "fasta", null));

        parameters.clear();
        parameters.add(new ToolAttributes("Percent of height to cut tree", "60", null));
        
        //tool = new Tool("Seecer", EToolType.PREPROCESSING, "Performs seecering", "There should be path I don't remember", inputs, parameters);
        //fullToolsList.add(tool);
        fullToolsList.add(new Tool("Clusters by cutting dendrogram", 
                ETool.CLUSTERS, 
                EToolType.DOWNSTREAM, 
                "Help me", 
                "shell_scripts/do_clusters.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getClusteringHelp()));
        
        
        // BLAST
        inputs.clear();       
        inputs.add(new ToolAttributes("Query", "fasta", null));
                
        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("blastn", "blastn"));
        dropdownList.add(new DropDownParamStruct("blastx", "blastx"));
        dropdownList.add(new DropDownParamStruct("tblastx", "tblastx"));
        parameters.add(new ToolAttributes("Blast version", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("nr", "nr"));
        dropdownList.add(new DropDownParamStruct("TAIR10_seq", "TAIR10_seq"));
        dropdownList.add(new DropDownParamStruct("TAIR10_cds", "TAIR10_cds"));
        dropdownList.add(new DropDownParamStruct("TAIR10_exon", "TAIR10_exon"));
        dropdownList.add(new DropDownParamStruct("TAIR10_cdna", "TAIR10_cdna"));
        dropdownList.add(new DropDownParamStruct("TAIR10_pep", "TAIR10_pep"));
        parameters.add(new ToolAttributes("Database", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        parameters.add(new ToolAttributes("Expectation value threshold", "10", ToolHelp.getSeecerKmer()));
        parameters.add(new ToolAttributes("Window size", "28", null));
        parameters.add(new ToolAttributes("Maximum number of hits per sequence", "5", null));
        parameters.add(new ToolAttributes("Output name", "blast results", null));

        fullToolsList.add(new Tool("BLAST", 
                ETool.BLAST, 
                EToolType.ANNOTATION, 
                "Performs seecering", 
                "shell_scripts/do_blast.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getSeecerHelp()));
        
        
        
         // HMMER
        inputs.clear();       
        inputs.add(new ToolAttributes("Query", "fasta", null));
                
        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("Pfam-A", "/mnt/SATA2/PFAM/Pfam-A.hmm"));
        dropdownList.add(new DropDownParamStruct("Pfam-B", "/mnt/SATA2/PFAM/Pfam-B.hmm"));
        parameters.add(new ToolAttributes("Database", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        parameters.add(new ToolAttributes("Minimum protein length", "100", null));
        parameters.add(new ToolAttributes("Sequence e-value threshold", "0.01", null));
        parameters.add(new ToolAttributes("Output name", "hmmer results", null));
        
        fullToolsList.add(new Tool("HMMER", 
                ETool.HMMER, 
                EToolType.ANNOTATION, 
                "Performs seecering", 
                "shell_scripts/do_hmmer.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getSeecerHelp()));

        
        
        
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
