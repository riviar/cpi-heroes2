/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Jobhistory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sessionbeans.JobHistoryFacade;

/**
 * Managed bean that works as an interface between the tool submission form and
 * the database facade
 *
 * @author Asier Gonzalez
 */
@ManagedBean
@RequestScoped
public class JobHistoryBean {

    /**
     * Name of the job
     */
    private String jobName;

    /**
     * Executed job
     */
    private Jobhistory newJob = new Jobhistory("New Job 2");

    /**
     *
     * @return Executed job
     */
    public Jobhistory getNewJob() {
        return newJob;
    }

    /**
     *
     * @param newJob Job to run
     */
    public void setNewJob(Jobhistory newJob) {
        this.newJob = newJob;
    }

    /**
     * Facade to add the job to the history
     */
    @EJB
    JobHistoryFacade jobHistoryFacade;

    /**
     * Managed bean with session scoped information
     */
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;

    /**
     * The job the user selected retrieved from the web
     */
    @ManagedProperty(value = "#{param.currentJob}")
    private String currentJob;

    /**
     * The id of the user selected job
     */
    @ManagedProperty(value = "#{param.selectedJob}")
    private String selectedJobId;

    /**
     *
     * @param currentJob Running or user selected job
     */
    public void setcurrentJob(String currentJob) {
        this.setCurrentJob(currentJob);
    }

    /**
     *
     * @param utilityBean
     */
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }

    /**
     *
     * @return User selected job id
     */
    public String getSelectedJobId() {
        return selectedJobId;
    }

    /**
     *
     * @param selectedJobId
     */
    public void setSelectedJobId(String selectedJobId) {
        this.selectedJobId = selectedJobId;
    }

    /**
     * Creates a new instance of JobHistoryBean
     */
    public JobHistoryBean() {
        //this.newJob = new Jobhistory();
        //jobHistoryFacade = new JobHistoryFacade();
    }

    /**
     * Creates a new instance of JobHistoryBean with a preconfigured job
     *
     * @param newJob
     */
    public JobHistoryBean(Jobhistory newJob) {
        this.newJob = newJob;
    }

    /**
     * Retrieves a content stream to be served to a downloading client. This
     * method extracts the name of the shell script from a string representing
     * the entire command, given with absolute path to the script executed.
     *
     * @param commandused String as stored in JobHistory.commandused.
     * @return StreamedContent object representing the file extracted from the
     * String commandused.
     * @throws IOException
     */
    public StreamedContent downloadScript(String commandused) throws IOException {
        // Strip all parameters from commandline
        // Assumes we have been given an an absolute path with no escaped spaces
        commandused = commandused.substring(0, commandused.indexOf(" "));
        // Extract name of file from absolute path
        String filename = commandused.substring("/home/vmuser/CPI/tools/shell_scripts/".length(), commandused.length());
        FileInputStream stream;
        try {
            stream = new FileInputStream(commandused);
        } catch (FileNotFoundException ex) {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.sendRedirect("errorpage.xhtml");
            return new DefaultStreamedContent();
        }
        DefaultStreamedContent downloadStream = new DefaultStreamedContent(stream, "application/octet-stream", filename);
        return downloadStream;
    }

    /**
     * Adds job to the <i>Jobhistory</i> table in the database and redirects to
     * the <i>Projects</i> page
     *
     * @return Returns the user to the <i>Projects</i> page
     */
    public String addJob2History() {

        if (jobHistoryFacade != null) {
            jobHistoryFacade.addJob(newJob);
        } else {
            jobHistoryFacade = new JobHistoryFacade();
            jobHistoryFacade.addJob(newJob);
        }

        //}
        return "project";
    }

    /**
     * @param jobName The job name to set
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Gets all the jobs from the database
     *
     * @return List of the jobs
     */
    public List<Jobhistory> getJobs() {
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();

        return jobs;
    }

    /**
     * Gets all the jobs from the selected project
     *
     * @return List of the project jobs
     */
    public List<Jobhistory> getProjectJobs() {
        List<Jobhistory> jobs = jobHistoryFacade.getProjectJobs(utilityBean.getSelectedProject().getIdprojects());
        List<Jobhistory> list = new ArrayList(jobs.size());
        for (int i = jobs.size() - 1; i >= 0; i--) {

            list.add(jobs.get(i));

        }

        return list;
    }

    /**
     * Gets the names of all the jobs
     *
     * @return List of the job names
     */
    public List<String> getJobName() {
        List<String> list = new ArrayList();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        for (Jobhistory job : jobs) {
            list.add(job.getJobname());
        }
        return list;
    }

    /**
     * Gets the executed command of all the jobs
     *
     * @return List of job commands
     */
    public List<String> getJobCommand() {
        List<String> list = new ArrayList();
        List<Jobhistory> jobs = jobHistoryFacade.getAllJobs();
        for (Jobhistory job : jobs) {
            list.add(job.getCommandused());
        }
        return list;
    }

    /**
     * Gets the process identifier of the current job
     *
     * @return Operating system level job PID
     */
    public int getJobPID() {
        System.out.println(jobName);
        return jobHistoryFacade.getJobPID(getCurrentJob());
    }

    /**
     * Uses the linux <i>ps</i> command to get the running time of an ongoing
     *
     * @param PID Identifier of the job whose running time is required
     * @return Running time
     */
    public String getJobRunningTime(int PID) {

        List<String> commandList = new ArrayList(5);
        commandList.add("ps");
        commandList.add("-p");
        commandList.add(Integer.toString(PID));
        commandList.add("-o");
        commandList.add("etime");
        ProcessBuilder pb = new ProcessBuilder().command(commandList);
        Process p = null;
        StringBuffer output = new StringBuffer();
        try {
            p = pb.start();
            p.waitFor();

            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (!line.contains("ELAPSED")) {
                    output.append(line);
                }

            }

            /*StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");

             StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");

             // start gobblers
             outputGobbler.start();
             errorGobbler.start();*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return output.toString();
    }

    /**
     *
     * @param currentJob the currentJob to set
     */
    public void setCurrentJob(String currentJob) {
        System.out.println("setCurrentJob: " + currentJob);
        this.currentJob = currentJob;
    }

    /**
     * Sets selected jobHistory item in utility bean and redirects to output
     * page
     *
     * @return Output page
     */
    public String selectJobHistoryItem() {
        System.out.println("Looking for job with id " + selectedJobId);
        Jobhistory selectedJobHistoryItem = jobHistoryFacade.findJobHistoryById(Integer.valueOf(selectedJobId));
        System.out.println("Found job with name " + selectedJobHistoryItem.getJobname());
        utilityBean.setSelectedJob(selectedJobHistoryItem);
        return "job_output?faces-redirect=true";
    }

    /**
     * @return the currentJob
     */
    public String getCurrentJob() {
        return currentJob;
    }

    /**
     * Deletes the selected job from history
     *
     * @return
     */
    public String deleteJob() {
        Jobhistory selectedJobHistoryItem = jobHistoryFacade.findJobHistoryById(Integer.valueOf(selectedJobId));
        jobHistoryFacade.remove(selectedJobHistoryItem);
        return null;
    }

    /**
     * Stops the execution of the selected job
     *
     * @return
     */
    public String cancelJob() {
        Jobhistory selectedJobHistoryItem = jobHistoryFacade.findJobHistoryById(Integer.valueOf(selectedJobId));
        Integer pid = selectedJobHistoryItem.getProcessid();
        String killpscommand = "kill " + pid.toString();
        System.out.println("Killing process - " + killpscommand);
        try {
            Process p = Runtime.getRuntime().exec(killpscommand);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
