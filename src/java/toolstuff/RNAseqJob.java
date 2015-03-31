/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
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
    private String output;
    private String outputDir="/home/vmuser/CPI/results";
    
    
    /**
     * provides access to session bean UtilityBean (here for selectedTool)
     */
    //@ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    private JobHistoryFacade jobHistoryFacade;

    public RNAseqJob(String jobName) {
        this.jobName = jobName;
        //this.command = "/home/vmuser/CPI/tools/";
        this.command = "/home/pitas/SOAPdenovo-Trans";
    }
    
    public RNAseqJob(UtilityBean utilityBean, JobHistoryFacade jobHistoryFacade, String jobName) {
        this.jobHistoryFacade = jobHistoryFacade;
        this.utilityBean = utilityBean;
        this.jobName = jobName;
        //this.command = "/home/vmuser/CPI/tools/";
        this.command = "/home/pitas/SOAPdenovo-Trans";
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
            case TRIMMOMATIC_TRIM:
                executeTrimmomaticTrim();
                break;
            case TRIMMOMATIC_ADAPT:
                executeTrimmomaticAdapt();
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
            case SOAPdenovo_Trans:
                executeSOAPdenovoTrans();
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
        
        //command += " " + inputFileName + " " + "-o "+outputDir+jobName;
        command += " " 
                + inputFileName + " "
                + outputDir;
        
        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Trimmomatic job for trimming using sliding window
     */
    private void executeTrimmomaticTrim() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String windowSize = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String requiredQuality = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String fwPaired=getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        String fwUnpaired=getUtilityBean().getSelectedTool().getParameterList().get(3).getValue();
        String rPaired=getUtilityBean().getSelectedTool().getParameterList().get(4).getValue();
        String rUnpaired=getUtilityBean().getSelectedTool().getParameterList().get(5).getValue();
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + windowSize + " "
                + requiredQuality + " "
                + outputDir + " "
                + fwPaired + " "
                + fwUnpaired + " "
                + rPaired + " "
                + rUnpaired;

        
        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Trimmomatic job for removing adapters
     */
    private void executeTrimmomaticAdapt() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String adapters = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String seedMismatches = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String palindromeTh = getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        String simpleTh = getUtilityBean().getSelectedTool().getParameterList().get(3).getValue();
        String fwPaired=getUtilityBean().getSelectedTool().getParameterList().get(4).getValue();
        String fwUnpaired=getUtilityBean().getSelectedTool().getParameterList().get(5).getValue();
        String rPaired=getUtilityBean().getSelectedTool().getParameterList().get(6).getValue();
        String rUnpaired=getUtilityBean().getSelectedTool().getParameterList().get(7).getValue();
        
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + adapters + " "
                + seedMismatches + " "
                + palindromeTh + " "
                + simpleTh + " "
                + outputDir + " "
                + fwPaired + " "
                + fwUnpaired + " "
                + rPaired + " "
                + rUnpaired;

        
        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Seecer job
     */
    private void executeSeecer() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String kmerCount =  getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String outfileName =  getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String leftCorrName =  getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        String rightCorrName =  getUtilityBean().getSelectedTool().getParameterList().get(3).getValue();

        /*
        //directory where seecer will store temporary files during job
        String tmpPath = "/home/vmuser/CPI/tools/Seecer/testdata/tmp";
        //String jellyfishPath = "/home/vmuser/CPI/tools/Preprocessing/Seecer/Seecer/jellyfish-1.1.11/bin";      
        command += " -t " + tmpPath + " -k " + KmerCount +" "+ rightInput + " " + leftInput;
	*/
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + kmerCount + " "
                + outputDir + " "
                + outfileName + " "
                + leftCorrName + " "
                + rightCorrName;


        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Trinity job
     * Be mindful of reversed right/left order!
     */
    private void executeTrinity() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
         
        String seqType = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        //String outputDir = "/home/lestelles/Desktop/"; // probably should be changed?
        String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + outputDir + " "
                + outfileName;

        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeVelvet() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String seqType = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String kmer = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String insLen = getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(3).getValue();
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + insLen + " "
                + outputDir + " "
                + outfileName;

        executeCommand(command);
    }
    
    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeTransabyss() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String kmer = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + outputDir + " "
                + outfileName;
                
        executeCommand(command);
       
    }
    
    private void executeSOAPdenovoTrans(){
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String seqType = getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String kmer = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        String insLen = getUtilityBean().getSelectedTool().getParameterList().get(2).getValue();
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(3).getValue();
        
        WriteConfig configFile = new WriteConfig(seqType, insLen, leftInput, rightInput);
        try {
            configFile.write();
        } catch (Exception ex) {
            Logger.getLogger(RNAseqJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        command += " " 
                + kmer + " "
                + outputDir + " "
                + outfileName;
                
        executeCommand(command);
    }

    /**
     * Executes shell command
     * @param command full command to execute
     * @return 
     */
    private void executeCommand(String command) {

        Jobhistory newJob;
        newJob = new Jobhistory(jobName, 1, utilityBean.getSelectedProject().getIdprojects(), command);
        jobHistoryFacade.create(newJob);
        
        //Job results are stored in a directory named after the job ID
        String jobID = Integer.toString(newJob.getIdjobs());
        newJob.setCommandused(command + " " + jobID);
        
        String[] commandArray = command.split("\\s+");
        List<String> commandList = new ArrayList(commandArray.length + 1);
        //Arrays.asList(commandArray);
        for (int i = 0; i < commandArray.length; i++) {
            commandList.add(commandArray[i]);
        }
        commandList.add(jobID);

        ProcessBuilder pb = new ProcessBuilder().command(commandList).redirectErrorStream(true);
        //ProcessBuilder pb = new ProcessBuilder().command("pwd").redirectErrorStream(true);
        Process p;
        try {
            p = pb.start();
            Field f = p.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            int pid = (int) f.get(p);
            newJob.setProcessid(pid);
            
            //Update job with the complete command and the pid
            jobHistoryFacade.edit(newJob);
            
            //Create two threads, one to perform the the job and another to return to projects page
            jobThread waitThread = new jobThread("waitThread");
            waitThread.setP(p);
            waitThread.setToolEnum(getUtilityBean().getSelectedTool().getToolEnum());
            waitThread.setUpdateJob(newJob);
            waitThread.setJobHistoryFacade(jobHistoryFacade);
            jobThread returnThread = new jobThread("returnThread");
            
            waitThread.start();
            returnThread.start();
            
            /*StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");

            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

            // start gobblers
            outputGobbler.start();
            errorGobbler.start();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //InputStream stdOut = p.getInputStream();
        /*StringBuffer output = new StringBuffer();

         Process p;
         try {

         p = Runtime.getRuntime().exec(command+" "+jobProjectID);
            
         p.waitFor();
         BufferedReader reader
         = new BufferedReader(new InputStreamReader(p.getInputStream()));

         String line = "";
         while ((line = reader.readLine()) != null) {
         output.append(line + "\n");
         }*/
   

        //System.out.println(output.toString());
    //return output.toString();
}
    
    /*private class StreamGobbler extends Thread {

        InputStream is;
        String type;

        private StreamGobbler(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(type + "> " + line);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }*/
    
    
    
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

