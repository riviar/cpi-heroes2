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

    private String inputPath;
    private String outputFile = "none";

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
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
    
}
