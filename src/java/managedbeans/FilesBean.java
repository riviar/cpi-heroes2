/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entitybeans.Files;
import java.util.ArrayList;
import java.util.List;
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
    
    //@ManagedProperty(value = "#{utilityBean}")
    //private UtilityBean utilityBean;

//    public void setUtilityBean(UtilityBean utilityBean) {
//        this.utilityBean = utilityBean;
//    }
    
    private int idProject;
    
    
    public FilesBean(){
        //idProject = utilityBean.getSelectedProject().getIdprojects();
        idProject = 0;
    }
    
    public List<Files> getFiles(){
        List<Files> files=filesFacade.getProjectFiles(idProject);
        return files;
    }
    
    public List<String> getFilesNames(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(idProject);
        for (Files file:files){
            list.add(file.getDisplayname());
        }
        return list;
    }
    
    public List<String> getFilesDescription(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(idProject);
        for (Files file:files){
            list.add(file.getDescription());
        }
        return list;
    }
    
    public List<String> getFilesPath(){
        List<String> list=new ArrayList();
        List<Files> files=filesFacade.getProjectFiles(idProject);
        for (Files file:files){
            list.add(file.getPath());
        }
        return list;
    }

    
}
