package org.iesalandalus.programacion.reservashotel.modelo;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Reservas;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Modelo implements IModelo{

    // Aquí hemos copiado y pegado
    private static IHabitaciones habitaciones;
    private static IReservas reservas;
    private static IHuespedes huespedes;
    private IFuenteDatos fuenteDatos;


    public Modelo(FactorialFuenteDatos factorialFuenteDatos){
        setFuenteDatos(factorialFuenteDatos.crear());
    }

    @Override
    public void comenzar(){
        huespedes=fuenteDatos.crearHuespedes();
        habitaciones=fuenteDatos.crearHabitaciones();
        reservas=fuenteDatos.crearReservas();
    }

    @Override
    public void terminar(){
        habitaciones.terminar();
        huespedes.terminar();
        reservas.terminar();
        System.out.println("Fin del programa.");
    }

    public void insertar(Huesped huesped) throws OperationNotSupportedException{
        // Aqui directamente hacemos la llamada al método que insertará el huesped en el array, y como no lo vamos a tratar aqui
        // sino que lo vamos a tratar la excepcion en la vista, pues hacemos un throws.
        huespedes.insertar(huesped);
    }

    public Huesped buscar(Huesped huesped){
        return huespedes.buscar(huesped);
    }

    public void borrar(Huesped huesped) throws OperationNotSupportedException{
        huespedes.borrar(huesped);
    }

    public ArrayList<Huesped> getHuespedes(){
        return huespedes.get();


    }

    public void insertar(Habitacion habitacion)throws OperationNotSupportedException{
        habitaciones.insertar(habitacion);
    }

    public Habitacion buscar(Habitacion habitacion){
        return habitaciones.buscar(habitacion);
    }

    public void borrar(Habitacion habitacion)throws OperationNotSupportedException{
        habitaciones.borrar(habitacion);
    }

    public ArrayList<Habitacion> getHabitaciones(){
        return habitaciones.get();

    }

    public ArrayList<Habitacion> getHabitaciones(TipoHabitacion tipoHabitacion){
        return habitaciones.get(tipoHabitacion);

    }

    public void insertar(Reserva reserva)throws OperationNotSupportedException{
        reservas.insertar(reserva);
    }

    public void borrar(Reserva reserva)throws OperationNotSupportedException{
        reservas.borrar(reserva);
    }

    public Reserva buscar(Reserva reserva){
        return reservas.buscar(reserva);
    }

    public ArrayList<Reserva> getReserva(){
        return reservas.get();
        // He hecho esto porque en el enunciado pone que que devolvemos una lista de objetos nuevos de los geters, y no una copia.
        // LUEGO pregunte a mis compañeros y me dijeron que solo hacia falta el reservas.get();
        /*Reserva[] reservasADevolver = new Reserva[reservas.get().length];

        for (int i=0; i<reservas.get().length;i++){
            reservasADevolver[i]= new Reserva(reservasADevolver[i].getHuesped(), reservasADevolver[i].getHabitacion(), reservasADevolver[i].getRegimen(),reservasADevolver[i].getFechaInicioReserva(), reservasADevolver[i].getFechaFinReserva(),reservasADevolver[i].getNumeroPersonas());
        }
        return reservasADevolver;*/
    }

    public ArrayList<Reserva> getReservas(Huesped huesped){
        return reservas.getReservas(huesped);

    }

    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion){
        return reservas.getReservas(tipoHabitacion);

    }

    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        return reservas.getReservas(habitacion);
    }

    public ArrayList<Reserva> getReservaFutura(Habitacion habitacion){
        return reservas.getReservasFuturas(habitacion);
    }

    public void realizarCheckIn(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckin(reserva,fecha);
    }

    public void realizarCheckOut(Reserva reserva, LocalDateTime fecha){
        reservas.realizarCheckout(reserva,fecha);
    }

    private void setFuenteDatos(IFuenteDatos fuenteDatos){
        this.fuenteDatos=fuenteDatos;
}
}

