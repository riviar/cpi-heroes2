/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entitybeans;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "workgroups")
@XmlRootElement
@NamedNativeQuery(name = "Workgroups.findByUsersMember", 
        query = "SELECT * FROM workgroups w LEFT JOIN users_has_workgroups uw ON w.idworkgroups=uw.workgroups_idworkgroups WHERE uw.users_idusers=?",
        resultClass = Workgroups.class)
@NamedQueries({
    @NamedQuery(name = "Workgroups.findAll", query = "SELECT w FROM Workgroups w"),
    @NamedQuery(name = "Workgroups.findByUsersOwner", query = "SELECT w FROM Workgroups w WHERE w.owner = :users"),
    @NamedQuery(name = "Workgroups.findByIdworkgroups", query = "SELECT w FROM Workgroups w WHERE w.idworkgroups = :idworkgroups"),
    @NamedQuery(name = "Workgroups.findByWorkgroupname", query = "SELECT w FROM Workgroups w WHERE w.workgroupname = :workgroupname")})
public class Workgroups implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
//    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idworkgroups")
    private Integer idworkgroups;
    @Size(max = 45)
    @Column(name = "workgroupname")
    private String workgroupname;
    @ManyToMany
    @JoinTable(name = "users_has_workgroups",
            inverseJoinColumns = {
                @JoinColumn(name = "users_idusers", referencedColumnName = "idusers")},
            joinColumns = {
                @JoinColumn(name = "workgroups_idworkgroups", referencedColumnName = "idworkgroups")}
    )
    private Collection<Users> usersCollection;
    @OneToMany(mappedBy = "workgroupid")
    private Collection<Projects> projectsCollection;
    @JoinColumn(name = "owner", referencedColumnName = "idusers")
    @ManyToOne(optional = false)
    private Users owner;

    public Workgroups() {
    }

    public Workgroups(Integer idworkgroups) {
        this.idworkgroups = idworkgroups;
    }

    public Integer getIdworkgroups() {
        return idworkgroups;
    }

    public void setIdworkgroups(Integer idworkgroups) {
        this.idworkgroups = idworkgroups;
    }

    public String getWorkgroupname() {
        return workgroupname;
    }

    public void setWorkgroupname(String workgroupname) {
        this.workgroupname = workgroupname;
    }

    @XmlTransient
    public Collection<Users> getUsersCollection() {
        return usersCollection;
    }

    public void setUsersCollection(Collection<Users> usersCollection) {
        this.usersCollection = usersCollection;
    }

    @XmlTransient
    public Collection<Projects> getProjectsCollection() {
        return projectsCollection;
    }

    public void setProjectsCollection(Collection<Projects> projectsCollection) {
        this.projectsCollection = projectsCollection;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idworkgroups != null ? idworkgroups.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workgroups)) {
            return false;
        }
        Workgroups other = (Workgroups) object;
        if ((this.idworkgroups == null && other.idworkgroups != null) || (this.idworkgroups != null && !this.idworkgroups.equals(other.idworkgroups))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entitybeans.Workgroups[ idworkgroups=" + idworkgroups + " ]";
    }

}

