/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.model;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 *
 * @author cbcar
 */
@Entity
@Table(name = "PLAM_TIPOPLANILLAS", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "TipoPlanilla.findAll", query = "SELECT t FROM TipoPlanilla t")
    , @NamedQuery(name = "TipoPlanilla.findById", query = "SELECT t FROM TipoPlanilla t WHERE t.id = :id")})
public class TipoPlanilla implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PLAM_TIPOPLANILLAS_TPLA_ID_GENERATOR", sequenceName = "una.PLAM_TIPOPLANILLAS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_TIPOPLANILLAS_TPLA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "TPLA_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "TPLA_CODIGO")
    private String codigo;
    @Basic(optional = false)
    @Column(name = "TPLA_DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "TPLA_PLAXMES")
    private Integer planillaPorMes;
    @Column(name = "TPLA_ANOULTPLA")
    private Integer anoUltimaPlanilla;
    @Column(name = "TPLA_MESULTPLA")
    private Integer mesUltimaPlanilla;
    @Column(name = "TPLA_NUMULTPLA")
    private Integer numeroUltimaPlanilla;
    @Basic(optional = false)
    @Column(name = "TPLA_ESTADO")
    private String estado;
    @Version
    @Basic(optional = false)
    @Column(name = "TPLA_VERSION")
    private Long version;
    @JoinTable(name = "PLAM_EMPLEADOSPLANILLA", joinColumns = {
        @JoinColumn(name = "EXP_IDTPLA", referencedColumnName = "TPLA_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "EXP_IDEMP", referencedColumnName = "EMP_ID")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Empleado> empleados;

    public TipoPlanilla() {
    }

    public TipoPlanilla(TipoPlanillaDto tipoPlanillaDto) {
        this.id = tipoPlanillaDto.getId();
        actualizar(tipoPlanillaDto);
    }
    
    public void actualizar(TipoPlanillaDto tipoPlanillaDto){
        this.codigo = tipoPlanillaDto.getCodigo();
        this.descripcion = tipoPlanillaDto.getDescripcion();
        this.planillaPorMes = tipoPlanillaDto.getPlanillaPorMes();
        this.anoUltimaPlanilla = tipoPlanillaDto.getAnoUltimaPlanilla();
        this.mesUltimaPlanilla = tipoPlanillaDto.getMesUltimaPlanilla();
        this.numeroUltimaPlanilla = tipoPlanillaDto.getNumeroUltimaPlanilla();
        this.estado = tipoPlanillaDto.getEstado();
        this.version = tipoPlanillaDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPlanillaPorMes() {
        return planillaPorMes;
    }

    public void setPlanillaPorMes(Integer planillaPorMes) {
        this.planillaPorMes = planillaPorMes;
    }

    public Integer getAnoUltimaPlanilla() {
        return anoUltimaPlanilla;
    }

    public void setAnoUltimaPlanilla(Integer anoUltimaPlanilla) {
        this.anoUltimaPlanilla = anoUltimaPlanilla;
    }

    public Integer getMesUltimaPlanilla() {
        return mesUltimaPlanilla;
    }

    public void setMesUltimaPlanilla(Integer mesUltimaPlanilla) {
        this.mesUltimaPlanilla = mesUltimaPlanilla;
    }

    public Integer getNumeroUltimaPlanilla() {
        return numeroUltimaPlanilla;
    }

    public void setNumeroUltimaPlanilla(Integer numeroUltimaPlanilla) {
        this.numeroUltimaPlanilla = numeroUltimaPlanilla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
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
        if (!(object instanceof TipoPlanilla)) {
            return false;
        }
        TipoPlanilla other = (TipoPlanilla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.TipoPlanilla[ Id=" + id + " ]";
    }
    
}
