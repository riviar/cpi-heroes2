/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import sessionbeans.FilesFacade;

/**
 *
 * @author lestelles
 */
@ManagedBean
@RequestScoped
public class FilesBean {
    
    @EJB
    private FilesFacade filesFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
    
    
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
    
    public List<String> getRawFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getTypeFiles(7);
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
    }
    
    public List<String> getPreprocessedFilesNames(){
        List<String> list=new ArrayList();
        
        //File types 1, 2 and 3 correspond to preprocessed files
        //List<Files> files=filesFacade.getTypeFiles(1);
        List<Files> files = filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),1);
        //files.addAll(filesFacade.getTypeFiles(2));
        files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),2));        
        //files.addAll(filesFacade.getTypeFiles(3));
        files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),3));
        
                
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
    }
    
    public List<String> getAssembledFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getTypeFiles(4);
        files.addAll(filesFacade.getTypeFiles(5));
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
    }
    
    public List<String> getDownstreamFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getTypeFiles(9);
        files.addAll(filesFacade.getTypeFiles(11));
        files.addAll(filesFacade.getTypeFiles(19));
        files.addAll(filesFacade.getTypeFiles(20));
        files.addAll(filesFacade.getTypeFiles(21));
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
    }
    
    public List<String> getAnnotatedFilesNames(){
        List<String> list=new ArrayList();
        //List<Files> files=filesFacade.getTypeFiles(16);
        List<Files> files = filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),16);
        //files.addAll(filesFacade.getTypeFiles(17));
        files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),17));
        //files.addAll(filesFacade.getTypeFiles(18));
        files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),18));
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
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
            case "Abundance estimation":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(5);
            case "DEG":
                usefulFiletypes.add(7);
                break;
            case "CLUSTERS":
                usefulFiletypes.add(7);
                break;
            case "BLAST":
                usefulFiletypes.add(4);
                usefulFiletypes.add(1);
                break;
            case "HMMER":
                usefulFiletypes.add(4);
                usefulFiletypes.add(1);
                break;
            default:
                usefulFiletypes.add(7);
                usefulFiletypes.add(1);
                usefulFiletypes.add(2);
                break;
        }
            
        //List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(0));
        for(int i=1; i<usefulFiletypes.size(); i++){
            files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(), usefulFiletypes.get(i)));
        }
        for (Files file:files){
            filesMap.put(file.getDisplayname(), file.getPath());
            System.out.println(file);
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
