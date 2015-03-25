/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.faces.bean.ManagedProperty;
import managedbeans.UtilityBean;

/**
 * Universal job class. 
 * Used to execute job using currently selected tool (UtilityBean).
 * @author Fox
 */
public class RNAseqJob {

    private String jobName;

    private String command;
    private String output;
    /**
     * provides access to session bean UtilityBean (here for selectedTool)
     */
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    public RNAseqJob(String jobName) {
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/";
    }

    /**
     * Executes Job based on selected tool.
     */
    public void execute() {
        appentExecutable();
        switch (utilityBean.getSelectedTool().getToolEnum()) {
            case FASTQC:
                executeFastQC();
                break;
            case TRIMMOMATIC:
                executeTrimmomatic();
                break;
            case SEECER:
                executeSeecer();
                break;
            case TRINITY:
                executeTrinity();
                break;
            case VELVET:
                executeVelvet();
                break;
            default:
                throw new AssertionError(utilityBean.getSelectedTool().getToolEnum().name());
        }
    }
    
    /**
     * Retrieves parameters and executes FastQC job
     */
    private void executeFastQC() {
        String inputFileName = utilityBean.getSelectedTool().getInputList().get(0).getValue();
        command += " " + inputFileName + " " + "-o /root/NetBeansProjects/izidev2/web/Output";
        String output = executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Trimmomatic job
     */
    private void executeTrimmomatic() {
        String rightInput = utilityBean.getSelectedTool().getInputList().get(0).getValue();
        String leftInput = utilityBean.getSelectedTool().getInputList().get(1).getValue();
        String windowSize = utilityBean.getSelectedTool().getParameterList().get(0).getValue();
        String requiredQuality = utilityBean.getSelectedTool().getParameterList().get(1).getValue();
        
        String finalCommand = "java -jar "+ command + " PE " 
                + rightInput + " "
                + leftInput
                + " paired_1 unpaired_1 paired_2 unpaired_2"
                + " SLIDINGWINDOW:"
                + windowSize + ":"
                + requiredQuality;
        System.out.println(finalCommand);
        
        //~/glassfish-4.1/glassfish/domains/domain1/config
        
        String output=executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Seecer job
     */
    private void executeSeecer() {
        
    }
    
    /**
     * Retrieves parameters and executes Trinity job
     */
    private void executeTrinity() {
        
    }
    
    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeVelvet() {
        
    }

    /**
     * Executes shell command
     * @param command full command to execute
     * @return 
     */
    private String executeCommand(String jobName, String command) {

        Jobhistory newJob;
        newJob = new Jobhistory(jobName, 1, 1, command);
        
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }
    
    /**
     * Appends path to executable file to the command
     */
    private void appentExecutable() {
        command += utilityBean.getSelectedTool().getPath();
    }
}
