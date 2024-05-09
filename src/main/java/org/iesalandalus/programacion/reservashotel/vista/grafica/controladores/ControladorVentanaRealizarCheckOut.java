package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControladorVentanaRealizarCheckOut {
    @FXML
    private Button btBuscarReservas;

    @FXML
    private TextField tfDNIHuesped;

    @FXML
    private TableView<Reserva> tvListadoReservas;

    @FXML
    void listarReservas(ActionEvent event) {
        String dni = tfDNIHuesped.getText();
        ArrayList<Reserva> listadoReservas = null;
        for (Huesped h : VistaGrafica.getInstancia().getControlador().getHuespedes()) {
            if (h.getDni().equals(dni)) {
                listadoReservas = (VistaGrafica.getInstancia().getControlador().getReserva(h));
            }
        }
        if (listadoReservas == null) {
            Dialogos.mostrarDialogoAdvertencia("Realizar CheckOut Reserva", "No se ha encontrado reservas para el huesped indicada o no existe.");
        }
        if (listadoReservas != null) {
            ObservableList<Reserva> obsReserva = FXCollections.observableArrayList(listadoReservas);

            TableColumn<Reserva, String> cltbPlantaHabitacion = new TableColumn<>("Planta Habitación");
            TableColumn<Reserva, String> cltbPuertaHabitacion = new TableColumn<>("Puerta Habitación");
            TableColumn<Reserva, String> cltbDNIHuesped = new TableColumn<>("DNI Huesped");
            TableColumn<Reserva, String> cltbFechaInicio = new TableColumn<>("Fecha Inicio Reserva");
            TableColumn<Reserva, String> cltbFechaFin = new TableColumn<>("Fecha Fin Reserva");

            tvListadoReservas.getColumns().add(cltbDNIHuesped);
            tvListadoReservas.getColumns().add(cltbPlantaHabitacion);
            tvListadoReservas.getColumns().add(cltbPuertaHabitacion);
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

            tvListadoReservas.setOnMouseClicked(event1 -> {
                int linea = tvListadoReservas.getSelectionModel().getSelectedIndex();
                TableColumn<Reserva, ?> columna = tvListadoReservas.getFocusModel().getFocusedCell().getTableColumn();
                if (linea >= 0 && columna != null){
                    if (Dialogos.mostrarDialogoConfirmacion("Realizar CheckOut", "¿Desea realizar el CheckOut a esta reserva?")){
                        LocalDateTime fechaCheckOut = LocalDateTime.now();
                        Reserva reserva = tvListadoReservas.getItems().get(linea);
                        if (reserva.getCheckIn()==null){
                            Dialogos.mostrarDialogoError("Realizar CheckOut","Antes de realizar el CheckOut, la reserva debe tener hecho un CheckIn.");}
                        else{
                        try {
                            try {
                                if (tvListadoReservas.getItems().get(linea).getCheckOut()==null) {
                                    VistaGrafica.getInstancia().getControlador().realizarCheckout(tvListadoReservas.getItems().get(linea), fechaCheckOut);
                                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                    stage.close();
                                }else{
                                    Dialogos.mostrarDialogoInformacion("Realizar CheckOut", "Esta reserva ya tiene checkOut.");
                                }
                            }catch (IllegalArgumentException e){
                                Dialogos.mostrarDialogoError("Realizar CheckOut", e.getMessage());
                            }
                        }catch (NullPointerException e){
                            Dialogos.mostrarDialogoError("Realizar CheckOut", e.getMessage());
                        }
                        }
                    }
                }
            }
            );

            tvListadoReservas.setItems(obsReserva);
            tvListadoReservas.setOpacity(0.67);
        }
    }
}
