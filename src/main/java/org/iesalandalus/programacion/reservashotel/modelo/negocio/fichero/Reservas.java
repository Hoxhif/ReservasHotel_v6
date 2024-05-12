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

    private ArrayList<Reserva> coleccionReservas= new ArrayList<Reserva>();
    private Document DOM;
    private Element listaReservas;

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATOR_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final String RUTA_FICHERO="datos/reservas.xml";
    private static final String RAIZ="Reservas";
    private static final String RESERVA="Reserva";
    private static final String DNI_HUESPED="Dni";
    private static final String PLANTA_HABITACION="Planta";
    private static final String PUERTA_HABITACION="Puerta";
    private static final String FECHA_INICIO_RESERVA="FechaInicioReserva";
    private static final String FECHA_FIN_RESERVA="FechaFinReserva";
    private static final String REGIMEN="Regimen";
    private static final String NUMERO_PERSONAS="Personas";
    private static final String CHECKIN="FechaCheckin";
    private static final String CHECKOUT="FechaCheckout";
    private static final String PRECIO="Precio";
    private static Reservas instancia;



    public Reservas (){
        comenzar();
    }

    public Reservas getInstancia(){
        if (instancia == null)
            instancia = new Reservas();
        return instancia;
    }

    @Override
    public ArrayList<Reserva> get(){
        ArrayList<Reserva> copiaReservas= new ArrayList<Reserva>();

        Iterator<Reserva> iteradorReservas= coleccionReservas.iterator();

        while(iteradorReservas.hasNext()){
            Reserva reserva= new Reserva(iteradorReservas.next());
            copiaReservas.add(reserva);
        }

        Comparator<Reserva> comparador= new Comparator<Reserva>() {
            @Override
            public int compare(Reserva o1, Reserva o2) {
                if (!o1.getFechaInicioReserva().equals(o2.getFechaInicioReserva()))
                    return o1.getFechaInicioReserva().compareTo(o2.getFechaInicioReserva());
                else return o1.getHabitacion().getIdentificador().compareTo(o2.getHabitacion().getIdentificador());
            }

        };
        Collections.sort(copiaReservas,comparador);

        return copiaReservas;
    }

    @Override
    public int getTamano() {
        return listaReservas.getChildNodes().getLength();
    }

    @Override
    public void insertar (Reserva reserva) throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        if (get().contains(reserva))
                throw new OperationNotSupportedException("ERROR: Ya existe una reserva igual.");
        coleccionReservas.add(reserva);
        escribirXML(reserva);
    }


    @Override
    public Reserva buscar (Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede buscar una reserva nula.");
        if (get().contains(reserva))
            return reserva;
        else return null;
    }

    @Override
    public void borrar (Reserva reserva)throws OperationNotSupportedException{
        if (reserva == null)
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        if (!get().contains(reserva))
            throw new OperationNotSupportedException("ERROR: No existe ninguna reserva como la indicada.");
        coleccionReservas.remove(reserva);

        NodeList reservaNodes = listaReservas.getElementsByTagName(RESERVA);
        for (int i = 0; i < reservaNodes.getLength(); i++) {
            Element reservaElement = (Element) reservaNodes.item(i);
            String dniHuesped = reservaElement.getElementsByTagName(DNI_HUESPED).item(0).getTextContent();
            if (dniHuesped.equals(reservaElement.getAttribute(DNI_HUESPED))) {
                Node parent = reservaElement.getParentNode();
                parent.removeChild(reservaElement);
                break;
            }
        }
    }


    @Override
    public ArrayList<Reserva> getReservas (Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huésped nulo.");

        ArrayList<Reserva> copiaReservaHuesped= new ArrayList<>();
        Iterator<Reserva> iteratorReserva= get().iterator();
        while (iteratorReserva.hasNext()){
            Reserva reserva= iteratorReserva.next();
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
    public ArrayList<Reserva> getReservas (TipoHabitacion tipoHabitacion) {
        if (tipoHabitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        ArrayList<Reserva> copiaReservaTipoHabitacion= new ArrayList<>();
        Iterator<Reserva> iteratorReserva= get().iterator();
        while (iteratorReserva.hasNext()){
            Reserva reserva= iteratorReserva.next();
            switch (tipoHabitacion){
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
    public ArrayList<Reserva> getReservas(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");

        ArrayList<Reserva> reservasHabitacionFuturas = new ArrayList<>();
        Iterator<Reserva> iteradorReservasFuturas= get().iterator();

        while (iteradorReservasFuturas.hasNext()){
            Reserva reservaFutura= iteradorReservasFuturas.next();
            if (reservaFutura.getHabitacion().equals(habitacion))
                reservasHabitacionFuturas.add(reservaFutura);
        }
        return reservasHabitacionFuturas;
    }

    @Override
    public ArrayList<Reserva> getReservasFuturas (Habitacion habitacion) {
        if (habitacion == null)
            throw new NullPointerException("ERROR: No se pueden buscar reservas de una habitación nula.");
        LocalDate fechaActual = LocalDate.now();
        ArrayList<Reserva> reservasHabitacionFuturas = new ArrayList<>();
        Iterator<Reserva> iteradorReservasFuturas= get().iterator();

        while (iteradorReservasFuturas.hasNext()){
            Reserva reservaFutura= iteradorReservasFuturas.next();
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
        while (iteradorReservaCheckin.hasNext()){
            Reserva reservaCheckin = iteradorReservaCheckin.next();
            if (reservaCheckin!=null)
                if (reservaCheckin.equals(reserva))
                    reservaCheckin.setCheckIn(fecha);
        }
    }

    public Element huespedToElement(Reserva reserva){
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
        numPersonasDOM.setTextContent(reserva.getNumeroPersonas()+"");
        reservadDOM.appendChild(numPersonasDOM);

        Element checkInDOM = DOM.createElement(CHECKIN);
        if (reserva.getCheckIn()!=null)checkInDOM.setTextContent(reserva.getCheckIn().format(FORMATOR_FECHA_HORA));
        reservadDOM.appendChild(checkInDOM);

        Element checkOutDOM = DOM.createElement(CHECKOUT);
        if (reserva.getCheckOut()!=null) checkOutDOM.setTextContent(reserva.getCheckOut().format(FORMATOR_FECHA_HORA));
        reservadDOM.appendChild(checkOutDOM);

        Element precioDOM = DOM.createElement(PRECIO);
        precioDOM.setTextContent(reserva.getPrecio()+"");
        reservadDOM.appendChild(precioDOM);

        return reservadDOM;
    }

    public Reserva elementToReserva(Element elementoReserva){
        Huesped huesped=null;
        Habitacion habitacion=null;
        Regimen regimen= null;
        LocalDate fechaIn;
        LocalDate fechaFin;
        int numPersonas;

        for (Reserva r: get()){
            if (r.getHuesped().getDni().equals(elementoReserva.getAttribute(DNI_HUESPED)))
                huesped = r.getHuesped();
        }
        for (Reserva r: get()){
            if (r.getHabitacion().getIdentificador().equals(elementoReserva.getAttribute(PLANTA_HABITACION)+elementoReserva.getAttribute(PUERTA_HABITACION)))
                habitacion=r.getHabitacion();
        }
        for (Regimen r: Regimen.values()){
            if (r.toString().equals(elementoReserva.getAttribute(REGIMEN)))
                regimen= r;
        }
        fechaIn = LocalDate.parse(elementoReserva.getAttribute(FECHA_INICIO_RESERVA));
        fechaFin = LocalDate.parse(elementoReserva.getAttribute(FECHA_FIN_RESERVA));
        numPersonas = Integer.parseInt(elementoReserva.getAttribute(NUMERO_PERSONAS));

        return new Reserva(huesped, habitacion, regimen, fechaIn, fechaFin, numPersonas);
    }

    public void leerXML(){
        for (int i = 0; i < DOM.getElementsByTagName(RESERVA).getLength(); i++){
            Element reserva = (Element) DOM.getElementsByTagName(RESERVA).item(i);
            coleccionReservas.add(elementToReserva(reserva));
        }
    }

    public void escribirXML(Reserva reserva){
        listaReservas.appendChild(huespedToElement(reserva));
    }

    @Override
    public void realizarCheckout(Reserva reserva, LocalDateTime fecha){
        if (reserva == null | fecha == null)
            throw new NullPointerException("Ni la reserva ni la fecha pueden ser nulas.");
        Iterator<Reserva> iteradorReservaCheckout= coleccionReservas.iterator();
        while (iteradorReservaCheckout.hasNext()){
            Reserva reservaCheckout = iteradorReservaCheckout.next();
            if (reservaCheckout!=null)
                if (reservaCheckout.equals(reserva))
                    reservaCheckout.setCheckOut(fecha);
        }
            }

            public void comenzar(){
                DOM = UtilidadesXML.xmlToDom(RUTA_FICHERO);
                if (DOM == null){
                    DOM= UtilidadesXML.crearDomVacio(RAIZ);
                }
                leerXML();
                listaReservas = DOM.getDocumentElement();
            }
            public void terminar(){
                UtilidadesXML.domToXml(DOM, RUTA_FICHERO);
            }
        }
