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
                "", 
                "do_SOAPdenovo-Trans.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getSOAPdeNovoTransHelp()));
                      
        

        //FastQC
        inputs.clear();
        inputs.add(new ToolAttributes("Fasta file", EToolParamType.DROPDOWN, "fasta", null,null));
        parameters.clear();

        fullToolsList.add(new Tool("FastQC", 
                ETool.FASTQC, 
                EToolType.PREPROCESSING,
                "", 
                //"Preprocessing/FastQC/fastqc", 
                "do_fastqc.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getFastqcHelp()));        
        
        
        //TRIMMOMATIC-TRIM
        inputs.clear();
        inputs.add(new ToolAttributes("Forward reads", "fasta", null));
        inputs.add(new ToolAttributes("Reverse reads", "fasta", null));
        
        parameters.clear();
        parameters.add(new ToolAttributes("Window Size", "4", null)); 
        parameters.add(new ToolAttributes("Required Quality", "15", null));
        parameters.add(new ToolAttributes("Forward paired file name", "fw_paired_trimming", null));
        parameters.add(new ToolAttributes("Forward unpaired file name", "fw_unpaired_trimming",null));
        parameters.add(new ToolAttributes("Reverse paired file name", "r_paired_trimming", null));
        parameters.add(new ToolAttributes("Reverse unpaired file name", "r_unpaired_trimming", null));

        fullToolsList.add(new Tool("Trimmomatic - Trimming", 
                ETool.TRIMMOMATIC_TRIM, 
                EToolType.PREPROCESSING, 
                "", 
                "do_trimmomatic-trim.sh", //change for VM
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
        parameters.add(new ToolAttributes("Seed mismatches", "2", null));
        parameters.add(new ToolAttributes("Palindrome Clip Thresshold", "30", null));
        parameters.add(new ToolAttributes("Simple Clip Thresshold", "10", null));
        parameters.add(new ToolAttributes("Forward paired file name", "fw_paired_adapt", null));
        parameters.add(new ToolAttributes("Forward unpaired file name", "fw_unpaired_adapt", null));
        parameters.add(new ToolAttributes("Reverse paired file name", "r_paired_adapt", null));
        parameters.add(new ToolAttributes("Reverse unpaired file name", "r_unpaired_adapt", null));
        
        fullToolsList.add(new Tool("Trimmomatic - Adapters", 
                ETool.TRIMMOMATIC_ADAPT, 
                EToolType.PREPROCESSING, 
                "", 
                "do_trimmomatic-adapt.sh", //change for VM
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getTrimmomaticAdapterHelp()));
        
        //SEECER
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer", "17", null));
        parameters.add(new ToolAttributes("Output file name", "output_from_seecer.fa", null));
        parameters.add(new ToolAttributes("Left corrected reads name", "leftCorrected.fa", null));
        parameters.add(new ToolAttributes("Right corrected reads name", "rightCorrected.fa", null));
        
        fullToolsList.add(new Tool("Seecer", 
                ETool.SEECER, 
                EToolType.PREPROCESSING, 
                "",
                "do_seecer.sh",
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
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        
        fullToolsList.add(new Tool("Trinity", 
                ETool.TRINITY, 
                EToolType.ASSEMBLY, 
                "", 
                "do_trinity.sh", 
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
        parameters.add(new ToolAttributes("Kmer", "31", null));
        parameters.add(new ToolAttributes("Insert length", "170", null));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        
        fullToolsList.add(new Tool("Velvet + Oases", 
                ETool.VELVET, 
                EToolType.ASSEMBLY, 
                "", 
                "do_velvet.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getVelvetHelp() ));
        
        
        //TRANSABYSS
        inputs.clear();
        inputs.add(new ToolAttributes("Left reads", "fasta", null));
        inputs.add(new ToolAttributes("Right reads", "fasta", null));
        
        parameters.clear();
        parameters.add(new ToolAttributes("Kmer", "31", null));
        parameters.add(new ToolAttributes("Output file name", "transcripts.fa", null));
        
        fullToolsList.add(new Tool("Trans-ABySS", 
                ETool.TRANSABYSS, 
                EToolType.ASSEMBLY, 
                "", 
                "do_transabyss.sh", 
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
        
        fullToolsList.add(new Tool("1. Abundance estimation", 
                ETool.ABUNDANCE_ESTIMATION, 
                EToolType.DOWNSTREAM, 
                "Estimate abundance of isoforms using the reads and the assembled fasta file", 
                "do_abundanceEstimation.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getAbundanceEstimationHelp()));

        
        //Differential gene expression
        inputs.clear();
        inputs.add(new ToolAttributes("Abundance estimation files", EToolParamType.MULTIPLE, "", null, null));

        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("RSEM", "RSEM"));
        dropdownList.add(new DropDownParamStruct("eXpress", "eXpress"));
        parameters.add(new ToolAttributes("Method used for abundance estimation", EToolParamType.DROPDOWN, "RSEM", dropdownList, null));
        parameters.add(new ToolAttributes("P-value cutoff for FDR", "0.001", null));
        parameters.add(new ToolAttributes("Minimum fold change", "2", null));
        parameters.add(new ToolAttributes("Maximum differentially expressed genes per comparison", "10", null));
        parameters.add(new ToolAttributes("Output name", "deg_samples", null));

        fullToolsList.add(new Tool("2. Differential gene expression", 
                ETool.DEG, 
                EToolType.DOWNSTREAM, 
                "", 
                "do_deg.sh", 
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getDifferentialGeneExpressionHelp()));
        
        
        //CLUSTERS
        inputs.clear();
        inputs.add(new ToolAttributes("Select output from differential gene expression", "fasta", null));

        parameters.clear();
        parameters.add(new ToolAttributes("Height to cut the tree", "60", null));
        
        fullToolsList.add(new Tool("3. Clusters by cutting dendrogram", 
                ETool.CLUSTERS, 
                EToolType.DOWNSTREAM, 
                "Help me", 
                "do_clusters.sh", 
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
        parameters.add(new ToolAttributes("Blast version", EToolParamType.DROPDOWN, "blastn", dropdownList, null));
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("nr", "nr"));
        dropdownList.add(new DropDownParamStruct("TAIR10_seq", "TAIR10_seq"));
        dropdownList.add(new DropDownParamStruct("TAIR10_cds", "TAIR10_cds"));
        dropdownList.add(new DropDownParamStruct("TAIR10_exon", "TAIR10_exon"));
        dropdownList.add(new DropDownParamStruct("TAIR10_cdna", "TAIR10_cdna"));
        dropdownList.add(new DropDownParamStruct("TAIR10_pep", "TAIR10_pep"));
        parameters.add(new ToolAttributes("Database", EToolParamType.DROPDOWN, "nr", dropdownList, null));
        parameters.add(new ToolAttributes("Expectation value threshold", "10", null));
        parameters.add(new ToolAttributes("Window size", "28", null));
        parameters.add(new ToolAttributes("Maximum number of hits per sequence", "5", null));
        parameters.add(new ToolAttributes("Output name", "blast results", null));

        fullToolsList.add(new Tool("BLAST", 
                ETool.BLAST, 
                EToolType.ANNOTATION, 
                "Performs seecering", 
                "do_blast.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getBlastHelp()));
        
        
        
         // HMMER
        inputs.clear();       
        inputs.add(new ToolAttributes("Query", "fasta", null));
                
        parameters.clear();
        dropdownList = new ArrayList();
        dropdownList.add(new DropDownParamStruct("Pfam-A", "/mnt/SATA2/PFAM/Pfam-A.hmm"));
        dropdownList.add(new DropDownParamStruct("Pfam-B", "/mnt/SATA2/PFAM/Pfam-B.hmm"));
        parameters.add(new ToolAttributes("Database", EToolParamType.DROPDOWN, "Pfam-A", dropdownList, null));
        parameters.add(new ToolAttributes("Minimum protein length", "100", null));
        parameters.add(new ToolAttributes("Sequence e-value threshold", "0.01", null));
        parameters.add(new ToolAttributes("Output name", "hmmer results", null));
        
        fullToolsList.add(new Tool("HMMER", 
                ETool.HMMER, 
                EToolType.ANNOTATION, 
                "Performs seecering", 
                "do_hmmer.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getHmmerHelp()));
        
        
        // Merge
        inputs.clear();       
        inputs.add(new ToolAttributes("Select files", EToolParamType.MULTIPLE, "", null, null));
        
        parameters.clear();
        parameters.add(new ToolAttributes("Output name", "merged reads", null));
        fullToolsList.add(new Tool("Merge files for assembly", 
                ETool.MERGE, 
                EToolType.PREPROCESSING, 
                "", 
                "do_merge.sh",
                new ArrayList<>(inputs), 
                new ArrayList<>(parameters),
                ToolHelp.getMergeHelp()));

        
        
        
        
        //finished creating tools
        //initializing base
        ToolsBase base = new ToolsBase();
        base.addTools(fullToolsList);
        return base;
    }
    
    
}
