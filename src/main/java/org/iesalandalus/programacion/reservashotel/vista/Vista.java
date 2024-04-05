package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.memoria.Habitaciones;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Vista {

    private Controlador controlador;

    public Vista(){
        //Al principio use Opcion.setVista(new Vista()), pero parece que usando this también funciona.
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador) {
        if (controlador == null)
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion = Opcion.SALIR;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            opcion.ejecutar();
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        controlador.terminar();
    }

    /*public void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case SALIR:
                break;
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case REALIZAR_CHECKIN:
                realizarCheckin();
                break;
            case REALIZAR_CHECKOUT:
                realizarCheckout();
                break;
            case CONSULTAR_DISPONIBILIDAD:
                try {
                    Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
                    if (habitacion instanceof )
                    if (habitacion==null){
                        System.out.println("La habitación no existe.");
                        break;
                    }
                    Habitacion comprobarHabitacion = consultarDisponibilidad(habitacion.getTipoHabitacion, Consola.leerFecha("Inserte la fecha de posible reserva: "), Consola.leerFecha("Inserte la fecha de posible fin reserva: "));
                    if (comprobarHabitacion == null)
                        System.out.println("La habitación estará ocupada en esas fechas.");
                    else if (comprobarHabitacion != null)
                        System.out.println("La habitación está disponible en esas fechas.");
                    break;

                } catch (NullPointerException e) {
                    System.out.println("-"+e.getMessage());
                }
        }
    }*/



    public void insertarHuesped() {
        // En esta parte lo que hago es crear un nuevo objeto de tipo huesped y le doy como valor los datos que devuelven leerHuesped al llamar al metodo y despues llamo al metodo de la clase huespedes para insertarlo en el array pasando por parametro al propiop huesped creado.
        // se podr�a realmente haber omitido la linea de crear un nuevo objeto nuevoHuesped y directamente haber puesto en huespedes.insertar(Consola.leerHuesped());
        try {
            Huesped nuevoHuesped = Consola.leerHuesped();
            if (nuevoHuesped != null){
                controlador.insertar(nuevoHuesped);
                System.out.println("Huesped creado satisfactoriamente");
            }else System.out.println("Error al crear el huesped. No se han introducido datos válidos.");
        } catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e) {
            System.out.println("-"+e.getMessage());

        }
    }

    public void buscarHuesped(){
        try{
            Huesped huesped = Consola.getClientePorDni();
            Iterator<Huesped> iteradorHuesped = controlador.getHuespedes().iterator();
            if (controlador.getHuespedes().isEmpty()) System.out.println("No hay huespedes registrados.");
            while (iteradorHuesped.hasNext()){
                Huesped huespedIterado= iteradorHuesped.next();
                if (huesped!=null)
                    if (huespedIterado.getDni().equals(huesped.getDni())){
                        System.out.println(controlador.buscar(huespedIterado));
                    }else System.out.println("No se a encontrado al Huesped.");
            }
        }catch(NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
        //System.out.println(Consola.getClientePorDni());
    }

    public void borrarHuesped(){
        try {
            Huesped huespedABorrar = Consola.getClientePorDni();
            Iterator<Huesped> iteradorHuesped= controlador.getHuespedes().iterator();
            while (iteradorHuesped.hasNext()){
                Huesped huespedIterado= iteradorHuesped.next();
                if (huespedIterado.getDni().equals(huespedABorrar.getDni())) {
                    controlador.borrar(huespedABorrar);
                    System.out.println("Huesped borrado satisfactoriamente");
                }else System.out.println("No se puede borrar un huesped que no existe.");
                }
        }catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
    }

    public void mostrarHuespedes(){
        if (controlador.getHuespedes().isEmpty()){
            System.out.println("No hay huespedes a mostrar. ");
        }else{
            System.out.println("Listado de huespedes: ");
            ArrayList<Huesped> listado= controlador.getHuespedes();
            Collections.sort(listado, Comparator.comparing(Huesped::getNombre));
            Iterator<Huesped> iteradorMostrarHuesped= listado.iterator();
            while (iteradorMostrarHuesped.hasNext()){
                System.out.println(iteradorMostrarHuesped.next().toString());
            }
            /*for (Huesped huesped: controlador.getHuespedes()){
                System.out.println(huesped.toString());
            }*/
        }

    }

    public void insertarHabitacion(){
        try{
            Habitacion nuevaHabitacion = Consola.leerHabitacion();
            if (nuevaHabitacion != null) {
                controlador.insertar(nuevaHabitacion);
                System.out.println("Habitaci�n creada satisfactoriamente");
            }else System.out.println("Error al crear la habitación, los datos no son los esperados.");
        }catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
    }

    public void buscarHabitacion(){
        try{
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            if (habitacion != null){
                /*System.out.println(controlador.buscar(habitacion));*/
                if (controlador.getHabitaciones().isEmpty()) System.out.println("No hay habitaciones que mostrar.");
                Iterator<Habitacion> iteradorHabitacion = controlador.getHabitaciones().iterator();
                while (iteradorHabitacion.hasNext()){
                    Habitacion habitacionIterada = iteradorHabitacion.next();
                    if (habitacionIterada.getIdentificador().equals(habitacion.getIdentificador())){
                        System.out.println(controlador.buscar(habitacionIterada));
                    }
                }
            }else System.out.println("No se ha encontrado la habitación.");
        }catch(NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }

        //System.out.println(Consola.leerHabitacionPorIdentificador());
    }

    public void borrarHabitacion(){
        try{
            Habitacion habitacionABorrar = Consola.leerHabitacionPorIdentificador();
            if (habitacionABorrar != null) {
                Iterator<Habitacion> iteradorHabitacion = controlador.getHabitaciones().iterator();
                while (iteradorHabitacion.hasNext()){
                    Habitacion habitacion = iteradorHabitacion.next();
                    if (habitacion.getIdentificador().equals(habitacionABorrar.getIdentificador())){
                        controlador.borrar(habitacion);
                    }
                }
                System.out.println("Habitaci�n borrada satisfactoriamente");
            }else System.out.println("No se puede borrar la habitación porque no existe.");
        }catch (OperationNotSupportedException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
    }

    public void mostrarHabitaciones(){
        if (controlador.getHabitaciones().isEmpty()){
            System.out.println("No hay habitaciones a mostrar. ");
        }else{
            System.out.println("Listado de Habitaciones: ");
            ArrayList<Habitacion> mostrarHabitaciones= controlador.getHabitaciones();
            Collections.sort(mostrarHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
            Iterator<Habitacion> iteradorMostrarHabitaciones= mostrarHabitaciones.iterator();
            while (iteradorMostrarHabitaciones.hasNext()){
                System.out.println(iteradorMostrarHabitaciones.next().toString());
            }
            /*for (Habitacion habitacion: controlador.getHabitaciones()){
                System.out.println(habitacion.toString());
            }*/
        }
    }

    public void insertarReserva(){
        try {
            Reserva nuevaReserva = Consola.leerReserva();
            Huesped huesped = null;
            Habitacion habitacion = null;
            for (Huesped huespedes : controlador.getHuespedes()) {
                if (huespedes.getDni().equals(nuevaReserva.getHuesped().getDni())) {
                    huesped = huespedes;
                }
            }

            for (Habitacion habitaciones : controlador.getHabitaciones()) {
                if (habitaciones.getIdentificador().equals(nuevaReserva.getHabitacion().getIdentificador())) {
                    habitacion = habitaciones;
                }
            }

            // ESTO ESTA SUJETO A CAMBIOS.


            if (habitacion instanceof Simple) {
                Reserva reservaReal = new Reserva(huesped, habitacion, nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
                if (nuevaReserva != null) {
                    if (getNumElementosNoNulos(controlador.getReservas()) > 0) { //CAMBIAR LENGTH POR ELEMENTOSNONULOS.
                        Habitacion habitacionDisponible = consultarDisponibilidad(TipoHabitacion.SIMPLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
                        if (habitacionDisponible != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        } else
                            System.out.println("No se puede realizar la reserva en esas fechas. No se encuentra disponible la habitaci�n");
                    } else {
                        if (nuevaReserva.getHabitacion() != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        }
                    }
                }
            }
            if (habitacion instanceof Doble){
                Reserva reservaReal = new Reserva(huesped, habitacion, nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
                if (nuevaReserva != null) {
                    if (getNumElementosNoNulos(controlador.getReservas()) > 0) { //CAMBIAR LENGTH POR ELEMENTOSNONULOS.
                        Habitacion habitacionDisponible = consultarDisponibilidad(TipoHabitacion.DOBLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
                        if (habitacionDisponible != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        } else
                            System.out.println("No se puede realizar la reserva en esas fechas. No se encuentra disponible la habitaci�n");
                    } else {
                        if (nuevaReserva.getHabitacion() != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        }
                    }
                }
            }
            if (habitacion instanceof Triple){
                Reserva reservaReal = new Reserva(huesped, habitacion, nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
                if (nuevaReserva != null) {
                    if (getNumElementosNoNulos(controlador.getReservas()) > 0) { //CAMBIAR LENGTH POR ELEMENTOSNONULOS.
                        Habitacion habitacionDisponible = consultarDisponibilidad(TipoHabitacion.TRIPLE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
                        if (habitacionDisponible != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        } else
                            System.out.println("No se puede realizar la reserva en esas fechas. No se encuentra disponible la habitaci�n");
                    } else {
                        if (nuevaReserva.getHabitacion() != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        }
                    }
                }
            }
            if (habitacion instanceof Suite){
                Reserva reservaReal = new Reserva(huesped, habitacion, nuevaReserva.getRegimen(), nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva(), nuevaReserva.getNumeroPersonas());
                if (nuevaReserva != null) {
                    if (getNumElementosNoNulos(controlador.getReservas()) > 0) { //CAMBIAR LENGTH POR ELEMENTOSNONULOS.
                        Habitacion habitacionDisponible = consultarDisponibilidad(TipoHabitacion.SUITE, nuevaReserva.getFechaInicioReserva(), nuevaReserva.getFechaFinReserva());
                        if (habitacionDisponible != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        } else
                            System.out.println("No se puede realizar la reserva en esas fechas. No se encuentra disponible la habitaci�n");
                    } else {
                        if (nuevaReserva.getHabitacion() != null) {
                            controlador.insertar(reservaReal);
                            System.out.println("Reserva creada satisfactoriamente");
                        }
                    }
                }
            }

        }
            //else System.out.println("No se puede añadir una reserva con datos nulos.");
        catch (OperationNotSupportedException | NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }}

    public void mostrarReservasHuesped(){
        try {
            listarReservas(Consola.getClientePorDni());
        }catch(NullPointerException | IllegalArgumentException e){
            System.out.println("- " + e.getMessage());
        }
    }



    public void listarReservas(Huesped huesped){

        Huesped huespedReal=null;

        for (Huesped huespedes: controlador.getHuespedes()){
            if (huespedes.getDni().equals(huesped.getDni())){
                huespedReal=huespedes;
            }
        }


        try{
            if (huesped != null) {
                int contador = 1;
                // La comparación para las reservas esta hecho en el método copiaProfunda de reservas.
                Iterator<Reserva> iteradorListarReservasHuesped= controlador.getReserva(huespedReal).iterator();
                while (iteradorListarReservasHuesped.hasNext()){
                    System.out.println(contador+": "+iteradorListarReservasHuesped.next());
                    contador++;
                }
                /*for (Reserva reservasHuesped : controlador.getReserva(huesped)) {
                    System.out.println(contador + ": " + reservasHuesped);
                    contador++;
                }*/
            }else System.out.println("El DNI del huesped introducido no existe.");
        }catch(NullPointerException e){
            System.out.println("-"+e.getMessage());
        }
    }

    public void mostrarReservasTipoHabitacion(){
        try {
            listarReservas(Consola.leerTipoHabitacion());
        }catch(NullPointerException | IllegalArgumentException e){
            System.out.println("- "+ e.getMessage());
        }
    }

    public void listarReservas (TipoHabitacion tipoHabitacion){

       try {
            if (tipoHabitacion != null) {
                Iterator<Reserva> iteradorReservaTipoHabitacion= controlador.getReserva(tipoHabitacion).iterator();
                while (iteradorReservaTipoHabitacion.hasNext()){
                    Reserva reservaSiguiente = iteradorReservaTipoHabitacion.next();
                    if (tipoHabitacion == TipoHabitacion.SIMPLE){
                    for (Reserva comprobarReserva: controlador.getReserva(TipoHabitacion.DOBLE)){
                        if (!comprobarReserva.getHabitacion().getIdentificador().equals(reservaSiguiente.getHabitacion().getIdentificador()))
                            System.out.println(reservaSiguiente);
                    }
                        for (Reserva comprobarReserva: controlador.getReserva(TipoHabitacion.TRIPLE)){
                            if (!comprobarReserva.getHabitacion().getIdentificador().equals(reservaSiguiente.getHabitacion().getIdentificador()))
                                System.out.println(reservaSiguiente);
                        }
                        for (Reserva comprobarReserva: controlador.getReserva(TipoHabitacion.SUITE)){
                            if (!comprobarReserva.getHabitacion().getIdentificador().equals(reservaSiguiente.getHabitacion().getIdentificador()))
                                System.out.println(reservaSiguiente);
                        }
                    }
                    System.out.println(reservaSiguiente);
                }
                /*for (Reserva reservasTipoHabitacion : controlador.getReserva(tipoHabitacion)) {
                    System.out.println(reservasTipoHabitacion);
                }*/
            }else System.out.println("El tipo de habitación introducida es nula.");
        }catch (NullPointerException e){
            System.out.println("-"+e.getMessage());
        }
    }

    public void comprobarDisponibilidad(){
        try {
            Habitacion habitacion = Consola.leerHabitacionPorIdentificador();
            if (habitacion==null){
                System.out.println("La habitación no existe.");
            }
            if (habitacion instanceof Simple){
                Habitacion comprobarHabitacion = consultarDisponibilidad(TipoHabitacion.SIMPLE, Consola.leerFecha("Inserte la fecha de posible reserva: "), Consola.leerFecha("Inserte la fecha de posible fin reserva: "));
                if (comprobarHabitacion == null)
                    System.out.println("La habitación estará ocupada en esas fechas.");
                else if (comprobarHabitacion != null)
                    System.out.println("La habitación está disponible en esas fechas.");
            }
            else if (habitacion instanceof Doble){
                Habitacion comprobarHabitacion = consultarDisponibilidad(TipoHabitacion.DOBLE, Consola.leerFecha("Inserte la fecha de posible reserva: "), Consola.leerFecha("Inserte la fecha de posible fin reserva: "));
                if (comprobarHabitacion == null)
                    System.out.println("La habitación estará ocupada en esas fechas.");
                else if (comprobarHabitacion != null)
                    System.out.println("La habitación está disponible en esas fechas.");
            }
            else if (habitacion instanceof Triple){
                Habitacion comprobarHabitacion = consultarDisponibilidad(TipoHabitacion.TRIPLE, Consola.leerFecha("Inserte la fecha de posible reserva: "), Consola.leerFecha("Inserte la fecha de posible fin reserva: "));
                if (comprobarHabitacion == null)
                    System.out.println("La habitación estará ocupada en esas fechas.");
                else if (comprobarHabitacion != null)
                    System.out.println("La habitación está disponible en esas fechas.");
            }
            else if (habitacion instanceof Suite){
                Habitacion comprobarHabitacion = consultarDisponibilidad(TipoHabitacion.SUITE, Consola.leerFecha("Inserte la fecha de posible reserva: "), Consola.leerFecha("Inserte la fecha de posible fin reserva: "));
                if (comprobarHabitacion == null)
                    System.out.println("La habitación estará ocupada en esas fechas.");
                else if (comprobarHabitacion != null)
                    System.out.println("La habitación está disponible en esas fechas.");
            }

        } catch (NullPointerException e) {
            System.out.println("-"+e.getMessage());
        }

    }

    public ArrayList<Reserva> getReservasAnulables (ArrayList<Reserva> reservasAAnular){
        LocalDate fechaAhora= LocalDate.now();
        ArrayList<Reserva> reservasAnulables= new ArrayList<>();
        Iterator<Reserva> iteradorReservasAnulables= reservasAAnular.iterator();
        while (iteradorReservasAnulables.hasNext()){
            Reserva reserva= iteradorReservasAnulables.next();
            if (reserva.getFechaInicioReserva().isAfter(fechaAhora))
                reservasAnulables.add(reserva);
        }
        /*for (Reserva reserva: reservasAAnular){
            if (reserva.getFechaInicioReserva().isAfter(fechaAhora))
                reservasAnulables.add(reserva);
        }*/
        return reservasAnulables;
    }

    public void anularReserva() {

        int opcion = 0;
        Huesped huesped = Consola.getClientePorDni();
        // Aqui lo que hago es copiar el codigo de leerReserva de un Huesped para poder saber cuales son de nuevo las reservas de ese huesped, ya que el m�todo no devuelve nada.

        if (getReservasAnulables(controlador.getReserva(huesped)).isEmpty())
                System.out.println("No hay reservas a anuelar, el Huesped no tiene hecha ninguna reserva.");
            else {
                if (getNumElementosNoNulos(controlador.getReserva(huesped))==0) {
                        Reserva reservaHuespedABorrar = controlador.getReserva(huesped).get(0);
                    do{
                        System.out.println("Desea anular la reserva que tiene?");
                        System.out.println("1.- S�");
                        System.out.println("2.- No");
                        System.out.println("Escoja una opci�n: ");
                        opcion = Entrada.entero();
                        if (opcion<1 || opcion>2)
                            System.out.println("Opci�n inv�lida, por favor, ingrese una opci�n correcta.");
                    }while (opcion<1 || opcion>2);
                    switch (opcion){
                        case 1:
                            try {
                                controlador.borrar(reservaHuespedABorrar);
                                System.out.println("La reserva se ha borrado satisfactoriamente.");
                                break;
                            }catch (OperationNotSupportedException e){
                                System.out.println("-"+e.getMessage());
                            }
                        case 2:
                            System.out.println("Operaci�n abortada");
                            break;
                    }

                } else {
                    int contador=1;
                    // ME FALTA POR A�ADIR LA CONFIRMACI�N FINAL DE BORRAR LA RESERVA QUE SE SELECCIONA.
                    do {
                        System.out.println("Listado de reservas del Huesped:");
                        Iterator<Reserva> iteradorAnularReserva= controlador.getReserva(huesped).iterator();
                        while (iteradorAnularReserva.hasNext()){
                            System.out.println(contador+ ".- "+iteradorAnularReserva.next().toString());
                            contador++;
                        }
                        /*for (Reserva reservasHuesped: getReservasAnulables(controlador.getReserva(huesped))){
                            System.out.println(contador+ ".- "+reservasHuesped.toString());
                            contador++;
                        }*/
                        System.out.println("Indique qu� reserva desea anular: ");
                        opcion = Entrada.entero();
                        if (opcion<1 || opcion>getReservasAnulables(controlador.getReserva(huesped)).size())
                            System.out.println("Opci�n inv�lida, por favor, ingrese una opci�n correcta,");
                    }while (opcion<1 || opcion>getReservasAnulables(controlador.getReserva(huesped)).size());
                    try{
                        controlador.borrar(controlador.getReserva(huesped).get(opcion-1));
                        System.out.println("La reserva se ha borrado satisfactoriamente.");
                    }catch(OperationNotSupportedException e){
                        System.out.println("-"+e.getMessage());
                    }
                }

            }
        }

    public void mostrarReservas() {
        int opcion;
        if (controlador.getReservas().isEmpty()) {
            System.out.println("No hay reservas a mostrar. ");
        } else {
            do {
                System.out.println("Indique el tipo de Reservas a mostrar: ");
                System.out.println("1.- Listar todas las reservas");
                System.out.println("2.- Listar reservas por huesped");
                System.out.println("3.- Listar reservas por tipo de habitaci�n");
                opcion = Entrada.entero();
                if (opcion < 1 || opcion > 3) System.out.println("Indique una opci�n v�lida.");
            } while (opcion < 1 || opcion > 3);
            switch (opcion) {
                case 1:
                    System.out.println("Listado de Reservas: ");
                    Iterator<Reserva> iteradorMostrarReservas= controlador.getReservas().iterator();
                    while (iteradorMostrarReservas.hasNext()){
                        System.out.println(iteradorMostrarReservas.next().toString());
                    }
                    /*for (Reserva reserva : controlador.getReservas()) {
                        System.out.println(reserva.toString());
                    }*/
                    break;
                case 2:
                        mostrarReservasHuesped();

                    break;
                case 3:
                        mostrarReservasTipoHabitacion();
                    break;
            }
        }
    }

    public int getNumElementosNoNulos(ArrayList<Reserva> reservaNoNula){
        return reservaNoNula.size();
    }


    public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva){

        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        ArrayList<Habitacion> habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i)!=null)
            {
                ArrayList<Reserva> reservasFuturas = controlador.getReservaFutura(habitacionesTipoSolicitado.get(i));
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est� disponible.
                    if (habitacionesTipoSolicitado.get(i) instanceof Simple)
                        habitacionDisponible=new Simple((Simple)habitacionesTipoSolicitado.get(i));
                    else if (habitacionesTipoSolicitado.get(i) instanceof Doble)
                        habitacionDisponible=new Doble((Doble)habitacionesTipoSolicitado.get(i));
                    else if (habitacionesTipoSolicitado.get(i) instanceof Triple)
                        habitacionDisponible=new Triple((Triple)habitacionesTipoSolicitado.get(i));
                    else if (habitacionesTipoSolicitado.get(i) instanceof Suite)
                        habitacionDisponible=new Suite((Suite)habitacionesTipoSolicitado.get(i));
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.


                    Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaFinReserva).reversed());




                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                        if (habitacionesTipoSolicitado.get(i) instanceof Simple)
                            habitacionDisponible=new Simple((Simple)habitacionesTipoSolicitado.get(i));
                        else if (habitacionesTipoSolicitado.get(i) instanceof Doble)
                            habitacionDisponible=new Doble((Doble)habitacionesTipoSolicitado.get(i));
                        else if (habitacionesTipoSolicitado.get(i) instanceof Triple)
                            habitacionDisponible=new Triple((Triple)habitacionesTipoSolicitado.get(i));
                        else if (habitacionesTipoSolicitado.get(i) instanceof Suite)
                            habitacionDisponible=new Suite((Suite)habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                        // Esta fue una solución que me proporcionó chatGPT para poder resolverlo, no sabía que hacer aqui sino.
                        Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaInicioReserva));




                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                            if (habitacionesTipoSolicitado.get(i) instanceof Simple)
                                habitacionDisponible=new Simple((Simple)habitacionesTipoSolicitado.get(i));
                            else if (habitacionesTipoSolicitado.get(i) instanceof Doble)
                                habitacionDisponible=new Doble((Doble)habitacionesTipoSolicitado.get(i));
                            else if (habitacionesTipoSolicitado.get(i) instanceof Triple)
                                habitacionDisponible=new Triple((Triple)habitacionesTipoSolicitado.get(i));
                            else if (habitacionesTipoSolicitado.get(i) instanceof Suite)
                                habitacionDisponible=new Suite((Suite)habitacionesTipoSolicitado.get(i));
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est�n alg�n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.size() && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas.get(j)!=null && reservasFuturas.get(j-1)!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(j-1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    //habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                                    if (habitacionesTipoSolicitado.get(i) instanceof Simple)
                                        habitacionDisponible=new Simple((Simple)habitacionesTipoSolicitado.get(i));
                                    else if (habitacionesTipoSolicitado.get(i) instanceof Doble)
                                        habitacionDisponible=new Doble((Doble)habitacionesTipoSolicitado.get(i));
                                    else if (habitacionesTipoSolicitado.get(i) instanceof Triple)
                                        habitacionDisponible=new Triple((Triple)habitacionesTipoSolicitado.get(i));
                                    else if (habitacionesTipoSolicitado.get(i) instanceof Suite)
                                        habitacionDisponible=new Suite((Suite)habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;

    }




    /*public Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva){

boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;

        ArrayList<Habitacion> habitacionesTipoSolicitado= controlador.getHabitaciones(tipoHabitacion);

        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;

        for (int i=0; i<habitacionesTipoSolicitado.size() && !tipoHabitacionEncontrada; i++)
        {

            if (habitacionesTipoSolicitado.get(i)!=null)
            {
                ArrayList<Reserva> reservasFuturas = controlador.getReserva(habitacionesTipoSolicitado.get(i));
                numElementos=getNumElementosNoNulos(reservasFuturas);

                if (numElementos == 0)
                {
                    //Si la primera de las habitaciones encontradas del tipo solicitado no tiene reservas en el futuro,
                    // quiere decir que est� disponible.
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado.get(i));
                    tipoHabitacionEncontrada=true;
                }
                else {

                    //Ordenamos de mayor a menor las reservas futuras encontradas por fecha de fin de la reserva.
                    // Si la fecha de inicio de la reserva es posterior a la mayor de las fechas de fin de las reservas
                    // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.


                    Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaFinReserva).reversed());




                    if (fechaInicioReserva.isAfter(reservasFuturas.get(0).getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                        tipoHabitacionEncontrada = true;
                    }

                    if (!tipoHabitacionEncontrada)
                    {
                        //Ordenamos de menor a mayor las reservas futuras encontradas por fecha de inicio de la reserva.
                        // Si la fecha de fin de la reserva es anterior a la menor de las fechas de inicio de las reservas
                        // (la reserva de la posici�n 0), quiere decir que la habitaci�n est� disponible en las fechas indicadas.

                        // Esta fue una solución que me proporcionó chatGPT para poder resolverlo, no sabía que hacer aqui sino.
                        Collections.sort(reservasFuturas, Comparator.comparing(Reserva::getFechaInicioReserva));




                        if (fechaFinReserva.isBefore(reservasFuturas.get(0).getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                            tipoHabitacionEncontrada = true;
                        }
                    }

                    //Recorremos el array de reservas futuras para ver si las fechas solicitadas est�n alg�n hueco existente entre las fechas reservadas
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.size() && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas.get(j)!=null && reservasFuturas.get(j-1)!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas.get(j-1).getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas.get(j).getFechaInicioReserva())) {

                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado.get(i));
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }

        return habitacionDisponible;

    }*/

    public void realizarCheckin(){
        try {
        Huesped huesped=Consola.getClientePorDni();
        listarReservas(huesped);
        ArrayList<Reserva> reservasHuesped= controlador.getReserva(huesped);
        if (reservasHuesped.isEmpty()){
            System.out.println("El huesped no tiene ninguna reserva.");
        }else{
            int opcion=0;
            do{
                System.out.println("Inserte que reserva desea realizar el checkIn: ");
                opcion=Entrada.entero();
                if(opcion<0 || opcion>reservasHuesped.size())
                    System.out.println("La opci�n no es v�lida.");

            }while (opcion<0 || opcion>reservasHuesped.size());


                if (controlador.getReserva((huesped)).get(opcion-1).getCheckIn()==null){
                    controlador.realizarCheckin(controlador.getReserva(huesped).get(opcion-1), Consola.leerFechaHora("Inserte la fecha y hora de Checkin: "));
                    System.out.println("Se ha realizado el CheckIn correctamente.");
                }else System.out.println("Ya se ha realizado el checkIn para esta reserva.");
            }}catch (NullPointerException | IllegalArgumentException e){
                System.out.println("-"+e.getMessage());
            }
        }



    public void realizarCheckout(){

        try {
        Huesped huesped=Consola.getClientePorDni();
        listarReservas(huesped);

        ArrayList<Reserva> reservasHuesped= controlador.getReserva(huesped);

        if (reservasHuesped.isEmpty()){
            System.out.println("El huesped no tiene reserva");
        }else{
            int opcion=0;
            do{
                System.out.println("Inserte que reserva desea realizar el checkout: ");
                opcion=Entrada.entero();
                if(opcion<0 || opcion>reservasHuesped.size())
                    System.out.println("La opci�n no es v�lida.");

            }while (opcion<0 || opcion>reservasHuesped.size());
            Reserva reservaARealizarCheckout= new Reserva(controlador.getReserva(huesped).get(0)); //Aqu� ten�a que inicializar la reserva porque sino me daba errores. Es posible que tenga que cambiarlo.
            for (int i=0; i<reservasHuesped.size();i++){
                if (opcion-1 == i){
                    reservaARealizarCheckout= reservasHuesped.get(i);
                }
            }

                if (reservaARealizarCheckout.getCheckIn() != null) {
                    if (reservaARealizarCheckout.getCheckOut() == null) {
                        controlador.realizarCheckout(reservaARealizarCheckout, Consola.leerFechaHora("Inserte la fecha y hora de Checkout: "));
                        System.out.println("Se ha realizado el CheckOut correctamente.");
                    } else System.out.println("Ya se ha realizado el CheckOut para esta reserva.");
                }else System.out.println("Antes de realizar el CheckOut se debe realizar el CheckIn.");
                }}catch(NullPointerException | IllegalArgumentException e){
                    System.out.println("-"+e.getMessage());
                }
            }
        }

