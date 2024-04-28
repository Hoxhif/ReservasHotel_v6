package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.reservashotel.vista.texto.VistaTexto;

public enum FactorialVista {

    TEXTO{
        public Vista crear(){
            return new VistaTexto();
        }
    },
    GRAFICA{
        public Vista crear(){
            return new VistaGrafica();
        }
    };

    public abstract Vista crear();

}
