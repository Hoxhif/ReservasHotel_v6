package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public enum TipoHabitacion {

    SIMPLE("SIMPLE"),
    DOBLE("DOBLE"),
    TRIPLE("TRIPLE"),
    SUITE("SUITE");

    private String cadenaAMostrar;

    private TipoHabitacion(String cadenaAMostrar){
        this.cadenaAMostrar=cadenaAMostrar;
    }


    @Override
    public String toString() {
        return ordinal()+1+".- "+cadenaAMostrar;
    }
}

