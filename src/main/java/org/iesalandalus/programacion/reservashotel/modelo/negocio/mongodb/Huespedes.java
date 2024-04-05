package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades.MongoDB;

import javax.naming.OperationNotSupportedException;
import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class Huespedes implements IHuespedes {

    private MongoCollection<Document> coleccionHuespedes;
    private static final String COLECCION="huespedes";
    public Huespedes(){
        comenzar();
    }

    @Override
    public ArrayList<Huesped> get() {
        ArrayList<Huesped> listaHuespedes = new ArrayList<>();
        FindIterable<Document> coleccionHuespedes = this.coleccionHuespedes.find().sort(Sorts.ascending(MongoDB.DNI));

        for (Document documento: coleccionHuespedes){
            listaHuespedes.add(new Huesped(MongoDB.getHuesped(documento)));
        }

    return listaHuespedes;

    }

    @Override
    public int getTamano() {
        return (int) coleccionHuespedes.countDocuments();
    }


    @Override
    public void insertar(Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede insertar un huésped nulo.");
        if (buscar(huesped) == null){
            coleccionHuespedes.insertOne(MongoDB.getDocumento(huesped));
        }
        else throw new OperationNotSupportedException("ERROR: Ya existe un huésped con ese dni.");
    }

    @Override
    public Huesped buscar (Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huésped nulo.");
        Document documentoHuesped = coleccionHuespedes.find().filter(eq(MongoDB.DNI,huesped.getDni())).first();
        if (documentoHuesped != null)
            return new Huesped(MongoDB.getHuesped(documentoHuesped));
        else return null;
    }

    @Override
    public void borrar (Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        if (buscar(huesped)==null)
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        coleccionHuespedes.deleteOne(MongoDB.getDocumento(huesped));
    }

    @Override
    public void comenzar(){
        coleccionHuespedes = MongoDB.getBD().getCollection(COLECCION);
    }

    @Override
    public void terminar(){
        MongoDB.cerrarConexion();
    }


}