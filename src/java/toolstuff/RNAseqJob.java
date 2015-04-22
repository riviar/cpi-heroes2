/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Files;
import entitybeans.Filetype;
import entitybeans.Jobhistory;
import entitybeans.Projects;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import sessionbeans.FilesFacade;
import sessionbeans.JobHistoryFacade;
import sessionbeans.ProjectSessionFacade;
import toolstuff.util.Tool;

/**
 * Universal job class. Used to execute job using currently selected tool
 * (UtilityBean).
 *
 * @author Rafal Kural
 */

@Stateful
public class RNAseqJob {

    /**
     * Name of the job
     */
    private String jobName;

    /**
     * Command used to run the job
     */
    private String command;
    private String output;
    //private String email;
    /**
     * Directory for output files
     */
    private String outputDir = "/home/vmuser/CPI/results";
    
    private Process p;
    private long startingTime;
    private Jobhistory updateJob;
    private String[] outputName;

    /**
     * provides access to session bean UtilityBean (here for selectedTool)
     */
    //@ManagedProperty(value = "#{utilityBean}")
    
    @EJB
    ProjectSessionFacade projectFacade;

    @EJB
    private JobHistoryFacade jobHistoryFacade;

    @EJB
    private FilesFacade filesFacade;
    
    Projects selectedProject;
    Tool selectedTool;

    public RNAseqJob() {
    }
    
        
    public void init(Projects selectedProject, Tool selectedTool, String jobName) {
        this.selectedProject = selectedProject;
        this.selectedTool = selectedTool;
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/shell_scripts/";
    }
    
    /*public void init(Projects selectedProject, Tool selectedTool, String email, String jobName) {
        this.selectedProject = selectedProject;
        this.selectedTool = selectedTool;
        this.jobName = jobName;
        this.email = email;
        this.command = "/home/vmuser/CPI/tools/";
    }*/
    
    /*private UtilityBean utilityBean;
    private JobHistoryFacade jobHistoryFacade;
    private FilesFacade filesFacade;
    private ProjectSessionFacade projectFacade;

    public RNAseqJob(String jobName) {
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/";
    }

    /**
     * Creates job object
     * @param utilityBean utility bean with session data
     * @param jobHistoryFacade 
     * @param filesFacade
     * @param projectFacade
     * @param jobName 
     */
    /*public RNAseqJob(UtilityBean utilityBean, JobHistoryFacade jobHistoryFacade, FilesFacade filesFacade, ProjectSessionFacade projectFacade, String jobName) {
        this.jobHistoryFacade = jobHistoryFacade;
        this.utilityBean = utilityBean;
        this.filesFacade = filesFacade;
        this.projectFacade = projectFacade;
        this.jobName = jobName;
        this.command = "/home/vmuser/CPI/tools/";
    }*/

    /**
     * Executes Job based on selected tool.
     */
    //@Asynchronous
    public void execute() {
        appentExecutable();
        switch (selectedTool.getToolEnum()) {
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
            case ABUNDANCE_ESTIMATION:
                executeAbundanceEstimation();
                break;
            case DEG:
                executeDeg();
                break;
            case CLUSTERS:
                executeClusters();
                break;
            case BLAST:
                executeBlast();
                break;
            case HMMER:
                executeHmmer();
                break;
            case MERGE:
                executeMerge();
                break;
            default:
                throw new AssertionError(selectedTool.getToolEnum().name());
        }
    }

    /**
     * Retrieves parameters and executes FastQC job
     */
    private void executeFastQC() {
        String inputFileName = selectedTool.getInputList().get(0).getValue();
        String[] outputName = new String[1];
        outputName[0] = "fastqc";
        //command += " " + inputFileName + " " + "-o "+outputDir+jobName;
        command += " " 
                + inputFileName + " "
                + outputDir;
        
        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Trimmomatic job for trimming using
     * sliding window
     */
    private void executeTrimmomaticTrim() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String windowSize = selectedTool.getParameterList().get(0).getValue();
        String requiredQuality = selectedTool.getParameterList().get(1).getValue();
        String fwPaired=selectedTool.getParameterList().get(2).getValue();
        String fwUnpaired=selectedTool.getParameterList().get(3).getValue();
        String rPaired=selectedTool.getParameterList().get(4).getValue();
        String rUnpaired=selectedTool.getParameterList().get(5).getValue();
        String[] outputName = new String[4];
        outputName[0] = fwPaired;
        outputName[1] = rPaired;
        outputName[2] = fwUnpaired;
        outputName[3] = rUnpaired;
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + windowSize + " "
                + requiredQuality + " "
                + outputDir;

        
        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Trimmomatic job for removing adapters
     */
    private void executeTrimmomaticAdapt() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String adapters = selectedTool.getParameterList().get(0).getValue();
        String seedMismatches = selectedTool.getParameterList().get(1).getValue();
        String palindromeTh = selectedTool.getParameterList().get(2).getValue();
        String simpleTh = selectedTool.getParameterList().get(3).getValue();
        String fwPaired=selectedTool.getParameterList().get(4).getValue();
        String fwUnpaired=selectedTool.getParameterList().get(5).getValue();
        String rPaired=selectedTool.getParameterList().get(6).getValue();
        String rUnpaired=selectedTool.getParameterList().get(7).getValue();
        String[] outputName = new String[4];
        outputName[0] = fwPaired;
        outputName[1] = rPaired;
        outputName[2] = fwUnpaired;
        outputName[3] = rUnpaired;
        
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + adapters + " "
                + seedMismatches + " "
                + palindromeTh + " "
                + simpleTh + " "
                + outputDir;

        
        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Seecer job
     */
    private void executeSeecer() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String kmerCount =  selectedTool.getParameterList().get(0).getValue();
        String leftCorrName =  selectedTool.getParameterList().get(1).getValue();
        String rightCorrName =  selectedTool.getParameterList().get(2).getValue();
        String[] outputName = new String[2];
        outputName[0] = leftCorrName;
        outputName[1] = rightCorrName;
        
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
                + outputDir;


        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Trinity job Be mindful of reversed
     * right/left order!
     */
    private void executeTrinity() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
         
        String seqType = selectedTool.getParameterList().get(0).getValue();
        //String outputDir = "/home/lestelles/Desktop/"; // probably should be changed?
        String outfileName = selectedTool.getParameterList().get(1).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + outputDir;

        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeVelvet() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String seqType = selectedTool.getParameterList().get(0).getValue();
        String kmer = selectedTool.getParameterList().get(1).getValue();
        String insLen = selectedTool.getParameterList().get(2).getValue();
        String outfileName = selectedTool.getParameterList().get(3).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        command += " " 
                + seqType + " "
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + insLen + " "
                + outputDir;

        executeCommand(command, outputName);
    }

    /**
     * Retrieves parameters and executes Velvet job
     */
    private void executeTransabyss() {
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String kmer = selectedTool.getParameterList().get(0).getValue();
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        String outfileName = selectedTool.getParameterList().get(1).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        command += " " 
                + leftInput + " "
                + rightInput + " "
                + kmer + " "
                + outputDir;
                
        executeCommand(command, outputName);
       
    }
    
     private void executeSOAPdenovoTrans(){
        String leftInput = selectedTool.getInputList().get(0).getValue();
        String rightInput = selectedTool.getInputList().get(1).getValue();
        
        String seqType = selectedTool.getParameterList().get(0).getValue();
        String kmer = selectedTool.getParameterList().get(1).getValue();
        String insLen = selectedTool.getParameterList().get(2).getValue();
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        String outfileName = selectedTool.getParameterList().get(3).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        WriteConfig configFile = new WriteConfig(seqType, insLen, leftInput, rightInput);
        try {
            configFile.write();
        } catch (Exception ex) {
            Logger.getLogger(RNAseqJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        command += " " 
                + kmer + " "
                + outputDir;
                
        executeCommand(command, outputName);
    }
    
    /**
     * Retrieves parameters and executes aligns transcripts to an assembly 
     * previously performed using Bowtie and estimates abundance using RSEM.
     */
    private void executeAbundanceEstimation() {
        String fasta = selectedTool.getInputList().get(0).getValue();
        String leftInput = selectedTool.getInputList().get(1).getValue();
        String rightInput = selectedTool.getInputList().get(2).getValue();
        
        String seqType = selectedTool.getParameterList().get(0).getValue();
        String estMethod = selectedTool.getParameterList().get(1).getValue();
        String topGenes = selectedTool.getParameterList().get(2).getValue();
        String prefix = selectedTool.getParameterList().get(3).getValue();
        String prefixforisoforms = prefix.replace(" ", "_");
        String[] outputName = new String[2];
        outputName[0] = prefix;

        //Pass the estimation methon to include it in the description within the database
        outputName[1] = estMethod;
        
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        //String outfileName = selectedTool.getParameterList().get(1).getValue();
        

        command += " " 
                + fasta + " "
                + leftInput + " "
                + rightInput + " "
                + seqType + " "
                + estMethod + " "
                + topGenes + " "
                + prefixforisoforms + " "
                + outputDir;
                
        executeCommand(command, outputName);
       
    }
    
    private void executeDeg() {
       String filesIsoforms = "";
       for (String filepath:selectedTool.getInputList().get(0).getValues()) {
           filesIsoforms = filesIsoforms.concat(filepath + ",");
       }
       //remove last comma
       filesIsoforms = filesIsoforms.substring(0, filesIsoforms.length()-1);

        String estMethod = selectedTool.getParameterList().get(0).getValue();
        String pvalue = selectedTool.getParameterList().get(1).getValue();
        String cFoldChange = selectedTool.getParameterList().get(2).getValue();
        String maxDeg = selectedTool.getParameterList().get(3).getValue();
        String prefix = selectedTool.getParameterList().get(4).getValue();
        String prefixforisoforms = "\""+prefix+"\"";
        String[] outputName = new String[2];
        outputName[0] = prefix;
        outputName[1] = estMethod;
        //String files = selectedTool.getParameterList().get(5).getValue();
       
        
//String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        //String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();
        
        System.out.println(estMethod);
        System.out.println(pvalue);
        System.out.println(cFoldChange);
        System.out.println(maxDeg);
        System.out.println(prefix);
        System.out.println(filesIsoforms);
        
     
         
        command += " " 
                + filesIsoforms + " "
                + estMethod + " "
                + pvalue + " "
                + cFoldChange + " "
                + maxDeg + " "
                + outputDir;
                          
        executeCommand(command, outputName);
       
    }
    private void executeClusters() {
        String RDataFile = selectedTool.getInputList().get(0).getValue();

        String ptree = selectedTool.getParameterList().get(0).getValue();
               
        
        //String outputDir = "/home/vmuser/CPI/results/"; // probably should be changed?
        //String outfileName = getUtilityBean().getSelectedTool().getParameterList().get(1).getValue();

        //The output of Cluster analysis isn't input for any other tool
        String[] outputName = new String[1];
        outputName[0] = jobName + "_output";
        
        command += " " 
                + RDataFile + " "
                + ptree + " "
                + outputDir;
                
        executeCommand(command, outputName);
       
    }
    
    
    private void executeBlast(){
        String query = selectedTool.getInputList().get(0).getValue();
        
        String blastVersion = selectedTool.getParameterList().get(0).getValue();
        System.out.println(blastVersion);
        String database = selectedTool.getParameterList().get(1).getValue();
        System.out.println(database);
        String eValue = selectedTool.getParameterList().get(2).getValue();
        System.out.println(eValue);
        String windowSize = selectedTool.getParameterList().get(3).getValue();
        System.out.println(windowSize);
        String maxHits = selectedTool.getParameterList().get(4).getValue();
        System.out.println(maxHits);
        String outfileName = selectedTool.getParameterList().get(5).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        command += " " 
                + blastVersion + " "
                + query + " "
                + database + " "
                + eValue + " "
                + windowSize + " "
                + maxHits + " "
                + outputDir;
                
        executeCommand(command, outputName);
    }
    
    
    private void executeHmmer(){
        String query = selectedTool.getInputList().get(0).getValue();
        
        String database = selectedTool.getParameterList().get(0).getValue();
        String minProt = selectedTool.getParameterList().get(1).getValue();
        String eValue = selectedTool.getParameterList().get(2).getValue();

        String outfileName = selectedTool.getParameterList().get(3).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
  
        
        command += " " 
                + query + " "
                + database + " "
                + minProt + " "
                + eValue + " "
                + outputDir;
                
        executeCommand(command, outputName);
    }
    
    
    private void executeMerge() {
       String files = "";
       for (String filepath:selectedTool.getInputList().get(0).getValues()) {
           files = files.concat(filepath + ",");
       }
       //remove last comma
       files = files.substring(0, files.length()-1);

        String outfileName = selectedTool.getParameterList().get(0).getValue();
        String[] outputName = new String[1];
        outputName[0] = outfileName;
        
        command += " " 
                + files + " "
                + outputDir;
                
        executeCommand(command, outputName);
       
    }
    
    
    /**
     * Executes shell command
     * @param command full command to execute
     * @return 
     */
    private void executeCommand(String command, String[] outputName) {

        Jobhistory newJob;
        long currentTime = System.currentTimeMillis();
        //newJob = new Jobhistory(jobName, 1, utilityBean.getSelectedProject().getIdprojects(), command, currentTime);
        newJob = new Jobhistory(jobName, 1, selectedProject.getIdprojects(), command, currentTime);
        
        System.out.println(jobHistoryFacade.findAll().size());
        jobHistoryFacade.create(newJob);
        
        
        //Job results are stored in a directory named after the job ID
        String jobID = Integer.toString(newJob.getIdjobs());
        /*String printcommand = command.replace("/home/vmuser/CPI/tools/shell_scripts/", "");
        printcommand = printcommand.replace("/home/vmuser/CPI/datasets/", "");
        printcommand = printcommand.replace("/home/vmuser/CPI/uploads/", "");
        printcommand = printcommand.replace("/home/vmuser/CPI/results", "");
        newJob.setCommandused(printcommand + " " + jobID);*/
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
            java.io.File file = new java.io.File("/home/vmuser/CPI/log/" + newJob.getIdjobs() + ".log");
            java.io.PrintWriter outputfile = new java.io.PrintWriter(file);
            outputfile.println("COMMAND: ");
            outputfile.println(command);
            outputfile.println("##############################################");
            outputfile.println("");
            outputfile.close();
            
            p = pb.start();
            Field f = p.getClass().getDeclaredField("pid");
            f.setAccessible(true);
            int pid = (int) f.get(p);
            newJob.setProcessid(pid);
            
            //Update job with the complete command and the pid
            jobHistoryFacade.edit(newJob);
            
            this.p = p;
            this.startingTime = currentTime;
            this.updateJob = newJob;
            this.outputName = outputName;
            
            //process(p, currentTime, newJob, outputName);
            
            /*StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR", newJob.getIdjobs());

            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT", newJob.getIdjobs());

            // start gobblers
            outputGobbler.start();
            errorGobbler.start();

            p.waitFor();

            //Update the status to finished (0) or error (-1)
            if (p.exitValue() == 0) {
                    //Running time
                newJob.setRunningtime(new java.sql.Time(System.currentTimeMillis()-currentTime-3600000));
                //newJob.setRunningtime(new java.sql.Time(86410000L - 3600000));

                //Normal termination
                newJob.setProcessid(0);

                //Add the output files to the database
                addOutputToDB(newJob, outputName);
            } else {
                //Error
                newJob.setProcessid(-1);

                //Delete the job directory and everything it contains 
                Process removeFiles = Runtime.getRuntime().exec("rm -r /home/vmuser/CPI/results/" + newJob.getIdjobs());
            }*/

            //jobHistoryFacade.edit(newJob);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
}

    
    @Asynchronous
    //public void process(Process p, long startingTime, Jobhistory updateJob, String[] outputName){
    public void process(){
        try {
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR", updateJob.getIdjobs());
            
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT", updateJob.getIdjobs());

            // start gobblers
            outputGobbler.start();
            errorGobbler.start();

            p.waitFor();

            //Update the status to finished (0) or error (-1)
            if (p.exitValue() == 0) {
                //Running time
                updateJob.setRunningtime(new java.sql.Time(System.currentTimeMillis()-startingTime-3600000));
                //updateJob.setRunningtime(new java.sql.Time(86410000L - 3600000));

                //Normal termination
                updateJob.setProcessid(0);

                //Add the output files to the database
                addOutputToDB(updateJob, outputName);
            } else {
                //Error
                updateJob.setProcessid(-1);
                
                //Delete the job directory and everything it contains 
                Process removeFiles = Runtime.getRuntime().exec("rm -r /home/vmuser/CPI/results/" + updateJob.getIdjobs());
            }
            jobHistoryFacade.edit(updateJob);
        } catch (InterruptedException ex) {
            Logger.getLogger(RNAseqJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RNAseqJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addOutputToDB(Jobhistory updateJob, String[] outputName){
        Files output1 = new Files();
        Files output2 = new Files();
        Files output3 = new Files();
        Files output4 = new Files();
        
        //Projects updateProject = project;
        Collection<Files> projectFiles = selectedProject.getFilesCollection();
        
        
        ArrayList<Projects> fileProject = new ArrayList(1);
        System.out.println("Project " + selectedProject.getProjectname());
        fileProject.add(selectedProject);
        
        switch (selectedTool.getToolEnum()) {
            case FASTQC:
                //HTML
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fastqc.html");
                output1.setDisplayname(outputName[0] + " (HTML)");
                output1.setDescription("FastQC output for " + updateJob.getJobname());
                //FastQC output HTML report filetype (2)
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                
                //Add output to database
                filesFacade.create(output1);
                break;
            case TRIMMOMATIC_TRIM:
                //PAIRED FORWARD
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_paired");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Trimmed forward paired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Trimmed file filetype (3)
                output1.setFiletype(new Filetype(3));
                output1.setProjectsCollection(fileProject);
                //PAIRED REVERSE
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_paired");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Trimmed reverse paired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Trimmed file filetype (3)
                output2.setFiletype(new Filetype(3));
                output2.setProjectsCollection(fileProject);
                //HTML with the FastQC quality report images before and after trimming
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/quality_comparison.html");
                output3.setDisplayname(updateJob.getJobname() + "(Quality comparison)");
                output3.setDescription("Quality per base report before and after the trimming from " + updateJob.getJobname() + " processed with Trimmomatic.");
                output3.setFiletype(new Filetype(3));
                output3.setProjectsCollection(fileProject);

                //UNPAIRED FORWARD
                /*output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_unpaired");
                output3.setDisplayname(outputName[2]);
                output3.setDescription("Trimmed forward unpaired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Trimmed file filetype (1)
                output3.setFiletype(new Filetype(1));
                output3.setProjectsCollection(fileProject);
                //UNPAIRED REVERSE
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_unpaired");
                output4.setDisplayname(outputName[3]);
                output4.setDescription("Trimmed reverse unpaired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Trimmed file filetype (1)
                output4.setFiletype(new Filetype(1));
                output4.setProjectsCollection(fileProject);*/
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                /*projectFiles.add(output4);*/
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to files table
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                /*filesFacade.create(output4);*/
                break;
            case TRIMMOMATIC_ADAPT:
                //PAIRED FORWARD
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_paired");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Adapters removed from the forward paired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //File without adapters filetype (4)
                output1.setFiletype(new Filetype(4));
                output1.setProjectsCollection(fileProject);
                //PAIRED REVERSE
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_paired");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Adapters removed from the reverse paired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //File without adapters filetype (4)
                output2.setFiletype(new Filetype(4));
                output2.setProjectsCollection(fileProject);
                //HTML with the FastQC quality report images before and after removing adapters
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/quality_comparison.html");
                output3.setDisplayname(outputName[2] + "_quality_comparison.html");
                output3.setDescription("Quality per base report before and after removing adapters from " + updateJob.getJobname() + " processed with Trimmomatic.");
                output3.setFiletype(new Filetype(2));
                output3.setProjectsCollection(fileProject);
                
                //UNPAIRED FORWARD
                /*output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_unpaired");
                output3.setDisplayname(outputName[2]);
                output3.setDescription("Adapters removed from the forward unpaired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //File without adapters filetype (2)
                output3.setFiletype(new Filetype(2));
                output3.setProjectsCollection(fileProject);
                //UNPAIRED REVERSE
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_unpaired");
                output4.setDisplayname(outputName[3]);
                output4.setDescription("Adapters removed from the reverse unpaired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //File without adapters filetype (2)
                output4.setFiletype(new Filetype(2));
                output4.setProjectsCollection(fileProject);*/
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                /*projectFiles.add(output4);*/
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                /*filesFacade.create(output4);*/
                break;
            case SEECER:
                //PAIRED 1
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/leftCorrected.fa");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Trimmed left reads from " + updateJob.getJobname() + " processed with SEECER.");
                //Trimmed file filetype (1)
                output1.setFiletype(new Filetype(22));
                output1.setProjectsCollection(fileProject);
                //PAIRED 2
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/rightCorrected.fa");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Trimmed right reads from " + updateJob.getJobname() + " processed with SEECER.");
                //Trimmed file filetype (1)
                output2.setFiletype(new Filetype(22));
                output2.setProjectsCollection(fileProject);
                //HTML with the FastQC quality report images before and after preprocessing
                /*output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/quality_comparison.html");
                output3.setDisplayname(outputName[2] + "_quality_comparison.html");
                output3.setDescription("Quality per base report before and after the trimming from " + updateJob.getJobname() + " processed with SEECER.");
                output3.setFiletype(new Filetype(3));
                output3.setProjectsCollection(fileProject);*/
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                //projectFiles.add(output3);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                              
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                //filesFacade.create(output3);
                break;
            case TRINITY:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0] + " (transcripts)");
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Trinity.");
                //Assembled transcripts filetype (5)
                output1.setFiletype(new Filetype(5));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[0] + " stats");
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Trinity.");
                //Assembly statistics filetype (6)
                output2.setFiletype(new Filetype(6));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                               
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case VELVET:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0] + " (transcripts)");
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Velvet.");
                //Assembled transcripts filetype (5)
                output1.setFiletype(new Filetype(5));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");

                output2.setDisplayname(outputName[0] + " stats");
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Velvet.");
                //Assembly statistics filetype (6)
                output2.setFiletype(new Filetype(6));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case TRANSABYSS:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0] + " (transcripts)");
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Trans-ABySS.");
                //Assembled transcripts filetype (5)
                output1.setFiletype(new Filetype(5));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[0] + " stats");
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Trans-ABySS.");
                //Assembly statistics filetype (6)
                output2.setFiletype(new Filetype(6));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case SOAPdenovo_Trans:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0] + " (transcripts)");
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with SOAPdenovo-Trans.");
                //Assembled transcripts filetype (5)
                output1.setFiletype(new Filetype(5));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[0] + " stats");
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with SOAPdenovo-Trans.");
                //Assembly statistics filetype (6)
                output2.setFiletype(new Filetype(6));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case ABUNDANCE_ESTIMATION:

                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/isoforms.results");
                output1.setDisplayname(outputName[0] + " (Ab Est results)");
                output1.setDescription("Abundance estimation of the isoforms from " + updateJob.getJobname() + " using " + outputName[1]);
                output1.setFiletype(new Filetype(8));

                output1.setProjectsCollection(fileProject);
                
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/abundance_estimation.pdf");
                output2.setDisplayname(outputName[0] + " PDF report");
                output2.setDescription("Abundance estimation report from " + updateJob.getJobname() + " using " + outputName[1]);
                output2.setFiletype(new Filetype(7));
                output2.setProjectsCollection(fileProject);
                
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/top_expressed.fa");
                output3.setDisplayname(outputName[0] + " (Top expressed genes)");
                output3.setDescription("Top expressed gene list from " + updateJob.getJobname() + " using " + outputName[1]);
                output3.setFiletype(new Filetype(24));//FASTA FILE
                output3.setProjectsCollection(fileProject);
                
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                break;
            case DEG:
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/DEG.RData");
                output1.setDisplayname(outputName[0] + " (DGE output)");
                output1.setDescription("Differential Gene Expression output from " + updateJob.getJobname() + " using " + outputName[1] + " to use in Cluster Analysis.");
                output1.setFiletype(new Filetype(10));
                output1.setProjectsCollection(fileProject);
                
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/DEG.pdf");
                output2.setDisplayname(outputName[0] + " DGE pdf report");
                output2.setDescription("Differential Gene Expression report from " + updateJob.getJobname() + " using " + outputName[1]);
                output2.setFiletype(new Filetype(9));
                output2.setProjectsCollection(fileProject);
                
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/metadata.zip");
                output3.setDisplayname(outputName[0] + " (DGE metadata)");
                output3.setDescription("Differential Gene Expression compressed metadata from " + updateJob.getJobname() + " using " + outputName[1]);
                output3.setFiletype(new Filetype(9));
                output3.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                break;                
            case CLUSTERS: 
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/clusters.pdf");
                output1.setDisplayname(outputName[0] + " clustering report");
                output1.setDescription("Clustering report from " + updateJob.getJobname());
                output1.setFiletype(new Filetype(11));
                output1.setProjectsCollection(fileProject);
                
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/metadata.zip");
                output2.setDisplayname(outputName[0] + " (Clustering metadata)");
                output2.setDescription("Clustering compressed metadata from " + updateJob.getJobname());
                output2.setFiletype(new Filetype(11));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case BLAST:
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/blast_results.xml");
                output1.setDisplayname(outputName[0] + " (BLAST XML file)");
                output1.setDescription("BLAST output in Extended Markup Language (XML) format from " + updateJob.getJobname());
                output1.setFiletype(new Filetype(12));
                output1.setProjectsCollection(fileProject);
                
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/blast_results.tab");
                output2.setDisplayname(outputName[0] + " (BLAST tab separated file)");
                output2.setDescription("BLAST output in tab separated format from " + updateJob.getJobname());
                output2.setFiletype(new Filetype(13));
                output2.setProjectsCollection(fileProject);
                
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/blast_results.esn");
                output3.setDisplayname(outputName[0] + " (BLAST ASN file)");
                output3.setDescription("BLAST output in canonical ESN format from " + updateJob.getJobname());
                output3.setFiletype(new Filetype(13));
                output3.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                break;
            case HMMER:
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/longest_orfs.pep");
                output1.setDisplayname(outputName[0] + " (ORFs for HMMER)");
                output1.setDescription("Open Reading Frames used by HMMER from " + updateJob.getJobname());
                output1.setFiletype(new Filetype(14));
                output1.setProjectsCollection(fileProject);
                
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/output_hmmer.txt");
                output2.setDisplayname(outputName[0] + " (HMMER output)");
                output2.setDescription("Protein domains identified by HMMER from " + updateJob.getJobname());
                output2.setFiletype(new Filetype(14));
                output2.setProjectsCollection(fileProject);
                
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/per_domain_hits_hmmer.txt");
                output3.setDisplayname(outputName[0] + " (HMMER hits per domain)");
                output3.setDescription("HMMER hits per domain from " + updateJob.getJobname());
                output3.setFiletype(new Filetype(14));
                output3.setProjectsCollection(fileProject);
                
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/per_sequence_hits_hmmer.txt");
                output4.setDisplayname(outputName[0] + " (HMMER hits per sequence)");
                output4.setDescription("HMMER hits per sequence from " + updateJob.getJobname());
                output4.setFiletype(new Filetype(14));
                output4.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                projectFiles.add(output4);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                filesFacade.create(output4);
                break;
            case MERGE:
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/longest_orfs.pep");
                output1.setDisplayname(outputName[0] + " (ORFs for HMMER)");
                output1.setDescription("Open Reading Frames used by HMMER from " + updateJob.getJobname());
                output1.setFiletype(new Filetype(23));
                output1.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                selectedProject.setFilesCollection(projectFiles);
                projectFacade.edit(selectedProject);
                                
                //Add outputs to database
                filesFacade.create(output1);                              
                break;
            default:
                throw new AssertionError(selectedTool.getToolEnum().name());
        }
}
    
    /**
     * Appends path to executable file to the command
     */
    private void appentExecutable() {
        System.out.println(selectedTool.getName());
        command += selectedTool.getPath();
    }

    /**
     * @return the utilityBean
     */
    /*public UtilityBean getUtilityBean() {
        return utilityBean;
    }*/

    /**
     * @param utilityBean the utilityBean to set
     */
    /*public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }*/

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

 
   
    
   
    
    
    
    
    
    
