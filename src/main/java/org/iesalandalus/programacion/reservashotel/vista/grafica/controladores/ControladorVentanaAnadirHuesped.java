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
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.DateTimeException;
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
        try{
        try{
        try {
            Huesped huesped = new Huesped(tfNombre.getText(), tfDNI.getText(), tfCorreo.getText(), tfTelefono.getText(), LocalDate.parse(tfFechaNac.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            VistaGrafica.getInstancia().getControlador().insertar(huesped);
            Dialogos.mostrarDialogoInformacion("Creación Huesped", "Se ha insertado correctamente el huesped.");
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow(); //Sacado de ChatGPT... un poco raro pero tiene su sentido...
            stage.close();
        }catch (IllegalArgumentException | OperationNotSupportedException e){
            Dialogos.mostrarDialogoError("Error al crear Huesped", e.getMessage());
        }
        }catch (DateTimeException e){
            Dialogos.mostrarDialogoError("Error al crear Huesped", "La fecha no es válida.");
        }
    }catch (NullPointerException e){
            Dialogos.mostrarDialogoError("Error al crear Huesped.", "No puede haber campos nulos.");
        }
    }
}

