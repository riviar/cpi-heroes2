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

    public FilesBean() {
    }
    /*public List<Files> getFiles(){
     List<Files> files=filesFacade.getProjectFiles(selectedProject);
     System.out.println("idproj filesbean"+selectedProject);
     return files;
     }*/

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

    public Collection<Files> getFilesForProject() {
        return utilityBean.getSelectedProject().getFilesCollection();
    }

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

    public List<String> getFilesDescription() {
        List<String> list = new ArrayList();
        List<Files> files = filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file : files) {
            list.add(file.getDescription());
        }
        return list;
    }

    public List<String> getFilesPath() {
        List<String> list = new ArrayList();
        List<Files> files = filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
        for (Files file : files) {
            list.add(file.getPath());
        }
        return list;
    }
    /*
     Creates a HashMap to use it in new_job_trimmomatic.xhtml
     */
    private static Map<String, Object> filesMap;

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
                break;
            case "Velvet + Oases":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                break;
            case "Trans-ABySS":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
                break;
            case "SOAPdenovo-Trans":
                usefulFiletypes.add(1);
                usefulFiletypes.add(3);
                usefulFiletypes.add(4);
                usefulFiletypes.add(22);
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
//List<Files> files=filesFacade.getProjectFiles(utilityBean.getSelectedProject().getIdprojects());
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

/* OLD VERSION, SORTING THE FILES ACCORDING TO THE FILE TYPE

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

@ManagedBean
@RequestScoped
public class FilesBean {
@EJB
private FilesFacade filesFacade;
@ManagedProperty(value = "#{utilityBean}")
private UtilityBean utilityBean;
public FilesBean(){
}

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
List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),7);
//List<Files> files=filesFacade.getTypeFiles(7);
for (Files file:files){
list.add(file.getDisplayname());
}
return list;
}
public List<String> getPreprocessedFilesNames(){
List<String> list=new ArrayList();
//File types 1, 2 and 3 correspond to preprocessed files
List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),1);
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),2));
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),3));

for (Files file:files){
list.add(file.getDisplayname());
}
return list;
}
public List<String> getAssembledFilesNames(){
List<String> list=new ArrayList();
List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),4);
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),5));

for (Files file:files){
list.add(file.getDisplayname());
}
return list;
}
public List<String> getDownstreamFilesNames(){
List<String> list=new ArrayList();
List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),9);
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),11));
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),19));
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),20));
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),21));

for (Files file:files){
list.add(file.getDisplayname());
}
return list;
}
public List<String> getAnnotatedFilesNames(){
List<String> list=new ArrayList();
List<Files> files=filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),16);
files.addAll(filesFacade.getFilesForTool(utilityBean.getSelectedProject().getIdprojects(),17));
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
case "Differential gene expression":
usefulFiletypes.add(1);
usefulFiletypes.add(7);
usefulFiletypes.add(9);
break;
case "Clusters by cutting dendrogram":
usefulFiletypes.add(1);
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

public void setUtilityBean(UtilityBean utilityBean) {
this.utilityBean = utilityBean;
}
}

    Status
    API
    Training
    Shop
    Blog
    About




*/
