package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ControladorVentanaPrincipal {



        @FXML
        private VBox mainvbox;

        // Botones menú principal

        @FXML
        private Button btHuesped;

        // Tabla Huespeded 1

        @FXML
        private TableView<Huesped> tbHuesped1;

        @FXML
        private TableView<Reserva> tbHuesped2;

        /*@FXML
        private TableColumn<Huesped, String> cltbHuesped1dni;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1email;

        @FXML
        private TableColumn<Huesped, LocalDate> cltbHuesped1fechaNacimiento;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1nombre;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1telefono;*/


        // Observables colecciones
        private static final ObservableList<Huesped> HUESPEDES = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getHuespedes());
        private static final ObservableList<Reserva> RESERVAS = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getReservas());

        @FXML
        void mostrarHuespedes(ActionEvent event) {
            System.out.println("Hola :D");
            TableColumn<Huesped, String> cltbHuesped1nombre = new TableColumn<>("Nombre");
            TableColumn<Huesped, String> cltbHuesped1dni  = new TableColumn<>("DNI");
            TableColumn<Huesped, LocalDate> cltbHuesped1telefono = new TableColumn<>("Teléfono");
            TableColumn<Huesped, String>cltbHuesped1email = new TableColumn<>("Email");
            TableColumn<Huesped, String> cltbHuesped1fechaNacimiento = new TableColumn<>("Fecha Nacimiento");

            tbHuesped1.getColumns().add(cltbHuesped1nombre);
            tbHuesped1.getColumns().add(cltbHuesped1dni);
            tbHuesped1.getColumns().add(cltbHuesped1telefono);
            tbHuesped1.getColumns().add(cltbHuesped1email);
            tbHuesped1.getColumns().add(cltbHuesped1fechaNacimiento);
            //tbHuesped1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            cltbHuesped1nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            cltbHuesped1nombre.setMinWidth(300);
            cltbHuesped1dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
            cltbHuesped1dni.setMinWidth(200);
            cltbHuesped1telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            cltbHuesped1telefono.setMinWidth(200);
            cltbHuesped1email.setCellValueFactory(new PropertyValueFactory<>("correo"));
            cltbHuesped1email.setMinWidth(300);
            cltbHuesped1fechaNacimiento.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbHuesped1fechaNacimiento.setMinWidth(150);

            tbHuesped1.setItems(HUESPEDES);
            //mainvbox.getChildren().addAll(tbHuesped1);
        }

        @FXML
        void seleccionHuesped(MouseEvent event) {
            // He decidido que estos serán los datos a poner en la tabla de reservas que hay cuando se selecciona un Huesped
            // Para dejar otros datos para la opción de reservas.

            Huesped huesped = tbHuesped1.getSelectionModel().getSelectedItem();
            ObservableList<Reserva> reservaObservable=null;

            for (Reserva reserva: RESERVAS) {
                if (reserva.getHuesped().equals(huesped)){
                    reservaObservable = FXCollections.observableArrayList(reserva);
            }

            TableColumn<Reserva, String> cltb2Planta = new TableColumn<>("Planta");
            TableColumn<Reserva, String> cltb2Puerta = new TableColumn<>("Puerta");
            TableColumn<Reserva, String> cltb2Identificador = new TableColumn<>("Identificador");
            TableColumn<Reserva, String> cltb2FechaInicio = new TableColumn<>("Fecha Inicio Reserva");
            TableColumn<Reserva, String>cltb2FechaFin = new TableColumn<>("Fecha Fin Reserva");

            tbHuesped2.getColumns().add(cltb2Planta);
            tbHuesped2.getColumns().add(cltb2Puerta);
            tbHuesped2.getColumns().add(cltb2Identificador);
            tbHuesped2.getColumns().add(cltb2FechaInicio);
            tbHuesped2.getColumns().add(cltb2FechaFin);

            cltb2Planta.setCellValueFactory(habitacion-> new SimpleStringProperty(""+habitacion.getValue().getHabitacion().getPlanta()));
            //cltb2Planta.setCellValueFactory(new PropertyValueFactory<>(""));
            cltb2Planta.setMinWidth(100);
            cltb2Puerta.setCellValueFactory(habitacion-> new SimpleStringProperty(""+habitacion.getValue().getHabitacion().getPuerta()));
            cltb2Puerta.setMinWidth(100);
            cltb2Identificador.setCellValueFactory(habitacion-> new SimpleStringProperty(""+habitacion.getValue().getHabitacion().getIdentificador()));
            cltb2Identificador.setMinWidth(200);
            cltb2FechaInicio.setCellValueFactory(reservas-> new SimpleStringProperty(reservas.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltb2FechaInicio.setMinWidth(300);
            cltb2FechaFin.setCellValueFactory(reservas-> new SimpleStringProperty(reservas.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltb2FechaFin.setMinWidth(150);

            tbHuesped2.setItems(reservaObservable);

            }
            }

        }


