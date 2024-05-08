package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControladorVentanaReservasHabitacion {
    @FXML
    private Button btBuscarReservas;

    @FXML
    private TextField tfIdentificadorHabitacion;

    @FXML
    private TableView<Reserva> tvListadoReservas;

    @FXML
    void listarReservas(ActionEvent event) {
        String dni = tfIdentificadorHabitacion.getText();
        ArrayList<Reserva> listadoReservas=null;
        for (Habitacion h: VistaGrafica.getInstancia().getControlador().getHabitaciones()){
            if (h.getIdentificador().equals(dni)){
                listadoReservas=(VistaGrafica.getInstancia().getControlador().getReservas(h));
            }
        }
        if (listadoReservas!= null) {
            ObservableList<Reserva> obsReserva = FXCollections.observableArrayList(listadoReservas);

            TableColumn<Reserva, String> cltbPlantaHabitacion = new TableColumn<>("Planta Habitación");
            TableColumn<Reserva, String> cltbPuertaHabitacion = new TableColumn<>("Puerta Habitación");
            TableColumn<Reserva, String> cltbDNIHuesped = new TableColumn<>("DNI Huesped");
            TableColumn<Reserva, String> cltbFechaInicio = new TableColumn<>("Fecha Inicio Reserva");
            TableColumn<Reserva, String> cltbFechaFin = new TableColumn<>("Fecha Fin Reserva");

            tvListadoReservas.getColumns().add(cltbPlantaHabitacion);
            tvListadoReservas.getColumns().add(cltbPuertaHabitacion);
            tvListadoReservas.getColumns().add(cltbDNIHuesped);
            tvListadoReservas.getColumns().add(cltbFechaInicio);
            tvListadoReservas.getColumns().add(cltbFechaFin);

            cltbPlantaHabitacion.setCellValueFactory(reserva -> new SimpleStringProperty(Integer.toString(reserva.getValue().getHabitacion().getPlanta())));
            cltbPlantaHabitacion.setMinWidth(205);
            cltbPuertaHabitacion.setCellValueFactory(reserva -> new SimpleStringProperty(Integer.toString(reserva.getValue().getHabitacion().getPuerta())));
            cltbPuertaHabitacion.setMinWidth(150);
            cltbDNIHuesped.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));
            cltbDNIHuesped.setMinWidth(150);
            cltbFechaInicio.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaInicio.setMinWidth(150);
            cltbFechaFin.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaFin.setMinWidth(150);

            tvListadoReservas.setItems(obsReserva);
            tvListadoReservas.setOpacity(0.67);
    }
}
}
