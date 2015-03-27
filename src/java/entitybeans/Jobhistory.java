/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitybeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pitas
 */
@Entity
@Table(name = "jobhistory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jobhistory.findAll", query = "SELECT j FROM Jobhistory j"),
    @NamedQuery(name = "Jobhistory.findByIdjobs", query = "SELECT j FROM Jobhistory j WHERE j.idjobs = :idjobs"),
    @NamedQuery(name = "Jobhistory.findByProcessid", query = "SELECT j FROM Jobhistory j WHERE j.processid = :processid"),
    @NamedQuery(name = "Jobhistory.findByCommandused", query = "SELECT j FROM Jobhistory j WHERE j.commandused = :commandused"),
    @NamedQuery(name = "Jobhistory.findByProjectid", query = "SELECT j FROM Jobhistory j WHERE j.projectid.idprojects = :projectid"),
    @NamedQuery(name = "Jobhistory.findByJobname", query = "SELECT j FROM Jobhistory j WHERE j.jobname = :jobname")})
public class Jobhistory implements Serializable {
    @JoinColumn(name = "projectid", referencedColumnName = "idprojects")
    @ManyToOne(optional = false)
    private Projects projectid;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idjobs")
    private Integer idjobs;
    @Column(name = "processid")
    private Integer processid;
    @Size(max = 45)
    @Column(name = "jobname")
    private String jobname;
    @Size(max = 9999)
    @Column(name = "commandused")
    private String commandused;

    public Jobhistory() {
        System.out.println("Default Constructor");
        jobname= "New job";
        processid = 11;
        projectid = new Projects(1);
        commandused = "Path and parameters";
        
    }
    
    public Jobhistory(String jobname) {
        System.out.println("Constructor II");
        this.jobname = jobname;
        processid = 11;
        projectid = new Projects(1);
        commandused = "Path and parameters";
    }
    
    public Jobhistory(String jobname, Integer processid, int projectid, String commandused) {
        System.out.println("Constructor III");
        this.jobname = jobname;
        this.processid = processid;
        this.projectid = new Projects(projectid);
        this.commandused = commandused;
    }

    public Jobhistory(Integer idjobs) {
        this.idjobs = idjobs;
    }

    public Integer getIdjobs() {
        return idjobs;
    }

    public void setIdjobs(Integer idjobs) {
        this.idjobs = idjobs;
    }

    public Integer getProcessid() {
        return processid;
    }

    public void setProcessid(Integer processid) {
        this.processid = processid;
    }
    
    public String getJobname() {
        return jobname;
    }

    public void setJobname(String jobname) {
        this.jobname = jobname;
    }

    public String getCommandused() {
        return commandused;
    }

    public void setCommandused(String commandused) {
        this.commandused = commandused;
    }

    public Projects getProjectid() {
        return projectid;
    }

    public void setProjectid(Projects projectid) {
        this.projectid = projectid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idjobs != null ? idjobs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jobhistory)) {
            return false;
        }
        Jobhistory other = (Jobhistory) object;
        if ((this.idjobs == null && other.idjobs != null) || (this.idjobs != null && !this.idjobs.equals(other.idjobs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Jobhistory[ idjobs=" + idjobs + " ]";
    }
}
