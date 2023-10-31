/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.unaplanilla.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.unaplanilla.model.EmpleadoDto;
import cr.ac.una.unaplanilla.service.EmpleadoService;
import cr.ac.una.unaplanilla.util.AppContext;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Carlos
 */
public class LogInViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView imvFondo;
    @FXML
    private JFXTextField txfUsuario;
    @FXML
    private JFXPasswordField psfClave;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnIngresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imvFondo.fitHeightProperty().bind(root.heightProperty());
        imvFondo.fitWidthProperty().bind(root.widthProperty());
    }

    @Override
    public void initialize() {
        txfUsuario.clear();
        psfClave.clear();
    }

    @FXML
    private void onActionBtnIngresar(ActionEvent event) {
        ingresar();
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    @FXML
    private void onKeyPressedIngresar(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            ingresar();
        }
    }

    private void ingresar() {
        try {
            if (txfUsuario.getText() == null || txfUsuario.getText().isBlank()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", getStage(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (psfClave.getText() == null || psfClave.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", getStage(), "Es necesario digitar la clave para ingresar al sistema.");
            } else {
                EmpleadoService empleadoService = new EmpleadoService();
                Respuesta respuesta = empleadoService.getUsuario(txfUsuario.getText(), psfClave.getText());
                if (respuesta.getEstado()) {
                    EmpleadoDto empleadoDto = (EmpleadoDto) respuesta.getResultado("Empleado");
                    AppContext.getInstance().set("Token", empleadoDto.getToken());
                    AppContext.getInstance().set("Usuario", empleadoDto);
                    if (getStage().getOwner() == null) {
                        FlowController.getInstance().goMain();
                    }
                    getStage().close();
                } else {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Validación Usuario", getStage(), respuesta.getMensaje());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(LogInViewController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
            new Mensaje().show(Alert.AlertType.ERROR, "LogIn", "Error ingresando al sistema.");
        }
    }
}
