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
    
    @ManagedProperty(value = "#{param.selectedProject}")
    private int selectedProject;
    
//    public void setUtilityBean(UtilityBean utilityBean) {
//        this.utilityBean = utilityBean;
//    }
    

    
    
    public FilesBean(){
        
    }


    
    /*public List<Files> getFiles(){

        List<Files> files=filesFacade.getProjectFiles(selectedProject);
        System.out.println("idproj filesbean"+selectedProject);
        return files;
    }*/
    
    public List<String> getFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(selectedProject);
        for (Files file:files){
            list.add(file.getDisplayname());            
        }
        return list;
    }
    
    public List<String> getFilesDescription(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(selectedProject);
        for (Files file:files){
            list.add(file.getDescription());
        }
        return list;
    }
    
    public List<String> getFilesPath(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(selectedProject);
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
        List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
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

    /**
     * @param selectedProject the selectedProject to set
     */
    public void setSelectedProject(int selectedProject) {
        this.selectedProject = selectedProject;
    }
    

        
}
