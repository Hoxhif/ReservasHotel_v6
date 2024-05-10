package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Regimen;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ControladorVentanaAnadirReserva {
    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Integer> cbNumPersonas;

    @FXML
    private ChoiceBox<Regimen> cbRegimen;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicion;

    @FXML
    private TextField tfDNIHuesped;

    @FXML
    private TextField tfIDHabitacion;

    @FXML
    void inicializaDatos(MouseEvent event) {
        if (cbNumPersonas.getItems().isEmpty()) cbNumPersonas.getItems().addAll(1, 2, 3, 4);
        if (cbRegimen.getItems().isEmpty()) {
            for (Regimen regimen : Regimen.values()) {
                cbRegimen.getItems().add(regimen);
            }
        }
    }

    @FXML
    void insertarReserva(ActionEvent event) {
        if (cbRegimen.getItems().isEmpty() || dpFechaInicion.getValue() == null || dpFechaFin.getValue()==null){
            Dialogos.mostrarDialogoError("Error Creación Reserva","Los campos no pueden ser nulos.");
        }else{
        Huesped huesped = null;
        Habitacion habitacion = null;
        LocalDate fechaI = dpFechaInicion.getValue();
        LocalDate fechaF = dpFechaFin.getValue();
        Regimen regimen = cbRegimen.getValue();
        int numPersonas = cbNumPersonas.getValue();


        for (Huesped h : VistaGrafica.getInstancia().getControlador().getHuespedes()) {
            if (h.getDni().equals(tfDNIHuesped.getText())) huesped = h;
        }

        for (Habitacion h : VistaGrafica.getInstancia().getControlador().getHabitaciones()) {
            if (h.getIdentificador().equals(tfIDHabitacion.getText())) habitacion = h;
        }



        try {
            try {
                VistaGrafica.getInstancia().getControlador().insertar(new Reserva(huesped, habitacion, regimen, fechaI, fechaF, numPersonas));
                Dialogos.mostrarDialogoInformacion("Creación Reserva", "Se ha insertado correctamente la reserva correctamente.");
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); //Sacado de ChatGPT... un poco raro pero tiene su sentido...
                stage.close();
            } catch (IllegalArgumentException | OperationNotSupportedException e) {
                Dialogos.mostrarDialogoError("Error Creación Reserva", e.getMessage());
            }

        }catch (NullPointerException e){
            Dialogos.mostrarDialogoError("Error Creación Reserva", e.getMessage());
        }
    }
    }
}
