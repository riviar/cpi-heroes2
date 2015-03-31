/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import outputview.EOutputType;
import outputview.GenericOutput;
import toolstuff.util.ETool;
import toolstuff.util.EToolType;
import toolstuff.util.Tool;
import toolstuff.util.ToolsBase;

/**
 * Managed bean for managing tool output page
 * @author Fox
 */
@ManagedBean
@RequestScoped
public class DataItemBean {
    
    private Jobhistory job;
    private ETool jobsTool;
    private List<GenericOutput> outputsList;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    @ManagedProperty(value = "#{param.output}")
    private String outputName;

    public UtilityBean getUtilityBean() {
        return utilityBean;
    }

    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public List<GenericOutput> getOutputsList() {
        return outputsList;
    }

    public void setOutputsList(List<GenericOutput> outputsList) {
        this.outputsList = outputsList;
    }
    
    

    /**
     * Creates a new instance of DataItemBean
     */
    public DataItemBean() {
        //test job can be assigned here, but i have no jobs on my machine
        detectTool();
        outputsList = getJobOutputFiles();
        //check for and assign ETool, check if job.path contains tool path
    }
    
    /**
     * Detects which tool was used to generate this jobhistory
     */
    private void detectTool() {
        String command = utilityBean.getSelectedJob().getCommandused();
        ToolsBase toolsBase = new ToolsBase();
        for(Tool tool:toolsBase.getAllTools()) {
            if (command.contains(tool.getPath())) {
                jobsTool = tool.getToolEnum();
                return;
            }
        }
    }
    
    /**
     * Retrieves list of files that are outputs of this job (intention: put them as links to their specific pages)
     * still not sure what exactly should be retrieved: paths, names, etc
     * @return 
     */
    private List<GenericOutput> getJobOutputFiles() {
        outputsList = new ArrayList();
        
        switch (jobsTool) {
            case FASTQC:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;

            case TRIMMOMATIC_TRIM:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
                
            case TRIMMOMATIC_ADAPT:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
                
            case SEECER:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
                
            case TRINITY:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
                
            case VELVET:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
                
            case TRANSABYSS:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "Path to html", jobsTool));
                outputsList.add(new GenericOutput("World Domination Tactics", EOutputType.MAGIC, "Path to World Domination", jobsTool));
                break;
            
        }
        return outputsList;
    }
    
    /**
     * Generates page center code for data_item page according to outputFile type
     * Dragon's den method. Do not disturb. Do not sing. Danger of burns
     * @return 
     */
    public String generateDataItemPage() {
        //now i think it could be enough to only switch on types, can be wrong though

        switch (utilityBean.getSelectedOutput().getOutputType()) {
            case HTML:
                //temporary only static fastqc method
            return displayFastQC();
            case CSV:
                return displayCSVOutput();
            case MAGIC:
                return "'tis be magics. No common folk should be able to see that message";
            default:
                throw new AssertionError(utilityBean.getSelectedOutput().getOutputType().name());
            
        }
    }
    
    /**
     * Returns html code to generate fastqc data item page
     * @return 
     */
    private String displayFastQC() {
        String jobid = Integer.toString(utilityBean.getSelectedJob().getIdjobs());
        String path = "/home/vmuser/CPI/results/" + jobid + "fastqc.html";
        return "<iframe id=\"frame\" src=\"" + path + "\" frameborder=\"0\" width=\"100%\" height=\"750px\"></iframe>";
    }
    
    /**
     * Returns html code to generate data item page from csv type file
     * @return 
     */
    private String displayCSVOutput() {
        return "Nothing to show now";
    }
    
    /**
     * Sets selevted output in utility bean and redirects to data_item page, redirects back to job_output if output was not found
     * @return 
     */
    public String selectOutputFile() {
        for (GenericOutput output:outputsList) {
            if (output.getName().equals(outputName)) {
                utilityBean.setSelectedOutput(output);
                return "data_item";
            }
        }
        return "job_output";
    }
    
}
