package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorVentanaBorrarHuesped {
    @FXML
    private Button btBorrar;

    @FXML
    private TextField tfDNIABorrar;

    @FXML
    void borrarHuesped(ActionEvent event) {
        try{
            boolean encontrado = false;
                for (Huesped h : VistaGrafica.getInstancia().getControlador().getHuespedes()) {
                    if (h.getDni().equals(tfDNIABorrar.getText())) {
                        encontrado = true;
                        if (VistaGrafica.getInstancia().getControlador().getReserva(h).isEmpty()) {
                            VistaGrafica.getInstancia().getControlador().borrar(h);
                            Dialogos.mostrarDialogoInformacion("Borrado Huesped", "Se ha borrado correctamente el huesped.");
                        }
                        else{
                            Dialogos.mostrarDialogoError("Borrado Huesped", "No se puede borrar un Huesped con reservas.");
                        }
                    }
                }
                if (!encontrado) {
                    Dialogos.mostrarDialogoAdvertencia("Borrado Huesped", "El huesped seleccionado no existe.");
                    tfDNIABorrar.deleteText(0,tfDNIABorrar.getText().length());
                }

                if (encontrado) {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.close();
                }
        }catch (OperationNotSupportedException e){
            e.getMessage();
        }
    }
}
