package cr.ac.una.unaplanilla.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author cbcar
 */
public class TipoPlanillaDto {

    public SimpleStringProperty id;
    public SimpleStringProperty codigo;
    public SimpleStringProperty descripcion;
    public SimpleStringProperty planillaPorMes;
    private Integer anoUltimaPlanilla;
    private Integer mesUltimaPlanilla;
    private Integer numeroUltimaPlanilla;
    public SimpleBooleanProperty estado;
    private Long version;
    private List<EmpleadoDto> empleados;
    private List<EmpleadoDto> empleadosEliminados;
    private Boolean modificado;

    public TipoPlanillaDto() {
        this.id = new SimpleStringProperty();
        this.codigo = new SimpleStringProperty();
        this.descripcion = new SimpleStringProperty();
        this.planillaPorMes = new SimpleStringProperty();
        this.estado = new SimpleBooleanProperty(true);
        this.modificado = false;
        empleados = FXCollections.observableArrayList();
        empleadosEliminados = new ArrayList<>();
    }

    public Long getId() {
        if (this.id.get() != null && !this.id.get().isEmpty()) {
            return Long.valueOf(this.id.get());
        } else {
            return null;
        }
    }

    public void setId(Long id) {
        this.id.set(id.toString());
    }

    public String getCodigo() {
        return codigo.get();
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public Integer getPlanillaPorMes() {
        if (this.planillaPorMes.get() != null && !this.planillaPorMes.get().isEmpty()) {
            return Integer.valueOf(this.planillaPorMes.get());
        } else {
            return null;
        }
    }

    public void setPlanillaPorMes(Integer planillaPorMes) {
        this.planillaPorMes.set(planillaPorMes.toString());
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
        return estado.get() ? "A" : "I";
    }

    public void setEstado(String estado) {
        this.estado.set(estado.equals("A"));
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public List<EmpleadoDto> getEmpleados() {
        return empleados;
    }

    public ObservableList<EmpleadoDto> getEmpleadosList() {
        return FXCollections.observableList(empleados);
    }

    public void setEmpleados(List<EmpleadoDto> empleados) {
        this.empleados = empleados;
    }

    public List<EmpleadoDto> getEmpleadosEliminados() {
        return empleadosEliminados;
    }

    public void setEmpleadosEliminados(List<EmpleadoDto> empleadosEliminados) {
        this.empleadosEliminados = empleadosEliminados;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoPlanillaDto other = (TipoPlanillaDto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "TipoPlanillaDto{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }

}
