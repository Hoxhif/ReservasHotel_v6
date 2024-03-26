package org.iesalandalus.programacion.reservashotel.modelo.dominio;

public class Simple extends Habitacion{
    private static final int NUM_MAXIMO_PERSONAS=1;

    public Simple(int planta, int puerta, double precio){
        super (planta, puerta,precio);
    }

    public Simple (Simple habitacionSimple){
        super(habitacionSimple);
        //super(habitacionSimple.getPlanta(), habitacionSimple.getPuerta(), habitacionSimple.getPrecio());
           /* if (habitacionSimple == null)
                throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
            super.setPlanta(habitacionSimple.getPlanta());
            super.setPuerta(habitacionSimple.getPuerta());
            super.setPrecio(habitacionSimple.getPrecio());*/
    }

    @Override
    public int getNumeroMaximoPersonas(){
        return NUM_MAXIMO_PERSONAS;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", habitación simple, capacidad=%d personas",
                getNumeroMaximoPersonas());
    }
}
