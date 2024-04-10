package org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;

import javax.naming.OperationNotSupportedException;
import java.util.*;


public class Habitaciones implements IHabitaciones {

    /*
    Los atributos pertenecientes a el array tampoco harán falta.
    private Habitacion coleccionHabitaciones[];
    private int capacidad;

    */


    // Usamos ArrayList porque nos lo pide en el enunciado

    private ArrayList<Habitacion> coleccionHabitaciones= new ArrayList<Habitacion>();

    public Habitaciones(){

    }

    /*
    Esto pertenecía a los Array normales, ya no harán falta con ArrayList.
    public Habitaciones (int capacidad){
        if (capacidad<=0)
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");

        this.capacidad=capacidad;
        this.coleccionHabitaciones = new Habitacion[getCapacidad()];
        this.tamano=0;

    }*/
    @Override
    public ArrayList<Habitacion> get(){
        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<Habitacion>();
        Habitacion habitacion;

        Iterator<Habitacion> habitacionIterator= coleccionHabitaciones.iterator();
        while (habitacionIterator.hasNext()){
            //Habitacion habitacion= new Habitacion(habitacionIterator.next());
            habitacion= habitacionIterator.next();
            if (habitacion instanceof Simple)
                //Hacemos uso de un casting para convertir la habitación en un simple.
                copiaHabitaciones.add(new Simple((Simple)habitacion));
            else if (habitacion instanceof Doble)
                copiaHabitaciones.add(new Doble((Doble)habitacion));
            else if (habitacion instanceof Triple)
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            else if (habitacion instanceof Suite)
                copiaHabitaciones.add(new Suite((Suite) habitacion));
        }
        // Había usado reversed al principio porque pensaba que iría de mas a menos, pero parece ser que no, que va de menos a mas por defecto.
        Collections.sort(copiaHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
        return copiaHabitaciones;

    }

    // Ahora ya no se usa private Habitacion[] por que ya no queremos devolver un array, sino un Arraylist.
 /*   private ArrayList<Habitacion> copiaProfundaHabitaciones(){

        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<Habitacion>();
        Habitacion habitacion;

        Iterator<Habitacion> habitacionIterator= coleccionHabitaciones.iterator();
        while (habitacionIterator.hasNext()){
            //Habitacion habitacion= new Habitacion(habitacionIterator.next());
            habitacion= habitacionIterator.next();
            if (habitacion instanceof Simple)
                //Hacemos uso de un casting para convertir la habitación en un simple.
                copiaHabitaciones.add(new Simple((Simple)habitacion));
            else if (habitacion instanceof Doble)
                copiaHabitaciones.add(new Doble((Doble)habitacion));
            else if (habitacion instanceof Triple)
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            else if (habitacion instanceof Suite)
                copiaHabitaciones.add(new Suite((Suite) habitacion));
        }
        // Había usado reversed al principio porque pensaba que iría de mas a menos, pero parece ser que no, que va de menos a mas por defecto.
        Collections.sort(copiaHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
        return copiaHabitaciones;
    }*/

    @Override
    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion){

        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo // Es posible que se haya equivocado al escribir el DNI.");


        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<>();

        Iterator<Habitacion> iteradorHabitacion= get().iterator();

        while(iteradorHabitacion.hasNext()){
            Habitacion comprobarHabitacion= iteradorHabitacion.next();
            switch (tipoHabitacion){
                case SIMPLE: copiaHabitaciones.add(new Simple((Simple)comprobarHabitacion)); break;
                case DOBLE: copiaHabitaciones.add(new Doble((Doble)comprobarHabitacion)); break;
                case TRIPLE: copiaHabitaciones.add(new Triple((Triple) comprobarHabitacion)); break;
                case SUITE: copiaHabitaciones.add(new Suite((Suite) comprobarHabitacion));
            }
        }
        Collections.sort(copiaHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
        return copiaHabitaciones;
    }
    @Override
    public int getTamano() {
        return get().size();
    }


    @Override
    public void insertar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitación nula.");

        if (get().contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitación con ese identificador.");
        }

        coleccionHabitaciones.add(habitacion);
    }

    @Override
    public Habitacion buscar (Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitación nula.");
        if (get().contains(habitacion)) {
            //return get().get(get().indexOf(habitacion)); // al final lo he dejado como estaba

            // Aquí en vez de lo que tenía hecho he usado el Iterator.

            Iterator<Habitacion> iteradorHabitacion = get().iterator();
            while (iteradorHabitacion.hasNext()) {
                if (habitacion.equals(iteradorHabitacion.next()))
                    return habitacion;
            }

        }return null;
    }

    @Override
    public void borrar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitación nula.");
        if (!get().contains(habitacion))
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitación como la indicada.");
        else{
            coleccionHabitaciones.remove(habitacion);
        }
    }

    public void comenzar(){}
    public void terminar(){}


}