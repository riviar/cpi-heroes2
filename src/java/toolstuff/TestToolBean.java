/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.util.regex.Pattern;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Fox
 */
@ManagedBean(name = "TestToolBean")
@RequestScoped
public class TestToolBean {

    private String inputPath="";
    private String inputPath2="";
    private String windowSize ="";
    private String qualityth ="";
    private String outputFile = "none";
    private String seqType="";
    private String kmer="";
    private String insLen="";
    private String jobid="";

    
    
    /**
     * Creates a new instance of TestToolBean
     */
    public TestToolBean() {
    }
    
    public void runFastQC() {
        AbstractJob job = new FastQCJob(getInputPath());
        job.execute();
        setOutputFile(getInputPath().substring(0, getInputPath().lastIndexOf(".")));
    }
    
    public void runTrimmomatic() {
       
        AbstractJob job = new TrimmomaticJob(getInputPath(), getInputPath2(), getWindowSize(), getQualityth());
        job.execute();
        setOutputFile(getInputPath().substring(0, getInputPath().lastIndexOf(".")));
    }
    
    public void runVelvet() {
        
        //inputPath are left reads and inputPath2 are right reads
        AbstractJob job = new VelvetJob(getSeqType(), getInputPath(), getInputPath2(), getKmer(), getInsLen(), getJobid());
        job.execute();
        setOutputFile(getInputPath().substring(0, getInputPath().lastIndexOf(".")));
    }

    /**
     * @return the inputPath
     */
    public String getInputPath() {
        return inputPath;
    }

    /**
     * @param inputPath the inputPath to set
     */
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * @return the inputPath2
     */
    public String getInputPath2() {
        return inputPath2;
    }

    /**
     * @param inputPath2 the inputPath2 to set
     */
    public void setInputPath2(String inputPath2) {
        this.inputPath2 = inputPath2;
    }

    /**
     * @return the windowSize
     */
    public String getWindowSize() {
        return windowSize;
    }

    /**
     * @param windowSize the windowSize to set
     */
    public void setWindowSize(String windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * @return the qualityth
     */
    public String getQualityth() {
        return qualityth;
    }

    /**
     * @param qualityth the qualityth to set
     */
    public void setQualityth(String qualityth) {
        this.qualityth = qualityth;
    }

    /**
     * @return the outputFile
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @return the seqType
     */
    public String getSeqType() {
        return seqType;
    }

    /**
     * @param seqType the seqType to set
     */
    public void setSeqType(String seqType) {
        this.seqType = seqType;
    }

    /**
     * @return the kmer
     */
    public String getKmer() {
        return kmer;
    }

    /**
     * @param kmer the kmer to set
     */
    public void setKmer(String kmer) {
        this.kmer = kmer;
    }

    /**
     * @return the insLen
     */
    public String getInsLen() {
        return insLen;
    }

    /**
     * @param insLen the insLen to set
     */
    public void setInsLen(String insLen) {
        this.insLen = insLen;
    }

    /**
     * @return the jobid
     */
    public String getJobid() {
        return jobid;
    }

    /**
     * @param jobid the jobid to set
     */
    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    
}
