/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff.util;

/**
 *
 * @author Gergely
 */
public class ToolHelp {
    private static String factqcHelp = "Version: 0.11.3 Tool description: A quality control tool for high throughput sequence data. Performs simple quality control checks. The left hand side of the main interactive display or the top of the HTML report show a summary of the modules which were run, and a quick evaluation of whether the results of the module seem entirely normal (green tick), slightly abnormal (orange triangle) or very unusual (red cross).";
    
    private static String trimmomaticHelp = "Version: 0.33 Tool description: Trimmomatic performs trimming tasks for paired-end and single-end data.";
    private static String trimmomaticSlidingWindow = "SLIDINGWINDOW: Performs a sliding window trimming approach. It starts scanning at the 5‟ end and clips the read once the average quality within the window falls below a threshold. By considering multiple bases, a single poor quality base will not cause the removal of high quality data later in the read.";
    private static String trimmomaticWindowSize = "specifies the number of bases to average across.";
    private static String trimmomaticRequiredQuality = "specifies the average quality required.";
    
    private static String seecerHelp = "Version: 0.1.3 Tool description: A sequencing error correction tool for RNA-seq data sets, based on hidden Markov Model (HMM). It takes the raw read sequences produced by a next generation sequencing platform like machines from Illumina or Roche. It removes mismatch and indel errors from the raw reads.";
    private static String seecerKmer = "specifies the k value, which is the size of k-mers used by SEECER to build the homogenous contigs for the HMM. The default value is 17.";
    
    private static String velvetHelp = "Velvet is a de novo genomic assembler specially designed for short read sequencing technologies, such as Solexa or 454. Developed by Daniel Zerbino and Ewan Birney at the European Bioinformatics Institute (EMBL-EBI), near Cambridge, in the United Kingdom. Velvet currently takes in short read sequences, removes errors then produces high quality unique contigs. It then uses paired-end read and long read information, when available, to retrieve the repeated areas between contigs. Velveth helps you construct the dataset for the following program, velvetg, and indicate to the system what each sequence file represents. Velvetg is the core of Velvet where the de Bruijn graph is built then manipulated.";
    private static String velvetKmer = "also known as hash length corresponds to the length, in base pairs, of the words being hashed. Experience shows that k-mer coverage should be above 10 to start getting decent results. If Ck is above 20, you might be \"wasting\" coverage.";
    private static String velvetInsertLEngth = "is understood to be the length of the sequenced fragment, i.e. it includes the length of the reads themselves.";
    
    private static String trinityHelp = "Tool description:\n" +
"Trinity represents an efficient and robust de novo reconstruction of transcriptomes from RNA-seq data. Developed at the Broad Institute and the Hebrew University of Jerusalem.\n" +
"Trinity combines three independent software modules: Inchworm, Chrysalis, and Butterfly, applied sequentially to process large volumes of RNA-seq reads. Trinity partitions the sequence data into many individual de Bruijn graphs.\n" +
"Inchworm assembles the RNA-seq data into the unique sequences of transcripts, often generating full-length transcripts for a dominant isoform, but then reports just the unique portions of alternatively spliced transcripts.\n" +
"Chrysalis clusters the Inchworm contigs into clusters and constructs complete de Bruijn graphs for each cluster. Each cluster represents the full transcriptonal complexity for a given gene (or sets of genes that share sequences in common). Chrysalis then partitions the full read set among these disjoint graphs.\n" +
"Butterfly then processes the individual graphs in parallel, tracing the paths that reads and pairs of reads take within the graph, ultimately reporting full-length transcripts for alternatively spliced isoforms, and teasing apart transcripts that corresponds to paralogous genes.";
    
    private static String transabyssHelp = "Tool description:\n" +
"Trans-ABySS is a de novo assembly tool of RNA-Seq data using ABySS (Assembly By Short Sequences) a de novo, parallel, paired-end sequence assembler that is designed for short reads. Developed in Canada's Michael Smith Genome Sciences Centre, BC Cancer Agency\n" +
"The current version of the Trans-ABySS package comes with 3 main applications:\n" +
"1. transabyss - assemble RNAseq data\n" +
"2. transabyss-merge - merge multiple assemblies from (1)\n" +
"3. transabyss-analyze - analyze assemblies, either from (1) or (2), for structural variants and splice variants. Requires reference genome and annotations";
    private static String transabyssKmer = "default 32, k=32 has a good trade-off for assembling both rare and common transcripts. Using larger k-mers improve the assembly quality of common transcripts and transcripts with repetitive regions, but the assembly of rare transcripts may suffer.";
 
    /**
     * @return the factqcHelp
     */
    public static String getFactqcHelp() {
        return factqcHelp;
    }

    /**
     * @return the trimmomaticHelp
     */
    public static String getTrimmomaticHelp() {
        return trimmomaticHelp;
    }

    /**
     * @return the trimmomaticSlidingWindow
     */
    public static String getTrimmomaticSlidingWindow() {
        return trimmomaticSlidingWindow;
    }

    /**
     * @return the trimmomaticWindowSize
     */
    public static String getTrimmomaticWindowSize() {
        return trimmomaticWindowSize;
    }

    /**
     * @return the trimmomaticRequiredQuality
     */
    public static String getTrimmomaticRequiredQuality() {
        return trimmomaticRequiredQuality;
    }

    /**
     * @return the seecerHelp
     */
    public static String getSeecerHelp() {
        return seecerHelp;
    }

    /**
     * @return the seecerKmer
     */
    public static String getSeecerKmer() {
        return seecerKmer;
    }

    /**
     * @return the velvetHelp
     */
    public static String getVelvetHelp() {
        return velvetHelp;
    }

    /**
     * @return the velvetKmer
     */
    public static String getVelvetKmer() {
        return velvetKmer;
    }

    /**
     * @return the velvetInsertLEngth
     */
    public static String getVelvetInsertLEngth() {
        return velvetInsertLEngth;
    }

    /**
     * @return the trinityHelp
     */
    public static String getTrinityHelp() {
        return trinityHelp;
    }

    /**
     * @return the transabyssHelp
     */
    public static String getTransabyssHelp() {
        return transabyssHelp;
    }

    /**
     * @return the transabyssKmer
     */
    public static String getTransabyssKmer() {
        return transabyssKmer;
    }

    
    
}


