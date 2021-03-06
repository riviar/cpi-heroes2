/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitybeans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Matthew Robinson
 */
@Entity
@Table(name = "projects")
@XmlRootElement
@NamedNativeQuery(name = "Projects.findInUsersWorkgroup",
    query = "SELECT * FROM projects p LEFT JOIN users_has_workgroups uw ON p.workgroupid=uw.workgroups_idworkgroups WHERE users_idusers=?",
    resultClass = Projects.class)
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findByIdprojects", query = "SELECT p FROM Projects p WHERE p.idprojects = :idprojects"),
    @NamedQuery(name = "Projects.findByProjectname", query = "SELECT p FROM Projects p WHERE p.projectname = :projectname"),
    @NamedQuery(name = "Projects.findByDescription", query = "SELECT p FROM Projects p WHERE p.description = :description"),
    @NamedQuery(name = "Projects.findByVisibility", query = "SELECT p FROM Projects p WHERE p.visibility = :visibility"),
    @NamedQuery(name = "Projects.findByOwner", query = "SELECT p FROM Projects p WHERE p.owner = :user"),
    //@NamedQuery(name = "Projects.findVisibleToUser", query = "SELECT p FROM Projects p JOIN p.workgroup w JOIN w.usersCollection u WHERE u.idusers = :user"),
    //@NamedQuery(name = "Projects.findInWorkgroupOwnedByUser", query = "SELECT p FROM Projects p JOIN p.workgroupid w WHERE w.owner = :user")})
})

public class Projects implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "projectid")
    private Collection<Jobhistory> jobhistoryCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
//    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idprojects")
    private Integer idprojects;
    @Size(max = 45)
    @Column(name = "projectname")
    private String projectname;
    @Size(max = 9999)
    @Column(name = "description")
    private String description;
    @Temporal(DATE)
    @Column(name = "creationdate")
    @NotNull
    private Date creationdate;
    @Size(max = 9)
    @Column(name = "visibility")
    @NotNull
    private String visibility;
    @JoinColumn(name = "owner", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private Users owner;
    @JoinColumn(name = "workgroupid", referencedColumnName = "idworkgroups")
    @ManyToOne
    private Workgroups workgroup;
    @ManyToMany
    @JoinTable(name = "project_has_files", joinColumns = {
        @JoinColumn(name = "projects_idprojects", referencedColumnName = "idprojects")}, inverseJoinColumns = {
        @JoinColumn(name = "resources_idresources", referencedColumnName = "idresources")})
    private Collection<Files> filesCollection;

    public Projects() {
    }

    public Projects(Integer idprojects) {
        this.idprojects = idprojects;
    }

    public Integer getIdprojects() {
        return idprojects;
    }

    public void setIdprojects(Integer idprojects) {
        this.idprojects = idprojects;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public Workgroups getWorkgroup() {
        return workgroup;
    }

    public void setWorkgroup(Workgroups workgroup) {
        this.workgroup = workgroup;
    }

    @XmlTransient
    public Collection<Files> getFilesCollection() {
        return filesCollection;
    }

    public void setFilesCollection(Collection<Files> filesCollection) {
        this.filesCollection = filesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprojects != null ? idprojects.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
// TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.idprojects == null && other.idprojects != null) || (this.idprojects != null && !this.idprojects.equals(other.idprojects))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitybeans.Projects[ idprojects=" + idprojects + " ]";
    }

    @XmlTransient
    public Collection<Jobhistory> getJobhistoryCollection() {
        return jobhistoryCollection;
    }

    public void setJobhistoryCollection(Collection<Jobhistory> jobhistoryCollection) {
        this.jobhistoryCollection = jobhistoryCollection;
    }

}
