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
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import javax.swing.JOptionPane;
import managedbeans.UtilityBean;
import sessionbeans.FilesFacade;
import sessionbeans.JobHistoryFacade;
import sessionbeans.ProjectSessionFacade;
import toolstuff.util.ETool;
import java.sql.Date;
import java.sql.Time;
import utils.FileEditor;

/**
 *
 * @author pitas
 */
public class jobThread extends Thread {

    private Process p;
    private String[] outputName; 
    private Jobhistory updateJob;
    private Projects project;
    private long currentTime;
    private ETool toolEnum;
    private JobHistoryFacade jobHistoryFacade;
    private FilesFacade filesFacade;
    private ProjectSessionFacade projectFacade;
           
    public jobThread(String string) {
        super(string);        
    }
    
    
    public void run(){
       System.out.println("Name: " + getName());
                     
       if(getName().equals("waitThread")){
            try {
                               
                System.out.println("Waiting");
                
                StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");

                StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

                // start gobblers
                outputGobbler.start();
                errorGobbler.start();
                
                p.waitFor();

                //Update the status to finished (0) or error (-1)
                if(p.exitValue()==0){
                    //Running time
                    updateJob.setRunningtime(new java.sql.Time(System.currentTimeMillis()-currentTime-3600000));
                    //updateJob.setRunningtime(new java.sql.Time(90000000000L-3600000));
                                        
                    //Normal termination
                    updateJob.setProcessid(0);
                    
                    //Add the output files to the database
                   // addOutputToDB();
                }else{
                    //Error
                    updateJob.setProcessid(-1);
                    
                    //Delete the job directory and everything it contains 
                    Process removeFiles = Runtime.getRuntime().exec("rm -r /home/vmuser/CPI/results/"+ updateJob.getIdjobs());
                }
                
                jobHistoryFacade.edit(updateJob);
                //JOptionPane.showMessageDialog(null, "Job Finished", "updateJob.getJobname() + \"finished\"", JOptionPane.ERROR_MESSAGE); 
            }catch (Exception e) {
               e.printStackTrace();
           }
                       
       }
    }

    public void addOutputToDB(){
        Files output1 = new Files();
        Files output2 = new Files();
        Files output3 = new Files();
        Files output4 = new Files();
        
        //Projects updateProject = project;
        Collection<Files> projectFiles = project.getFilesCollection();
        
        
        ArrayList<Projects> fileProject = new ArrayList(1);
        System.out.println("Project " + project.getProjectname());
        fileProject.add(project);
        
        switch (toolEnum) {
            case FASTQC:
                //HTML
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fastqc.html");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("FastQC output for " + updateJob.getJobname());
                //Add a mock filetype. In the future it has to mean HTML
                output1.setFiletype(new Filetype(1));
                output1.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                
                //Add output to database
                filesFacade.create(output1);
                FileEditor.editFastQCHTML("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fastqc.html");
                break;
            case TRIMMOMATIC_TRIM:
                //PAIRED FORWARD
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_paired");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Trimmed forward paired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //PAIRED REVERSE
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_paired");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Trimmed reverse paired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                //UNPAIRED FORWARD
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_unpaired");
                output3.setDisplayname(outputName[2]);
                output3.setDescription("Trimmed forward unpaired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output3.setFiletype(new Filetype(2));
                output3.setProjectsCollection(fileProject);
                //UNPAIRED REVERSE
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_unpaired");
                output4.setDisplayname(outputName[3]);
                output4.setDescription("Trimmed reverse unpaired reads from " + updateJob.getJobname() + " processed Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output4.setFiletype(new Filetype(2));
                output4.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                projectFiles.add(output4);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                                
                //Add outputs to files table
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                filesFacade.create(output4);
                break;
            case TRIMMOMATIC_ADAPT:
                //PAIRED FORWARD
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_paired");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Adapters removed from the forward paired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output1.setFiletype(new Filetype(3));
                output1.setProjectsCollection(fileProject);
                //PAIRED REVERSE
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_paired");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Adapters removed from the reverse paired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output2.setFiletype(new Filetype(3));
                output2.setProjectsCollection(fileProject);
                //UNPAIRED FORWARD
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/fw_unpaired");
                output3.setDisplayname(outputName[2]);
                output3.setDescription("Adapters removed from the forward unpaired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output3.setFiletype(new Filetype(3));
                output3.setProjectsCollection(fileProject);
                //UNPAIRED REVERSE
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/r_unpaired");
                output4.setDisplayname(outputName[3]);
                output4.setDescription("Adapters removed from the reverse unpaired reads from " + updateJob.getJobname() + " processed with Trimmomatic.");
                //Add a mock filetype. In the future it has to mean FASTA or FASTQ (The same as input type)
                output4.setFiletype(new Filetype(3));
                output4.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                projectFiles.add(output3);
                projectFiles.add(output4);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                filesFacade.create(output3);
                filesFacade.create(output4);
                break;
            case SEECER:
                //PAIRED 1
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/leftCorrected.fa");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Trimmed left reads from " + updateJob.getJobname() + " processed with SEECER.");
                //Add a mock filetype. In the future it has to mean FASTA
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //PAIRED 2
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/rightCorrected.fa");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Trimmed right reads from " + updateJob.getJobname() + " processed with SEECER.");
                //Add a mock filetype. In the future it has to mean FASTA
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                              
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case TRINITY:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Trinity.");
                //Add a mock filetype. In the future it has to mean FASTA
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Trinity.");
                //Add a mock filetype. In the future it has to mean FASTA
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                               
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case VELVET:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Velvet.");
                //Add a mock filetype. In the future it has to mean FASTA
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Velvet.");
                //Add a mock filetype. In the future it has to mean FASTA
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case TRANSABYSS:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with Trans-ABySS.");
                //Add a mock filetype. In the future it has to mean FASTA
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[1]);
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with Trans-ABySS.");
                //Add a mock filetype. In the future it has to mean FASTA
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case SOAPdenovo_Trans:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/transcripts.fa");
                output1.setDisplayname(outputName[0] + " transcripts");
                output1.setDescription("Transcripts from " + updateJob.getJobname() + " processed with SOAPdenovo-Trans.");
                //Add a mock filetype. In the future it has to mean FASTA
                output1.setFiletype(new Filetype(2));
                output1.setProjectsCollection(fileProject);
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/stats.txt");
                output2.setDisplayname(outputName[0] + " stats");
                output2.setDescription("Assembly statistics from " + updateJob.getJobname() + " processed with SOAPdenovo-Trans.");
                //Add a mock filetype. In the future it has to mean FASTA
                output2.setFiletype(new Filetype(2));
                output2.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                projectFiles.add(output2);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                
                //Add outputs to database
                filesFacade.create(output1);
                filesFacade.create(output2);
                break;
            case ABUNDANCE_ESTIMATION:
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "/" + outputName[0] + ".isoforms.results");
                output1.setDisplayname(outputName[0]);
                output1.setDescription("Abundance sttimation output from " + updateJob.getJobname() + " using RSEM.");
                //Add a mock filetype. In the future it has to mean TAB
                output1.setFiletype(new Filetype(4));
                output1.setProjectsCollection(fileProject);
                
                //Add output files to project table
                projectFiles.add(output1);
                project.setFilesCollection(projectFiles);
                projectFacade.edit(project);
                                
                //Add outputs to database
                filesFacade.create(output1);
                break;
            case DEG:
                break;
            case CLUSTERS:
                break;
            default:
                throw new AssertionError(toolEnum.name());
        }
}
    
   
    /**
     * @return the p
     */
    public Process getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(Process p) {
        this.p = p;
    }

    /**
     * @return the updateJob
     */
    public Jobhistory getUpdateJob() {
        return updateJob;
    }

    /**
     * @param updateJob the updateJob to set
     */
    public void setUpdateJob(Jobhistory updateJob) {
        this.updateJob = updateJob;
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
    
    public ETool getToolEnum() {
        return toolEnum;
    }

    public void setToolEnum(ETool toolEnum) {
        this.toolEnum = toolEnum;
    }

    /**
     * @return the outputName
     */
    public String[] getOutputName() {
        return outputName;
    }

    /**
     * @param outputName the outputName to set
     */
    public void setOutputName(String[] outputName) {
        this.outputName = outputName;
    }

    /**
     * @return the project
     */
    public Projects getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Projects project) {
        this.project = project;
    }

    /**
     * @return the filesFacade
     */
    public FilesFacade getFilesFacade() {
        return filesFacade;
    }

    /**
     * @param filesFacade the filesFacade to set
     */
    public void setFilesFacade(FilesFacade filesFacade) {
        this.filesFacade = filesFacade;
    }

    /**
     * @return the projectFacade
     */
    public ProjectSessionFacade getProjectFacade() {
        return projectFacade;
    }

    /**
     * @param projectFacade the projectFacade to set
     */
    public void setProjectFacade(ProjectSessionFacade projectFacade) {
        this.projectFacade = projectFacade;
    }

    /**
     * @return the currentTime
     */
    public long getCurrentTime() {
        return currentTime;
    }

    /**
     * @param currentTime the currentTime to set
     */
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;        
    }
}
