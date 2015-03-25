/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import toolstuff.util.Tool;

/**
 *
 * @author Fox
 */
public class SeecerJob extends AbstractJob {

    public SeecerJob(Tool tool) {
        setTool(tool);
        setExecutableFile(tool.getPath());
    }

    @Override
    //creates html file in input file location with name input_fastqc.html
    public void execute() {

        String filename1 = getTool().getInputList().get(0).getValue();
        String filename2 = getTool().getInputList().get(1).getValue();
        String KmerCount = getTool().getParameterList().get(0).getValue();
        
        String tmpPath = "/home/vmuser/CPI/tools/Seecer/testdata/tmp";
        //String jellyfishPath = "/home/vmuser/CPI/tools/Preprocessing/Seecer/Seecer/jellyfish-1.1.11/bin";
        
        
        String command = getCommand() +" -t "+ tmpPath + " -k " + KmerCount +" "+ filename1 + " " + filename2;
	
        String output = executeCommand(command);
        
        

        System.out.println("**************************");
        System.out.println(command);

    }

}
