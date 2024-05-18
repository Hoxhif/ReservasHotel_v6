package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero;



import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reservas implements IReservas {

    private ArrayList<Reserva> coleccionReservas = new ArrayList<Reserva>();
    private Document DOM;
    private Element listaReservas;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATOR_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String RUTA_FICHERO = "datos/reservas.xml";
    private static final String RAIZ = "Reservas";
    private static final String RESERVA = "Reserva";
    private static final String DNI_HUESPED = "Dni";
    private static final String PLANTA_HABITACION = "Planta";
    private static final String PUERTA_HABITACION = "Puerta";
    private static final String FECHA_INICIO_RESERVA = "FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA = "FechaFinReserva";
    private static final String REGIMEN = "Regimen";
    private static final String NUMERO_PERSONAS = "Personas";
    private static final String CHECKIN = "FechaCheckin";
    private static final String CHECKOUT = "FechaCheckout";
    private static final String PRECIO = "Precio";
    private static Reservas instancia;


    public Reservas() {
        comenzar();
    }

    public static Reservas getInstancia() {
        if (instancia == null)
            instancia = new Reservas();
        return instancia;
    }

    @Override
    public ArrayList<Reserva> get() {
        ArrayList<Reserva> copiaReservas = new ArrayList<Reserva>();

        Iterator<Reserva> iteradorReservas = coleccionReservas.iterator();

        while (iteradorReservas.hasNext()) {
            Reserva reserva = new Reserva(iteradorReservas.next());
            copiaReservas.add(reserva);
        }

        Comparator<Reserva> comparador = new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                if (!o1.getFechaInicioReserva().equals(o2.getFechaInicioReserva()))
                    return o1.getFechaInicioReserva().compareTo(o2.getFechaInicioReserva());
                else return o1.getHabitacion().getIdentificador().compareTo(o2.getHabitacion().getIdentificador());
            }

        };
        Collections.sort(copiaReservas, comparador);

        return copiaReservas;
    }

    @Override
    public int getTamano() {
        return listaReservas.getChildNodes().getLength();
    }

    @Override
    public void insertar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (get().contains(reserva))
            throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        coleccionReservas.add(reserva);
        //escribirXML();
    }


    @Override
    public Reserva buscar(Reserva reserva) {
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        if (get().contains(reserva))
            return reserva;
        else return null;
    }

    @Override
    public void borrar(Reserva reserva) throws OperationNotSupportedException {
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (!get().contains(reserva))
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        coleccionReservas.remove(reserva);

        NodeList reservaNodes = listaReservas.getElementsByTagName(RESERVA);
        for (int i = 0; i < reservaNodes.getLength(); i++) {
            Element reservaElement = (Element) reservaNodes.item(i);
            String dniHuesped = reservaElement.getAttribute(DNI_HUESPED);
            String plantaHabi = reservaElement.getAttribute(PLANTA_HABITACION);
            String puertaHabi = reservaElement.getAttribute(PUERTA_HABITACION);
            if (dniHuesped.equals(reserva.getHuesped().getDni()) && reserva.getHabitacion().getIdentificador().equals(plantaHabi+puertaHabi)) {
                Node parent = reservaElement.getParentNode();
                parent.removeChild(reservaElement);
                break;
            }
        }
    }


    @Override
    public ArrayList<Reserva> getReservas(Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");

        ArrayList<Reserva> copiaReservaHuesped = new ArrayList<>();
        Iterator<Reserva> iteratorReserva = get().iterator();
        while (iteratorReserva.hasNext()) {
            Reserva reserva = iteratorReserva.next();
            if (reserva.getHuesped().equals(huesped))
                copiaReservaHuesped.add(reserva);
        }
        /*for (Reserva reserva: get()){
            if (reserva.getHuesped().equals(huesped))
                copiaReservaHuesped.add(reserva);
        }*/

        return copiaReservaHuesped;
    }

    @Override
    public ArrayList<Reserva> getReservas(TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        ArrayList<Reserva> copiaReservaTipoHabitacion = new ArrayList<>();
        Iterator<Reserva> iteratorReserva = get().iterator();
        while (iteratorReserva.hasNext()) {
            Reserva reserva = iteratorReserva.next();
            switch (tipoHabitacion) {
                case SIMPLE:
                    if (reserva.getHabitacion() instanceof Simple) copiaReservaTipoHabitacion.add(new Reserva(reserva));
                case DOBLE:
                    if (reserva.getHabitacion() instanceof Doble) copiaReservaTipoHabitacion.add(new Reserva(reserva));
                case TRIPLE:
                    if (reserva.getHabitacion() instanceof Triple) copiaReservaTipoHabitacion.add(new Reserva(reserva));
                case SUITE:
                    if (reserva.getHabitacion() instanceof Suite) copiaReservaTipoHabitacion.add(new Reserva(reserva));
            }

        }

        return copiaReservaTipoHabitacion;
    }


    @Override
    public ArrayList<Reserva> getReservas(Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");

        ArrayList<Reserva> reservasHabitacionFuturas = new ArrayList<>();
        Iterator<Reserva> iteradorReservasFuturas = get().iterator();

        while (iteradorReservasFuturas.hasNext()) {
            Reserva reservaFutura = iteradorReservasFuturas.next();
            if (reservaFutura.getHabitacion().equals(habitacion))
                reservasHabitacionFuturas.add(reservaFutura);
        }
        return reservasHabitacionFuturas;
    }

    @Override
    public ArrayList<Reserva> getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        LocalDate fechaActual = LocalDate.now();
        ArrayList<Reserva> reservasHabitacionFuturas = new ArrayList<>();
        Iterator<Reserva> iteradorReservasFuturas = get().iterator();

        while (iteradorReservasFuturas.hasNext()) {
            Reserva reservaFutura = iteradorReservasFuturas.next();
            if (reservaFutura.getHabitacion().equals(habitacion) && reservaFutura.getFechaInicioReserva().isAfter(fechaActual))
                reservasHabitacionFuturas.add(reservaFutura);
        }
        return reservasHabitacionFuturas;
    }

    @Override
    public void realizarCheckin(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null | fecha == null)
            throw new NullPointerException("Ni la reserva ni la fecha pueden ser nulas.");
        Iterator<Reserva> iteradorReservaCheckin = coleccionReservas.iterator();
        while (iteradorReservaCheckin.hasNext()) {
            Reserva reservaCheckin = iteradorReservaCheckin.next();
            if (reservaCheckin != null)
                if (reservaCheckin.equals(reserva))
                    reservaCheckin.setCheckIn(fecha);
        }
    }

    public Element reservaToElement(Reserva reserva) {
        Element reservadDOM = DOM.createElement(RESERVA);
        reservadDOM.setAttribute(DNI_HUESPED, reserva.getHuesped().getDni());
        reservadDOM.setAttribute(PLANTA_HABITACION, String.valueOf(reserva.getHabitacion().getPlanta()));
        reservadDOM.setAttribute(PUERTA_HABITACION, String.valueOf(reserva.getHabitacion().getPuerta()));

        Element regimenDOM = DOM.createElement(REGIMEN);
        regimenDOM.setTextContent(reserva.getRegimen().toString());
        reservadDOM.appendChild(regimenDOM);

        Element fechaIn = DOM.createElement(FECHA_INICIO_RESERVA);
        fechaIn.setTextContent(reserva.getFechaInicioReserva().format(FORMATO_FECHA));
        reservadDOM.appendChild(fechaIn);

        Element fechaFinDOM = DOM.createElement(FECHA_FIN_RESERVA);
        fechaFinDOM.setTextContent(reserva.getFechaFinReserva().format(FORMATO_FECHA));
        reservadDOM.appendChild(fechaFinDOM);

        Element numPersonasDOM = DOM.createElement(NUMERO_PERSONAS);
        numPersonasDOM.setTextContent(reserva.getNumeroPersonas() + "");
        reservadDOM.appendChild(numPersonasDOM);

        Element checkInDOM = DOM.createElement(CHECKIN);
        if (reserva.getCheckIn() != null) checkInDOM.setTextContent(reserva.getCheckIn().format(FORMATOR_FECHA_HORA));
        reservadDOM.appendChild(checkInDOM);

        Element checkOutDOM = DOM.createElement(CHECKOUT);
        if (reserva.getCheckOut() != null)
            checkOutDOM.setTextContent(reserva.getCheckOut().format(FORMATOR_FECHA_HORA));
        reservadDOM.appendChild(checkOutDOM);

        Element precioDOM = DOM.createElement(PRECIO);
        precioDOM.setTextContent(reserva.getPrecio() + "");
        reservadDOM.appendChild(precioDOM);

        return reservadDOM;
    }

    public Reserva elementToReserva(Element elementoReserva) {
        String dni;
        String identificador;
        Huesped huesped = null;
        Habitacion habitacion = null;
        String regimenString=null;
        Regimen regimen = null;
        String fechaIn=null;
        String fechaFin=null;
        LocalDate fechaIFinal;
        LocalDate fechaFFinal;
        String numPersonas=null;
        String checkInString="";
        String checkOutString="";
        Reserva reserva;

        dni = elementoReserva.getAttribute(DNI_HUESPED);

        identificador = elementoReserva.getAttribute(PLANTA_HABITACION)+elementoReserva.getAttribute(PUERTA_HABITACION);


        for (Huesped huesoped: Huespedes.getInstancia().get()) {
            if (huesoped.getDni().equals(dni))
                huesped = huesoped;
        }
        for (Habitacion h: Habitaciones.getInstancia().get()) {
            if (h.getIdentificador().equals(identificador))
                habitacion = h;
        }

        NodeList fechaILista = elementoReserva.getElementsByTagName(FECHA_INICIO_RESERVA);
        if (fechaILista.getLength()>0)
            fechaIn = fechaILista.item(0).getTextContent();
        String fechadivididaI[] = fechaIn.split("/");
        fechaIFinal = LocalDate.of(Integer.parseInt(fechadivididaI[2]), Integer.parseInt(fechadivididaI[1]), Integer.parseInt(fechadivididaI[0]));

        NodeList fechaFLista = elementoReserva.getElementsByTagName(FECHA_FIN_RESERVA);
        if (fechaFLista.getLength()>0)
            fechaFin = fechaFLista.item(0).getTextContent();
        String fechadivididaF[] = fechaFin.split("/");
        fechaFFinal = LocalDate.of(Integer.parseInt(fechadivididaF[2]), Integer.parseInt(fechadivididaF[1]), Integer.parseInt(fechadivididaF[0]));

        NodeList numPersonasList = elementoReserva.getElementsByTagName(NUMERO_PERSONAS);
        if (numPersonasList.getLength()>0)
            numPersonas = numPersonasList.item(0).getTextContent();

        NodeList regimenList = elementoReserva.getElementsByTagName(REGIMEN);
        if (regimenList.getLength()>0)
            regimenString = regimenList.item(0).getTextContent();

        for (Regimen r: Regimen.values()){
            if (r.toString().equals(regimenString))
                regimen=r;
        }

        reserva = new Reserva(huesped, habitacion, regimen, fechaIFinal, fechaFFinal, Integer.parseInt(numPersonas));

        NodeList checkInList = elementoReserva.getElementsByTagName(CHECKIN);
        if (checkInList.getLength()>0)
            checkInString= checkInList.item(0).getTextContent();
        if (!checkInString.isBlank()) {
            reserva.setCheckIn(LocalDateTime.parse(checkInString, FORMATOR_FECHA_HORA));
        }

        NodeList checkoutList = elementoReserva.getElementsByTagName(CHECKOUT);
        if (checkoutList.getLength()>0)
            checkOutString = checkoutList.item(0).getTextContent();
        if (!checkOutString.isBlank())
            reserva.setCheckOut(LocalDateTime.parse(checkOutString,FORMATOR_FECHA_HORA));
        return reserva;
    }

    public void leerXML() {
        NodeList listaNodos = DOM.getDocumentElement().getChildNodes();
        for (int i = 0; i < listaNodos.getLength(); i++) {
            Node nodo = listaNodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                coleccionReservas.add(elementToReserva((Element) DOM.getDocumentElement().getChildNodes().item(i)));
            }
        }
    }

    public void escribirXML() {
        //ArrayList<String> dniExistentes = new ArrayList<>();
        //ArrayList<String> identificadorExistentes = new ArrayList<>();
        /*NodeList reservasNodes = listaReservas.getElementsByTagName(RESERVA);
        for (int i = 0; i < reservasNodes.getLength(); i++) {
            Element reservaElement = (Element) reservasNodes.item(i);
            String dniHuesped = reservaElement.getAttribute(DNI_HUESPED);
            String habiPlanta = reservaElement.getAttribute(PLANTA_HABITACION);
            String habiPuerta = reservaElement.getAttribute(PUERTA_HABITACION);
            String idHabi = habiPlanta + habiPuerta;

            //dniExistentes.add(dniHuesped);
            //identificadorExistentes.add(habiPlanta+habiPuerta);

            for (Reserva reserva : get()){
                if (!reserva.getHabitacion().getIdentificador().equals(idHabi) || !reserva.getHuesped().getDni().equals(dniHuesped)){
                    listaReservas.appendChild(reservaToElement(reserva));
                }
                else if (reserva.getCheckIn() != null || reserva.getCheckOut() != null){
                    try{
                        Reserva reservaCambiada = new Reserva(reserva);
                        borrar(reserva);
                        insertar(reservaCambiada);
                        listaReservas.appendChild(reservaToElement(reservaCambiada));
                    }catch (OperationNotSupportedException e){
                        System.out.println(e.getMessage());
                }
            }
        }
        }*/
        NodeList reservasNode = listaReservas.getElementsByTagName(RESERVA);
        for (Reserva reserva: get()){
            boolean existe= false;
            for (int i=0; i<reservasNode.getLength();i++) {
                Node nodoReservas = reservasNode.item(i); //Esto lo he tenido que hacer para quitarme un problema de class cast exception usando chatgpt
                if (nodoReservas.getNodeType() == Node.ELEMENT_NODE) {
                    Element reservaElemento = (Element) nodoReservas;
                    String dniHuesped = reservaElemento.getAttribute(DNI_HUESPED);
                    String plantaHabi = reservaElemento.getAttribute(PLANTA_HABITACION);
                    String puertaHabi = reservaElemento.getAttribute(PUERTA_HABITACION);
                    String idHabi = plantaHabi + puertaHabi;
                    if (reserva.getHabitacion().getIdentificador().equals(idHabi) && reserva.getHuesped().getDni().equals(dniHuesped)) {
                        existe = true;
                        if (reserva.getCheckIn() != null || reserva.getCheckOut() != null) {
                            Element nuevaReservaElemento = reservaToElement(reserva);
                            listaReservas.replaceChild(nuevaReservaElemento, reservaElemento);
                        }
                        break;
                    }
                }
            }
            if (!existe){
                listaReservas.appendChild(reservaToElement(reserva));
            }
        }
    }
    /*
        NodeList habitacionesNodes = listaReservas.getElementsByTagName(RESERVA);
        NodeList habitaciones2Nodes = listaReservas.getElementsByTagName(RESERVA);
        for (int i = 0; i < habitacionesNodes.getLength(); i++) {
            for (int j = 0; i<habitaciones2Nodes.getLength();i++){
                Element habitacionElement = (Element) habitacionesNodes.item(i);
                String identificadorHabitacion = habitacionElement.getAttribute(PLANTA_HABITACION);
                Element habitacionElement2 = (Element) habitaciones2Nodes.item(j);
                String identificador2Habitacion = habitacionElement2.getAttribute(PUERTA_HABITACION);
                identificadorExistentes.add(identificadorHabitacion+identificador2Habitacion);
            }
        }*/
    /*
    NodeList listaNodos = DOM.getDocumentElement().getChildNodes();
            for (int i = 0; i < listaNodos.getLength(); i++) {
                Node nodo = listaNodos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    if (!reservas.getHuesped().getDni().equals((elementToReserva((Element) DOM.getDocumentElement().getChildNodes().item(i)).getHuesped().getDni())) || !reservas.getHabitacion().getIdentificador().equals((elementToReserva((Element) DOM.getDocumentElement().getChildNodes().item(i)).getHabitacion().getIdentificador())))
                        listaReservas.appendChild(reservaToElement(reservas));
     */

    @Override
    public void realizarCheckout(Reserva reserva, LocalDateTime fecha) {
        if (reserva == null | fecha == null)
            throw new NullPointerException("Ni la reserva ni la fecha pueden ser nulas.");
        Iterator<Reserva> iteradorReservaCheckout = coleccionReservas.iterator();
        while (iteradorReservaCheckout.hasNext()) {
            Reserva reservaCheckout = iteradorReservaCheckout.next();
            if (reservaCheckout != null)
                if (reservaCheckout.equals(reserva))
                    reservaCheckout.setCheckOut(fecha);
        }
    }

    public void comenzar() {
        DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
        if (DOM == null) {
            DOM = UtilidadesXML.crearDomVacio(RUTA_FICHERO, RAIZ);
        }
        leerXML();
        listaReservas = DOM.getDocumentElement();
    }

    public void terminar() {
        escribirXML();
        if (UtilidadesXML.domToXml(DOM, RUTA_FICHERO))
            System.out.println("Archivos guardados correctamente");
        else System.out.println("Error al guardar los archivos");

        }


}
