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
        System.out.println("##########################################");

        AbstractJob job = new TrimmomaticJob(getInputPath(), getInputPath2(), getWindowSize(), getQualityth());
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
    
}
