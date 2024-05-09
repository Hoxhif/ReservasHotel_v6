package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControladorVentanaReservasHuesped {

    @FXML
    private Button btBuscarReservas;

    @FXML
    private TextField tfDNIHuesped;

    @FXML
    private TableView<Reserva> tvListadoReservas;

    @FXML
    void listarReservas(ActionEvent event) {
        String dni = tfDNIHuesped.getText();
        ArrayList<Reserva> listadoReservas=null;
        for (Huesped h: VistaGrafica.getInstancia().getControlador().getHuespedes()){
            if (h.getDni().equals(dni)){
                listadoReservas=(VistaGrafica.getInstancia().getControlador().getReserva(h));
            }
        }
        if (listadoReservas == null){
            Dialogos.mostrarDialogoAdvertencia("Reservas Huesped", "No se ha encontrado reservas para el huesped indicado o no existe.");
        }
        if (listadoReservas!= null) {
            ObservableList<Reserva> obsReserva = FXCollections.observableArrayList(listadoReservas);

            TableColumn<Reserva, String> cltbnombreHuesped = new TableColumn<>("Nombre Huesped");
            TableColumn<Reserva, String> cltbdni = new TableColumn<>("DNI");
            TableColumn<Reserva, String> cltbIdentificador = new TableColumn<>("Habitación");
            TableColumn<Reserva, String> cltbFechaInicio = new TableColumn<>("Fecha Inicio Reserva");
            TableColumn<Reserva, String> cltbFechaFin = new TableColumn<>("Fecha Fin Reserva");

            tvListadoReservas.getColumns().add(cltbnombreHuesped);
            tvListadoReservas.getColumns().add(cltbdni);
            tvListadoReservas.getColumns().add(cltbIdentificador);
            tvListadoReservas.getColumns().add(cltbFechaInicio );
            tvListadoReservas.getColumns().add(cltbFechaFin);

            cltbnombreHuesped.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHuesped().getNombre()));
            cltbnombreHuesped.setMinWidth(205);
            cltbdni.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHuesped().getDni()));
            cltbdni.setMinWidth(150);
            cltbIdentificador.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getHabitacion().getIdentificador()));
            cltbIdentificador.setMinWidth(150);
            cltbFechaInicio.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaInicio.setMinWidth(150);
            cltbFechaFin.setCellValueFactory(reserva -> new SimpleStringProperty(reserva.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaFin.setMinWidth(150);

            tvListadoReservas.setItems(obsReserva);
            tvListadoReservas.setOpacity(0.67);
        }
    }

}
