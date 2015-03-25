/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import toolstuff.util.Tool;

/**
 *
 * @author lestelles
 */
public class TrimmomaticJob extends AbstractJob {
    
    public TrimmomaticJob(Tool tool){ 
        //    , String fastaWithAdaptersEtc, String seedMismatches, String palindromeClipThreshold, String simpleClipThreshold) {
        System.out.println("***********************************************************************");
        
        setTool(tool);
        setExecutableFile(tool.getPath());  
    }

    
    @Override
    public void execute(){
                       
        System.out.println(getParameters().get("input1"));
        System.out.println(getParameters().get("input2"));
        System.out.println(getParameters().get("windowSize"));
        System.out.println(getParameters().get("requiredQuality"));
        String command = "java -jar "+ getCommand()+ " PE " 
                + getParameters().get("input1")+ " "
                + getParameters().get("input2")
                + " paired_1 unpaired_1 paired_2 unpaired_2"
                + " SLIDINGWINDOW:"
                + getParameters().get("windowSize")+":"
                + getParameters().get("requiredQuality");
        System.out.println(command);
        
        //~/glassfish-4.1/glassfish/domains/domain1/config
        
        String output=executeCommand(getParameters().get("jobName"), command);
        
    }
    

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
}
