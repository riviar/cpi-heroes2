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
                + outputDir + " "
                + jobName;
        
        output = executeCommand(jobName, command);
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
                + jobName + " "
                + fwPaired + " "
                + fwUnpaired + " "
                + rPaired + " "
                + rUnpaired;

        
        output=executeCommand(jobName, command);
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
                + jobName + " "
                + fwPaired + " "
                + fwUnpaired + " "
                + rPaired + " "
                + rUnpaired;

        
        output=executeCommand(jobName, command);
    }
    
    /**
     * Retrieves parameters and executes Seecer job
     */
    private void executeSeecer() {
        String leftInput = getUtilityBean().getSelectedTool().getInputList().get(0).getValue();
        String rightInput = getUtilityBean().getSelectedTool().getInputList().get(1).getValue();
        
        String kmerCount =  getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        String outfileName =  getUtilityBean().getSelectedTool().getParameterList().get(0).getValue();
        
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
                + jobName + " "
                + outfileName;

        
        
        output = executeCommand(jobName ,command);
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
                + jobName + " "
                + outfileName;

        output=executeCommand(jobName ,command);
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
                + jobName + " "
                + outfileName;

        output=executeCommand(jobName, command);
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
                + jobName + " "
                + outfileName /*+" "
                + "> /home/vmuser/CPI/results/logfile"*/;
        
        output = executeCommand(jobName, command);
    }

    /**
     * Executes shell command
     * @param command full command to execute
     * @return 
     */
    private String executeCommand(String jobName, String command) {

        Jobhistory newJob;
        newJob = new Jobhistory(jobName, 1, utilityBean.getSelectedProject().getIdprojects(), command);
        jobHistoryFacade.create(newJob);
        
        StringBuffer output = new StringBuffer();

        Process p;
        try {




            /*p = Runtime.getRuntime().exec("export TRANSABYSS=/home/vmuser/CPI/tools/TRANSABYSS");
            p = Runtime.getRuntime().exec("export PATH=$PATH:$TRANSABYSS/blat");
            p = Runtime.getRuntime().exec("export PATH=$PATH:$TRANSABYSS/bowtie2-2.2.4");
            p = Runtime.getRuntime().exec("export PATH=$PATH:$TRANSABYSS/transabyss-master");
            */
            
            //p = Runtime.getRuntime().exec("export PATH=$PATH:/home/vmuser/CPI/tools/TRINITY/jre1.7.0_75/bin:/home/vmuser/CPI/tools/TRINITY/samtools-0.1.19/misc:/home/vmuser/CPI/tools/TRINITY/samtools-0.1.19/bcftools:/home/vmuser/CPI/tools/TRINITY/samtools-0.1.19:/home/vmuser/CPI/tools/TRINITY/bowtie-0.12.9:/home/vmuser/CPI/tools/TRINITY/ncbi-blast-2.2.30+/bin:/usr/lib/lightdm/lightdm:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/home/vmuser/CPI/tools/ncbi-blast-2.2.30+/bin:/home/vmuser/CPI/tools/Preprocessing/FastQC:/home/vmuser/SATA2/PFAM/hmmer-3.1b2:/home/vmuser/CPI/tools/ncbi-blast-2.2.30+:/usr/bin:/bin:/home/lucia/TRINITY/trinityrnaseq-2.0.5/trinity-plugins/rsem-1.2.19:/home/vmuser/CPI/tools/TRANSABYSS/blat:/home/vmuser/CPI/tools/TRANSABYSS/bowtie2-2.2.4:/home/vmuser/CPI/tools/TRANSABYSS/transabyss-master");
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

        System.out.print(output.toString());
        return output.toString();
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
