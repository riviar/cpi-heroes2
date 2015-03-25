/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package toolstuff;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import javax.faces.bean.ManagedProperty;
import managedbeans.UtilityBean;
import toolstuff.util.Tool;

/**
 * Outdated and marked for deletion
 * @author Fox
 */
public abstract class AbstractJob {
    
    //path to directory with tools
    private String jobName = "{}";
    private String command = "/home/vmuser/CPI/tools/";
    private Tool tool;
    private String output;
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    public void execute(){};

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }   

    public void setExecutableFile(String filename) {
        command += filename;
    }

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }
    
    public String executeCommand(String command) {

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
