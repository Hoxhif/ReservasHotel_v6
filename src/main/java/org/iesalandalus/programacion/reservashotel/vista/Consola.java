package org.iesalandalus.programacion.reservashotel.vista;



import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//import static org.iesalandalus.programacion.reservashotel.MainApp.*;

//import static org.iesalandalus.programacion.reservashotel.modelo.Modelo.*;


public class Consola {
    private Consola(){

    }

    public static void mostrarMenu(){
        System.out.println("Bienvenido al programa de gestión de reservas de un Hotel, creado por José Antonio Guirado González");
        System.out.println("Opciones: ");

        Opcion menuOpciones[]= Opcion.values();

        for (Opcion opcion: menuOpciones){
            if (opcion.ordinal()<menuOpciones.length) //De esta manera quito la opcion de consultar disponibilidad del menu.
                System.out.println(opcion);
        }

   }


    public static Opcion elegirOpcion(){
        int opcion;
        do {
            System.out.println("Elija una opción: ");
            opcion = Entrada.entero();
            if (opcion<=0 || opcion>Opcion.values().length)
                System.out.println("Opción no válida.");
        }while (opcion<=0 || opcion>Opcion.values().length);
        return Opcion.values()[opcion-1];
    }

    public static Huesped leerHuesped(){
        String nombre, dni, telefono, correo;

        //do {
            System.out.println("Escriba el nombre del Huesped: ");
            nombre = Entrada.cadena();
            System.out.println("Escriba el DNI del Huesped: ");
            dni = Entrada.cadena();
            System.out.println("Escriba el telefono del Huesped: ");
            telefono = Entrada.cadena();
            System.out.println("Escriba el correo del Huesped: ");
            correo = Entrada.cadena();
            //if (!dni.matches("\\d{8}[A-HJ-NP-TV-Z]") || !telefono.matches("\\d{9}") || !correo.matches("[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]+"))
                //System.out.println("El DNI, el correo o el teléfono no ha sido válido, por favor intentelo de nuevo.");
        //}while (!dni.matches("\\d{8}[A-HJ-NP-TV-Z]") || !telefono.matches("\\d{9}") || !correo.matches("[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]+"));

        try{
            return new Huesped(nombre,dni,correo,telefono,leerFecha("Escriba su fecha de nacimiento: "));
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
            return null;
        }
    }


    public static Huesped getClientePorDni(){
        // método sujeto a cambios ...
        // Este algoritmo ha sido creado gracias a que en la clase MainApp tengo acceso public en los atributos.
        // Si no no podría hacerlo de otra mnaera.
        try{
            String dni;
            System.out.println("Inserte el DNI del huesped: ");
            dni = Entrada.cadena();
            /*Iterator<Huesped> iteradorHuesped= huespedes.get().iterator();
            while (iteradorHuesped.hasNext()){
                Huesped huesped= iteradorHuesped.next();
                if (huesped.getDni().equals(dni))
                    return huesped;
            }*/
            return new Huesped("tertererer", dni, "geerer@gmail.com", "543123243", LocalDate.of(2000,1,1));

        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());

        }return null;


        // Aquí, leyendo el enunciado, yo entendía que, introduciendo un DNI, podamos obtener los datos de un huesped ya existente en el sistema, su nombre, correo, telefono, etc...
        // Sin embargo, no se como hacerlo, ya que no consigo entender como añadir el array dentro de esta clase para que lo recorre y hacer la comparacion del DNI de un huesped con el DNI introducido.
        // pregunté a chatGPT sobre este metodo en concreto y me dio una solución que, no se si podría funcionar porque no llegué a implementar ya que era una solucion en la que creaba el metodo con parámetros, cosa que
        // no podía hacer porque este mñetodo no se le pasan parámetros. Pregunte a compañeros de la clase que habían hecho este método sobre que es lo que hicieron mas o menos para tener una idea de que hacer y me dijeron
        // que lo que habian hecho era pedirle el DNI y crear un objeto con los parametros inventados menos el dni que sería el que nosotros insertamos...


    /*String dni;
        System.out.println("Inserte el Dni del huesped: ");
        dni = Entrada.cadena();
        try{
            return new Huesped("José Juan",dni, "josejuan@gmail.com","654432143", LocalDate.of(2001,01,01));
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }return null;*/

    }

    public static LocalDate leerFecha(String mensaje){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha = null;

        do {
            try {
                System.out.println(mensaje);
                String fechaString = Entrada.cadena();
                fecha = LocalDate.parse(fechaString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha introducida tiene un formato incorrecto (Usar dd/MM/yyyy)");
            }
        } while (fecha == null);

        return fecha;
    }

    public static LocalDateTime leerFechaHora(String mensaje){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime fecha = null;

        do {
            try {
                System.out.println(mensaje);
                String fechaString = Entrada.cadena();
                fecha = LocalDateTime.parse(fechaString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("La fecha introducida tiene un formato incorrecto (Usar dd/MM/yyyy HH:mm:ss)");
            }
        } while (fecha == null);

        return fecha;
    }


    public static Habitacion leerHabitacion(){
        int numeroPlanta, numeroPuerta;
        double precio;
        int numCamasIndividuales;
        int numCamasDobles;
        int numBanos;
        char respuesta=' ';
        boolean jacuzzi=false;
    // Esto lo hago porque con las expceciones el programa se me apaga por completo y tengo que reiniciar el programa cada vez que hago un fallo, por lo que para ahorrarme eso tengo que hacer comprobaciones con el Do While.
            //do {
                System.out.println("Escriba el número de la planta: ");
                numeroPlanta = Entrada.entero();
                //if (numeroPlanta < Habitacion.MIN_NUMERO_PLANTA || numeroPlanta > Habitacion.MAX_NUMERO_PLANTA)
                    //System.out.println("El numero de la planta debe ser entre 1 y 3.");
            //} while (numeroPlanta < Habitacion.MIN_NUMERO_PLANTA || numeroPlanta > Habitacion.MAX_NUMERO_PLANTA);
            //do {
                System.out.println("Escriba el número de la puerta: ");
                numeroPuerta = Entrada.entero();
              //  if (numeroPuerta < Habitacion.MIN_NUMERO_PUERTA || numeroPuerta > Habitacion.MAX_NUMERO_PUERTA)
                //    System.out.println("El numero de la puerta debe ser entre 0 y 14.");
            //} while (numeroPuerta < Habitacion.MIN_NUMERO_PUERTA || numeroPuerta > Habitacion.MAX_NUMERO_PUERTA);
        //do {
            System.out.println("Escriba el precio de la habitación: ");
            precio = Entrada.realDoble();
          //  if (precio<Habitacion.MIN_PRECIO_HABITACION || precio>Habitacion.MAX_PRECIO_HABITACION) System.out.println("El precio de una habitación no debe ser superior a 40 o 150.");
        //}while(precio<Habitacion.MIN_PRECIO_HABITACION || precio>Habitacion.MAX_PRECIO_HABITACION);
        System.out.println("Indique el tipo de habitación: ");
        TipoHabitacion tipo = leerTipoHabitacion();
        try {
            switch (tipo) {
                case SIMPLE:try{ return new Simple(numeroPlanta, numeroPuerta, precio); }catch(NullPointerException | IllegalArgumentException e){
                    System.out.println("-"+e.getMessage());
                    break;
                }
                case DOBLE:

                    try {
                        System.out.println("Inserte el número de camas individuales: ");
                        numCamasIndividuales = Entrada.entero();
                        System.out.println("Inserte el número de camas dobles: ");
                        numCamasDobles = Entrada.entero();
                        /*int opcion;
                        do {
                            System.out.println("Indique el formato de las camas: ");
                            System.out.println("1.- 2 camas individuales y 0 dobles");
                            System.out.println("2.- 1 cama individual y 1 doble");
                            opcion = Entrada.entero();
                        }while (opcion<1 || opcion>2);
                        switch (opcion){
                            case 1:
                                return new Doble(numeroPlanta, numeroPuerta, precio, 2,0);
                            case 2:
                                return new Doble(numeroPlanta, numeroPuerta, precio, 1,1);
                        }*/
                        return new Doble(numeroPlanta, numeroPuerta, precio, numCamasIndividuales, numCamasDobles);
                    }catch (NullPointerException | IllegalArgumentException e){
                        System.out.println("-"+e.getMessage());
                        break;
                    }
                case TRIPLE:

                    try {
                        System.out.println("Inserte el número de camas individuales: ");
                        numCamasIndividuales = Entrada.entero();
                        System.out.println("Inserte el número de camas dobles: ");
                        numCamasDobles = Entrada.entero();
                        System.out.println("Inserte la cantidad de baños en la habitación: ");
                        numBanos = Entrada.entero();
                        return new Triple(numeroPlanta, numeroPuerta, precio, numBanos, numCamasIndividuales, numCamasDobles);
                        /*int opcion;
                        do {
                            System.out.println("Indique el formato de las camas: ");
                            System.out.println("1.- 3 camas individuales y 0 dobles");
                            System.out.println("2.- 1 cama individual y 1 doble");
                            opcion = Entrada.entero();
                            System.out.println("Inserte la cantidad de baños en la habitación: ");
                            numBanos = Entrada.entero();
                        }while (opcion<1 || opcion>2);
                        switch (opcion){
                            case 1:
                                return new Triple(numeroPlanta, numeroPuerta, precio, numBanos,3,0);
                            case 2:
                                return new Triple(numeroPlanta, numeroPuerta, precio, numBanos,1,1);
                        }*/

                    }catch(NullPointerException | IllegalArgumentException e){
                        System.out.println("-"+e.getMessage());
                        break;
                    }
                case SUITE:
                    try {
                        System.out.println("Inserte la cantidad de baños en la habitación: ");
                        numBanos = Entrada.entero();
                        do {
                            System.out.println("¿Dispone la habitación de Jacuzzi?:  (S/N)");
                            respuesta = Entrada.caracter();
                            if (respuesta != 'N' && respuesta != 'S') System.out.println("La respuesta debe ser S o N");
                        } while (respuesta != 'N' && respuesta != 'S');
                        if (respuesta == 'S') jacuzzi = true;
                        else jacuzzi = false;
                        return new Suite(numeroPlanta, numeroPuerta, precio, numBanos, jacuzzi);
                    }catch (NullPointerException | IllegalArgumentException e){
                        System.out.println("-"+e.getMessage());
                        break;
                    }

            }
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }

        return null;
    }

    public static Habitacion leerHabitacionPorIdentificador(){

        try{
            int numPlanta, numPuerta;
            //String combinacion;
            do {
                System.out.println("Indique el numero de la planta: ");
                numPlanta = Entrada.entero();
                if (numPlanta<Habitacion.MIN_NUMERO_PLANTA || numPlanta>Habitacion.MAX_NUMERO_PLANTA) System.out.println("El numero de la planta no puede ser superior a 3 o menor de 1.");
            }while (numPlanta<Habitacion.MIN_NUMERO_PLANTA || numPlanta>Habitacion.MAX_NUMERO_PLANTA);
            do {
                System.out.println("Indique el numero de la puerta: ");
                numPuerta = Entrada.entero();
                if (numPuerta<Habitacion.MIN_NUMERO_PUERTA || numPuerta>Habitacion.MAX_NUMERO_PUERTA) System.out.println("El numero de puerta no puede ser menor de 0 o mayor de 14.");
            }while (numPuerta<Habitacion.MIN_NUMERO_PUERTA || numPuerta>Habitacion.MAX_NUMERO_PUERTA);
            //combinacion = ""+numPlanta+numPuerta;

            /*Iterator<Habitacion> iteradorHabitacion= habitaciones.get().iterator();
            while (iteradorHabitacion.hasNext()){
                Habitacion habitacion = iteradorHabitacion.next();
                if (habitacion.getIdentificador().equals(combinacion))*/

                    return new Simple(numPlanta,numPuerta,140);

            /*for (Habitacion habitacionCorrespondiente: habitaciones.get()){
                if (habitacionCorrespondiente.getIdentificador().equals(combinacion))
                    return habitacionCorrespondiente;
            }*/

        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
        return null;

    }

    public static TipoHabitacion leerTipoHabitacion(){
        int opcion;
        do {
            System.out.println("Tipos de habitación: ");

            TipoHabitacion menuOpciones[]= TipoHabitacion.values();

            for (TipoHabitacion tipo: menuOpciones){
                System.out.println(tipo.toString());
            }

            System.out.println("Elije un tipo de habitación: ");
            opcion = Entrada.entero();
            if (opcion<1 || opcion>TipoHabitacion.values().length) System.out.println("Elija una opción adecuada, por favor.");
        }while (opcion<1 || opcion>TipoHabitacion.values().length);
            return TipoHabitacion.values()[opcion-1];
    }

    public static Regimen leerRegimen(){
        int opcion;
        do {
            System.out.println("Tipos de régimen: ");
            Regimen menuOpciones[]= Regimen.values();

            for (Regimen regimen: menuOpciones){
                System.out.println(regimen.toString());
            }
            System.out.println("Elije un tipo de régimen: ");
            opcion = Entrada.entero();
            if (opcion<1 || opcion>Regimen.values().length+1) System.out.println("Elija una opción adecuada, por favor.");
        }while (opcion<1 || opcion>Regimen.values().length+1);
        return Regimen.values()[opcion-1];
    }

    public static Reserva leerReserva(){

        int numPersonas;
        Huesped huesped;
        Habitacion habitacion;

        do {
            huesped = getClientePorDni();
        }while (huesped==null);

        do {
            habitacion = leerHabitacionPorIdentificador();
        }while (habitacion==null);

        System.out.println("Seleccione un tipo de régimen: ");
        Regimen regimen = leerRegimen();


            do {
                System.out.println("Indique el número de personas en la reserva: ");
                numPersonas = Entrada.entero();
                if (numPersonas <= 0 || numPersonas > habitacion.getNumeroMaximoPersonas()) {
                    System.out.println("No se admite esa cantidad de personas en la habitación.");
                }
            }while(numPersonas<=0 || numPersonas>habitacion.getNumeroMaximoPersonas());

        try {
            return new Reserva(huesped, habitacion, regimen, leerFecha("Inserte la fecha de inicio de reserva: "), leerFecha("Inserte la fecha de fin de reserva: "), numPersonas);
        }catch(NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
            return null;
        }


    }

}
