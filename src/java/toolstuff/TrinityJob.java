/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author lestelles
 */
public class TrinityJob extends AbstractJob {
    
    public TrinityJob(String seqType, String leftReads, String rightReads, String jobid){ 
        System.out.println("***********************************************************************");
        
        getParameters().put("seqType", seqType);
        getParameters().put("leftReads", leftReads);
        getParameters().put("rightReads", rightReads);
        getParameters().put("outdir", "/home/lestelles/Desktop/");
        getParameters().put("jobid", jobid);
        
        
        setExecutableFile("/home/lestelles/Desktop/do_trinity.sh");
    
    }
        
    @Override
    public void execute(){

        String command = getCommand()+ " " 
                + getParameters().get("seqType")+ " "
                + getParameters().get("leftReads") + " "
                + getParameters().get("rightReads")+ " "
                + getParameters().get("outdir")+" "
                + getParameters().get("jobid");
        System.out.println(command);

        //~/glassfish-4.1/glassfish/domains/domain1/config
        String output=executeCommand(command);

    }


    private String executeCommand(String command) {

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
