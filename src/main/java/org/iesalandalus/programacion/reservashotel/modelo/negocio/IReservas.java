package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface IReservas {
    ArrayList<Reserva> get();
    int getTamano();
    void insertar (Reserva reserva) throws OperationNotSupportedException;
    Reserva buscar (Reserva reserva);
    void borrar (Reserva reserva) throws OperationNotSupportedException;
    ArrayList<Reserva> getReservas(Huesped huesped);
    ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion);
    ArrayList<Reserva> getReservasFuturas(Habitacion habitacion);
    void realizarCheckin(Reserva reserva, LocalDateTime fecha);
    void realizarCheckout (Reserva reserva, LocalDateTime fecha);
}
