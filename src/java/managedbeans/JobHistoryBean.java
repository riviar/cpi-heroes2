/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import sessionbeans.JobHistoryFacade;

/**
 *
 * @author pitas
 */
@ManagedBean
@RequestScoped
public class JobHistoryBean {

    @EJB
    private JobHistoryFacade jobHistoryFacade;
    
    
    /**
     * Creates a new instance of JobHistoryBean
     */
    public JobHistoryBean() {
    }
    
    public List<String> getJobCommand() {
        List<String> list = new ArrayList();
        //List<Integer> jobs = jobHistoryFacade.getAllJobs();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        for (Jobhistory job:jobs) {
            list.add(job.getCommandused());
        }
        return list;        
    }
    
}
