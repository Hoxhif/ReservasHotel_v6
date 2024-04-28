package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactorialFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactorialVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;


public class MainApp {

    public static void main(String[] args) {
        //Inicio del programa.
        try {
            Modelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
            Vista vista=null;
            FactorialVista fvista = procesarArgumentosVista(args);
            if (fvista.equals(FactorialVista.TEXTO))
                vista = new VistaTexto();
            else if (fvista.equals(FactorialVista.GRAFICA))
                vista = new VistaGrafica();
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

    private static FactorialVista procesarArgumentosVista(String[] argumentos){
        for (String cadena: argumentos){
            if (cadena.equals("-vTexto")) return FactorialVista.TEXTO;
            if (cadena.equals("vGrafica")) return FactorialVista.GRAFICA;
        }return FactorialVista.TEXTO;
    }

}