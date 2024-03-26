package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Huesped {

    // Patrones:
    private static final String ER_TELEFONO="\\d{9}"; // Son nueve numeros del 0 al 9 Ej: 654321254.
    private static final String ER_CORREO="[a-zA-Z0-9.]+@[a-zA-Z]+\\.[a-zA-Z]+"; // Son una o más palabras de la a a la z, seguido del @ con mas palabras de la a a la z con un punto y la a a la z de nuevo.
    private static final String ER_DNI="\\d{8}[A-HJ-NP-TV-Z]"; // Son ocho numeros del 0 al 9 mas una letra, no aparecen ni la I, O, U, Ñ.
    public static final String FORMATO_FECHA="\\d{4}-\\d{2}-\\d{2}"; // dos digitos del 0 al 9 separados por barra menos el año que serán 4 dígitos. En el test pone que esta es la manera correcta del formato.. \d{2}/\d{2}/\d{4}}

    // Atributos:
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;

    // Constructor con parámetros de la clase Huesped
    public Huesped(String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
    }

    // Constructor copia de la clase Huesped
    public Huesped(Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("ERROR: No es posible copiar un huésped nulo.");
        setNombre(huesped.getNombre());
        setDni(huesped.getDni());
        setCorreo(huesped.getCorreo());
        setTelefono(huesped.getTelefono());
        setFechaNacimiento(huesped.getFechaNacimiento());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws NullPointerException, IllegalArgumentException {
        if (nombre==null)
            throw new NullPointerException("ERROR: El nombre de un huésped no puede ser nulo.");
        if (nombre.isBlank())
            throw new IllegalArgumentException("ERROR: El nombre de un huésped no puede estar vacío.");
        if (!nombre.matches( "[\\p{L}\\s]+")) // Este patrón permite nombres usando abecedario y permitiendo añadir mas de un nombre y apellidos, ese patrón lo obtuve a través de ChatGPT al preguntar por un patrón de nombres, anteriormente usaba (([a-zA-Z]+( [a-zA-Z]+)*")).
            throw new IllegalArgumentException("El nombre introducido no es válido");
        this.nombre = formateaNombre(nombre);
    }

    private String formateaNombre(String nombre){
        // Este método sirve para que el nombre esté bien formateado (Sin espacios en blanco, y la primera letra de cada palabra la ponga en mayúscula, resto en minúscula.
        try {
            String nombreFormateado = "";
            String formateaNombre[] = nombre.split("\\s+");
            String nombreCompleto;
            int indice=0;
            for (String parteNombre : formateaNombre) {
                if (indice==0 && !parteNombre.isBlank()){
                    nombreCompleto = parteNombre.substring(0, 1).toUpperCase() + parteNombre.substring(1).toLowerCase();
                    nombreFormateado += nombreCompleto;
                    indice++;
                }else if (indice>0 && !parteNombre.isBlank()){
                    nombreCompleto = " "+ parteNombre.substring(0, 1).toUpperCase() + parteNombre.substring(1).toLowerCase();
                    nombreFormateado += nombreCompleto;
                }
            }
            return nombreFormateado;
        }catch(NullPointerException | IllegalArgumentException e){
            return e.getMessage();
        }

    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) throws NullPointerException, IllegalArgumentException {
        if (telefono==null)
            throw new NullPointerException("ERROR: El teléfono de un huésped no puede ser nulo.");
        if (telefono.isBlank() || !telefono.matches(ER_TELEFONO))
            throw new IllegalArgumentException("ERROR: El teléfono del huésped no tiene un formato válido.");
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) throws NullPointerException, IllegalArgumentException {
        if (correo==null)
            throw new NullPointerException("ERROR: El correo de un huésped no puede ser nulo.");
        if (correo.isBlank() || !correo.matches(ER_CORREO))
            throw new IllegalArgumentException("ERROR: El correo del huésped no tiene un formato válido.");
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) throws NullPointerException, IllegalArgumentException {
        if (dni==null)
            throw new NullPointerException("ERROR: El dni de un huésped no puede ser nulo.");
        if (dni.isBlank() || !dni.matches(ER_DNI))
            throw new IllegalArgumentException("ERROR: El dni del huésped no tiene un formato válido.");
        if (!comprobarDni(dni))
            throw new IllegalArgumentException("ERROR: La letra del dni del huésped no es correcta.");
        this.dni = dni;
    }
    private boolean comprobarDni(String dni){
        /* Aquí me quedé atascado durante días porque no entendía porque daba error esta parte.
        lo que pasaba esque yo tenía claro la diferencia entre el espacio que ocupa un DNI que son 9 y
        las posiciones dentro del String, es decir 0-8. pero claro, aqui hago un substring del string, y seguía
        utilizando las posiciones del string normal. por eso me me pillaba el DNI sin el ultimo digito del mismo.
         */
        String numeroDni=dni.substring(0,8);
        double dniNumerico=Double.parseDouble(numeroDni);
        int comprobar;
        comprobar=(int)dniNumerico%23;
        switch (comprobar) {
            case 0:
                if (dni.substring(8).equalsIgnoreCase("T"))
                    return true;
                else return false;
            case 1:
                if (dni.substring(8).equalsIgnoreCase("R"))
                    return true;
                else return false;
            case 2:
                if (dni.substring(8).equalsIgnoreCase("W"))
                    return true;
                else return false;
            case 3:
                if (dni.substring(8).equalsIgnoreCase("A"))
                    return true;
                else return false;
            case 4:
                if (dni.substring(8).equalsIgnoreCase("G"))
                    return true;
                else return false;
            case 5:
                if (dni.substring(8).equalsIgnoreCase("M"))
                    return true;
                else return false;
            case 6:
                if (dni.substring(8).equalsIgnoreCase("Y"))
                    return true;
                else return false;
            case 7:
                if (dni.substring(8).equalsIgnoreCase("F"))
                    return true;
                else return false;
            case 8:
                if (dni.substring(8).equalsIgnoreCase("P"))
                    return true;
                else return false;
            case 9:
                if (dni.substring(8).equalsIgnoreCase("D"))
                    return true;
                else return false;
            case 10:
                if (dni.substring(8).equalsIgnoreCase("X"))
                    return true;
                else return false;
            case 11:
                if (dni.substring(8).equalsIgnoreCase("B"))
                    return true;
                else return false;
            case 12:
                if (dni.substring(8).equalsIgnoreCase("N"))
                    return true;
                else return false;
            case 13:
                if (dni.substring(8).equalsIgnoreCase("J"))
                    return true;
                else return false;
            case 14:
                if (dni.substring(8).equalsIgnoreCase("Z"))
                    return true;
                else return false;
            case 15:
                if (dni.substring(8).equalsIgnoreCase("S"))
                    return true;
                else return false;
            case 16:
                if (dni.substring(8).equalsIgnoreCase("Q"))
                    return true;
                else return false;
            case 17:
                if (dni.substring(8).equalsIgnoreCase("V"))
                    return true;
                else return false;
            case 18:
                if (dni.substring(8).equalsIgnoreCase("H"))
                    return true;
                else return false;
            case 19:
                if (dni.substring(8).equalsIgnoreCase("L"))
                    return true;
                else return false;
            case 20:
                if (dni.substring(8).equalsIgnoreCase("C"))
                    return true;
                else return false;
            case 21:
                if (dni.substring(8).equalsIgnoreCase("K"))
                    return true;
                else return false;
            case 22:
                if (dni.substring(8).equalsIgnoreCase("E"))
                    return true;
                else return false;
            default:
                return false;
        }
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    private void setFechaNacimiento(LocalDate fechaNacimiento) throws NullPointerException, IllegalArgumentException {
        if (fechaNacimiento==null)
            throw new NullPointerException("ERROR: La fecha de nacimiento de un huésped no puede ser nula.");
        if (!fechaNacimiento.toString().matches(FORMATO_FECHA))
            throw new IllegalArgumentException("La fecha no es válida.");
        if (fechaNacimiento.isEqual(LocalDate.now()) || fechaNacimiento.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de nacimiento no es válida.");
        this.fechaNacimiento = fechaNacimiento;
    }

    private String getIniciales(){
        // Este algoritmo está obtenido del ejemplo compartido de la carpeta drive.
        String[] palabras = formateaNombre(getNombre()).split("\\s");
        String c = "";
        for (int i = 0; i < palabras.length; i++) {
            if (!palabras[i].equals(""))
                c = c + palabras[i].charAt(0);
        }
        return c.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s", getNombre(), getIniciales(), getDni(), getCorreo(), getTelefono(), getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

}