package org.iesalandalus.programacion.reservashotel.modelo.negocio.mongodb.utilidades;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.mongodb.client.model.Filters.eq;

public class MongoDB {

    public static final DateTimeFormatter FORMATO_DIA = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter FORMATO_DIA_HORA = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    private static final int PUERTO= 27017;
    private static final String DB = "reservashotel";

    private static final String SERVIDOR= "dawjose.yjbablx.mongodb.net";
    private static final String USUARIO="reservashotel";
    private static final String CONTRASENA = "reservashotel-2024";

    public static final String HUESPED = "huesped";
    public static final String NOMBRE= "nombre";
    public static final String DNI= "dni";
    public static final String TELEFONO= "telefono";
    public static final String CORREO="correo";
    public static final String FECHA_NACIMIENTO="fecha_nacimiento";
    public static final String HUESPED_DNI= HUESPED + "." + DNI;
    public static final String HABITACION = "habitacion";
    public static final String IDENTIFICADOR="identificador";
    public static final String PLANTA = "planta";
    public static final String PUERTA = "puerta";
    public static final String PRECIO="precio";
    public static final String HABITACION_IDENTIFICADOR = HABITACION + "." + IDENTIFICADOR;
    public static final String TIPO = "tipo";
    public static final String TIPO_SIMPLE="SIMPLE";
    public static final String TIPO_DOBLE="DOBLE";
    public static final String TIPO_TRIPLE="TRIPLE";
    public static final String TIPO_SUITE="SUITE";
    public static final String CAMAS_INDIVIDUALES="camas_individuales";
    public static final String CAMAS_DOBLES="camas_dobles";
    public static final String BANOS="banos";
    public static final String JACUZZI="jacuzzi";
    public static final String REGIMEN="regimen";
    public static final String FECHA_INICIO_RESERVA="fecha_inicio_reserva";
    public static final String FECHA_FIN_RESERVA="fecha_fin_reserva";
    public static final String CHECKIN="checkin";
    public static final String CHECKOUT="checkout";
    public static final String PRECIO_RESERVA="precio_reserva";
    public static final String NUMERO_PERSONAS="numero_personas";

    private static MongoClient conexion;

    private MongoDB(){

    }

    public static MongoDatabase getBD(){
        if (conexion == null){
            establecerConexion();
        }
        return conexion.getDatabase(DB);
    }

    private static void establecerConexion(){
        //conexion = MongoClients.create(new ConnectionString(SERVIDOR));
        String connectionString;
        ServerApi serverApi;
        MongoClientSettings settings;

        if (!SERVIDOR.equals("localhost")){
            connectionString = "mongodb+srv://"+ USUARIO+ ":" + CONTRASENA + "@"+ SERVIDOR +"/?retryWrites=true&w=majority";

            serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();

            settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();
        }else{
            connectionString = "mongodb://" + USUARIO + ":" + CONTRASENA + "@" + SERVIDOR + ":" + PUERTO;

            MongoCredential credenciales = MongoCredential.createScramSha1Credential(USUARIO, DB, CONTRASENA.toCharArray());

            settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).credential(credenciales).build();
        }

        conexion = MongoClients.create(settings);

        try{
            if (!SERVIDOR.equals("localhost")){
                MongoDatabase database = conexion.getDatabase(DB);
                database.runCommand(new Document("pìng",1));
            }
        }catch (MongoException e){
            e.printStackTrace();
        }
        System.out.println("Conexión a MongoDB realizada correctamente.");
    }

    public static void cerrarConexion(){
        //conexion.close();
        if (conexion != null){
            conexion.close();
            conexion = null;
            System.out.println("Conexión a MongoDB cerrada correctamente.");
        }
    }

    public static Document getDocumento (Huesped huesped){
        if (huesped == null)
            throw new NullPointerException("ERROR: El huesped es nulo.");
        return new Document().append(NOMBRE,huesped.getNombre()).append(DNI,huesped.getDni()).append(TELEFONO,huesped.getTelefono()).append(CORREO,huesped.getCorreo()).append(FECHA_NACIMIENTO,huesped.getFechaNacimiento());
    }

    public static Huesped getHuesped(Document documentoHuesped){
        if (documentoHuesped == null)
            throw new NullPointerException("ERROR: No existe el huesped en la Base de Datos.");
        String fechaHuesped= documentoHuesped.getString(FECHA_NACIMIENTO);
        LocalDate fecha = LocalDate.parse(fechaHuesped,FORMATO_DIA);
        return new Huesped(documentoHuesped.getString(NOMBRE),documentoHuesped.getString(DNI),documentoHuesped.getString(CORREO), documentoHuesped.getString(TELEFONO),fecha);
    }

    public static Document getDocumento(Habitacion habitacion){
        if (habitacion == null)
            throw new NullPointerException("ERROR: La habitación es nula.");

        if (habitacion instanceof Simple){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO, TIPO_SIMPLE).append(NUMERO_PERSONAS,habitacion.getNumeroMaximoPersonas());
        }
        if (habitacion instanceof Doble){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO, TIPO_DOBLE).append(CAMAS_INDIVIDUALES,((Doble) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES,((Doble) habitacion).getNumCamasDobles()).append(NUMERO_PERSONAS,habitacion.getNumeroMaximoPersonas());
        }
        if (habitacion instanceof Triple){
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO, TIPO_TRIPLE).append(BANOS,((Triple) habitacion).getNumBanos()).append(CAMAS_INDIVIDUALES,((Triple) habitacion).getNumCamasIndividuales()).append(CAMAS_DOBLES,((Triple) habitacion).getNumCamasDobles()).append(NUMERO_PERSONAS,habitacion.getNumeroMaximoPersonas());
        }
        else {
            return new Document().append(PLANTA,habitacion.getPlanta()).append(PUERTA,habitacion.getPuerta()).append(IDENTIFICADOR,habitacion.getIdentificador()).append(PRECIO,habitacion.getPrecio()).append(TIPO, TIPO_SUITE).append(BANOS,((Suite) habitacion).getNumBanos()).append(JACUZZI,((Suite) habitacion).isTieneJacuzzi()).append(NUMERO_PERSONAS,habitacion.getNumeroMaximoPersonas());
        }

    }

    public static Habitacion getHabitacion(Document documentoHabitacion){
        if (documentoHabitacion == null)
            throw new NullPointerException("ERROR: No existe esa habitación en la Base de Datos.");

        switch(documentoHabitacion.getString(TIPO)){
            case TIPO_SIMPLE: return new Simple(Integer.parseInt(documentoHabitacion.getString(PLANTA)),Integer.parseInt(documentoHabitacion.getString(PUERTA)),Double.parseDouble(documentoHabitacion.getString(PRECIO)));
            case TIPO_DOBLE: return new Doble(Integer.parseInt(documentoHabitacion.getString(PLANTA)),Integer.parseInt(documentoHabitacion.getString(PUERTA)),Double.parseDouble(documentoHabitacion.getString(PRECIO)),Integer.parseInt(documentoHabitacion.getString(CAMAS_INDIVIDUALES)),Integer.parseInt(documentoHabitacion.getString(CAMAS_DOBLES)));
            case TIPO_TRIPLE: return new Triple(Integer.parseInt(documentoHabitacion.getString(PLANTA)),Integer.parseInt(documentoHabitacion.getString(PUERTA)),Double.parseDouble(documentoHabitacion.getString(PRECIO)),Integer.parseInt(documentoHabitacion.getString(BANOS)),Integer.parseInt(documentoHabitacion.getString(CAMAS_INDIVIDUALES)),Integer.parseInt(documentoHabitacion.getString(CAMAS_DOBLES)));
            case TIPO_SUITE: return new Suite(Integer.parseInt(documentoHabitacion.getString(PLANTA)),Integer.parseInt(documentoHabitacion.getString(PUERTA)),Double.parseDouble(documentoHabitacion.getString(PRECIO)),Integer.parseInt(documentoHabitacion.getString(BANOS)),Boolean.parseBoolean(documentoHabitacion.getString(JACUZZI)));
        }
        return null;
    }

    public static Reserva getReserva(Document documentoReserva){
        if (documentoReserva == null)
            throw new NullPointerException("ERROR: No existe esa reserva en la Base de Datos.");

        MongoCollection<Document> coleccionHuespedes = getBD().getCollection("reservas");
        Document documentoHuesped = coleccionHuespedes.find().filter(eq(HUESPED_DNI,documentoReserva.getString(HUESPED_DNI))).first();
        MongoCollection<Document> coleccionHabitaciones = getBD().getCollection("reservas");
        Document documentoHabitacion = coleccionHabitaciones.find().filter(eq(HABITACION_IDENTIFICADOR,documentoReserva.getString(HABITACION_IDENTIFICADOR))).first();

        String fechaInicioCadena = documentoReserva.getString(FECHA_INICIO_RESERVA);
        LocalDate fechaInicio = LocalDate.parse(fechaInicioCadena, FORMATO_DIA);
        String fechaFinCadena = documentoReserva.getString(FECHA_FIN_RESERVA);
        LocalDate fechaFin = LocalDate.parse(fechaFinCadena, FORMATO_DIA);
        Regimen regimen= null;

        Regimen[] opciones = Regimen.values();
        for (Regimen opcion: opciones){
            if (opcion.toString().equals(documentoReserva.getString(REGIMEN))){
                regimen = opcion;
            }
        }

        Reserva reserva =  new Reserva(getHuesped(documentoHuesped),getHabitacion(documentoHabitacion), regimen, fechaInicio, fechaFin, Integer.parseInt(documentoReserva.getString(NUMERO_PERSONAS)));

        if (documentoReserva.getString(CHECKIN)!=null){
            String fechaCheckIn = documentoReserva.getString(CHECKIN);
            LocalDateTime fechaCheckin = LocalDateTime.parse(fechaCheckIn,FORMATO_DIA_HORA);
            reserva.setCheckIn(fechaCheckin);
        }
        if (documentoReserva.getString(CHECKOUT)!= null){
            String fechaCheckOut = documentoReserva.getString(CHECKOUT);
            LocalDateTime fechaCheckout = LocalDateTime.parse(fechaCheckOut, FORMATO_DIA_HORA);
            reserva.setCheckOut(fechaCheckout);
        }
        return reserva;
    }

    public static Document getDocumento(Reserva reserva){
        if (reserva == null)
            throw new NullPointerException("ERROR: La reserva es nula.");
        return new Document().append(HUESPED,reserva.getHuesped()).append(HABITACION,reserva.getHabitacion()).append(REGIMEN,reserva.getRegimen()).append(FECHA_INICIO_RESERVA, reserva.getFechaInicioReserva()).append(FECHA_FIN_RESERVA, reserva.getFechaFinReserva()).append(NUMERO_PERSONAS, reserva.getNumeroPersonas()).append(CHECKIN, reserva.getCheckIn()).append(CHECKOUT, reserva.getCheckOut()).append(PRECIO_RESERVA, reserva.getPrecio());
    }



}
