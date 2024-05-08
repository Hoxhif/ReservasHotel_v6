package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

import javax.naming.OperationNotSupportedException;

public class ControladorVentanaAnadirHabitacion {

    @FXML
    private Button btConfirmar;

    @FXML
    private ComboBox<Integer> cbBanos;

    @FXML
    private ComboBox<Integer> cbCamasDobles;

    @FXML
    private ComboBox<Integer> cbCamasIndividuales;

    @FXML
    private ChoiceBox<Integer> cbPlanta;

    @FXML
    private ChoiceBox<Integer> cbPuerta;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    private RadioButton rbJacuzzi;
    @FXML
    private TextField tfPrecio;



    @FXML
    void eleccionTipo(ActionEvent event) {
    if (cbTipo.getValue().equals("Simple")){
        cbCamasDobles.setDisable(true);
        cbCamasIndividuales.setDisable(true);
        rbJacuzzi.setDisable(true);
        cbBanos.setDisable(true);
    }
    if (cbTipo.getValue().equals("Doble")){
        rbJacuzzi.setDisable(true);
        cbBanos.setDisable(true);

        cbCamasIndividuales.setDisable(false);
        cbCamasDobles.setDisable(false);
    }
    if (cbTipo.getValue().equals("Triple")){
        rbJacuzzi.setDisable(true);

        cbCamasIndividuales.setDisable(false);
        cbCamasDobles.setDisable(false);
        cbBanos.setDisable(false);
    }
    if (cbTipo.getValue().equals("Suite")){
        cbCamasDobles.setDisable(true);
        cbCamasIndividuales.setDisable(true);

        cbBanos.setDisable(false);
        rbJacuzzi.setDisable(false);
    }
    }

    @FXML
    void insertarHabitacion(ActionEvent event) {
        if (cbTipo.getValue().equals("Simple")){
            try {
                Habitacion habitacionAInsertar = new Simple(cbPlanta.getValue(), cbPuerta.getValue(), Double.parseDouble(tfPrecio.getText()));
                VistaGrafica.getInstancia().getControlador().insertar(habitacionAInsertar);
            }catch (IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error Creación Habitación", e.getMessage());
            }
        }
        if (cbTipo.getValue().equals("Doble")){
            try{
                Habitacion habitacionAInsertar = new Doble(cbPlanta.getValue(), cbPuerta.getValue(), Double.parseDouble(tfPrecio.getText()), cbCamasIndividuales.getValue(), cbCamasDobles.getValue());
                VistaGrafica.getInstancia().getControlador().insertar(habitacionAInsertar);
            }catch (IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error Creación Habitación", e.getMessage());
            }
        }
        if (cbTipo.getValue().equals("Triple")){
            try {
                Habitacion habitacionAInsertar = new Triple(cbPlanta.getValue(), cbPuerta.getValue(), Double.parseDouble(tfPrecio.getText()), cbBanos.getValue(), cbCamasIndividuales.getValue(), cbCamasDobles.getValue());
                VistaGrafica.getInstancia().getControlador().insertar(habitacionAInsertar);
            }catch (IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error Creación Habitación", e.getMessage());
            }
        }
        if (cbTipo.getValue().equals("Suite")){
            try{
                boolean quierejacuzzi;
                if (rbJacuzzi.isSelected()) quierejacuzzi = true;
                else quierejacuzzi = false;
                Habitacion habitacionAInsertar = new Suite(cbPlanta.getValue(), cbPuerta.getValue(), Double.parseDouble(tfPrecio.getText()),cbBanos.getValue(), quierejacuzzi);
                VistaGrafica.getInstancia().getControlador().insertar(habitacionAInsertar);
            }catch (IllegalArgumentException | OperationNotSupportedException e){
                Dialogos.mostrarDialogoError("Error Creación Habitación", e.getMessage());
            }
        }
        Dialogos.mostrarDialogoInformacion("Creación Habitación", "Se ha insertado correctamente la habitación.");
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void inicializaDatos(){
        if (cbPlanta.getItems().isEmpty())
            cbPlanta.getItems().addAll(1,2,3);
        if (cbPuerta.getItems().isEmpty())
            cbPuerta.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        if (cbBanos.getItems().isEmpty())
            cbBanos.getItems().addAll(1,2);
        if (cbCamasIndividuales.getItems().isEmpty())
            cbCamasIndividuales.getItems().addAll(1,2,3);
        if (cbCamasDobles.getItems().isEmpty())
            cbCamasDobles.getItems().addAll(1,2);
        if (cbTipo.getItems().isEmpty())
            cbTipo.getItems().addAll("Simple", "Doble", "Triple", "Suite");


        /*for (int i=0; i<=3; i++){
            insertarPlanta.add(i);
        }
        cbPlanta.getItems().setAll(insertarPlanta);

        for (int i=1; i<=14; i++){
            insertarPuerta.add(i);
        }
        cbPuerta.getItems().setAll(insertarPuerta);

        for (int i=1; i<=3; i++){
            insertarCamasIndividuales.add(i);
        }
        cbCamasIndividuales.getItems().setAll(insertarCamasIndividuales);

        for (int i=0; i<=1; i++){
            insertarCamasDobles.add(i);
        }
        cbCamasDobles.getItems().setAll(insertarCamasDobles);

        for (int i=1; i<=2; i++){
            insertarBanos.add(i);
        }
        cbBanos.getItems().setAll(insertarBanos);*/

        /*
        cbPlanta.setOnAction(boton -> {
            cbPlanta.getItems().add(1);
            cbPlanta.getItems().add(2);
            cbPlanta.getItems().add(3);
        });
        cbPuerta.setOnAction(boton -> {
            cbPuerta.getItems().add(1);
            cbPuerta.getItems().add(2);
            cbPuerta.getItems().add(3);
            cbPuerta.getItems().add(4);
            cbPuerta.getItems().add(5);
            cbPuerta.getItems().add(6);
            cbPuerta.getItems().add(7);
            cbPuerta.getItems().add(8);
            cbPuerta.getItems().add(9);
            cbPuerta.getItems().add(10);
            cbPuerta.getItems().add(11);
            cbPuerta.getItems().add(12);
            cbPuerta.getItems().add(13);
            cbPuerta.getItems().add(14);
        });
        cbCamasIndividuales.setOnAction(boton -> {
            cbCamasIndividuales.getItems().add(1);
            cbCamasIndividuales.getItems().add(2);
            cbCamasIndividuales.getItems().add(3);
        });
        cbCamasDobles.setOnAction(boton -> {
            cbCamasDobles.getItems().add(0);
            cbCamasDobles.getItems().add(1);
        });
        cbBanos.setOnAction(boton -> {
            cbBanos.getItems().add(1);
            cbBanos.getItems().add(2);
            cbBanos.getItems().setAll();
        });
        cbTipo.setOnAction(boton -> {
            Habitacion Simple = new Simple(1,1,50);
            Habitacion Doble = new Doble(1,2,50, 1,0);
            Habitacion Triple = new Triple(1,3,50,1 ,1,0);
            Habitacion Suite = new Suite(1,3,50,1, false);
            cbTipo.getItems().add(Simple);
            cbTipo.getItems().add(Doble);
            cbTipo.getItems().add(Triple);
            cbTipo.getItems().add(Suite);
            cbTipo.getItems().setAll();
        });*/
    }

}

