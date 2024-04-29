package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.controlador.Controlador;
import org.iesalandalus.programacion.reservashotel.modelo.FactorialFuenteDatos;
import org.iesalandalus.programacion.reservashotel.modelo.Modelo;
import org.iesalandalus.programacion.reservashotel.vista.FactorialVista;
import org.iesalandalus.programacion.reservashotel.vista.Vista;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

import java.io.IOException;


public class MainApp {

    public static void main(String[] args) {
        //Inicio del programa.
            try {
                Modelo modelo = new Modelo(procesarArgumentosFuenteDatos(args));
                Controlador controlador = new Controlador(modelo, procesarArgumentosVista(args).crear());
                controlador.comenzar();
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("-" + e.getMessage());
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
            if (cadena.equals("-vGrafica")) return FactorialVista.GRAFICA;
        }return FactorialVista.TEXTO;
    }

}