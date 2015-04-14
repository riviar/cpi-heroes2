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

    private static String fastqcHelp = "<b>Version:</b> 0.11.3 </br></br><b>Tool description:</b> </br>A quality control tool for high throughput sequence data. Performs simple quality control checks. "
            + "The left hand side of the main interactive display or the top of the HTML report show a summary of the modules which were run, and a quick evaluation."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://www.bioinformatics.babraham.ac.uk/projects/fastqc/\" target=\"_blank\">FastQC home page (opens in a new window)</a>";

    private static String trimmomaticHelp = "<b>Version:</b> 0.33 </br></br><b>Tool description:</b> </br>Trimmomatic performs trimming tasks for paired-end and single-end data. For trimming the SlidingWindow method is used. "
            + "</br></br> <b>SLIDINGWINDOW:</b> Performs a sliding window trimming approach. It starts scanning at the 5‟ end and clips the read once the average quality within the window falls below a threshold. By considering multiple bases, a single poor quality base will not cause the removal of high quality data later in the read. "
            + "</br></br> <b>Window size:</b> specifies the number of bases to average across. "
            + "</br></br> <b>Required Quality:</b> specifies the average quality required."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://www.usadellab.org/cms/?page=trimmomatic\" target=\"_blank\">Trimmomatic home page (in a new window)</a>";
    private static String trimmomaticSlidingWindow = "SLIDINGWINDOW: Performs a sliding window trimming approach. It starts scanning at the 5‟ end and clips the read once the average quality within the window falls below a threshold. By considering multiple bases, a single poor quality base will not cause the removal of high quality data later in the read.";
    private static String trimmomaticWindowSize = "Window Size: specifies the number of bases to average across.";
    private static String trimmomaticRequiredQuality = "Required Quality: specifies the average quality required.";
    
    private static String trimmomaticAdapterHelp = "<b>Process description:</b> </br>This step is used to find and remove Illumina adapters. Suggested adapter sequences are provided for TruSeq2 (as used in GAII machines) and TruSeq3 (as used by HiSeq and MiSeq machines)."
            
            + "If you use FASTQC, the „Overrepresented Sequences‟ report can help indicate which adapter file is best suited for your data. “Illumina Single End” or “Illumina Paired End” sequences indicate single-end or paired-end TruSeq2 libraries, and the appropriate adapter files are “TruSeq2-SE.fa” and “TruSeq2-PE.fa” respectively. "
            + "“TruSeq Universal Adapter” or “TruSeq Adapter, Index …” indicates TruSeq-3 libraries, and the appropriate adapter files are “TruSeq3-SE.fa” or “TruSeq3-PE.fa” respectively."
            + "</br></br> <b>seedMismatches:</b> specifies the maximum mismatch count which will still allow a full match to be performed."
            + "</br></br> <b>palindromeClipThreshold:</b> specifies how accurate the match between the two 'adapter ligated' reads must be for PE palindrome read alignment."
            + "</br></br> <b>simpleClipThreshold:</b> specifies how accurate the match between any adapter etc. sequence must be against a read."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://www.usadellab.org/cms/?page=trimmomatic\" target=\"_blank\">Trimmomatic home page (in a new window)</a>";
    
    private static String trimmomaticAdapterSeedMismatches = "seedMismatches: specifies the maximum mismatch count which will still allow a full match to be performed.";
    private static String palindromeClipTreshold = "palindromeClipThreshold: specifies how accurate the match between the two 'adapter ligated' reads must be for PE palindrome read alignment.";
    private static String simpleClipTreshold = "simpleClipThreshold: specifies how accurate the match between any adapter etc. sequence must be against a read.";

    private static String seecerHelp = "<b>Version:</b> 0.1.3 </br></br><b>Tool description:</b> </br>A sequencing error correction tool for RNA-seq data sets, based on hidden Markov Model (HMM). It takes the raw read sequences produced by a next generation sequencing platforms. "
            + "It removes mismatch and indel errors from the raw reads. "
            + "</br></br> <b>K-mer:</b> specifies the k value, which is the size of k-mers used by SEECER to build the homogenous contigs for the HMM. The default value is 17."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://sb.cs.cmu.edu/seecer/\" target=\"_blank\">Seecer home page (in a new window)</a>";
    private static String seecerKmer = "</br></br><b>K-mer:</b> specifies the k value, which is the size of k-mers used by SEECER to build the homogenous contigs for the HMM. The default value is 17.";

    private static String velvetHelp = "<b>Version:</b> 1.2.10"
            + "</br></br><b>Tool description:</b> "
            + "</br>Velvet is a de novo genomic assembler specially designed for short read sequencing technologies, such as Solexa or 454. It consists of two main blocks: Velveth and Velvetg."
            + "Velveth constructs the dataset for velvetg, and indicates to the system what each sequence file represents. Velvetg is the core of Velvet where the de Bruijn graph is built then manipulated. "
            + "Oases is a De novo transcriptome assembler for very short reads." 
            + " </br></br> <b>K-mer:</b> also known as hash length corresponds to the length, in base pairs, of the words being hashed. Experience shows that k-mer coverage should be above 10 to start getting decent results. If Ck is above 20, you might be \\\"wasting\\\" coverage."
            + " </br></br> <b>Insert Length:</b> is understood to be the length of the sequenced fragment, i.e. it includes the length of the reads themselves."
            + "</br></br> <b>Web page:</b> " + "<a href=\"https://www.ebi.ac.uk/~zerbino/velvet/\" target=\"_blank\">Velvet home page (in a new window)</a>";
    private static String velvetKmer = "K-mer: also known as hash length corresponds to the length, in base pairs, of the words being hashed. Experience shows that k-mer coverage should be above 10 to start getting decent results. If Ck is above 20, you might be \"wasting\" coverage.";
    private static String velvetInsertLength = "Insert length: is understood to be the length of the sequenced fragment, i.e. it includes the length of the reads themselves.";

    private static String trinityHelp = "<b>Version:</b> 2.0.5"
            + "</br></br><b>Tool description:</b> "
            + "</br>Trinity combines three independent software modules: Inchworm, Chrysalis, and Butterfly, applied sequentially."
            + "Inchworm reports the unique portions of alternatively spliced transcripts."
            + "Chrysalis clusters the Inchworm contigs into clusters and constructs complete de Bruijn graphs for each cluster."
            + "Butterfly then processes the individual graphs in parallel, reporting full-length transcripts for alternatively spliced isoforms."
            + "</br></br><b>Trinity uses a fixed k-mer value (25).</b>"
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://trinityrnaseq.github.io/\" target=\"_blank\">Trinity home page (in a new window)</a>";

    private static String transabyssHelp = "<b>Version:</b> 1.5.2"
            + "</br></br><b>Tool description:</b> "
            + "</br>Trans-ABySS is a de novo assembly tool of RNA-Seq data using ABySS (Assembly By Short Sequences) a de novo, parallel, paired-end sequence assembler that is designed for short reads."
            + "It consists of three main blocks: "
            + "Transabyss - assemble RNAseq data. "
            + "Transabyss-merge - merge multiple assemblies. "
            + "Transabyss-analyze - analyze assemblies for structural variants and splice variants."
            + "</br></br> <b>K-mer:</b> default 32, k=32 has a good trade-off for assembling both rare and common transcripts. Using larger k-mers improve the assembly quality of common transcripts and transcripts with repetitive regions, but the assembly of rare transcripts may suffer."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://www.bcgsc.ca/platform/bioinfo/software/trans-abyss\" target=\"_blank\">TransAbyss home page (in a new window)</a>";
    private static String transabyssKmer = "K-mer: default 32, k=32 has a good trade-off for assembling both rare and common transcripts. Using larger k-mers improve the assembly quality of common transcripts and transcripts with repetitive regions, but the assembly of rare transcripts may suffer.";
    
    private static String SOAPdeNovoTrans = "<b>Version:</b> 1.0.4"
            + "</br></br> <b>Tool description:</b> <br>SOAPdenovo-Trans is a de novo transcriptome assembler basing on the SOAPdenovo framework, adapt to alternative splicing and different expression level among transcripts."
            + "</br></br> <b>K-mer:</b> The value is always depended on data size and its transcript features." 
            + "SOAPdenovo-Trans accepts odd Kmer value from 13 to 127.\n" 
            + "Ordinarily, SOAPdenovo-Trans always assembles the RNA-seq data by small kmer (~35-mer) as some of the transcripts are in low expression level."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://soap.genomics.org.cn/SOAPdenovo-Trans.html\" target=\"_blank\">Soap de Novo-Trans home page (in a new window)</a>";
            
    private static String clustering = "<b>Tool description:</b> </br>Run this tool to automatically split the data set into a sets of transcripts with related expression patterns by partitioning the hierarchically clustered transcript tree."
            + "</br></br><b>Percent of height to cut tree:</b> cut tree based on this percent of max(height) of tree."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://trinityrnaseq.github.io/analysis/diff_expression_analysis.html#DE_analysis\" target=\"_blank\">Trinity clustering help page (in a new window)</a>";
    
    private static String BlastX = "<b>Tool description:</b> </br>BlastX is a program provided by NCBI for comparing nucleotide query sequences translated in all reading frames against a protein sequence database. "
            + "Local Database: (nr) Non-redundant protein sequences from GenPept, Swissprot, PIR, PDF, PDB, and NCBI RefSeq."
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://www.ncbi.nlm.nih.gov/books/NBK21097/\" target=\"_blank\">The BLAST Sequence Analysis Tool (in a new window)</a>";

 
    

    
    
    //abundance estimation help 
    private static String abEstFastaInputHelp="This fasta file is generated in the assembly of the reads.";
    private static String abEstReadsHelp="These are the right and left corresponding to the assembled fasta file.";
    private static String abEstReadTypeHelp="Select if the reads are in FASTA or FASTQ format." ;
    private static String abEstMethodHelp="Choose an estimation method for abundance estimation." ;
    private static String abEstTopGenes="Select the percentage of top expressed genes that you wand to include in your report and in a fasta file." ;
    private static String abEstNameHelp="Write a name for your output files." ;
    private static String abundanceEstimationHelp="<b>Tool description: </b>"
            + "The first step that needs to be performed for downstream analysis to estimate the abundance of the isoforms generated by the assembly. For this purpose, the reads are mapped to the assembled contigs using Bowtie and quantification of expression is performed using RSEM or eXpress (depending on the user's selection). Additionally, the user can extract the percentage they desire of the  top expressed isoforms."
            + "</br></br> <b>Assembled fasta file: </b>" + abEstFastaInputHelp
            + "</br></br> <b>Left and right reads: </b>" + abEstReadsHelp
            + "</br></br> <b>Reads file format: </b>" + abEstReadTypeHelp
            + "</br></br> <b>Estimation method: </b>" + abEstMethodHelp
            + "</br></br> <b>Percentage of top genes to show: </b>" + abEstTopGenes
            + "</br></br> <b>Name for output: </b>" + abEstNameHelp
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://http://trinityrnaseq.github.io/analysis/abundance_estimation.html\" target=\"_blank\">Trinity abundance estimation help page (in a new window).</a>";


    
    //differential gene expression help
    private static String dgeFilesHelp="Select as many files as you want to perform differential gene expression.";
    private static String dgeEstMethodHelp="Estimation method used in the previous step.";
    private static String dgePvalueHelp="P-value threshold for false discovery rate.";
    private static String dgeFoldChangeHelp="Minimum fold change expressed as |log2(a/b)| (meaning that the default value of 2 is equivalent to 2^2=4 fold change)";
    private static String dgeMaxDegHelp="Maximum number of differentially expressed genes per comparison to be included in the report (even if BLAST has ore hits).";
    private static String dgeNameHelp="Write a name for your output files." ;
    private static String differentialGeneExpressionHelp="<b>Tool description: </b>"
            + "If the user has sequenced more than one sample under different conditions, they can perform differential gene expression using the abundance estimation files from the previous step. This tool uses the Bioconductor package edgeR for identification of differentially expressed genes."
            + "</br></br> <b>Abundance estimation files: </b>" + dgeFilesHelp
            + "</br></br> <b>Method used for abundance estimation: </b>" + dgeEstMethodHelp
            + "</br></br> <b>P-value offcuts for FDR: </b>" + dgePvalueHelp
            + "</br></br> <b>Minimum fold change: </b>" + dgeFoldChangeHelp
            + "</br></br> <b>Maximum differentially expressed genes per comparison: </b>" + dgeMaxDegHelp
            + "</br></br> <b>Assembled fasta file: </b>" + dgeNameHelp
            + "</br></br> <b>Web page:</b> " + "<a href=\"http://trinityrnaseq.github.io/analysis/diff_expression_analysis.html\" target=\"_blank\">Trinity DGE help page (in a new window)</a>";
    
    
    //clustering by cutting tree





    
   
    
    
    
    /**
     * @return the factqcHelp
     */
    public static String getFastqcHelp() {
        return fastqcHelp;
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
     * @return the velvetHelp
     */
    public static String getVelvetHelp() {
        return velvetHelp;
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
     * @return the abundanceEstimation
     */
    public static String getAbundanceEstimationHelp() {
        return abundanceEstimationHelp;
    }

    /**
     * @return the clustering
     */
    public static String getClusteringHelp() {
        return getClustering();
    }

    /**
     * @return the SOAPdeNovoTrans
     */
    public static String getSOAPdeNovoTransHelp() {
        return getSOAPdeNovoTrans();
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

    /**
     * @return the seecerKmer
     */
    public static String getSeecerKmer() {
        return seecerKmer;
    }

    /**
     * @return the velvetKmer
     */
    public static String getVelvetKmer() {
        return velvetKmer;
    }

    /**
     * @return the velvetInsertLength
     */
    public static String getVelvetInsertLength() {
        return velvetInsertLength;
    }

    /**
     * @return the transabyssKmer
     */
    public static String getTransabyssKmer() {
        return transabyssKmer;
    }

    /**
     * @return the SOAPdeNovoTrans
     */
    public static String getSOAPdeNovoTrans() {
        return SOAPdeNovoTrans;
    }

    /**
     * @return the clustering
     */
    public static String getClustering() {
        return clustering;
    }

    /**
     * @return the BlastX
     */
    public static String getBlastX() {
        return BlastX;
    }


    /**
     * @return the differentialGeneExpressionHelp
     */
    public static String getDifferentialGeneExpressionHelp() {
        return differentialGeneExpressionHelp;
    }

   
     
}
