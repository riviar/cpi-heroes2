/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sessionbeans.FilesFacade;

/**
 * Deals with the <i>Files</i> table of the database in order to manage files information
 * @author Lucia Estelles Lopez
 * @author Asier Gonzalez
 * @author Matthew Robinson
 */
@ManagedBean
@ViewScoped
public class FilesBean {

    /**
     * Facade to add the output files to the database
     */
    @EJB
    private FilesFacade filesFacade;
    
    /**
     * Managed bean with session scoped information
     */
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    /**
     * File fetched from the database
     */
    private Files selectedFile;

    /**
     * Empty constructor that an EJB requires to create a new instance of FilesBean
     */
    public FilesBean() {
    }
    
    /**
     * Gets the name the user gave to each file
     * @return Name of the files
     */
    public List<String> getFilesNames() {
        List<String> list = new ArrayList();
        List<Files> files = filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file : files) {
            list.add(file.getDisplayname());
        }
        return list;
    }

    public Files getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(Files selectedFile) {
        this.selectedFile = selectedFile;
    }

    /**
     * Copies the selected file from the server to the computer of the user
     * @return Downloaded file
     * @throws IOException Throws an exception if either the origin or destiny paths are incorrect
     */
    public StreamedContent downloadFile() throws IOException {
        Files file = selectedFile;
        FileInputStream stream;
        try {
            stream = new FileInputStream(file.getPath());
        } catch (FileNotFoundException ex) {
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.sendRedirect("errorpage.xhtml");
            return new DefaultStreamedContent();
        }
        DefaultStreamedContent downloadStream = new DefaultStreamedContent(stream, "application/octet-stream", file.getDisplayname());
        return downloadStream;
    }

    /**
     * Gets all the files for a selected project
     * @return Project files
     */
    public Collection<Files> getFilesForProject() {
        return utilityBean.getSelectedProject().getFilesCollection();
    }

    /**
     * Gets file descriptions according to its <i>filetype</i>
     * @param file 
     * @return Description of the file
     */
    public String getFileSupertype(Files file) {
        String supertype;
        switch (file.getFiletype().getFiletypeid()) {
            case 1:
                supertype = "01 Uploaded file";
                break;
            case 2:
                supertype = "02 FastQC HTML report";
                break;
            case 3:
                supertype = "03 Trimmed reads from Trimmomatic";
                break;
            case 4:
                supertype = "04 Preprocessed file (Adapters removed)";
                break;
            case 22:
                supertype = "05 Trimmed reads from SEECER";
                break;            
            case 23:
                supertype = "06 Merged reads";
                break;
            case 5:
                supertype = "06 Assembled transcripts";
                break;
            case 6:
                supertype = "07 Assembly statistics";
                break;
            case 7:
                supertype = "08 Abundance estimation report";
                break;
            case 8:
                supertype = "09 Abundance estimation isoforms file";
                break;
            case 24:
                supertype = "10 Top expressed genes";
                break; 
            case 9:
                supertype = "11 Differential gene expression report";
                break;
            case 10:
                supertype = "12 Differential gene expression analysis file";
                break;
            case 11:
                supertype = "13 Cluster analysis report";
                break;
            case 12:
                supertype = "14 BLAST XML file";
                break;
            case 13:
                supertype = "15 BLAST tab-separated or ASN file";
                break;
            case 14:
                supertype = "16 HMMER results";
                break;
            default:
                supertype = "Unknown file type";
                break;
        }
        return supertype;
    }

    /**
     * Gets all the files description for a selected project
     * @return Description of the files
     */
    public List<String> getFilesDescription() {
        List<String> list = new ArrayList();
        List<Files> files = filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file : files) {
            list.add(file.getDescription());
        }
        return list;
    }

    /**
     * Gets all the files path for a selected project
     * @return Path of the files
     */
    public List<String> getFilesPath() {
        List<String> list = new ArrayList();
        List<Files> files = filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file : files) {
            list.add(file.getPath());
        }
        return list;
    }
    
    /**
     * Map to control the files that have to be showed for each tool
     */
    private static Map<String, Object> filesMap;

    /**
     * Returns the project files that can be used by the selected tool 
     * @return 
     */
    public Map<String, Object> getFilesMap() {
        filesMap = new LinkedHashMap<>();
        ArrayList<Integer> usefulFiletypes = new ArrayList();
        switch (utilityBean.getSelectedTool().getName()) {
            case "FastQC":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                break;
            case "Trimmomatic - Trimming":
                usefulFiletypes.add(1);
                usefulFiletypes.add(4);
                break;
            case "Trimmomatic - Adapters":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                break;
            case "Seecer":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                break;
            case "Trinity":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                usefulFiletypes.add(23);
                break;
            case "Velvet + Oases":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                usefulFiletypes.add(23);
                break;
            case "Trans-ABySS":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                usefulFiletypes.add(23);
                break;
            case "SOAPdenovo-Trans":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                usefulFiletypes.add(23);
                break;
            case "1. Abundance estimation":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                usefulFiletypes.add(5);
                usefulFiletypes.add(23);
                break;
            case "2. Differential gene expression":
                usefulFiletypes.add(8);                
                break;
            case "3. Clusters by cutting dendrogram":
                usefulFiletypes.add(10);
                break;
            case "BLAST":
                usefulFiletypes.add(1);
                usefulFiletypes.add(5);
                usefulFiletypes.add(24);
                break;
            case "HMMER":
                usefulFiletypes.add(1);
                usefulFiletypes.add(5);
                usefulFiletypes.add(24);
                break;
            case "Merge files for assembly":
                usefulFiletypes.add(1);
                break;
            default:
                break;
        }
        List<Files> files = filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(0));
        for (int i = 1; i < usefulFiletypes.size(); i++) {
            files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(i)));
        }
        for (Files file : files) {
            filesMap.put(file.getDisplayname(), file.getPath());
        }
        return filesMap;
    }

    /**
     * @param utilityBean the utilityBean to set
     */
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }
}