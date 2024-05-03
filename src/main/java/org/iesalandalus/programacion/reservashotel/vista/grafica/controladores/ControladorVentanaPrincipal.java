package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;

import java.time.LocalDate;

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
        private TableView<?> tbHuesped2;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1dni;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1email;

        @FXML
        private TableColumn<Huesped, LocalDate> cltbHuesped1fechaNacimiento;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1nombre;

        @FXML
        private TableColumn<Huesped, String> cltbHuesped1telefono;


        // Observables colecciones
        private static final ObservableList<Huesped> HUESPEDES = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getHuespedes());

        @FXML
        void mostrarHuespedes(ActionEvent event) {
            System.out.println("Hola :D");


            cltbHuesped1nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            cltbHuesped1dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
            cltbHuesped1telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            cltbHuesped1email.setCellValueFactory(new PropertyValueFactory<>("correo"));
            cltbHuesped1fechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
            tbHuesped1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tbHuesped1.setItems(HUESPEDES);
            //mainvbox.getChildren().addAll(tbHuesped1);
        }

    }

