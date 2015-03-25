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
public class FastQCJob extends AbstractJob {

    public FastQCJob(Tool tool) {
        setTool(tool);
        setExecutableFile(getTool().getPath());
    }

    @Override
    //creates html file in input file location with name input_fastqc.html
    public void execute() {
        String inputFileName = getTool().getInputList().get(0).getValue();
        
        String command = getCommand() + " " + inputFileName + " " + "-o /root/NetBeansProjects/izidev2/web/Output";
	
        String output = executeCommand(command);
        //System.out.println(output);

    }
}
