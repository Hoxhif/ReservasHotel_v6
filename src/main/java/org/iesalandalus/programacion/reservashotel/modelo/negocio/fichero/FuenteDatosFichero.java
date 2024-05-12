package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.Reservas;

public class FuenteDatosFichero implements IFuenteDatos {

    @Override
    public IHuespedes crearHuespedes(){
        return new Huespedes();
    }

    @Override
    public IHabitaciones crearHabitaciones(){
        return new Habitaciones();
    }

    @Override
    public IReservas crearReservas(){
        return new Reservas();
    }
}
