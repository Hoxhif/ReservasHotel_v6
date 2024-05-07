package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import javax.naming.OperationNotSupportedException;

public class ControladorVentanaBorrarHuesped {
    @FXML
    private Button btBorrar;

    @FXML
    private TextField tfDNIABorrar;

    @FXML
    void borrarHuesped(ActionEvent event) {
        try{
        for (Huesped h: VistaGrafica.getInstancia().getControlador().getHuespedes()){
            if (h.getDni().equals(tfDNIABorrar.getText())){
                VistaGrafica.getInstancia().getControlador().borrar(h);
            }
        }
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
        }catch (OperationNotSupportedException e){
            e.getMessage();
        }
    }
}
