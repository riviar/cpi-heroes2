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
import javax.faces.bean.RequestScoped;
import outputview.EOutputType;
import outputview.GenericOutput;
import toolstuff.util.ETool;
import toolstuff.util.EToolType;

/**
 * Managed bean for managing tool output page
 * @author Fox
 */
@ManagedBean
@RequestScoped
public class DataItemBean {
    
    private Jobhistory job;
    private ETool jobsTool;

    /**
     * Creates a new instance of DataItemBean
     */
    public DataItemBean() {
        //test job can be assigned here, but i have no jobs on my machine
        
        //check for and assign ETool, check if job.path contains tool path
    }
    
    /**
     * Retrieves list of files that are outputs of this job (intention: put them as links to their specific pages)
     * still not sure what exactly should be retrieved: paths, names, etc
     * @return 
     */
    public List<GenericOutput> getJobOutputFiles() {
        List<GenericOutput> outputsList = new ArrayList();
        
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
    
}
