package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ControladorVentanaPrincipal {
    @FXML
    private HBox barraElementos;
    @FXML
    private HBox barraOpciones;
    @FXML
    private Button btHuespedes;
    @FXML
    private Button btHabitaciones;
    @FXML
    private Button btReservas;
    @FXML
    private VBox tablaListados;
    @FXML
    private ImageView imagenIES;

    private TableView<Huesped> tableviewHuesped = null;
    private TableView<Habitacion> tableviewHabitacion = null;
    private TableView<Reserva> tableviewReservas = null;

    private Button anadirHuesped=null;
    private Button borrarHuesped=null;
    private Button reservasHuesped=null;
    private Button anadirHabitacion=null;
    private Button borrarHabitacion=null;
    private Button reservasHabitacion=null;
    private Button anadirReserva=null;
    private Button anularReserva=null;
    private Button consultarDisponibilidad=null;


    private static final ObservableList<Huesped> HUESPEDES = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getHuespedes());
    private static final ObservableList<Reserva> RESERVAS = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getReservas());
    private static final ObservableList<Habitacion> HABITACIONES = FXCollections.observableArrayList(VistaGrafica.getInstancia().getControlador().getHabitaciones());


    @FXML
    void inicializarUIHuespedes(ActionEvent event) {
        tablaListados.getChildren().remove(imagenIES);
        if (tableviewHabitacion != null) {
            tablaListados.getChildren().remove(tableviewHabitacion);
            tableviewHabitacion = null;
            barraElementos.getChildren().remove(anadirHabitacion);
            anadirHabitacion=null;
            barraElementos.getChildren().remove(borrarHabitacion);
            borrarHabitacion=null;
            barraElementos.getChildren().remove(reservasHabitacion);
            reservasHabitacion=null;
        }
        if (tableviewReservas != null){
            tablaListados.getChildren().remove(tableviewReservas);
            tableviewReservas= null;
            barraElementos.getChildren().remove(anadirReserva);
            anadirReserva=null;
            barraElementos.getChildren().remove(anularReserva);
            anularReserva=null;
            barraElementos.getChildren().remove(consultarDisponibilidad);
            consultarDisponibilidad=null;
        }
        mostrarHuespedes();
        if (anadirHuesped==null) {
            anadirHuesped = new Button("Añadir Huesped");
            borrarHuesped = new Button("Borrar Huesped");
            reservasHuesped = new Button("Reservas del Huesped");

            barraElementos.getChildren().add(anadirHuesped);
            ImageView imagenMas = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/añadir.png");
            imagenMas.setFitHeight(50);
            imagenMas.setFitWidth(50);
            anadirHuesped.setGraphic(imagenMas);

            barraElementos.getChildren().add(borrarHuesped);
            ImageView imagenMenos = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/borrar.png");
            imagenMenos.setFitHeight(50);
            imagenMenos.setFitWidth(50);
            borrarHuesped.setGraphic(imagenMenos);

            barraElementos.getChildren().add(reservasHuesped);
            ImageView imagenReserva = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/verReservas.png");
            imagenReserva.setFitHeight(50);
            imagenReserva.setFitWidth(50);
            reservasHuesped.setGraphic(imagenReserva);
        }
        anadirHuesped.setOnAction(evento -> addHuesped());
        borrarHuesped.setOnAction(evento -> removeHuesped());
        reservasHuesped.setOnAction(evento -> bookingsHuesped());
    }

    @FXML
    void inicializarUIHabitaciones(ActionEvent event) {
        tablaListados.getChildren().remove(imagenIES);
        if (tableviewHuesped != null) {
            tablaListados.getChildren().remove(tableviewHuesped);
            barraElementos.getChildren().remove(anadirHuesped);
            anadirHuesped=null;
            barraElementos.getChildren().remove(borrarHuesped);
            borrarHuesped=null;
            barraElementos.getChildren().remove(reservasHuesped);
            reservasHuesped=null;
            tableviewHuesped = null;
        }
        if (tableviewReservas != null){
            tablaListados.getChildren().remove(tableviewReservas);
            tableviewReservas = null;
            barraElementos.getChildren().remove(anadirReserva);
            anadirReserva=null;
            barraElementos.getChildren().remove(anularReserva);
            anularReserva=null;
            barraElementos.getChildren().remove(consultarDisponibilidad);
            consultarDisponibilidad=null;
        }
        mostrarHabitaciones();
        if (anadirHabitacion==null) {
            anadirHabitacion = new Button("Añadir Habitación");
            borrarHabitacion = new Button("Borrar Habitación");
            reservasHabitacion = new Button("Reservas de la Habitación");

            barraElementos.getChildren().add(anadirHabitacion);
            ImageView imagenMas = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/añadir.png");
            imagenMas.setFitHeight(50);
            imagenMas.setFitWidth(50);
            anadirHabitacion.setGraphic(imagenMas);

            barraElementos.getChildren().add(borrarHabitacion);
            ImageView imagenMenos = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/borrar.png");
            imagenMenos.setFitHeight(50);
            imagenMenos.setFitWidth(50);
            borrarHabitacion.setGraphic(imagenMenos);

            barraElementos.getChildren().add(reservasHabitacion);
            ImageView imagenReserva = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/verReservas.png");
            imagenReserva.setFitHeight(50);
            imagenReserva.setFitWidth(50);
            reservasHabitacion.setGraphic(imagenReserva);
        }
        anadirHabitacion.setOnAction(evento -> addHuesped());
        borrarHabitacion.setOnAction(evento -> removeHuesped());
        reservasHabitacion.setOnAction(evento -> bookingsHuesped());
    }

    @FXML
    void inicializarUIReservas(ActionEvent event){
        tablaListados.getChildren().remove(imagenIES);
        if (tableviewHuesped != null){
            tablaListados.getChildren().remove(tableviewHuesped);
            barraElementos.getChildren().remove(anadirHuesped);
            anadirHuesped=null;
            barraElementos.getChildren().remove(borrarHuesped);
            borrarHuesped=null;
            barraElementos.getChildren().remove(reservasHuesped);
            reservasHuesped=null;
            tableviewHuesped = null;
        }
        if (tableviewHabitacion != null){
            tablaListados.getChildren().remove(tableviewHabitacion);
            tableviewHabitacion = null;
            barraElementos.getChildren().remove(anadirHabitacion);
            anadirHabitacion=null;
            barraElementos.getChildren().remove(borrarHabitacion);
            borrarHabitacion=null;
            barraElementos.getChildren().remove(reservasHabitacion);
            reservasHabitacion=null;
        }
        mostrarReservas();
        if (anadirReserva==null) {
            anadirReserva = new Button("Añadir Reserva");
            anularReserva = new Button("Anular Reserva");
            consultarDisponibilidad = new Button("Consultar Disponibilidad");

            barraElementos.getChildren().add(anadirReserva);
            ImageView imagenMas = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/añadir.png");
            imagenMas.setFitHeight(50);
            imagenMas.setFitWidth(50);
            anadirReserva.setGraphic(imagenMas);

            barraElementos.getChildren().add(anularReserva);
            ImageView imagenMenos = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/borrar.png");
            imagenMenos.setFitHeight(50);
            imagenMenos.setFitWidth(50);
            anularReserva.setGraphic(imagenMenos);

            barraElementos.getChildren().add(consultarDisponibilidad);
            ImageView imagenReserva = new ImageView("org/iesalandalus/programacion/reservashotel/vista/grafica/recursos/imagenes/disponibilidad.png");
            imagenReserva.setFitHeight(50);
            imagenReserva.setFitWidth(50);
            consultarDisponibilidad.setGraphic(imagenReserva);
        }
        anadirReserva.setOnAction(evento -> addHuesped());
        anularReserva.setOnAction(evento -> removeHuesped());
        consultarDisponibilidad.setOnAction(evento -> bookingsHuesped());
    }

    void mostrarHuespedes() {
        if (tableviewHuesped == null) {
            tableviewHuesped = new TableView<>();
            tableviewHuesped.setPrefSize(0, 771);
            tablaListados.getChildren().add(tableviewHuesped);

            TableColumn<Huesped, String> cltbHuesped1nombre = new TableColumn<>("Nombre");
            TableColumn<Huesped, String> cltbHuesped1dni = new TableColumn<>("DNI");
            TableColumn<Huesped, LocalDate> cltbHuesped1telefono = new TableColumn<>("Teléfono");
            TableColumn<Huesped, String> cltbHuesped1email = new TableColumn<>("Email");
            TableColumn<Huesped, String> cltbHuesped1fechaNacimiento = new TableColumn<>("Fecha Nacimiento");

            tableviewHuesped.getColumns().add(cltbHuesped1nombre);
            tableviewHuesped.getColumns().add(cltbHuesped1dni);
            tableviewHuesped.getColumns().add(cltbHuesped1telefono);
            tableviewHuesped.getColumns().add(cltbHuesped1email);
            tableviewHuesped.getColumns().add(cltbHuesped1fechaNacimiento);


            cltbHuesped1nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            cltbHuesped1nombre.setMinWidth(300);
            cltbHuesped1dni.setCellValueFactory(new PropertyValueFactory<>("dni"));
            cltbHuesped1dni.setMinWidth(200);
            cltbHuesped1telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            cltbHuesped1telefono.setMinWidth(200);
            cltbHuesped1email.setCellValueFactory(new PropertyValueFactory<>("correo"));
            cltbHuesped1email.setMinWidth(300);
            cltbHuesped1fechaNacimiento.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbHuesped1fechaNacimiento.setMinWidth(150);

            tableviewHuesped.setItems(HUESPEDES);
            tableviewHuesped.setOpacity(0.67);
        }
    }

    void mostrarHabitaciones() {
        if (tableviewHabitacion == null) {
            tableviewHabitacion = new TableView<>();
            tableviewHabitacion.setPrefSize(0, 771);
            tablaListados.getChildren().add(tableviewHabitacion);

            TableColumn<Habitacion, String> cltbHabitacionPlanta = new TableColumn<>("Planta");
            TableColumn<Habitacion, String> cltbHabitacionPuerta = new TableColumn<>("Puerta");
            TableColumn<Habitacion, LocalDate> cltbHabitacionIdentificador = new TableColumn<>("Identificador");
            TableColumn<Habitacion, String> cltbHabitacionTipo = new TableColumn<>("Tipo");
            TableColumn<Habitacion, String> cltbHabitacionPrecio = new TableColumn<>("Precio");
            TableColumn<Habitacion, String> cltbHabitacionCamasSimples= new TableColumn<>("Nº Camas Simples");
            TableColumn<Habitacion, String> cltbHabitacionCamasDobles = new TableColumn<>("Nº Camas Dobles");
            TableColumn<Habitacion, String> cltbHabitacionBanos = new TableColumn<>("Nº Baños");
            TableColumn<Habitacion, String> cltbHabitacionJacuzzi = new TableColumn<>("Jacuzzi");

            tableviewHabitacion.getColumns().add(cltbHabitacionPlanta);
            tableviewHabitacion.getColumns().add(cltbHabitacionPuerta);
            tableviewHabitacion.getColumns().add(cltbHabitacionIdentificador);
            tableviewHabitacion.getColumns().add(cltbHabitacionTipo);
            tableviewHabitacion.getColumns().add(cltbHabitacionPrecio);
            tableviewHabitacion.getColumns().add(cltbHabitacionCamasSimples);
            tableviewHabitacion.getColumns().add(cltbHabitacionCamasDobles);
            tableviewHabitacion.getColumns().add(cltbHabitacionBanos);
            tableviewHabitacion.getColumns().add(cltbHabitacionJacuzzi);


            cltbHabitacionPlanta.setCellValueFactory(new PropertyValueFactory<>("planta"));
            cltbHabitacionPlanta.setMinWidth(100);
            cltbHabitacionPuerta.setCellValueFactory(new PropertyValueFactory<>("puerta"));
            cltbHabitacionPuerta.setMinWidth(100);
            cltbHabitacionIdentificador.setCellValueFactory(new PropertyValueFactory<>("identificador"));
            cltbHabitacionIdentificador.setMinWidth(100);
            cltbHabitacionTipo.setCellValueFactory(habitacion -> comprobarHabitacion(habitacion.getValue()));
            cltbHabitacionTipo.setMinWidth(100);
            cltbHabitacionPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            cltbHabitacionPrecio.setMinWidth(150);
            cltbHabitacionCamasSimples.setCellValueFactory(tipoHabitacion-> {
                Habitacion habitacion = tipoHabitacion.getValue();
                if (habitacion instanceof Simple) return new SimpleStringProperty("No especificado");
                if (habitacion instanceof Doble) return new SimpleStringProperty(Integer.toString(((Doble) habitacion).getNumCamasIndividuales()));
                if (habitacion instanceof Triple) return new SimpleStringProperty(Integer.toString(((Triple) habitacion).getNumCamasIndividuales()));
                if (habitacion instanceof Suite) return new SimpleStringProperty("No especificado");
                else return new SimpleStringProperty("No especificado");
                    });
            cltbHabitacionCamasSimples.setMinWidth(150);
            cltbHabitacionCamasDobles.setCellValueFactory(tipoHabitacion-> {
                Habitacion habitacion = tipoHabitacion.getValue();
                if (habitacion instanceof Simple) return new SimpleStringProperty("No especificado");
                if (habitacion instanceof Doble) return new SimpleStringProperty(Integer.toString(((Doble) habitacion).getNumCamasDobles()));
                if (habitacion instanceof Triple) return new SimpleStringProperty(Integer.toString(((Triple) habitacion).getNumCamasDobles()));
                if (habitacion instanceof Suite) return new SimpleStringProperty("No especificado");
                else return new SimpleStringProperty("No especificado");
            });
            cltbHabitacionCamasDobles.setMinWidth(150);
            cltbHabitacionBanos.setCellValueFactory(tipoHabitacion-> {
                Habitacion habitacion = tipoHabitacion.getValue();
                if (habitacion instanceof Simple) return new SimpleStringProperty("No especificado");
                if (habitacion instanceof Doble) return new SimpleStringProperty("No especificado");
                if (habitacion instanceof Triple) return new SimpleStringProperty(Integer.toString(((Triple) habitacion).getNumBanos()));
                if (habitacion instanceof Suite) return new SimpleStringProperty(Integer.toString(((Suite) habitacion).getNumBanos()));
                else return new SimpleStringProperty("No especificado");
            });
            cltbHabitacionBanos.setMinWidth(150);
            cltbHabitacionJacuzzi.setCellValueFactory(tipoHabitacion-> {
                Habitacion habitacion = tipoHabitacion.getValue();
                if (habitacion instanceof Simple) return new SimpleStringProperty("No tiene");
                if (habitacion instanceof Doble) return new SimpleStringProperty("No tiene");
                if (habitacion instanceof Triple) return new SimpleStringProperty("No tiene");
                if (habitacion instanceof Suite) return new SimpleStringProperty(Boolean.toString((((Suite) habitacion).isTieneJacuzzi())));
                else return new SimpleStringProperty("No especificado");
            });
            cltbHabitacionJacuzzi.setMinWidth(150);

            tableviewHabitacion.setItems(HABITACIONES);
            tableviewHabitacion.setOpacity(0.67);
        }

    }

    void mostrarReservas(){
        if (tableviewReservas == null) {
            tableviewReservas = new TableView<>();
            tableviewReservas.setPrefSize(0, 771);
            tablaListados.getChildren().add(tableviewReservas);

            TableColumn<Reserva, String> cltbNombreHuesped = new TableColumn<>("Nombre Huesped");
            TableColumn<Reserva, String> cltbDNIHuesped = new TableColumn<>("DNI");
            TableColumn<Reserva, String> cltbIdentificadorHabitacion = new TableColumn<>("Habitación");
            TableColumn<Reserva, String> cltbTipoHabitacion = new TableColumn<>("Tipo de Habitación");
            TableColumn<Reserva, Regimen> cltbRegimen = new TableColumn<>("Regimen");
            TableColumn<Reserva, String> cltbFechaInicio = new TableColumn<>("Fecha Inicio Reserva");
            TableColumn<Reserva, String> cltbFechaFin = new TableColumn<>("Fecha Fin Reserva");
            TableColumn<Reserva, String> cltbCheckIn = new TableColumn<>("Check-In");
            TableColumn<Reserva, String> cltbCheckOut = new TableColumn<>("Check-Out");
            TableColumn<Reserva, String> cltbNumPersonas = new TableColumn<>("Nº Personas");
            TableColumn<Reserva, String> cltbPrecio = new TableColumn<>("Precio Final");


            tableviewReservas.getColumns().add(cltbNombreHuesped);
            tableviewReservas.getColumns().add(cltbDNIHuesped);
            tableviewReservas.getColumns().add(cltbIdentificadorHabitacion);
            tableviewReservas.getColumns().add(cltbTipoHabitacion);
            tableviewReservas.getColumns().add(cltbRegimen);
            tableviewReservas.getColumns().add(cltbFechaInicio);
            tableviewReservas.getColumns().add(cltbFechaFin);
            tableviewReservas.getColumns().add(cltbCheckIn);
            tableviewReservas.getColumns().add(cltbCheckOut);
            tableviewReservas.getColumns().add(cltbNumPersonas);
            tableviewReservas.getColumns().add(cltbPrecio);


            cltbNombreHuesped.setCellValueFactory(huesped -> new SimpleStringProperty(huesped.getValue().getHuesped().getNombre()));
            cltbNombreHuesped.setMinWidth(110);
            cltbDNIHuesped.setCellValueFactory(huesped-> new SimpleStringProperty(huesped.getValue().getHuesped().getDni()));
            cltbDNIHuesped.setMinWidth(80);
            cltbIdentificadorHabitacion.setCellValueFactory(habitacion -> new SimpleStringProperty(habitacion.getValue().getHabitacion().getIdentificador()));
            cltbIdentificadorHabitacion.setMinWidth(100);
            cltbTipoHabitacion.setCellValueFactory(habitacion -> comprobarHabitacion(habitacion.getValue().getHabitacion()));
            cltbTipoHabitacion.setMinWidth(50);
            cltbRegimen.setCellValueFactory(new PropertyValueFactory<>("regimen"));
            cltbRegimen.setMinWidth(150);
            cltbFechaInicio.setCellValueFactory(fechaInicio -> new SimpleStringProperty(fechaInicio.getValue().getFechaInicioReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaInicio.setMinWidth(100);
            cltbFechaFin.setCellValueFactory(fechaFin -> new SimpleStringProperty(fechaFin.getValue().getFechaFinReserva().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
            cltbFechaFin.setMinWidth(100);
            /*cltbCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
            cltbCheckIn.setMinWidth(100);
            cltbCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
            cltbCheckOut.setMinWidth(100);*/
            cltbCheckIn.setCellValueFactory(checkin -> {
                if (checkin.getValue().getCheckIn()==null){
                    return new SimpleStringProperty("No registrado");
                }else return new SimpleStringProperty(checkin.getValue().getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            });
            cltbCheckIn.setMinWidth(100);
            cltbCheckOut.setCellValueFactory(checkout -> {
                if (checkout.getValue().getCheckIn()==null){
                    return new SimpleStringProperty("No registrado");
                }else return new SimpleStringProperty(checkout.getValue().getCheckIn().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            });
            cltbCheckOut.setMinWidth(100);
            cltbNumPersonas.setCellValueFactory(new PropertyValueFactory<>("numeroPersonas"));
            cltbNumPersonas.setMinWidth(50);
            cltbPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            cltbPrecio.setMinWidth(100);

            tableviewReservas.setItems(RESERVAS);
            tableviewReservas.setOpacity(0.67);
        }
    }

    private ObservableValue<String> comprobarHabitacion(Habitacion habitacion){
        if (habitacion instanceof Simple) return new SimpleStringProperty("Simple");
        if (habitacion instanceof Doble) return new SimpleStringProperty("Doble");
        if (habitacion instanceof Triple) return new SimpleStringProperty("Triple");
        if (habitacion instanceof Suite) return new SimpleStringProperty("Suite");
        else return new SimpleStringProperty("Simple");
    }

    private void addHuesped(){
        //System.out.println("Hola, esto funciona");
        FXMLLoader fxmlLoader= new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaAnadirHuesped.fxml"));
        try{
            Parent raiz= fxmlLoader.load();
            Scene scene= new Scene(raiz, 600,400);
            Stage escenarioAnadirHuesped = new Stage();
            escenarioAnadirHuesped.setScene(scene);
            escenarioAnadirHuesped.setTitle("Añadir Huesped");
            escenarioAnadirHuesped.resizableProperty().set(false);
            escenarioAnadirHuesped.initModality(Modality.APPLICATION_MODAL);
            escenarioAnadirHuesped.showAndWait();
            HUESPEDES.setAll(VistaGrafica.getInstancia().getControlador().getHuespedes());
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void removeHuesped(){
        System.out.println("Hola, esto funciona");
    }

    private void bookingsHuesped(){
        System.out.println("Hola, esto funciona");
    }

}

