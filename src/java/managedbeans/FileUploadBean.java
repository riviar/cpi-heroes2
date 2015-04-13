/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

/**
 *
 * @author pitas
 */
import entitybeans.Files;
import entitybeans.Filetype;
import entitybeans.Projects;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.EJB;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import sessionbeans.FilesFacade;
import sessionbeans.ProjectSessionFacade;
  
@ManagedBean
public class FileUploadBean {
   private String destination="/home/pitas/Deskargak/";
    //private String destination="/home/vmuser/CPI/uploads/";
    
    @EJB
    private FilesFacade filesFacade;
    
    @EJB
    ProjectSessionFacade projectFacade;
    
    @ManagedProperty(value = "#{utilityBean}")
    private UtilityBean utilityBean;
 
    public void upload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
        } catch (IOException e) {
            e.printStackTrace();
        }
 
    }  
 
    public void copyFile(String fileName, InputStream in) {
           try {
              
              
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
              
                int read = 0;
                byte[] bytes = new byte[1024];
              
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
              
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
                addFileToDB(fileName);
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
    
    public void addFileToDB(String fileName){
        Files uploadedFile = new Files();
        Collection<Files> projectFiles = utilityBean.getSelectedProject().getFilesCollection();
                
        ArrayList<Projects> fileProject = new ArrayList(1);
        System.out.println("Project " + utilityBean.getSelectedProject().getProjectname());
        fileProject.add(utilityBean.getSelectedProject());
        
        uploadedFile.setPath(destination + fileName);
        uploadedFile.setDisplayname(fileName);
        uploadedFile.setDescription("User uploaded file");
        //All uploaded files will be considered raw files, filetype 7
        uploadedFile.setFiletype(new Filetype(7));
        uploadedFile.setProjectsCollection(fileProject);

        //Add output files to project table
        projectFiles.add(uploadedFile);
        utilityBean.getSelectedProject().setFilesCollection(projectFiles);
        projectFacade.edit(utilityBean.getSelectedProject());

        //Add output to database
        filesFacade.create(uploadedFile);
    }
    
     /**
     * @param utilityBean the utilityBean to set
     */
    public void setUtilityBean(UtilityBean utilityBean) {
        this.utilityBean = utilityBean;
    }
}