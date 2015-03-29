/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Map;
//import javax.faces.bean.ManagedProperty;
import managedbeans.UtilityBean;
import sessionbeans.JobHistoryFacade;

/**
 * Universal job class. 
 * Used to execute job using currently selected tool (UtilityBean).
 * @author Fox
 */
public class RNAseqJob {

    private String jobName;

    private String command;
    //private String output;
    /**
     * provides access to session bean UtilityBean (here for selectedTool)
     */
    //@ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    private JobHistoryFacade jobHistoryFacade;

    public RNAseqJob(String jobName) {
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/";
    }
    
    public RNAseqJob(UtilityBean utilityBean, JobHistoryFacade jobHistoryFacade, String jobName) {
        this.jobHistoryFacade = jobHistoryFacade;
        this.utilityBean = utilityBean;
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/";
    }

    /**
     * Executes Job based on selected tool.
     */
    public void execute() {
        appentExecutable();
        switch (getUtilityBean().getSelectedTool().getToolEnum()) {
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
            case TRANSABYSS:
                executeTransabyss();
                break;
            default:
                throw new AssertionError(getUtilityBean().getSelectedTool().getToolEnum().name());
        }
    }
    
    /**
     * Retrieves parameters and executes FastQC job
     */
    private void executeFastQC() {
        String inputFileName = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        
        command += " " + inputFileName + " " + "-o /root/NetBeansProjects/izidev2/web/Output";
        executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Trimmomatic job
     */
    private void executeTrimmomatic() {
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String windowSize = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String requiredQuality = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        
        String finalCommand = "java -jar "+ command + " PE " 
                + rightInput + " "
                + leftInput
                + " paired_1 unpaired_1 paired_2 unpaired_2"
                + " SLIDINGWINDOW:"
                + windowSize + ":"
                + requiredQuality;
        System.out.println(finalCommand);
        
        //~/glassfish-4.1/glassfish/domains/domain1/config
        
        executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Seecer job
     */
    private void executeSeecer() {
        String rightInput =  getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String leftInput =  getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String KmerCount =  getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        
        //directory where seecer will store temporary files during job
        String tmpPath = "/home/vmuser/CPI/tools/Seecer/testdata/tmp";
        //String jellyfishPath = "/home/vmuser/CPI/tools/Preprocessing/Seecer/Seecer/jellyfish-1.1.11/bin";
        
        
        command += " -t " + tmpPath + " -k " + KmerCount +" "+ rightInput + " " + leftInput;
	
        executeCommand(jobName ,command);
    }
    
    /**
     * Retrieves parameters and executes Trinity job
     * Be mindful of reversed right/left order!
     */
    private void executeTrinity() {
        String rightInput = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String leftInput = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        
        String seqType = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String outputDir = "/home/lestelles/Desktop/"; // probably should be changed?
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + outputDir + " "
                + jobName;

        //~/glassfish-4.1/glassfish/domains/domain1/config
        executeCommand(jobName ,command);
    }
    
    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeVelvet() {
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        
        String seqType = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String kmer = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String insLen = getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + insLen + " "
                + outputDir + " "
                + jobName;

        //~/glassfish-4.1/glassfish/domains/domain1/config
        executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeTransabyss() {
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        
        String kmer = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + outputDir + " "
                + jobName;
        
        executeCommand(jobName, command);
    }

    /**
     * Executes shell command
     * @param command full command to execute
     * @return 
     */
    private void executeCommand(String jobName, String command) {

        Jobhistory newJob;
        //newJob = new Jobhistory(jobName, 1, utilityBean.getSelectedProject().getIdprojects(), command);
        //jobHistoryFacade.create(newJob);
        int pid;
        
        //StringBuffer output = new StringBuffer();

        Process p;
        try {
            //Launch the job
            //p = Runtime.getRuntime().exec(command);
                       
            p = Runtime.getRuntime().exec("ps -p 3143 -o start,etime");
            
            //Get the PID
            Field f = p.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            pid = (int) f.get(p);
            
            //Add job to history
            newJob = new Jobhistory(jobName, pid, utilityBean.getSelectedProject().getIdprojects(), command);
            jobHistoryFacade.create(newJob);
            
            //Create two threads: One to control if the job is finished and te other to return to the projects page
            jobThread returnThread = new jobThread("returnThread");
            returnThread.start();
            jobThread waitJobCompleteThread = new jobThread("waitThread");
            waitJobCompleteThread.setP(p);
            waitJobCompleteThread.setUpdateJob(newJob);
            waitJobCompleteThread.setJobHistoryFacade(jobHistoryFacade);
            waitJobCompleteThread.start();
            
            //newJob.setProcessid(-1);
            //jobHistoryFacade.edit(newJob);
            
                       
            /*p.waitFor();
            
            //Update the status to finished
            //jobHistoryFacade.updateJob(newJob);
            
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(output.toString());
        //return output.toString();
    }
    
    
    
    /**
     * Appends path to executable file to the command
     */
    private void appentExecutable() {
        System.out.println(getUtilityBean().getSelectedTool().getName());
        command += getUtilityBean().getSelectedTool().getPath();              
    }

    /**
     * @return the utilityBean
     */
    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    /**
     * @param utilityBean the utilityBean to set
     */
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    /**
     * @return the jobHistoryFacade
     */
    public JobHistoryFacade getJobHistoryFacade() {
        return jobHistoryFacade;
    }

    /**
     * @param jobHistoryFacade the jobHistoryFacade to set
     */
    public void setJobHistoryFacade(JobHistoryFacade jobHistoryFacade) {
        this.jobHistoryFacade = jobHistoryFacade;
    }
}
