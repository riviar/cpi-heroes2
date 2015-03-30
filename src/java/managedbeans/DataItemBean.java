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
    public List<String> getJobOutputFiles() {
        List<String> outputsList = new ArrayList();
        
        switch (jobsTool) {
            case FASTQC:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;

            case TRIMMOMATIC_TRIM:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
                
            case TRIMMOMATIC_ADAPT:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
                
            case SEECER:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
                
            case TRINITY:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
                
            case VELVET:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
                
            case TRANSABYSS:
                outputsList.add("Path to html");
                outputsList.add("Path to World Domination");
                break;
            
        }
        return outputsList;
    }
    
}
