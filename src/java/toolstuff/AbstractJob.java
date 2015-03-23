/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.util.HashMap;

/**
 *
 * @author Fox
 */
public abstract class AbstractJob {
    
    private String command = "/home/vmuser/CPI/tools/";
    private HashMap<String, String> parameters = new HashMap();
    private String output;
    
    public void execute(){};
    
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setExecutableFile(String filename) {
        command += filename;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }   
    
}
