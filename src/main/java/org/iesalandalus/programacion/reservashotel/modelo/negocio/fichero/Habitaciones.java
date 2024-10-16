package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IHabitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Habitaciones implements IHabitaciones {
    private ArrayList<Habitacion> coleccionHabitaciones= new ArrayList<Habitacion>();

    private Document DOM;
    private Element listaHabitaciones;

    private static final String RUTA_FICHERO = "datos/habitaciones.xml";
    private static final String RAIZ="Habitaciones";
    private static final String HABITACION="Habitaci�n";
    private static final String IDENTIFICADOR="Identificador";
    private static final String PLANTA="Planta";
    private static final String PUERTA="Puerta";
    private static final String PRECIO="Precio";
    private static final String CAMAS_INDIVIDUALES="CamasIndividuales";
    private static final String CAMAS_DOBLES="CamasDobles";
    private static final String BANOS="Banos";
    private static final String JACUZZI="Jacuzzi";
    private static final String TIPO="Tipo";
    private static final String SIMPLE="Simple";
    private static final String DOBLE="Doble";
    private static final String TRIPLE="Triple";
    private static final String SUITE ="Suite";
    private static Habitaciones instancia;


    public Habitaciones(){
        comenzar();
    }

    public static Habitaciones getInstancia(){
        if (instancia == null)
            instancia = new Habitaciones();
        return instancia;
    }

    @Override
    public ArrayList<Habitacion> get(){
        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<Habitacion>();
        Habitacion habitacion;

        Iterator<Habitacion> habitacionIterator= coleccionHabitaciones.iterator();
        while (habitacionIterator.hasNext()){
            //Habitacion habitacion= new Habitacion(habitacionIterator.next());
            habitacion= habitacionIterator.next();
            if (habitacion instanceof Simple)
                //Hacemos uso de un casting para convertir la habitaci�n en un simple.
                copiaHabitaciones.add(new Simple((Simple)habitacion));
            else if (habitacion instanceof Doble)
                copiaHabitaciones.add(new Doble((Doble)habitacion));
            else if (habitacion instanceof Triple)
                copiaHabitaciones.add(new Triple((Triple) habitacion));
            else if (habitacion instanceof Suite)
                copiaHabitaciones.add(new Suite((Suite) habitacion));
        }
        // Hab�a usado reversed al principio porque pensaba que ir�a de mas a menos, pero parece ser que no, que va de menos a mas por defecto.
        Collections.sort(copiaHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
        return copiaHabitaciones;

    }

    @Override
    public ArrayList<Habitacion> get(TipoHabitacion tipoHabitacion){

        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo // Es posible que se haya equivocado al escribir el DNI.");

        ArrayList<Habitacion> copiaHabitaciones= new ArrayList<>();

        Iterator<Habitacion> iteradorHabitacion= get().iterator();

        while(iteradorHabitacion.hasNext()){
            Habitacion comprobarHabitacion= iteradorHabitacion.next();
            switch (tipoHabitacion){
                case SIMPLE:
                    if (comprobarHabitacion instanceof Simple) {
                        copiaHabitaciones.add((Simple) comprobarHabitacion);
                    }
                    break;
                case DOBLE:
                    if (comprobarHabitacion instanceof Doble) {
                        copiaHabitaciones.add((Doble) comprobarHabitacion);
                    }
                    break;
                case TRIPLE:
                    if (comprobarHabitacion instanceof Triple) {
                        copiaHabitaciones.add((Triple) comprobarHabitacion);
                    }
                    break;
                case SUITE:
                    if (comprobarHabitacion instanceof Suite) {
                        copiaHabitaciones.add((Suite) comprobarHabitacion);
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de habitaci�n no soportado: " + tipoHabitacion);
            }
        }
        Collections.sort(copiaHabitaciones, Comparator.comparing(Habitacion::getIdentificador));
        return copiaHabitaciones;
    }
    @Override
    public int getTamano() {
        return listaHabitaciones.getChildNodes().getLength();
    }


    @Override
    public void insertar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede insertar una habitaci�n nula.");

        if (get().contains(habitacion)){
            throw new OperationNotSupportedException("ERROR: Ya existe una habitaci�n con ese identificador.");
        }

        coleccionHabitaciones.add(habitacion);
        //escribirXML(habitacion);
    }

    @Override
    public Habitacion buscar (Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede buscar una habitaci�n nula.");
        if (get().contains(habitacion)) {
            //return get().get(get().indexOf(habitacion)); // al final lo he dejado como estaba

            // Aqu� en vez de lo que ten�a hecho he usado el Iterator.

            Iterator<Habitacion> iteradorHabitacion = get().iterator();
            while (iteradorHabitacion.hasNext()) {
                if (habitacion.equals(iteradorHabitacion.next()))
                    return habitacion;
            }

        }return null;
    }

    @Override
    public void borrar (Habitacion habitacion) throws OperationNotSupportedException{
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se puede borrar una habitaci�n nula.");
        if (!get().contains(habitacion))
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitaci�n como la indicada.");
        else{
            coleccionHabitaciones.remove(habitacion);

            NodeList habitacionNodes = listaHabitaciones.getElementsByTagName(HABITACION);
            for (int i = 0; i < habitacionNodes.getLength(); i++) {
                Element habitacionElement = (Element) habitacionNodes.item(i);
                String idHabitacion = habitacionElement.getAttribute(IDENTIFICADOR);
                if (idHabitacion.equals(habitacion.getIdentificador())) {
                    Node parent = habitacionElement.getParentNode();
                    parent.removeChild(habitacionElement);
                    break;
                }
            }
        }
    }

    public Element habitacionToElement(Habitacion habitacion){
        String tipo=null;
        if (habitacion instanceof Simple) tipo=SIMPLE;
        if (habitacion instanceof Doble) tipo=DOBLE;
        if (habitacion instanceof Triple) tipo=TRIPLE;
        if (habitacion instanceof Suite) tipo=SUITE;
        Element habitaciondDOM = DOM.createElement(HABITACION);
        habitaciondDOM.setAttribute(IDENTIFICADOR, habitacion.getIdentificador());
        habitaciondDOM.setAttribute(TIPO, tipo);

        Element plantaDOM = DOM.createElement(PLANTA);
        plantaDOM.setTextContent(habitacion.getPlanta()+"");
        habitaciondDOM.appendChild(plantaDOM);

        Element puertaDOM = DOM.createElement(PUERTA);
        puertaDOM.setTextContent(habitacion.getPuerta()+"");
        habitaciondDOM.appendChild(puertaDOM);

        Element precioDOM = DOM.createElement(PRECIO);
        precioDOM.setTextContent(Double.toString(habitacion.getPrecio()));
        habitaciondDOM.appendChild(precioDOM);

        if (habitacion instanceof Doble) {
            Element tipoDOM = DOM.createElement(tipo);
            //tipoDOM.setTextContent(tipo);
            habitaciondDOM.appendChild(tipoDOM);

            Element camasInDOM = DOM.createElement(CAMAS_INDIVIDUALES);
            camasInDOM.setTextContent(String.valueOf(((Doble) habitacion).getNumCamasIndividuales()));
            tipoDOM.appendChild(camasInDOM);

            Element camasDobDOM = DOM.createElement(CAMAS_DOBLES);
            camasDobDOM.setTextContent(String.valueOf(((Doble) habitacion).getNumCamasDobles()));
            camasDobDOM.appendChild(tipoDOM);
        }

        if (habitacion instanceof Triple){
            Element tipoDOM = DOM.createElement(tipo);
            //tipoDOM.setTextContent(tipo);
            habitaciondDOM.appendChild(tipoDOM);

            Element camasInDOM = DOM.createElement(CAMAS_INDIVIDUALES);
            camasInDOM.setTextContent(String.valueOf(((Triple) habitacion).getNumCamasIndividuales()));
            tipoDOM.appendChild(camasInDOM);

            Element camasDobDOM = DOM.createElement(CAMAS_DOBLES);
            camasDobDOM.setTextContent(String.valueOf(((Triple) habitacion).getNumCamasDobles()));
            camasDobDOM.appendChild(tipoDOM);

            Element banosDOM = DOM.createElement(BANOS);
            banosDOM.setTextContent(String.valueOf(((Triple) habitacion).getNumBanos()));
            tipoDOM.appendChild(banosDOM);
        }

        if (habitacion instanceof Suite){
            Element tipoDOM = DOM.createElement(tipo);
            //tipoDOM.setTextContent(tipo);
            habitaciondDOM.appendChild(tipoDOM);

            Element banosDOM = DOM.createElement(BANOS);
            banosDOM.setTextContent(String.valueOf(((Suite) habitacion).getNumBanos()));
            tipoDOM.appendChild(banosDOM);

            Element jacuzziDOM = DOM.createElement(JACUZZI);
            if (((Suite) habitacion).isTieneJacuzzi())
                jacuzziDOM.setTextContent("Con Jacuzzi");
            else jacuzziDOM.setTextContent("Sin Jacuzzi");
            tipoDOM.appendChild(jacuzziDOM);
        }
        return habitaciondDOM;
    }

    public Habitacion elementToHabitacion(Element elementoHabitacion){
        String planta=null;
        String puerta=null;
        String precio=null;
        String numCamIn=null;
        String numCamDob=null;
        String numBanos=null;
        boolean jacuzzi=false;

        NodeList plantaList = elementoHabitacion.getElementsByTagName(PLANTA);
        if (plantaList.getLength() > 0) {
            planta = plantaList.item(0).getTextContent();
        }

        NodeList puertaList = elementoHabitacion.getElementsByTagName(PUERTA);
        if (puertaList.getLength() > 0) {
            puerta = puertaList.item(0).getTextContent();
        }

        NodeList precioList = elementoHabitacion.getElementsByTagName(PRECIO);
        if (precioList.getLength()> 0){
            precio = precioList.item(0).getTextContent();
        }

        //planta = Integer.parseInt(elementoHabitacion.getAttribute(PLANTA));
        //puerta = Integer.parseInt(elementoHabitacion.getAttribute(PUERTA));
        //precio = Double.parseDouble(elementoHabitacion.getAttribute(PRECIO));

        switch (elementoHabitacion.getAttribute(TIPO)){
            case SIMPLE: return new Simple(Integer.parseInt(planta), Integer.parseInt(puerta), Double.parseDouble(precio));
            case DOBLE:
                NodeList numCamInList = elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES);
                if (numCamInList.getLength()>0)
                    numCamIn = numCamInList.item(0).getTextContent();
                NodeList numCamDoblesList = elementoHabitacion.getElementsByTagName(CAMAS_DOBLES);
                if (numCamDoblesList.getLength() > 0)
                    numCamDob = numCamDoblesList.item(0).getTextContent();
                return new Doble(Integer.parseInt(planta),Integer.parseInt(puerta), Double.parseDouble(precio), Integer.parseInt(numCamIn), Integer.parseInt(numCamDob));
                /*numCamIn = Integer.parseInt(elementoHabitacion.getAttribute(CAMAS_INDIVIDUALES));
                numCamDob = Integer.parseInt(elementoHabitacion.getAttribute(CAMAS_DOBLES));
                return new Doble(planta, puerta, precio, numCamIn, numCamDob);*/
            case TRIPLE:
                /*numCamIn = Integer.parseInt(elementoHabitacion.getAttribute(CAMAS_INDIVIDUALES));
                numCamDob = Integer.parseInt(elementoHabitacion.getAttribute(CAMAS_DOBLES));
                numBanos = Integer.parseInt(elementoHabitacion.getAttribute(BANOS));
                return new Triple(planta, puerta, precio, numBanos, numCamIn, numCamDob);*/
                NodeList numCamInListTriple = elementoHabitacion.getElementsByTagName(CAMAS_INDIVIDUALES);
                if (numCamInListTriple.getLength()>0)
                    numCamIn = numCamInListTriple.item(0).getTextContent();
                NodeList numCamDoblesListTriple = elementoHabitacion.getElementsByTagName(CAMAS_DOBLES);
                if (numCamDoblesListTriple.getLength() > 0)
                    numCamDob = numCamDoblesListTriple.item(0).getTextContent();
                NodeList numBanosList = elementoHabitacion.getElementsByTagName(BANOS);
                if (numBanosList.getLength()>0)
                    numBanos = numBanosList.item(0).getTextContent();
                return new Triple(Integer.parseInt(planta),Integer.parseInt(puerta), Double.parseDouble(precio), Integer.parseInt(numBanos),Integer.parseInt(numCamIn), Integer.parseInt(numCamDob));
            case SUITE:
                /*numBanos = Integer.parseInt(elementoHabitacion.getAttribute(BANOS));
                if (elementoHabitacion.getAttribute(JACUZZI).equals("Con Jacuzzi")) jacuzzi=true;
                else jacuzzi=false;
                return new Suite(planta, puerta, precio, numBanos, jacuzzi);*/
                NodeList numBanosListSuite = elementoHabitacion.getElementsByTagName(BANOS);
                if (numBanosListSuite.getLength()>0)
                    numBanos = numBanosListSuite.item(0).getTextContent();
                NodeList jacuzziList = elementoHabitacion.getElementsByTagName(JACUZZI);
                if (jacuzziList.getLength()>0)
                    if (jacuzziList.item(0).getTextContent().equals("Con Jacuzzi"))
                        jacuzzi=true;
                    else jacuzzi=false;
                return new Suite(Integer.parseInt(planta),Integer.parseInt(puerta), Double.parseDouble(precio), Integer.parseInt(numBanos), jacuzzi);
            default: return new Simple(Integer.parseInt(planta), Integer.parseInt(puerta), Double.parseDouble(precio));
        }
    }

    public void leerXML(){
        /*for (int i = 0; i < DOM.getElementsByTagName(HABITACION).getLength(); i++){
            Element habitacion = (Element) DOM.getElementsByTagName(HABITACION).item(i);
            coleccionHabitaciones.add(elementToHabitacion(habitacion));
        }*/
        NodeList listaNodos = DOM.getDocumentElement().getChildNodes();
        for (int i=0; i<listaNodos.getLength(); i++){
            Node nodo= listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE){
                coleccionHabitaciones.add(elementToHabitacion((Element)DOM.getDocumentElement().getChildNodes().item(i)));
            }
        }
    }

    public void escribirXML(){
        ArrayList<String> identificadorExistentes = new ArrayList<>();
        NodeList habitacionesNodes = listaHabitaciones.getElementsByTagName(HABITACION);
        for (int i = 0; i < habitacionesNodes.getLength(); i++) {
            Element habitacionElement = (Element) habitacionesNodes.item(i);
            String puertaHabitacion = habitacionElement.getElementsByTagName(PUERTA).item(0).getTextContent();
            String plantaHabitacion = habitacionElement.getElementsByTagName(PLANTA).item(0).getTextContent();
            identificadorExistentes.add(plantaHabitacion+puertaHabitacion);
        }
        for (Habitacion habitacion : coleccionHabitaciones) {
            if (!identificadorExistentes.contains(habitacion.getIdentificador())) {
                listaHabitaciones.appendChild(habitacionToElement(habitacion));
            }
        }
    }

    public void comenzar(){
        /*if (UtilidadesXML.xmlToDom(RUTA_FICHERO) != null)
            DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
        if (DOM == null){
            DOM= UtilidadesXML.crearDomVacio(RUTA_FICHERO, RAIZ);
        }
        leerXML();
        listaHabitaciones = DOM.getDocumentElement();*/
        DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
        if (DOM == null){
            DOM= UtilidadesXML.crearDomVacio(RUTA_FICHERO, RAIZ);
        }
        leerXML();
        listaHabitaciones = DOM.getDocumentElement();
    }
    public void terminar(){
        escribirXML();
        if (UtilidadesXML.domToXml(DOM, RUTA_FICHERO))
            System.out.println("Archivos guardados correctamente");
        else System.out.println("Error al guardar los archivos");
    }


}