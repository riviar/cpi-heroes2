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

    public String getInputPath() {
        return inputPath;
    }
    
  
    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }
    
    /**
     * @return the inputPath2
     */
    public String getInputPath2() {
        return inputPath2;
    }
    
    public void setInputPath2(String inputPath2) {
        this.inputPath2 = getInputPath2();        
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

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }
    
    
    /**
     * Creates a new instance of TestToolBean
     */
    public TestToolBean() {
    }
    
    public void runFastQC() {
        AbstractJob job = new FastQCJob(inputPath);
        job.execute();
        outputFile = inputPath.substring(0, inputPath.lastIndexOf("."));
    }
    
    public void runSeecer() {
        AbstractJob job = new SeecerJob(getInputPath(), getInputPath2());
        job.execute();
        outputFile = inputPath.substring(0, inputPath.lastIndexOf("."));
    }

    
}
