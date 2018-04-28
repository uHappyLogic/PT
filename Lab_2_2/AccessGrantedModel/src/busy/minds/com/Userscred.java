/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busy.minds.com;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author local_user
 */
@Entity
@Table(name = "userscred")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Userscred.findAll", query = "SELECT u FROM Userscred u"),
    @NamedQuery(name = "Userscred.findById", query = "SELECT u FROM Userscred u WHERE u.id = :id")})
public class Userscred implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Short id;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Users userId;
    @JoinColumn(name = "CREDENTIAL_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Credentials credentialId;

    public Userscred() {
    }

    public Userscred(Short id) {
        this.id = id;
    }
    
    public Userscred(Users userId, Credentials credentialId) {
        this.userId = userId;
        this.credentialId = credentialId;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
    }

    public Credentials getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Credentials credentialId) {
        this.credentialId = credentialId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Userscred)) {
            return false;
        }
        Userscred other = (Userscred) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "busy.minds.com.Userscred[ id=" + id + " ]";
    }
    
}
