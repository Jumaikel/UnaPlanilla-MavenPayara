package cr.ac.una.unaplanilla.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.unaplanilla.model.TipoPlanillaDto;
import cr.ac.una.unaplanilla.service.TipoPlanillaService;
import cr.ac.una.unaplanilla.util.FlowController;
import cr.ac.una.unaplanilla.util.Formato;
import cr.ac.una.unaplanilla.util.Mensaje;
import cr.ac.una.unaplanilla.util.Respuesta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jumaikel
 */
public class BuscarViewController extends Controller implements Initializable {

    @FXML
    private JFXTextField txtfCodigo;
    @FXML
    private JFXTextField txtfDescripcion;
    @FXML
    private JFXButton btnAgregar;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private TableView<TipoPlanillaDto> tblvTiposPlanilla;
    @FXML
    private TableColumn<TipoPlanillaDto, String> colId;
    @FXML
    private TableColumn<TipoPlanillaDto, String> colCodigo;
    @FXML
    private TableColumn<TipoPlanillaDto, String> colDescripcion;
    @FXML
    private TableColumn<TipoPlanillaDto, String> colEstado;
    @FXML
    private JFXComboBox<String> cmbEstado;

    ObservableList<String> estados = FXCollections.observableArrayList("Estado", "Activo", "Inactivo");
    ObservableList<TipoPlanillaDto> tiposPlanilla;

    List<TipoPlanillaDto> tiposPlanillaDtoList;
    TipoPlanillaDto seleccion;
    TiposPlanillaViewController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtfCodigo.setTextFormatter(Formato.getInstance().maxLengthFormat(4));
        txtfDescripcion.setTextFormatter(Formato.getInstance().letrasFormat(40));
        cmbEstado.setItems(estados);

        controller = (TiposPlanillaViewController) FlowController.getInstance().getController("TiposPlanillaView");

        cargarListaTiposPlanilla();

        if (tiposPlanillaDtoList != null) {
            setTableColums();
            txtfCodigo.textProperty().addListener((observable, oldValue, newValue) -> filtrar());
            txtfDescripcion.textProperty().addListener((observable, oldValue, newValue) -> filtrar());
            cmbEstado.valueProperty().addListener((observable, oldValue, newValue) -> filtrar());
        }

    }

    @Override
    public void initialize() {

    }

    @FXML
    private void onMouseClickedAgregar(MouseEvent event) {
        if (seleccion != null) {
            controller.cargarTipoPlanilla(seleccion);
            getStage().close();
            FlowController.getInstance().deleteView("BuscarView");
        }
    }

    @FXML
    private void onMouseClickedSalir(MouseEvent event) {
        FlowController.getInstance().deleteView("BuscarView");
        ((Stage) btnSalir.getScene().getWindow()).close();
    }

    @FXML
    private void onMouseClickedTblvTipoPlanilla(MouseEvent event) {
        seleccion = tblvTiposPlanilla.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2) {
            seleccion = tblvTiposPlanilla.getSelectionModel().getSelectedItem();
            if (seleccion != null) {
                controller.cargarTipoPlanilla(seleccion);
                getStage().close();
                FlowController.getInstance().deleteView("BuscarView");
            }
        }
    }

    void setTableColums() {
        colId.setCellValueFactory(cd -> cd.getValue().id);
        colCodigo.setCellValueFactory(cd -> cd.getValue().codigo);
        colDescripcion.setCellValueFactory(cd -> cd.getValue().descripcion);

        colEstado.setCellValueFactory(cellData -> {
            if (cellData.getValue().getEstado().equals("A")) {
                return new SimpleStringProperty("Activo");
            } else {
                return new SimpleStringProperty("Inactivo");
            }
        });
        tiposPlanilla = FXCollections.observableArrayList(tiposPlanillaDtoList);
        tblvTiposPlanilla.setItems(tiposPlanilla);
    }

    void cargarListaTiposPlanilla() {
        try {
            TipoPlanillaService service = new TipoPlanillaService();
            Respuesta respuesta = service.getTiposPlanilla();
            if (respuesta.getEstado()) {
                tiposPlanillaDtoList = (List<TipoPlanillaDto>) respuesta.getResultado("TiposPlanilla");
            } else {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipo Planilla", getStage(), respuesta.getMensaje());
            }
        } catch (Exception ex) {
            Logger.getLogger(EmpleadosViewController.class.getName()).log(Level.SEVERE, "Error consultando los tipos de planilla.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar Tipos Planilla", getStage(), "Ocurrio un error consultando los tipos de planilla.");
        }
    }

    private void filtrar() {
        String codigo = txtfCodigo.getText();
        String descripcion = txtfDescripcion.getText();
        String estado = cmbEstado.getValue();

        List<TipoPlanillaDto> resultado = tiposPlanillaDtoList.stream()
                .filter(item -> item.getCodigo().contains(codigo))
                .filter(item -> item.getDescripcion().contains(descripcion))
                .filter(item -> estado == null || estado.isEmpty() || estado == "Estado" || item.getEstado().equals(estado.equals("Activo") ? "A" : "I"))
                .collect(Collectors.toList());

        tiposPlanilla.clear();
        tiposPlanilla.addAll(resultado);
    }
}
