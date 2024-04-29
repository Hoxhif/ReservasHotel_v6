package org.iesalandalus.programacion.reservashotel.vista.grafica;

import org.iesalandalus.programacion.reservashotel.vista.Vista;

import java.io.IOException;

public class VistaGrafica extends Vista {

    private static VistaGrafica instancia;

    public VistaGrafica(){

    }

    public static VistaGrafica getInstancia(){
        if (instancia == null)
            instancia = new VistaGrafica();
        return instancia;
    }


    @Override
    public void comenzar() {
            LanzadorVentanaPrincipal.comenzar();
    }

    @Override
    public void terminar() {
        getControlador().terminar();
    }
}
