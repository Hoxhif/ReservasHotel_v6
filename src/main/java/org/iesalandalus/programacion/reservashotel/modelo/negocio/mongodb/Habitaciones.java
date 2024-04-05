package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;


import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static com.mongodb.client.model.Filters.eq;


public class Habitaciones implements IHabitaciones {

    private MongoCollection<Document> coleccionHabitaciones;
    private static final String COLECCION="habitaciones";

    public Habitaciones(){

    }

    @Override
    public ArrayList<Habitacion> get(){

        ArrayList<Habitacion> copiaHabitacion = new ArrayList<>();
        FindIterable<Document> documentosHabitaciones = coleccionHabitaciones.find().sort(Sorts.ascending(MongoDB.IDENTIFICADOR));

        for (Document documentoHabitacion: documentosHabitaciones){
            Habitacion habitacion = MongoDB.getHabitacion(documentoHabitacion);
            if (habitacion instanceof Simple) copiaHabitacion.add(new Simple((Simple) habitacion));
            if (habitacion instanceof Doble) copiaHabitacion.add(new Doble((Doble) habitacion));
            if (habitacion instanceof Triple) copiaHabitacion.add(new Triple((Triple) habitacion));
            if (habitacion instanceof Suite) copiaHabitacion.add(new Suite((Suite) habitacion));
        }
        return copiaHabitacion;
    }

    @Override
    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion){

        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo // Es posible que se haya equivocado al escribir el DNI.");

        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<>();
        FindIterable<Document> findTipoHabitacion = coleccionHabitaciones.find().filter(eq(MongoDB.TIPO,tipoHabitacion.toString()));
        for (Document documentoHabitacion: findTipoHabitacion){
            Habitacion habitacion = MongoDB.getHabitacion(documentoHabitacion);
            if (habitacion instanceof Simple) copiaHabitaciones.add(new Simple((Simple) habitacion));
            if (habitacion instanceof Doble) copiaHabitaciones.add(new Doble((Doble) habitacion));
            if (habitacion instanceof Triple) copiaHabitaciones.add(new Triple((Triple) habitacion));
            if (habitacion instanceof Suite) copiaHabitaciones.add(new Suite((Suite) habitacion));
        }
        return copiaHabitaciones;
    }
    @Override
    public int getTamano() {
        return (int) coleccionHabitaciones.countDocuments();
    }


    @Override
    public void insertar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");
        if (buscar(habitacion)!= null){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }
        coleccionHabitaciones.insertOne(MongoDB.getDocumento(habitacion));
    }

    @Override
    public Habitacion buscar (Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");

        Document documentoHabitacion = coleccionHabitaciones.find().filter(eq(MongoDB.IDENTIFICADOR,habitacion.getIdentificador())).first();
        if (documentoHabitacion != null){
            Habitacion habitacionBuscada = MongoDB.getHabitacion(documentoHabitacion);
            if (habitacionBuscada instanceof Simple) return new Simple((Simple) habitacionBuscada);
            if (habitacionBuscada instanceof Doble) return new Doble((Doble) habitacionBuscada);
            if (habitacionBuscada instanceof Triple) return new Triple((Triple) habitacionBuscada);
            if (habitacionBuscada instanceof  Suite) return new Suite((Suite) habitacionBuscada);
        }
        return null;
    }

    @Override
    public void borrar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (buscar(habitacion)== null)
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        coleccionHabitaciones.deleteOne(MongoDB.getDocumento(habitacion));
    }

    @Override
    public void comenzar(){
        coleccionHabitaciones = MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
    }


}