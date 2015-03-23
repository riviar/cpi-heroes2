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
 * @author Fox
 */
public class SeecerJob extends AbstractJob {

    public SeecerJob(String inputPath1, String inputPath2) {
        getParameters().put("input1", inputPath1);
        getParameters().put("input2", inputPath2);
        setExecutableFile("/home/vmuser/CPI/tools/Preprocessing/Seecer/Seecer/SEECER/bin/run_seecer.sh");
    }

    @Override
    //creates html file in input file location with name input_fastqc.html
    public void execute() {

        String filename1 = getParameters().get("input1");
        String filename2 = getParameters().get("input2");
        
        String tmpPath = "/home/vmuser/CPI/tools/Seecer/testdata/tmp";
        String jellyfishPath = "/home/vmuser/CPI/tools/Preprocessing/Seecer/Seecer/jellyfish-1.1.11/bin";
        
        String command = getCommand() + " "+"-t "+ tmpPath+" "+"-j " +jellyfishPath+" " + filename1 + "" + filename2;
	
        String output = executeCommand(command);
        
        

        System.out.println("**************************");
        System.out.println(command);

    }

    private String executeCommand(String command) {

        StringBuffer output = new StringBuffer();
        
        //output directory -o /root/NetBeansProjects/cpi-heroes2izi/web/Output


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
