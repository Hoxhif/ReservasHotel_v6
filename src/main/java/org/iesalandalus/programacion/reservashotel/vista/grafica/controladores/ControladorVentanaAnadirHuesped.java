package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorVentanaAnadirHuesped {
    @FXML
    private Button btConfirmar;
    @FXML
    private TextField tfCorreo;

    @FXML
    private TextField tfDNI;

    @FXML
    private TextField tfFechaNac;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    void insertarHuesped(ActionEvent event) {
        Huesped huesped = new Huesped(tfNombre.getText(), tfDNI.getText(), tfCorreo.getText(), tfTelefono.getText(), LocalDate.parse(tfFechaNac.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        try {
            VistaGrafica.getInstancia().getControlador().insertar(huesped);
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); //Sacado de ChatGPT... un poco raro pero tiene su sentido...
            stage.close();
        }catch (OperationNotSupportedException e){
            e.getMessage();
        }
        }

}

