/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanillaws.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.QueryHint;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 *
 * @author Carlos
 */
@Entity
@Table(name = "PLAM_EMPLEADOS", schema = "UNA")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e"),
    @NamedQuery(name = "Empleado.findByEmpId", query = "SELECT e FROM Empleado e WHERE e.id = :id"),
    @NamedQuery(name = "Empleado.findByCedulaNombrePapellido", query = "SELECT e FROM Empleado e WHERE UPPER(e.nombre) like :nombre and UPPER(e.cedula) like :cedula and UPPER(e.primerApellido) like :pApellido", hints = @QueryHint(name = "eclipselink.refresh", value = "true")),
    @NamedQuery(name = "Empleado.findByUsuClave", query = "SELECT e FROM Empleado e WHERE e.usuario = :usuario and e.clave = :clave", hints = @QueryHint(name = "eclipselink.refresh", value = "true"))
})
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "PLAM_EMPLEADOS_EMP_ID_GENERATOR", sequenceName = "una.PLAM_EMPLEADOS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLAM_EMPLEADOS_EMP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "EMP_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "EMP_NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "EMP_PAPELLIDO")
    private String primerApellido;
    @Basic(optional = false)
    @Column(name = "EMP_SAPELLIDO")
    private String segundoApellido;
    @Basic(optional = false)
    @Column(name = "EMP_CEDULA")
    private String cedula;
    @Basic(optional = false)
    @Column(name = "EMP_GENERO")
    private String genero;
    @Column(name = "EMP_CORREO")
    private String correo;
    @Basic(optional = false)
    @Column(name = "EMP_ADMINISTRADOR")
    private String administrador;
    @Column(name = "EMP_USUARIO")
    private String usuario;
    @Column(name = "EMP_CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "EMP_FINGRESO")
    private LocalDate fechaIngreso;
    @Column(name = "EMP_FSALIDA")
    private LocalDate fechaSalida;
    @Basic(optional = false)
    @Column(name = "EMP_ESTADO")
    private String estado;
    @Version
    @Basic(optional = false)
    @Column(name = "EMP_VERSION")
    private Long version;
    @ManyToMany(mappedBy = "empleados", fetch = FetchType.LAZY)
    private List<TipoPlanilla> tiposPlanilla;

    public Empleado() {
    }

    public Empleado(Long id) {
        this.id = id;
    }
    
    public Empleado(EmpleadoDto empleadoDto) {
        this.id = empleadoDto.getId();
        actualizar(empleadoDto);
    }

    public void actualizar(EmpleadoDto empleadoDto) {
        this.nombre = empleadoDto.getNombre();
        this.primerApellido = empleadoDto.getPrimerApellido();
        this.segundoApellido = empleadoDto.getSegundoApellido();
        this.cedula = empleadoDto.getCedula();
        this.genero = empleadoDto.getGenero();
        this.correo = empleadoDto.getCorreo();
        this.administrador = empleadoDto.getAdministrador();
        this.usuario = empleadoDto.getUsuario();
        this.clave = empleadoDto.getClave();
        this.fechaIngreso = empleadoDto.getFechaIngreso();
        this.fechaSalida = empleadoDto.getFechaSalida();
        this.estado = empleadoDto.getEstado();
        this.version = empleadoDto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
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

    public List<TipoPlanilla> getTiposPlanilla() {
        return tiposPlanilla;
    }

    public void setTiposPlanilla(List<TipoPlanilla> tiposPlanilla) {
        this.tiposPlanilla = tiposPlanilla;
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
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.unaplanilla.model.Empleado[ id=" + id + " ]";
    }
    
}
