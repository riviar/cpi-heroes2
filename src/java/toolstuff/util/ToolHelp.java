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

    private static String factqcHelp = "Version: 0.11.3 </br></br><b>Tool description:</b> A quality control tool for high throughput sequence data. Performs simple quality control checks. "
            + "The left hand side of the main interactive display or the top of the HTML report show a summary of the modules which were run, and a quick evaluation of whether the results of the module seem entirely normal (green tick), slightly abnormal (orange triangle) or very unusual (red cross).";

    private static String trimmomaticHelp = "Version: 0.33 </br></br><b>Tool description:</b> Trimmomatic performs trimming tasks for paired-end and single-end data. "
            + "</br></br> <b>SLIDINGWINDOW:</b> Performs a sliding window trimming approach. It starts scanning at the 5‟ end and clips the read once the average quality within the window falls below a threshold. By considering multiple bases, a single poor quality base will not cause the removal of high quality data later in the read. "
            + "</br></br> <b>Window size:</b> specifies the number of bases to average across. "
            + "</br></br> <b>Required Quality:</b> specifies the average quality required.";
    private static String trimmomaticSlidingWindow = "SLIDINGWINDOW: Performs a sliding window trimming approach. It starts scanning at the 5‟ end and clips the read once the average quality within the window falls below a threshold. By considering multiple bases, a single poor quality base will not cause the removal of high quality data later in the read.";
    private static String trimmomaticWindowSize = "Window Size: specifies the number of bases to average across.";
    private static String trimmomaticRequiredQuality = "Required Quality: specifies the average quality required.";
    
    private static String trimmomaticAdapterHelp = "</br></br><b>Process description:</b> This step is used to find and remove Illumina adapters. Suggested adapter sequences are provided for TruSeq2 (as used in GAII machines) and TruSeq3 (as used by HiSeq and MiSeq machines), for both single-end and paired-end mode. "
            + "These sequences have not been extensively tested, and depending on specific issues which may occur in library preparation, other sequences may work better for a given dataset. As a rule of thumb newer libraries will use TruSeq3, but this really depends on your service provider. "
            + "If you use FASTQC, the „Overrepresented Sequences‟ report can help indicate which adapter file is best suited for your data. “Illumina Single End” or “Illumina Paired End” sequences indicate single-end or paired-end TruSeq2 libraries, and the appropriate adapter files are “TruSeq2-SE.fa” and “TruSeq2-PE.fa” respectively. "
            + "“TruSeq Universal Adapter” or “TruSeq Adapter, Index …” indicates TruSeq-3 libraries, and the appropriate adapter files are “TruSeq3-SE.fa” or “TruSeq3-PE.fa”, for single-end and paired-end data respectively. Adapter sequences for TruSeq2 multiplexed libraries, indicated by “Illumina Multiplexing …”, and the various RNA library preparations are not currently included."
            + "</br></br> <b>seedMismatches:</b> specifies the maximum mismatch count which will still allow a full match to be performed."
            + "</br></br> <b>palindromeClipThreshold:</b> specifies how accurate the match between the two 'adapter ligated' reads must be for PE palindrome read alignment."
            + "</br></br> <b>simpleClipThreshold:</b> specifies how accurate the match between any adapter etc. sequence must be against a read.";
    
    private static String trimmomaticAdapterSeedMismatches = "seedMismatches: specifies the maximum mismatch count which will still allow a full match to be performed.";
    private static String palindromeClipTreshold = "palindromeClipThreshold: specifies how accurate the match between the two 'adapter ligated' reads must be for PE palindrome read alignment.";
    private static String simpleClipTreshold = "simpleClipThreshold: specifies how accurate the match between any adapter etc. sequence must be against a read.";

    private static String seecerHelp = "Version: 0.1.3 </br></br><b>Tool description:</b> A sequencing error correction tool for RNA-seq data sets, based on hidden Markov Model (HMM). It takes the raw read sequences produced by a next generation sequencing platform like machines from Illumina or Roche. "
            + "It removes mismatch and indel errors from the raw reads. "
            + "</br></br> <b>K-mer:</b> specifies the k value, which is the size of k-mers used by SEECER to build the homogenous contigs for the HMM. The default value is 17.";
    private static String seecerKmer = "</br></br><b>K-mer:</b> specifies the k value, which is the size of k-mers used by SEECER to build the homogenous contigs for the HMM. The default value is 17.";

    private static String velvetHelp = "</br></br><b>Tool description:</b> "
            + "Velvet is a de novo genomic assembler specially designed for short read sequencing technologies, such as Solexa or 454. Developed by Daniel Zerbino and Ewan Birney at the European Bioinformatics Institute (EMBL-EBI), near Cambridge, in the United Kingdom. "
            + "Velvet currently takes in short read sequences, removes errors then produces high quality unique contigs. It then uses paired-end read and long read information, when available, to retrieve the repeated areas between contigs. "
            + "Velveth helps you construct the dataset for the following program, velvetg, and indicate to the system what each sequence file represents. Velvetg is the core of Velvet where the de Bruijn graph is built then manipulated. " 
            + " </br></br> <b>K-mer:</b> also known as hash length corresponds to the length, in base pairs, of the words being hashed. Experience shows that k-mer coverage should be above 10 to start getting decent results. If Ck is above 20, you might be \\\"wasting\\\" coverage."
            + " </br></br> <b>Insert Length:</b> is understood to be the length of the sequenced fragment, i.e. it includes the length of the reads themselves.";
    private static String velvetKmer = "K-mer: also known as hash length corresponds to the length, in base pairs, of the words being hashed. Experience shows that k-mer coverage should be above 10 to start getting decent results. If Ck is above 20, you might be \"wasting\" coverage.";
    private static String velvetInsertLength = "Inser length: is understood to be the length of the sequenced fragment, i.e. it includes the length of the reads themselves.";

    private static String trinityHelp = "</br></br><b>Tool description:</b> "
            + "Trinity represents an efficient and robust de novo reconstruction of transcriptomes from RNA-seq data. Developed at the Broad Institute and the Hebrew University of Jerusalem.\n"
            + "Trinity combines three independent software modules: Inchworm, Chrysalis, and Butterfly, applied sequentially to process large volumes of RNA-seq reads. Trinity partitions the sequence data into many individual de Bruijn graphs."
            + "Inchworm assembles the RNA-seq data into the unique sequences of transcripts, often generating full-length transcripts for a dominant isoform, but then reports just the unique portions of alternatively spliced transcripts."
            + "Chrysalis clusters the Inchworm contigs into clusters and constructs complete de Bruijn graphs for each cluster. Each cluster represents the full transcriptonal complexity for a given gene (or sets of genes that share sequences in common). Chrysalis then partitions the full read set among these disjoint graphs."
            + "Butterfly then processes the individual graphs in parallel, tracing the paths that reads and pairs of reads take within the graph, ultimately reporting full-length transcripts for alternatively spliced isoforms, and teasing apart transcripts that corresponds to paralogous genes.";

    private static String transabyssHelp = "</br></br><b>Tool description:</b> "
            + "Trans-ABySS is a de novo assembly tool of RNA-Seq data using ABySS (Assembly By Short Sequences) a de novo, parallel, paired-end sequence assembler that is designed for short reads. Developed in Canada's Michael Smith Genome Sciences Centre, BC Cancer Agency"
            + "The current version of the Trans-ABySS package comes with 3 main applications:"
            + "1. transabyss - assemble RNAseq data"
            + "2. transabyss-merge - merge multiple assemblies from (1)"
            + "3. transabyss-analyze - analyze assemblies, either from (1) or (2), for structural variants and splice variants. Requires reference genome and annotations. "
            + "</br></br> <b>K-mer:</b> default 32, k=32 has a good trade-off for assembling both rare and common transcripts. Using larger k-mers improve the assembly quality of common transcripts and transcripts with repetitive regions, but the assembly of rare transcripts may suffer.";
    private static String transabyssKmer = "K-mer: default 32, k=32 has a good trade-off for assembling both rare and common transcripts. Using larger k-mers improve the assembly quality of common transcripts and transcripts with repetitive regions, but the assembly of rare transcripts may suffer.";

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
    
    public static String getTrimmomaticAdapterHelp() {
        return trimmomaticAdapterHelp;
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
    public static String getVelvetInsertLength() {
        return velvetInsertLength;
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

    /**
     * @return the trimmomaticAdapterSeedMismatches
     */
    public static String getTrimmomaticAdapterSeedMismatches() {
        return trimmomaticAdapterSeedMismatches;
    }

    /**
     * @return the palindromeClipTreshold
     */
    public static String getPalindromeClipTreshold() {
        return palindromeClipTreshold;
    }

    /**
     * @return the simpleClipTreshold
     */
    public static String getSimpleClipTreshold() {
        return simpleClipTreshold;
    }
    
    

}
