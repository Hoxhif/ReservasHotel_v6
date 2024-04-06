package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import javax.print.Doc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class Reservas implements IReservas{

    private static final String COLECCION="reservas";
    private MongoCollection<Document> coleccionReservas;

    public Reservas (){
        comenzar();
    }

    @Override
    public ArrayList<Reserva> get(){

        ArrayList<Reserva> copiaReservas= new ArrayList<>();
        // El orderBy lo he encontrado en esta página web: https://www.mongodb.com/docs/drivers/java/sync/v4.3/fundamentals/crud/read-operations/sort/
        //FindIterable<Document> coleccionReservas = this.coleccionReservas.find().sort(Sorts.orderBy(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA),Sorts.ascending(MongoDB.IDENTIFICADOR)));
        // El enunciado pone que solo es por fecha de incio de reserva
        FindIterable<Document> coleccionReservas = this.coleccionReservas.find().sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document documentoReserva: coleccionReservas){
            Reserva reserva = new Reserva(MongoDB.getReserva(documentoReserva));
            if (!documentoReserva.getString(MongoDB.CHECKIN).equals("No registrado")){
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if (!documentoReserva.getString(MongoDB.CHECKOUT).equals("No registrado")){
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            copiaReservas.add(reserva);
        }
        return copiaReservas;

    }


    @Override
    public int getTamano() {
        return (int) coleccionReservas.countDocuments();
    }

    @Override
    public void insertar (Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (buscar(reserva)!=null)
                throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        coleccionReservas.insertOne(MongoDB.getDocumento(reserva));
    }


    @Override
    public Reserva buscar (Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        Document documentoReserva = coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR, reserva.getHabitacion().getIdentificador())
                )).first();
        if (documentoReserva != null)
            return new Reserva(MongoDB.getReserva(documentoReserva));
        return null;
    }

    @Override
    public void borrar (Reserva reserva)throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (buscar(reserva)==null)
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        coleccionReservas.deleteOne(MongoDB.getDocumento(reserva));
    }


    @Override
    public ArrayList<Reserva> getReservas (Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");

        ArrayList<Reserva> copiaReservaHuesped= new ArrayList<>();
        FindIterable<Document> documentosReservasHuesped = coleccionReservas.find().filter(eq(MongoDB.HUESPED_DNI, huesped.getDni())).sort(Sorts.ascending(MongoDB.FECHA_INICIO_RESERVA));

        for (Document documentoReserva: documentosReservasHuesped){
            Reserva reserva= MongoDB.getReserva(documentoReserva);
            if (!documentoReserva.getString(MongoDB.CHECKIN).equals("No registrado")){
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if (!documentoReserva.getString(MongoDB.CHECKOUT).equals("No registrado")){
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            copiaReservaHuesped.add(reserva);
        }
        return copiaReservaHuesped;
    }

    @Override
    public ArrayList<Reserva> getReservas (TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        String tipo [] = tipoHabitacion.toString().split(" ");

        ArrayList<Reserva> copiaReservaTipoHabitacion= new ArrayList<>();
        FindIterable<Document> documentosReservaTipoHabitacion = coleccionReservas.find().filter(eq(MongoDB.HABITACION_TIPO,tipo[1]));
        for (Document documentoReserva: documentosReservaTipoHabitacion){
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if (!documentoReserva.getString(MongoDB.CHECKIN).equals("No registrado")){
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if (!documentoReserva.getString(MongoDB.CHECKOUT).equals("No registrado")){
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            copiaReservaTipoHabitacion.add(reserva);
        }

        return copiaReservaTipoHabitacion;
    }

    /*
    En este caso, como necesitamos obtener las fechas posteriores a las que tenemos, primero inicializamos una variable de tipo LocalDate con el valor de la fecha actual y entonces hacemos lo mismo que antes verificando la fecha actual.
     */

    @Override
    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        ArrayList<Reserva> reservasHabitacion = new ArrayList<>();
        FindIterable<Document> documentosReservasHabitacion = coleccionReservas.find().filter(eq(MongoDB.HABITACION_IDENTIFICADOR,habitacion.getIdentificador()));
        for (Document documentoReserva: documentosReservasHabitacion){
            Reserva reserva = MongoDB.getReserva(documentoReserva);
            if (documentoReserva.getString(MongoDB.CHECKIN) != null || documentoReserva.getString(MongoDB.CHECKIN).equals("No registrado")){
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if (documentoReserva.getString(MongoDB.CHECKOUT)!= null || documentoReserva.getString(MongoDB.CHECKOUT).equals("No registrado")){
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReserva.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            reservasHabitacion.add(reserva);
        }
        return reservasHabitacion;
    }

    @Override
    public ArrayList<Reserva> getReservasFuturas (Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        LocalDate fechaActual = LocalDate.now();
        ArrayList<Reserva> reservasHabitacionFuturas = new ArrayList<>();
        //FindIterable<Document> documentosReservasFuturas = coleccionReservas.find().filter(eq(MongoDB.FECHA_INICIO_RESERVA,fechaActual.toString().formatted(MongoDB.FORMATO_DIA)));
        FindIterable<Document> documentosReservasFuturas = coleccionReservas.find().filter(and(
                eq(MongoDB.FECHA_INICIO_RESERVA,fechaActual.toString().formatted(MongoDB.FORMATO_DIA)),
                eq(MongoDB.HABITACION_IDENTIFICADOR,habitacion.getIdentificador())
        ));

        for (Document documentoReservaFutura: documentosReservasFuturas){
            Reserva reserva = MongoDB.getReserva(documentoReservaFutura);
            if (documentoReservaFutura.getString(MongoDB.CHECKIN) != null || documentoReservaFutura.getString(MongoDB.CHECKIN).equals("No registrado")){
                LocalDateTime fechaCheckIn = LocalDateTime.parse(documentoReservaFutura.getString(MongoDB.CHECKIN),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckIn(fechaCheckIn);
            }
            if (documentoReservaFutura.getString(MongoDB.CHECKOUT)!= null || documentoReservaFutura.getString(MongoDB.CHECKOUT).equals("No registrado")){
                LocalDateTime fechaCheckOut = LocalDateTime.parse(documentoReservaFutura.getString(MongoDB.CHECKOUT),MongoDB.FORMATO_DIA_HORA);
                reserva.setCheckOut(fechaCheckOut);
            }
            reservasHabitacionFuturas.add(reserva);
        }

        return reservasHabitacionFuturas;
    }

    @Override
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null | fecha == null)
            throw new NullPointerException("Ni la reserva ni la fecha pueden ser nulas.");

        Document documentoCheckInReservas = coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())
        )).first();
        if (documentoCheckInReservas != null){
            reserva.setCheckIn(fecha);
            String cadenaFecha= fecha.format(MongoDB.FORMATO_DIA_HORA);
            coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKIN,documentoCheckInReservas.getString(MongoDB.CHECKIN)), Updates.set(MongoDB.CHECKIN, cadenaFecha));
        }

    }

    @Override
    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        if (reserva == null | fecha == null)
            throw new NullPointerException("Ni la reserva ni la fecha pueden ser nulas.");

        Document documentoCheckOutReservas = coleccionReservas.find().filter(and(
                eq(MongoDB.HUESPED_DNI,reserva.getHuesped().getDni()),
                eq(MongoDB.HABITACION_IDENTIFICADOR,reserva.getHabitacion().getIdentificador())
        )).first();
        if (documentoCheckOutReservas != null){
            reserva.setCheckOut(fecha);
            String cadenaFecha= fecha.format(MongoDB.FORMATO_DIA_HORA);
            coleccionReservas.updateOne(Filters.eq(MongoDB.CHECKOUT,documentoCheckOutReservas.getString(MongoDB.CHECKOUT)), Updates.set(MongoDB.CHECKOUT, cadenaFecha));
            coleccionReservas.updateOne(Filters.eq(MongoDB.PRECIO_RESERVA, documentoCheckOutReservas.getDouble(MongoDB.PRECIO_RESERVA)),Updates.set(MongoDB.PRECIO_RESERVA, reserva.getPrecio()));
        }

    }

            @Override

            public void comenzar(){
                coleccionReservas = MongoDB.getBD().getCollection(COLECCION);
            }

            @Override
            public void terminar(){
                MongoDB.cerrarConexion();
            }

}
