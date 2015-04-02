/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

/**
 *
 * @author pitas
 */
public class WriteConfig {

    String seqType;
    //String max_read_len;
    String insertSize;
    String leftRead;
    String rightRead;
    
    public WriteConfig(String seqType, /*String max_read_len,*/ String insertSize, String leftRead, String rightRead) {
        this.seqType = seqType;
        //this.max_read_len = max_read_len;
        this.insertSize = insertSize;
        this.leftRead = leftRead;
        this.rightRead = rightRead;
    }
    
    public void write() throws Exception {
        java.io.File file = new java.io.File("/home/vmuser/CPI/tools/SOAPdenovo-Trans/config_file");
        
        java.io.PrintWriter output = new java.io.PrintWriter(file);
                
        //output.println("#maximal read length");
        //output.println("max_rd_len=50");
        output.println("[LIB]");
        //output.println("#maximal read length in this lib");
        //output.println("rd_len_cutof=45");
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
