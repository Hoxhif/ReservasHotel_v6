package org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ControladorVentanaComprobarDisponibilidad {
    @FXML
    private Button btComprobar;

    @FXML
    private DatePicker dpFechaFin;

    @FXML
    private DatePicker dpFechaInicio;

    @FXML
    private TextField tfIdentificadorAComprobar;

    private Habitacion comprobador(Habitacion habitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva){

        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;


        if (habitacion==null)
            return habitacionDisponible;



                ArrayList<Reserva> reservasFuturas = VistaGrafica.getInstancia().getControlador().getReservas(habitacion);
                numElementos= reservasFuturas.size();

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est? disponible.
                    if (habitacion instanceof Simple)
                        habitacionDisponible=new Simple((Simple)habitacion);
                    else if (habitacion instanceof Doble)
                        habitacionDisponible=new Doble((Doble)habitacion);
                    else if (habitacion instanceof Triple)
                        habitacionDisponible=new Triple((Triple)habitacion);
                    else if (habitacion instanceof Suite)
                        habitacionDisponible=new Suite((Suite)habitacion);
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici?n 0), quiere decir que la habitaci?n est? disponible en las fechas indicadas.


                    Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaFinReserva).reversed());




                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                        if (habitacion instanceof Simple)
                            habitacionDisponible=new Simple((Simple)habitacion);
                        else if (habitacion instanceof Doble)
                            habitacionDisponible=new Doble((Doble)habitacion);
                        else if (habitacion instanceof Triple)
                            habitacionDisponible=new Triple((Triple)habitacion);
                        else if (habitacion instanceof Suite)
                            habitacionDisponible=new Suite((Suite)habitacion);
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posici?n 0), quiere decir que la habitaci?n est? disponible en las fechas indicadas.

                        // Esta fue una solución que me proporcionó chatGPT para poder resolverlo, no sabía que hacer aqui sino.
                        Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaInicioReserva));




                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                            if (habitacion instanceof Simple)
                                habitacionDisponible=new Simple((Simple)habitacion);
                            else if (habitacion instanceof Doble)
                                habitacionDisponible=new Doble((Doble)habitacion);
                            else if (habitacion instanceof Triple)
                                habitacionDisponible=new Triple((Triple)habitacion);
                            else if (habitacion instanceof Suite)
                                habitacionDisponible=new Suite((Suite)habitacion);
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est?n alg?n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {

                                    //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                                    if (habitacion instanceof Simple)
                                        habitacionDisponible=new Simple((Simple)habitacion);
                                    if (habitacion instanceof Doble)
                                        habitacionDisponible=new Doble((Doble)habitacion);
                                    if (habitacion instanceof Triple)
                                        habitacionDisponible=new Triple((Triple)habitacion);
                                    if (habitacion instanceof Suite)
                                        habitacionDisponible=new Suite((Suite)habitacion);
                                }
                            }
                        }

        return habitacionDisponible;

    }

    @FXML
    void comprobarDisponibilidad(ActionEvent event) {
        if (dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null) {
            Dialogos.mostrarDialogoError("Error Comprobar Habitación", "Las fechas no pueden ser nulas.");
        } else {
            boolean habitacionEncontrada = false;
            for (Habitacion h : VistaGrafica.getInstancia().getControlador().getHabitaciones()) {
                if (h.getIdentificador().equals(tfIdentificadorAComprobar.getText())) {
                    habitacionEncontrada = true;
                    if (comprobador(h, dpFechaInicio.getValue(), dpFechaFin.getValue()) == null)
                        Dialogos.mostrarDialogoInformacion("Comprobar Habitación", "La habitación estará ocupada en estas fechas.");
                    else
                        Dialogos.mostrarDialogoInformacion("Comprobar Habitación", "La habitación estará libre en estas fechas.");
                }
                ;
            }
            if (!habitacionEncontrada) {
                Dialogos.mostrarDialogoAdvertencia("Comprobar Habitación", "La habitación indicada no existe.");
            }
            if (habitacionEncontrada) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            }
        }
    }
}
