/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import sessionbeans.JobHistoryFacade;

/**
 *
 * @author pitas
 */
public class jobThread extends Thread {

    private Process p;
    private Jobhistory updateJob;
    private JobHistoryFacade jobHistoryFacade;
    
    public jobThread(String string) {
        super(string);
    }
    
    
    public void run(){
       System.out.println("Name: " + getName());
       StringBuffer output = new StringBuffer();
              
       if(getName().equals("waitThread")){
            try {
                               
                System.out.println("Waiting");
                //p.waitFor();
                StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");

                StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

                // start gobblers
                outputGobbler.start();
                errorGobbler.start();
                
                Thread.sleep(5000);

                //Update the status to finished
                //jobHistoryFacade.updateJob(newJob);
                /*BufferedReader reader
                        = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    output.append(line + "\n");
                }*/
                
                updateJob.setProcessid(-1);
                jobHistoryFacade.edit(updateJob);
                //JOptionPane.showMessageDialog(null, "Job Finished", "updateJob.getJobname() + \"finished\"", JOptionPane.ERROR_MESSAGE); 
            }catch (Exception e) {
               e.printStackTrace();
           }
            System.out.println(output.toString());
           
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
}
