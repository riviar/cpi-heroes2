/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Files;
import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import javax.swing.JOptionPane;
import managedbeans.UtilityBean;
import sessionbeans.JobHistoryFacade;
import toolstuff.util.ETool;

/**
 *
 * @author pitas
 */
public class jobThread extends Thread {

    private Process p;
    private Jobhistory updateJob;
    private ETool toolEnum;
    private JobHistoryFacade jobHistoryFacade;
    
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
                    //Normal termination
                    updateJob.setProcessid(0);
                    
                    //Add the output files to the database
                    addOutputToDB();
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
        
        switch (toolEnum) {
            case FASTQC:
                //HTML
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs());                
                break;
            case TRIMMOMATIC_TRIM:
                //PAIRED 1
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs());
                //PAIRED 2
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs());
                //UNPAIRED 1
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs());
                //UNPAIRED 2
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs());
                break;
            case TRIMMOMATIC_ADAPT:
                //PAIRED 1
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //PAIRED 2
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //UNPAIRED 1
                output3.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //UNPAIRED 2
                output4.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME"); 
                break;
            case SEECER:
                //PAIRED 1
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //PAIRED 2
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                break;
            case TRINITY:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                break;
            case VELVET:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                break;
            case TRANSABYSS:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                break;
            case SOAPdenovo_Trans:
                //TRANSCRIPTS
                output1.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                //STATISTICS
                output2.setPath("/home/vmuser/CPI/results/" + updateJob.getIdjobs() + "PREDEFINEDNAME");
                break;
            /*case ABUNDANCE_ESTIMATION:
                
                break;*/
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
}
