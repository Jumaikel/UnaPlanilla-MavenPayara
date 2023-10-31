/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.util.BindingUtils;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class EmpleadosViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtNombre;
    @FXML
    private JFXTextField txtPApellido;
    @FXML
    private JFXTextField txtSApellido;
    @FXML
    private JFXTextField txtCedula;
    @FXML
    private JFXRadioButton rdbMasculino;
    @FXML
    private ToggleGroup tggGenero;
    @FXML
    private JFXRadioButton rdbFemenino;
    @FXML
    private JFXCheckBox chkAdministrador;
    @FXML
    private JFXCheckBox chkActivo;
    @FXML
    private JFXDatePicker dtpFIngreso;
    @FXML
    private JFXDatePicker dtpFSalida;
    @FXML
    private JFXTextField txtCorreo;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtClave;
    @FXML
    private JFXButton btnNuevo;
    @FXML
    private JFXButton btnBuscar;
    @FXML
    private JFXButton btnEliminar;
    @FXML
    private JFXButton btnGuardar;
    EmpleadoDto empleadoDto;
    List<Node> requeridos = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rdbMasculino.setUserData("M");
        rdbFemenino.setUserData("F");
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        txtNombre.setTextFormatter(Formato.getInstance().letrasFormat(30));
        txtPApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtSApellido.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtCedula.setTextFormatter(Formato.getInstance().cedulaFormat(40));
        txtCorreo.setTextFormatter(Formato.getInstance().maxLengthFormat(80));
        txtUsuario.setTextFormatter(Formato.getInstance().letrasFormat(15));
        txtClave.setTextFormatter(Formato.getInstance().maxLengthFormat(8));
        this.empleadoDto = new EmpleadoDto();
        nuevoEmpleado();
        indicarRequeridos();
    }
    
    @Override
    public void initialize() {

    }
    
    private void nuevoEmpleado() {
        unbindEmpleado();
        this.empleadoDto = new EmpleadoDto();
        bindEmpleado(true);
        validarAdministrador();
        txtId.clear();
        txtId.requestFocus();
    }

    private void indicarRequeridos() {
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtNombre, txtCedula, txtPApellido, dtpFIngreso));
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && (((JFXTextField) node).getText()==null || ((JFXTextField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && (((JFXPasswordField) node).getText()==null || ((JFXPasswordField) node).getText().isBlank())) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXDatePicker && ((JFXDatePicker) node).getValue() == null) {
                if (validos) {
                    invalidos += ((JFXDatePicker) node).getAccessibleText();
                } else {
                    invalidos += "," + ((JFXDatePicker) node).getAccessibleText();
                }
                validos = false;
            } else if (node instanceof JFXComboBox && ((JFXComboBox) node).getSelectionModel().getSelectedIndex() < 0) {
                if (validos) {
                    invalidos += ((JFXComboBox) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXComboBox) node).getPromptText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    private void bindEmpleado(Boolean nuevo) {
        if (!nuevo) {
            txtId.textProperty().bind(empleadoDto.id);
        }
        txtCedula.textProperty().bindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().bindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().bindBidirectional(empleadoDto.primerApellido);
        txtSApellido.textProperty().bindBidirectional(empleadoDto.segundoApellido);
        txtUsuario.textProperty().bindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().bindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().bindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().bindBidirectional(empleadoDto.fechaIngreso);
        dtpFSalida.valueProperty().bindBidirectional(empleadoDto.fechaSalida);
        chkActivo.selectedProperty().bindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().bindBidirectional(empleadoDto.administrador);
        BindingUtils.bindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }

    private void unbindEmpleado() {
        txtId.textProperty().unbind();
        txtCedula.textProperty().unbindBidirectional(empleadoDto.cedula);
        txtNombre.textProperty().unbindBidirectional(empleadoDto.nombre);
        txtPApellido.textProperty().unbindBidirectional(empleadoDto.primerApellido);
        txtSApellido.textProperty().unbindBidirectional(empleadoDto.segundoApellido);
        txtUsuario.textProperty().unbindBidirectional(empleadoDto.usuario);
        txtClave.textProperty().unbindBidirectional(empleadoDto.clave);
        txtCorreo.textProperty().unbindBidirectional(empleadoDto.correo);
        dtpFIngreso.valueProperty().unbindBidirectional(empleadoDto.fechaIngreso);
        dtpFSalida.valueProperty().unbindBidirectional(empleadoDto.fechaSalida);
        chkActivo.selectedProperty().unbindBidirectional(empleadoDto.estado);
        chkAdministrador.selectedProperty().unbindBidirectional(empleadoDto.administrador);
        BindingUtils.unbindToggleGroupToProperty(tggGenero, empleadoDto.genero);
    }
    
    private void validarAdministrador() {
        if (chkAdministrador.isSelected()) {
            requeridos.addAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.setDisable(false);
            txtClave.setDisable(false);
        } else {
            requeridos.removeAll(Arrays.asList(txtUsuario, txtClave));
            txtUsuario.clear();
            txtUsuario.setDisable(true);
            txtClave.clear();
            txtClave.setDisable(true);
        }
    }
    
    @FXML
    private void onKeyPressedTxtId(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !txtId.getText().isEmpty()) {
            cargarEmpleado(Long.valueOf(txtId.getText()));
        }
    }

    private void cargarEmpleado(Long id) {
        try {
            EmpleadoService service = new EmpleadoService();
            Respuesta respuesta = service.getEmpleado(id);
            if (respuesta.getEstado()) {
                unbindEmpleado();
                this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                bindEmpleado(false);
                validarAdministrador();
                validarRequeridos();
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error consultando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Empleado", getStage(), "Ocurrio un error consultando el empleado.");
        }
    }
    
    @FXML
    private void onActionChkAdministrador(ActionEvent event) {
        validarAdministrador();
    }

    @FXML
    private void onActionBtnNuevo(ActionEvent event) {
        if (new Mensaje().showConfirmation("Limpiar Empleado", getStage(), "¿Esta seguro que desea limpiar el registro?")) {
            nuevoEmpleado();
        }
    }

    @FXML
    private void onActionBtnGuardar(ActionEvent event) {
        try {
            String invalidos = validarRequeridos();
            if (!invalidos.isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), invalidos);
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.guardarEmpleado(empleadoDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar empleado", getStage(), respuesta.getMensaje());
                } else {
                    unbindEmpleado();
                    this.empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    bindEmpleado(false);
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar empleado", getStage(), "Empleado actualizado correctamente.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error guardando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar Empleado", getStage(), "Ocurrio un error guardando el empleado.");
        }
    }

    @FXML
    private void buscarEmpleado(ActionEvent event) {
        cargarEmpleado(Long.valueOf(txtId.getText()));
    }

    @FXML
    private void eliminarEmpleado(ActionEvent event) {
        try {
            if (this.empleadoDto.getId()== null) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Debe cargar el empleado que desea eliminar.");
            } else {

                EmpleadoService service = new EmpleadoService();
                Respuesta respuesta = service.eliminarEmpleado(this.empleadoDto.getId());
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), respuesta.getMensaje());
                } else {
                    new Mensaje().showModal(Alert.AlertType.INFORMATION, "Eliminar empleado", getStage(), "Empleado eliminado correctamente.");
                    nuevoEmpleado();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error eliminando el empleado.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Eliminar empleado", getStage(), "Ocurrio un error eliminando el empleado.");
        }
    }

}
