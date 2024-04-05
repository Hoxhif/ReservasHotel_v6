package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactorialFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.Vista;


public class MainApp {

    public static void main(String[] args) {
        //Inicio del programa.
        try {
            Modelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
            Vista vista = new Vista();
            Controlador controlador = new Controlador(modelo, vista);
            controlador.comenzar();
        }catch (NullPointerException | IllegalArgumentException e){
            System.out.println("-"+e.getMessage());
        }
    }

    private static FactorialFuenteDatos procesarArgumentosFuenteDatos(String[] argumentos){
        for (String cadena: argumentos){
            if (cadena.equals("-fdmongodb")) return FactorialFuenteDatos.MONGODB;
            if (cadena.equals("-fdmemoria")) return FactorialFuenteDatos.MEMORIA;
        }return FactorialFuenteDatos.MEMORIA;
    }
}