package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public abstract class Habitacion {
    public static final double MIN_PRECIO_HABITACION=40;
    public static final double MAX_PRECIO_HABITACION=150;
    public static final int MIN_NUMERO_PUERTA=0;
    public static final int MAX_NUMERO_PUERTA=14;
    public static final int MIN_NUMERO_PLANTA=1;
    public static final int MAX_NUMERO_PLANTA=3;

    protected String identificador;
    protected int planta;
    protected int puerta;
    protected double precio;

    /*public Habitacion(){

    }*/

    public Habitacion(int planta, int puerta, double precio){
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador(String.valueOf(getPlanta()+String.valueOf(getPuerta())));
    }

    /*public Habitacion(int planta, int puerta, double precio,TipoHabitacion tipoHabitacion){
        this(planta,puerta,precio);
        setTipoHabitacion(tipoHabitacion);
        setIdentificador(String.valueOf(getPlanta()+String.valueOf(getPuerta())));
    }*/

    public Habitacion(Habitacion habitacion){
        if (habitacion==null)
            throw new NullPointerException("ERROR: No es posible copiar una habitación nula.");
        setPlanta(habitacion.getPlanta());
        setPuerta(habitacion.getPuerta());
        setPrecio(habitacion.getPrecio());
        setIdentificador();
    }

    //Declaramos el método abstracto en nuestra clase Habitación.
    public abstract int getNumeroMaximoPersonas();


    public String getIdentificador() {
        return identificador;
    }

    protected void setIdentificador(){
        // Al principio no entendía porque había dos metodos para indentificador, y la verdad que hace días cuando hice esta clase hice este algoritmo para calcular el identificador de la habitación
        // Dias despues voy por la clase de array de habitaciones y no se ni porque hice esto, no sirve de nada y daba problemas, me sumaba uno en el identificador, supongo que al no entender porque
        // de esta clase pues hice algo para autoincrementar quizas el valor del mismo, lo dejo igualmente ahí por ahora..
       /* int siguientePlanta=getPlanta(), siguientePuerta=getPuerta();
    if (getPuerta()>0 && getPlanta()>=1) {
        if ((getPlanta() == 1 || getPlanta() == 2 || getPlanta() == 3) && getPuerta() < 14) {
        siguientePuerta++;
        } else if ((getPlanta() == 1 || getPlanta() == 2) && getPuerta() == 14) {
        siguientePlanta++;
        }*/
        this.identificador= String.valueOf(getPlanta())+String.valueOf(getPuerta());
        //}else
        // this.identificador= String.valueOf(siguientePlanta)+String.valueOf(siguientePuerta);

    }


    protected void setIdentificador(String identificador) throws NullPointerException, IllegalArgumentException {
        if (identificador==null)
            throw new NullPointerException("ERROR: El identificador de la habitación no puede ser nulo.");
        if (identificador.isBlank())
            throw new IllegalArgumentException("ERROR: El identificador no puede estar vacio.");
        this.identificador = identificador;

    }

    public int getPlanta() {
        return planta;
    }

    protected void setPlanta(int planta) throws IllegalArgumentException {
        if (planta<MIN_NUMERO_PLANTA || planta>MAX_NUMERO_PLANTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como planta de una habitación un valor menor que 1 ni mayor que 3.");
        this.planta = planta;
    }

    public int getPuerta() {
        return puerta;
    }

    protected void setPuerta(int puerta) throws IllegalArgumentException {
        if (puerta<MIN_NUMERO_PUERTA || puerta>MAX_NUMERO_PUERTA)
            throw new IllegalArgumentException("ERROR: No se puede establecer como puerta de una habitación un valor menor que 0 ni mayor que 14.");
        this.puerta = puerta;
    }

    public double getPrecio() {
        return precio;
    }

    protected void setPrecio(double precio) throws IllegalArgumentException {
        if (precio<MIN_PRECIO_HABITACION || precio>MAX_PRECIO_HABITACION)
            throw new IllegalArgumentException("ERROR: No se puede establecer como precio de una habitación un valor menor que 40.0 ni mayor que 150.0.");
        this.precio = precio;
    }

    /*
    public TipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(TipoHabitacion tipoHabitacion) throws NullPointerException {
        if (tipoHabitacion==null)
            throw new NullPointerException("ERROR: No se puede establecer un tipo de habitación nula.");
        this.tipoHabitacion = tipoHabitacion;
    }*/

    @Override
    public boolean equals(Object o) {
        /*if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return Objects.equals(identificador, that.identificador);*/
        Habitacion habitacion = (Habitacion) o;
        return habitacion.getIdentificador().equals(this.identificador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador);
    }

    @Override
    public String toString() {
        return String.format("identificador=%s (%d-%d), precio habitación=%s", getIdentificador(), getPlanta(), getPuerta(), getPrecio());
    }
}