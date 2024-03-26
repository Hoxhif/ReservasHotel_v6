package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;

public interface IHabitaciones {
    ArrayList<Habitacion> get();
    ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion);
    int getTamano();
    void insertar(Habitacion habitacion) throws OperationNotSupportedException;
    Habitacion buscar(Habitacion habitacion);
    void borrar(Habitacion habitacion) throws OperationNotSupportedException;

}
