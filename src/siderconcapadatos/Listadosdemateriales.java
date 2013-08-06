/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siderconcapadatos;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author mauro
 */
@Entity
@Table(name = "listadosdemateriales", catalog = "siderpruebas", schema = "")
@NamedQueries({
    @NamedQuery(name = "Listadosdemateriales.findAll", query = "SELECT l FROM Listadosdemateriales l"),
    @NamedQuery(name = "Listadosdemateriales.findByNumero", query = "SELECT l FROM Listadosdemateriales l WHERE l.numero = :numero"),
    @NamedQuery(name = "Listadosdemateriales.findByFechaEntrega", query = "SELECT l FROM Listadosdemateriales l WHERE l.fechaEntrega = :fechaEntrega"),
    @NamedQuery(name = "Listadosdemateriales.findByVehiculo", query = "SELECT l FROM Listadosdemateriales l WHERE l.vehiculo = :vehiculo"),
    @NamedQuery(name = "Listadosdemateriales.findByRevision", query = "SELECT l FROM Listadosdemateriales l WHERE l.revision = :revision"),
    @NamedQuery(name = "Listadosdemateriales.findByPesoTotal", query = "SELECT l FROM Listadosdemateriales l WHERE l.pesoTotal = :pesoTotal")})
public class Listadosdemateriales implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero")
    private Integer numero;
    @Basic(optional = false)
    @Column(name = "fechaEntrega")
    private String fechaEntrega;
    @Basic(optional = false)
    @Column(name = "vehiculo")
    private int vehiculo;
    @Basic(optional = false)
    @Column(name = "revision")
    private int revision;
    @Basic(optional = false)
    @Column(name = "pesoTotal")
    private double pesoTotal;

    public Listadosdemateriales() {
    }

    public Listadosdemateriales(Integer numero) {
        this.numero = numero;
    }

    public Listadosdemateriales(Integer numero, String fechaEntrega, int vehiculo, int revision, double pesoTotal) {
        this.numero = numero;
        this.fechaEntrega = fechaEntrega;
        this.vehiculo = vehiculo;
        this.revision = revision;
        this.pesoTotal = pesoTotal;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        Integer oldNumero = this.numero;
        this.numero = numero;
        changeSupport.firePropertyChange("numero", oldNumero, numero);
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        String oldFechaEntrega = this.fechaEntrega;
        this.fechaEntrega = fechaEntrega;
        changeSupport.firePropertyChange("fechaEntrega", oldFechaEntrega, fechaEntrega);
    }

    public int getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(int vehiculo) {
        int oldVehiculo = this.vehiculo;
        this.vehiculo = vehiculo;
        changeSupport.firePropertyChange("vehiculo", oldVehiculo, vehiculo);
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        int oldRevision = this.revision;
        this.revision = revision;
        changeSupport.firePropertyChange("revision", oldRevision, revision);
    }

    public double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(double pesoTotal) {
        double oldPesoTotal = this.pesoTotal;
        this.pesoTotal = pesoTotal;
        changeSupport.firePropertyChange("pesoTotal", oldPesoTotal, pesoTotal);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Listadosdemateriales)) {
            return false;
        }
        Listadosdemateriales other = (Listadosdemateriales) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "siderconcapadatos.Listadosdemateriales[ numero=" + numero + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
