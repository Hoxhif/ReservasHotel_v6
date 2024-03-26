package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Reserva {

    public static final int MAX_NUMERO_MESES_RESERVA=6;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT=12;
    public static final String FORMATO_FECHA_RESERVA="dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA="dd/MM/yyyy HH:mm:ss";
    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas){
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
        precio=0;
        /*if (getCheckIn()!=null){
            setCheckIn(checkIn);
        }
        if (getCheckOut()!=null){
            setCheckOut(checkOut);
        }*/
        checkIn=null;
        checkOut=null;
        //He tenido problemas con el checkIn y checkOut, he intentado mil cosas pero no consigo entender por que me sigue mostrando en el método toString "No registrado" cuando ya he hecho un checkIn.
    }

    public Reserva (Reserva reserva){
        if (reserva==null)
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        setNumeroPersonas(reserva.getNumeroPersonas());
        precio=reserva.getPrecio();
        if (reserva.getCheckIn()!=null)
            setCheckIn(reserva.getCheckIn());
        if (reserva.getCheckOut()!=null)
            setCheckOut(reserva.getCheckOut());
        //checkIn=reserva.getCheckIn();
        //checkOut=reserva.getCheckOut();
    }

    public Huesped getHuesped() {
        return new Huesped(huesped);
    }

    public void setHuesped(Huesped huesped) {
        if (huesped ==null)
            throw new NullPointerException("ERROR: El huésped de una reserva no puede ser nulo.");
        this.huesped = new Huesped(huesped);
    }

    public Habitacion getHabitacion() {
        if (habitacion instanceof Simple)
            return new Simple((Simple)habitacion);
        else if (habitacion instanceof Doble)
            return new Doble((Doble)habitacion);
        else if (habitacion instanceof Triple)
            return new Triple((Triple) habitacion);
        else if (habitacion instanceof Suite)
            return new Suite((Suite) habitacion);
        //return new Habitacion(habitacion);
        else return null;
    }

    public void setHabitacion(Habitacion habitacion) {
        if (habitacion==null)
            throw new NullPointerException("ERROR: La habitación de una reserva no puede ser nula.");
        if (habitacion instanceof Simple)
            this.habitacion= new Simple((Simple) habitacion);
        else if (habitacion instanceof Doble)
            this.habitacion= new Doble((Doble) habitacion);
        else if (habitacion instanceof Triple)
            this.habitacion= new Triple((Triple) habitacion);
        else if (habitacion instanceof Suite)
            this.habitacion=new Suite((Suite)habitacion);

        //this.habitacion = new Habitacion(habitacion);
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen==null)
            throw new NullPointerException("ERROR: El régimen de una reserva no puede ser nulo.");
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        LocalDate hoy = LocalDate.now();
        if (fechaInicioReserva == null)
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        if (fechaInicioReserva.isBefore(hoy))
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al día de hoy.");
        if (fechaInicioReserva.isAfter(LocalDate.of(hoy.getYear(),MAX_NUMERO_MESES_RESERVA, hoy.getDayOfMonth())))
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        LocalDate hoy = LocalDate.now();
        if (fechaFinReserva==null)
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        if (fechaFinReserva.isBefore(getFechaInicioReserva()))
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        if (getFechaInicioReserva().isAfter(LocalDate.of(hoy.getYear(),MAX_NUMERO_MESES_RESERVA,hoy.getDayOfMonth())))
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        if (fechaFinReserva.equals(getFechaInicioReserva()))
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }


    public void setCheckIn(LocalDateTime checkIn) {
            if (checkIn == null)
                throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
            if (checkIn.isBefore(getFechaInicioReserva().atStartOfDay()))
                throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if (checkOut==null)
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        if (checkOut.isBefore(getCheckIn()))
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        if (checkOut.isAfter(getFechaFinReserva().atTime(MAX_HORAS_POSTERIOR_CHECKOUT, 0)))
                throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como máximo 12 horas después de la fecha de fin de la reserva.");
        this.checkOut = checkOut;
        setPrecio();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio() {

            Period tiempoEstancia = Period.between(getFechaInicioReserva(), getFechaFinReserva());
            this.precio = (getNumeroPersonas() * (habitacion.getPrecio() + getRegimen().getIncrementoPrecio()) * tiempoEstancia.getDays());
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas<=0)
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede ser menor o igual a 0.");
        if (numeroPersonas>habitacion.getNumeroMaximoPersonas())
            throw new IllegalArgumentException("ERROR: El número de personas de una reserva no puede superar al máximo de personas establacidas para el tipo de habitación reservada.");
        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {

        String checkInString="";
        String checkOutString="";
        if (checkIn==null) checkInString="No registrado";
        else checkInString= getCheckIn().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA));
        if (checkOut==null) checkOutString="No registrado";
        else checkOutString= getCheckOut().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA));

        return String.format("Huesped: %s %s Habitación:%s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",getHuesped().getNombre(), getHuesped().getDni(),
                getHabitacion().toString(), getFechaInicioReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)),
                getFechaFinReserva().format(DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA)), checkInString,
                checkOutString, getPrecio(), getNumeroPersonas());
    }

    /*public static void main(String[] args) {
        Huesped huesped1 = new Huesped("Juan ANTOnio GuirADO GonzaleZ","77657325X","correo1@gmail.com","654421245", LocalDate.of(2001,2,12));
        Habitacion habitacion1 = new Habitacion(1,12,60,TipoHabitacion.SIMPLE);
        Reserva reserva1 = new Reserva(huesped1,habitacion1,Regimen.MEDIA_PENSION,LocalDate.now(),LocalDate.of(2023,3,12),1);

        System.out.println();
    }*/

}
