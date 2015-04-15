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
 *
 * @author lestelles
 */
@ManagedBean
@ViewScoped
public class FilesBean {
    
    @EJB
    private FilesFacade filesFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    private Files selectedFile;

    public FilesBean(){
        
    }


    
    /*public List<Files> getFiles(){

        List<Files> files=filesFacade.getProjectFiles(selectedProject);
        System.out.println("idproj filesbean"+selectedProject);
        return files;
    }*/
    
    public List<String> getFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file:files){
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
    
    public Collection<Files> getFilesForProject(){
        return utilityBean.getSelectedProject().getFilesCollection();
    }
    
    public String getFileSupertype(Files file) {
        String supertype;
        switch(file.getFiletype().getFiletypeid()) {
            case 1:
            case 2:
            case 3:
                supertype = "Raw file";
                break;
            case 4:
            case 5:
                supertype = "Assembled file";
                break;
            case 6:
            case 7:
                supertype = "Downstream file";
                break;
            case 8:
            case 9:
                supertype = "Anotated file";
                break;
            default:
                supertype = "Unknown file type";
                break;
        }
        return supertype;
    }

    public List<String> getFilesDescription(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file:files){
            list.add(file.getDescription());
        }
        return list;
    }
    
    public List<String> getFilesPath(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file:files){
            list.add(file.getPath());            
        }
        return list;
    }
    
    
    /*
    Creates a HashMap to use it in new_job_trimmomatic.xhtml
    */
    private static Map<String,Object> filesMap;
    public Map<String,Object> getFilesMap() {
        filesMap = new LinkedHashMap<>();
        ArrayList<Integer> usefulFiletypes = new ArrayList();
        switch (utilityBean.getSelectedTool().getName()) {
            case "FastQC":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "Trimmomatic - Trimming":
                usefulFiletypes.add(7);
                usefulFiletypes.add(2);
                break;
            case "Trimmomatic - Adapters":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                break;
            case "Seecer":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "Trinity":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "Velvet + Oases":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "Trans-ABySS":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "SOAPdenovo-Trans":
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
            case "ABUNDANCE_ESTIMATION":
                usefulFiletypes.add(4);
                break;
            case "DEG":
                usefulFiletypes.add(9);
                break;
            case "CLUSTERS":
                usefulFiletypes.add(11);                
                break;
            default:
                break;
        }
            
        //List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(0));
        for(int i=1; i<usefulFiletypes.size(); i++){
            files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(i)));
        }
        for (Files file:files){
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
