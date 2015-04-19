/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import outputview.EOutputType;
import outputview.GenericOutput;
import utils.FileReader;
import toolstuff.util.ETool;
import toolstuff.util.EToolType;
import toolstuff.util.Tool;
import toolstuff.util.ToolsBase;
import toolstuff.util.ToolsBaseFactory;
import utils.FileEditor;

/**
 * Managed bean for managing tool output page
 *
 * @author Rafal Kural
 * @version 1.0
 */
@ManagedBean
@RequestScoped
public class DataItemBean {

    private Jobhistory job;
    /**
     * Tool enum of selected workflow history item
     */
    private ETool jobsTool;
    /**
     * List of outputs produced from chosen workflow history item
     */
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
        ///detectTool();
        //outputsList = getJobOutputFiles();
        //check for and assign ETool, check if job.path contains tool path
    }

    /**
     * Detects which tool was used to generate this jobhistory
     */
    private void detectTool() {
        System.out.println("Detecting tool for " + utilityBean.getSelectedJob().getJobname());
        String command = utilityBean.getSelectedJob().getCommandused();
        ToolsBase toolsBase = ToolsBaseFactory.initializeToolsBase();
        for (Tool tool : toolsBase.getAllTools()) {
            System.out.println("Comparing " + command + " with " + tool.getPath());
            if (command.contains(tool.getPath())) {
                jobsTool = tool.getToolEnum();
                return;
            }
        }
    }

    /**
     * Retrieves list of files that are outputs of this job (intention: put them
     * as links to their specific pages) still not sure what exactly should be
     * retrieved: paths, names, etc
     *
     * @return
     */
    public List<GenericOutput> getJobOutputFiles() {
        detectTool();
        outputsList = new ArrayList();
        String jobid = Integer.toString(utilityBean.getSelectedJob().getIdjobs());

        outputsList.add(new GenericOutput("Log", EOutputType.TXT, "/home/vmuser/CPI/log/" + jobid + ".log", jobsTool));
        switch (jobsTool) {
            case FASTQC:
                outputsList.add(new GenericOutput("FastQC Report", EOutputType.HTML, "../results/" + jobid + "/fastqc.html", jobsTool));
                break;

            case TRIMMOMATIC_TRIM:
                outputsList.add(new GenericOutput("Forward paired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/fw_paired", jobsTool));
                outputsList.add(new GenericOutput("Forward unpaired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/fw_unpaired", jobsTool));
                outputsList.add(new GenericOutput("Reverse paired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/r_paired", jobsTool));
                outputsList.add(new GenericOutput("Reverse unpaired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/r_unpaired", jobsTool));
                outputsList.add(new GenericOutput("Quality Comparison", EOutputType.HTML, "/home/vmuser/CPI/results/" + jobid + "/" + jobid + "_quality_comparison.html", jobsTool));
                break;

            case TRIMMOMATIC_ADAPT:
                outputsList.add(new GenericOutput("Forward paired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/fw_paired", jobsTool));
                outputsList.add(new GenericOutput("Forward unpaired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/fw_unpaired", jobsTool));
                outputsList.add(new GenericOutput("Reverse paired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/r_paired", jobsTool));
                outputsList.add(new GenericOutput("Reverse unpaired", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/r_unpaired", jobsTool));
                outputsList.add(new GenericOutput("Quality Comparison", EOutputType.HTML, "/home/vmuser/CPI/results/" + jobid + "/" + jobid + "_quality_comparison.html", jobsTool));
                break;

            case SEECER:
                outputsList.add(new GenericOutput("Left Corrected", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/leftCorrected.fa", jobsTool));
                outputsList.add(new GenericOutput("Right Corrected", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/rightCorrected.fa", jobsTool));
                break;

            case TRINITY:
                outputsList.add(new GenericOutput("Transcripts", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/transcripts.fa", jobsTool));
                outputsList.add(new GenericOutput("Stats", EOutputType.TXT, "/home/vmuser/CPI/results/" + jobid + "/stats.txt", jobsTool));
                break;

            case VELVET:
                outputsList.add(new GenericOutput("Transcripts", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/transcripts.fa", jobsTool));
                outputsList.add(new GenericOutput("Stats", EOutputType.TXT, "/home/vmuser/CPI/results/" + jobid + "/stats.txt", jobsTool));
                break;

            case TRANSABYSS:
                outputsList.add(new GenericOutput("Transcripts", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/transcripts.fa", jobsTool));
                outputsList.add(new GenericOutput("Stats", EOutputType.TXT, "/home/vmuser/CPI/results/" + jobid + "/stats.txt", jobsTool));
                break;
            case SOAPdenovo_Trans:
                outputsList.add(new GenericOutput("Transcripts", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/transcripts.fa", jobsTool));
                outputsList.add(new GenericOutput("Stats", EOutputType.TXT, "/home/vmuser/CPI/results/" + jobid + "/stats.txt", jobsTool));
                break;
            case ABUNDANCE_ESTIMATION:
                outputsList.add(new GenericOutput("Abudance Estimation Report", EOutputType.PDF, "/home/vmuser/CPI/results/" + jobid + "/abundance_estimation.pdf", jobsTool));
                outputsList.add(new GenericOutput("Isoforms", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/isoforms.results", jobsTool));
                outputsList.add(new GenericOutput("Top expressed", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/top_expressed.fa", jobsTool));
                break;
            case DEG:
                outputsList.add(new GenericOutput("DEG Report", EOutputType.PDF, "/home/vmuser/CPI/results/" + jobid + "/DEG.pdf", jobsTool));
                outputsList.add(new GenericOutput("DEG.RData", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/DEG.RData", jobsTool));
                outputsList.add(new GenericOutput("Metadata", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/metadata.zip", jobsTool));
                break;
            case CLUSTERS:
                outputsList.add(new GenericOutput("Clusters Report", EOutputType.PDF, "/home/vmuser/CPI/results/" + jobid + "/clusters.pdf", jobsTool));
                outputsList.add(new GenericOutput("Metadata", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/metadata.zip", jobsTool));
                break;
            case HMMER:
                outputsList.add(new GenericOutput("Longest Orfs", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/longest_orfs.pep", jobsTool));
                outputsList.add(new GenericOutput("Output hmmer", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/output_hmmer.txt", jobsTool));
                outputsList.add(new GenericOutput("Per domain hits", EOutputType.TXT, "/home/vmuser/CPI/results/" + jobid + "/per_domain_hits_hmmer.txt", jobsTool));
                outputsList.add(new GenericOutput("Per sequence hits", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/per_sequence_hits_hmmer.txt", jobsTool));
                break;
            case BLAST:
                outputsList.add(new GenericOutput("Blast results XML", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/blast_results.xml", jobsTool));
                outputsList.add(new GenericOutput("Blast results Table", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/blast_results.tab", jobsTool));
                break;
            case MERGE:
                outputsList.add(new GenericOutput("Blast results XML", EOutputType.NO_DISPLAY, "/home/vmuser/CPI/results/" + jobid + "/merged_reads", jobsTool));
                break;
        }
        return outputsList;
    }

    /**
     * Generates page center code for data_item page according to outputFile
     * type Dragon's den method. Do not disturb. Do not sing. Danger of burns
     *
     * @return
     */
    public String generateDataItemPage() {
        //now i think it could be enough to only switch on types, can be wrong though

        switch (utilityBean.getSelectedOutput().getOutputType()) {
            case HTML:
                return displayFastQC();
            case NO_DISPLAY:
                return null;
            case PDF:
                return displayPDFOutput();
            case MAGIC:
                return "'tis be magics. No common folk should be able to see that message";
            default:
                throw new AssertionError(utilityBean.getSelectedOutput().getOutputType().name());

        }
    }

    /**
     * Returns html code to generate fastqc iframe
     *
     * @return
     */
    private String displayFastQC() {
        String path = utilityBean.getSelectedOutput().getPath();
        //FileEditor.editFastQCHTML(path);
        //path = "../" + path.substring(16); //or 15, check that
        return "<iframe id=\"frame\" src=\"" + path + "\" frameborder=\"0\" width=\"100%\" height=\"750px\"></iframe>";
    }
    
    /**
     * Returns html code to generate pdf output iframe
     * @return 
     */
    private String displayPDFOutput() {
        String path = utilityBean.getSelectedOutput().getPath();
        String src = "http://docs.google.com/gview?url=" + path + "&embedded=true";
        return "<iframe id=\"frame\" src=\"" + src + "\" frameborder=\"0\" width=\"100%\" height=\"750px\"></iframe>";
    }

    /**
     * Returns html code to generate data item page from csv type file
     *
     * @return
     */
    private String displayCSVOutput() {
        String path = utilityBean.getSelectedOutput().getPath();
        try {
            String contents = FileReader.readFile(path, 10);
            return contents;
        } catch (FileNotFoundException e) {
            return "File not found";
        }
    }

    /**
     * Sets selevted output in utility bean and redirects to data_item page,
     * redirects back to job_output if output was not found
     *
     * @return
     */
    public String selectOutputFile() {
        System.out.println("Inside select output file method");
        getJobOutputFiles(); //ensure initializing outputs list, since it can't be initialized in constuctor - bean stuff, probably could be fixed with viewscoped
        for (GenericOutput output : outputsList) {
            System.out.println("Trying to match output: " + output.getName() + " with output: " + outputName);
            if (output.getName().equals(outputName)) {
                utilityBean.setSelectedOutput(output);
                System.out.println("Trying to show file " + output.getPath());
                return "data_item?faces-redirect=true";
            }
        }
        return "job_output?faces-redirect=true";
    }

    /**
     * Returns true if lists of outputs contains any file usable as reports
     *
     * @return
     */
    public boolean containsReportFile() {
        getJobOutputFiles(); //ensure initializing outputs list, since it can't be initialized in constuctor - bean stuff
        for (GenericOutput output : outputsList) {
            if (output.getOutputType() == EOutputType.HTML
                    || output.getOutputType() == EOutputType.TXT
                    || output.getOutputType() == EOutputType.PDF) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns list of files usable as reports
     *
     * @return
     */
    public List<GenericOutput> getReportFiles() {
        getJobOutputFiles(); //ensure initializing outputs list, since it can't be initialized in constuctor - bean stuff
        List<GenericOutput> reports = new ArrayList();
        for (GenericOutput output : outputsList) {
            if (output.getOutputType() == EOutputType.HTML
                    || output.getOutputType() == EOutputType.TXT
                    || output.getOutputType() == EOutputType.PDF) {
                reports.add(output);
            }
        }
        return reports;
    }

    /**
     * Downloads selected file, method from unknown source, test it on VM
     * @return 
     */
    public String downloadFile() {
        selectOutputFile();
        System.out.println("Trying to get path of output file..");
        String path = utilityBean.getSelectedOutput().getPath();
        System.out.println("Retrieving file from: " + path);
        
        File file = new File(path);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        response.setHeader("Content-Disposition", "attachment;filename=" + utilityBean.getSelectedOutput().getName());
        response.setContentLength((int) file.length());
        ServletOutputStream out = null;
        try {
            FileInputStream input = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            out = response.getOutputStream();
            int i = 0;
            while ((i = input.read(buffer)) != -1) {
                out.write(buffer);
                out.flush();
            }
            FacesContext.getCurrentInstance().getResponseComplete();
        } catch (IOException err) {
            err.printStackTrace();
            return "error";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
        return "job_output?faces-redirect=true";
    }

}
