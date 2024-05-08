package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorVentanaBorrarHabitacion {
    @FXML
    private Button btBorrar;

    @FXML
    private TextField tfIdentificadorABorrar;

    @FXML
    void borrarHabitacion(ActionEvent event) {
        try{
            boolean encontrado = false;
            for (Habitacion h: VistaGrafica.getInstancia().getControlador().getHabitaciones()){
                if (h.getIdentificador().equals(tfIdentificadorABorrar.getText())){
                    encontrado = true;
                    VistaGrafica.getInstancia().getControlador().borrar(h);
                    Dialogos.mostrarDialogoInformacion("Borrado Habitación", "Se ha borrado correctamente la habitación.");
                }
            }
            if (!encontrado) {
                Dialogos.mostrarDialogoAdvertencia("Borrado Habitación", "La habitación seleccionada no existe.");
                tfIdentificadorABorrar.deleteText(0,tfIdentificadorABorrar.getText().length());
            }
            if (encontrado) {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.close();
            }
        }catch (OperationNotSupportedException e){
            e.getMessage();
        }
    }

}
