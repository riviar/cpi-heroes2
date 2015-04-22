/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

/**
 * Sets the configuration file for SOAPdenovo-Trans 
 * @author Asier Gonzalez
 */
public class WriteConfig {

    /**
     * Sequence type
     * <p><code>fasta</code> for files with either fa or fasta extension and <code>fastq</code> for fq and fastq
     */
    String seqType;
    
    /**
     * Insert size of the pair-ended experiment, that is, the distance between the forward and reverse reads
     */
    String insertSize;
    
    /**
     * Name of the left of forward read
     */
    String leftRead;
    
    /**
     * Name of the right or reverse read
     */
    String rightRead;
    
    /**
     * Sets all the parameters
     * @param seqType Sequence type (fasta or fastq)
     * @param insertSize Insert size
     * @param leftRead Name of the left/forward reads
     * @param rightRead Name of the right/reverse reads
     */
    public WriteConfig(String seqType, String insertSize, String leftRead, String rightRead) {
        this.seqType = seqType;
        this.insertSize = insertSize;
        this.leftRead = leftRead;
        this.rightRead = rightRead;
    }
    
    /**
     * Writes the configuration to <i>/home/vmuser/CPI/tools/SOAPdenovo-Trans/config_file</i>
     * @throws Exception Writing to a file can cause an exception if the destination file does not exist or it cannot be accessed
     */
    public void write() throws Exception {
        java.io.File file = new java.io.File("/home/vmuser/CPI/tools/SOAPdenovo-Trans/config_file");
        
        java.io.PrintWriter output = new java.io.PrintWriter(file);
                
        output.println("[LIB]");
        output.println("#average insert size");
        output.println("avg_ins=" + insertSize);
        
        if(seqType.equals("fastq")){
            output.println("#fastq file for read 1 ");
            output.println("q1=" + leftRead);
            output.println("#fastq file for read 2 always follows fastq file for read 1");
            output.println("q2=" + rightRead);
        }else if(seqType.equals("fasta")) {
            output.println("#fasta file for read 1 ");
            output.println("f1=" + leftRead);
            output.println("#fasta file for read 2 always follows fastq file for read 1");
            output.println("f2=" + rightRead);
        }else{
            output.println("#BAM file for reads ");
            output.println("b=/path/**LIBNAMEA**/BAM_reads.bam");
        }
                
        output.close();
    }
}
