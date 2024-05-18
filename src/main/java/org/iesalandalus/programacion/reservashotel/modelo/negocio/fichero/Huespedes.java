package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHuespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.iesalandalus.programacion.reservashotel.vista.grafica.VistaGrafica;
import org.w3c.dom.*;

import javax.naming.OperationNotSupportedException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Huespedes implements IHuespedes {

    private ArrayList<Huesped> coleccionHuesped= new ArrayList<>();
    private Document DOM;
    private Element listaHuespedes;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final String RUTA_FICHERO="datos/huespedes.xml";
    private static final String RAIZ="Huespedes";
    private static final String HUESPED="Huesped";
    private static final String NOMBRE="Nombre";
    private static final String DNI="Dni";
    private static final String CORREO="Correo";
    private static final String TELEFONO="Telefono";
    private static final String FECHA_NACIMIENTO="FechaNacimiento";
    private static Huespedes instancia;


    public Huespedes(){
        comenzar();
    }

    @Override
    public int getTamano() {
        return listaHuespedes.getChildNodes().getLength();
    }

    @Override
    public ArrayList<Huesped> get(){
        ArrayList<Huesped> copiaHuesped= new ArrayList<Huesped>();
        Huesped huesped;

        Iterator<Huesped> huespedIterator= coleccionHuesped.iterator();
        while (huespedIterator.hasNext()){
            //Habitacion habitacion= new Habitacion(habitacionIterator.next());
            huesped= huespedIterator.next();
                //Hacemos uso de un casting para convertir la habitaci�n en un simple.
                copiaHuesped.add(new Huesped(huesped));
        }
        // Hab�a usado reversed al principio porque pensaba que ir�a de mas a menos, pero parece ser que no, que va de menos a mas por defecto.
        Collections.sort(copiaHuesped, Comparator.comparing(Huesped::getDni));
        return copiaHuesped;
    }

    @Override
    public void insertar (Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede insertar un huesped nulo.");

        if (get().contains(huesped)){
            throw new OperationNotSupportedException("ERROR: Ya existe un huesped con ese DNI.");
        }

        coleccionHuesped.add(huesped);
        //escribirXML(huesped);
        //leerXML();
    }

    @Override
    public Huesped buscar (Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede buscar un huesped nulo.");
        if (get().contains(huesped)) {
            Iterator<Huesped> iteradorHuesped = get().iterator();
            while (iteradorHuesped.hasNext()) {
                if (huesped.equals(iteradorHuesped.next()))
                    return huesped;
            }
        }return null;
    }

    @Override
    public void borrar (Huesped huesped) throws OperationNotSupportedException{
        if (huesped == null)
            throw new NullPointerException("ERROR: No se puede borrar un huesped nulo nula.");
        if (!get().contains(huesped))
            throw new OperationNotSupportedException("ERROR: No existe ning�n huesped como la indicada.");
        else{
            coleccionHuesped.remove(huesped);

            // Esto es obtenido por chatgpt.
            NodeList huespedNodes = listaHuespedes.getElementsByTagName(HUESPED);
            for (int i = 0; i < huespedNodes.getLength(); i++) {
                Element huespedElement = (Element) huespedNodes.item(i);
                String dniHuesped = huespedElement.getElementsByTagName(DNI).item(0).getTextContent();
                if (dniHuesped.equals(huespedElement.getAttribute(DNI))) {
                    Node parent = huespedElement.getParentNode();
                    parent.removeChild(huespedElement);
                    break;
                }
            }
        }
    }

    public static Huespedes getInstancia(){
        if (instancia == null)
            instancia = new Huespedes();
        return instancia;
    }

    public void comenzar(){
        // El método getDocumentElement nos proporciona el elemento Raiz de nuestro XML.
        //listaHuespedes = DOM.getDocumentElement();
        //DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
        //leerXML();
        //if (UtilidadesXML.xmlToDom(RUTA_FICHERO) != null)
        DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
        if (DOM == null){
            DOM= UtilidadesXML.crearDomVacio(RUTA_FICHERO, RAIZ);
        }
        leerXML();
        listaHuespedes = DOM.getDocumentElement();
        }


    public void terminar(){
        escribirXML();
        if (UtilidadesXML.domToXml(DOM, RUTA_FICHERO))
            System.out.println("Archivos guardados correctamente");
        else System.out.println("Error al guardar los archivos");
    }

    public Huesped elementToHuesped(Element elementoHuesped){
        String nombre=null;
        String dni;
        String correo=null;
        String telefono=null;
        LocalDate fechaNac=null;
        String fechaNacimiento;
/*
        nombre = elementoHuesped.getAttribute(NOMBRE);
        dni = elementoHuesped.getAttribute(DNI);
        correo = elementoHuesped.getAttribute(CORREO);
        telefono = elementoHuesped.getAttribute(TELEFONO);
        fechaNacimiento = elementoHuesped.getAttribute(FECHA_NACIMIENTO);
        String fechadividida[] = fechaNacimiento.split("/");
        fechaNac = LocalDate.of(Integer.parseInt(fechadividida[2]), Integer.parseInt(fechadividida[1]), Integer.parseInt(fechadividida[0]));*/

        NodeList nombreList = elementoHuesped.getElementsByTagName(NOMBRE);
        if (nombreList.getLength() > 0) {
            nombre = nombreList.item(0).getTextContent();
        }

        /*NodeList dniList = elementoHuesped.getElementsByTagName(DNI);
        if (dniList.getLength() > 0) {
            dni = dniList.item(0).getTextContent();
        }*/

        dni = elementoHuesped.getAttribute(DNI);

        NodeList correoList = elementoHuesped.getElementsByTagName(CORREO);
        if (correoList.getLength() > 0) {
            correo = correoList.item(0).getTextContent();
        }

        NodeList telefonoList = elementoHuesped.getElementsByTagName(TELEFONO);
        if (telefonoList.getLength() > 0) {
            telefono = telefonoList.item(0).getTextContent();
        }

        NodeList fechaNacimientoList = elementoHuesped.getElementsByTagName(FECHA_NACIMIENTO);
        if (fechaNacimientoList.getLength() > 0) {
            fechaNacimiento = fechaNacimientoList.item(0).getTextContent();
            String fechadividida[] = fechaNacimiento.split("/");
            fechaNac = LocalDate.of(Integer.parseInt(fechadividida[2]), Integer.parseInt(fechadividida[1]), Integer.parseInt(fechadividida[0]));
        }


        return new Huesped(nombre, dni, correo, telefono, fechaNac);
    }

    public Element huespedToElement(Huesped huesped){
        Element huespedDOM = DOM.createElement(HUESPED);
        huespedDOM.setAttribute(DNI, huesped.getDni());

        Element nombreDOM = DOM.createElement(NOMBRE);
        nombreDOM.setTextContent(huesped.getNombre());
        huespedDOM.appendChild(nombreDOM);

        Element correoDOM = DOM.createElement(CORREO);
        correoDOM.setTextContent(huesped.getCorreo());
        huespedDOM.appendChild(correoDOM);

        Element telefonoDOM = DOM.createElement(TELEFONO);
        telefonoDOM.setTextContent(huesped.getTelefono());
        huespedDOM.appendChild(telefonoDOM);

        Element fechaDOM = DOM.createElement(FECHA_NACIMIENTO);
        fechaDOM.setTextContent(huesped.getFechaNacimiento().format(FORMATO_FECHA));
        huespedDOM.appendChild(fechaDOM);

        return huespedDOM;
    }

    public void leerXML(){
        /*for (int i = 0; i < DOM.getElementsByTagName(HUESPED).getLength(); i++){
            Element huesped = (Element) DOM.getElementsByTagName(HUESPED).item(i);
            /*
            String dni = huesped.getElementsByTagName(DNI).item(0).getTextContent();
            String nombre = huesped.getElementsByTagName(NOMBRE).item(0).getTextContent();
            String telefono = huesped.getElementsByTagName(TELEFONO).item(0).getTextContent();
            String correo = huesped.getElementsByTagName(CORREO).item(0).getTextContent();
            LocalDate fechaNac = LocalDate.parse(huesped.getElementsByTagName(FECHA_NACIMIENTO).item(0).getTextContent());
            coleccionHuesped.add(elementToHuesped(huesped));*/
            NodeList listaNodos = DOM.getDocumentElement().getChildNodes();
            for (int i=0; i<listaNodos.getLength(); i++){
                Node nodo= listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE){
                    coleccionHuesped.add(elementToHuesped((Element)DOM.getDocumentElement().getChildNodes().item(i)));
                }
        }
    }

    public void escribirXML(){
        for (Huesped h: coleccionHuesped){
            listaHuespedes.appendChild(huespedToElement(h));
        }
            //listaHuespedes.appendChild(huespedToElement(huesped));
    }

}