module ReservasHotel.v4.main {
    // No se muy bien esto por que es así pero me lo he copiado y adaptado de los ejemplos.
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires java.naming;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires entrada;

    opens org.iesalandalus.programacion.reservashotel to javafx.fxml;
    exports org.iesalandalus.programacion.reservashotel;
    exports org.iesalandalus.programacion.reservashotel.vista.grafica.controladores;
    opens org.iesalandalus.programacion.reservashotel.vista.grafica.controladores to javafx.fxml;
    exports org.iesalandalus.programacion.reservashotel.vista.grafica to javafx.graphics;

    exports org.iesalandalus.programacion.reservashotel.modelo.dominio;
    opens org.iesalandalus.programacion.reservashotel.modelo.dominio to javafx.fxml;
}