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
public class FastQCJob extends AbstractJob {

    public FastQCJob(String inputPath) {
        getParameters().put("input", inputPath);
        setExecutableFile("Preprocessing/FastQC/fastqc");
    }

    @Override
    //creates html file in input file location with name input_fastqc.html
    public void execute() {

        String filename = getParameters().get("input");
        
        String command = getCommand() + " " + filename + " " + "-o /root/NetBeansProjects/cpi-heroes2izi/web/Output";
	
        String output = executeCommand(command);

        //System.out.println(output);

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
