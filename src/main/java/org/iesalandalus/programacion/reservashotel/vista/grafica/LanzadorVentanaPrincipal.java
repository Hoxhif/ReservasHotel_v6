package org.iesalandalus.programacion.reservashotel.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.reservashotel.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.reservashotel.vista.grafica.utilidades.Dialogos;


public class LanzadorVentanaPrincipal extends Application {


    public static void comenzar() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource("vistas/ventanaPrincipal.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistas/ventanaPrincipal.fxml"));
            Parent raiz = fxmlLoader.load();
            Scene scene = new Scene(raiz, 1400, 800); //Me ha gustado esta resoluci�n para mi proyecto en la ventana principal.
            primaryStage.setTitle("DAW - Gesti�n Hotel");
            primaryStage.setScene(scene);
            primaryStage.setOnCloseRequest(e -> confirmarSalida(primaryStage, e));
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e){
        if (Dialogos.mostrarDialogoConfirmacion("DAW - Gesti�n Hotel", "�Est�s seguro de que desea cerrar la aplicaci�n?")){
            escenarioPrincipal.close();
        }else{
            e.consume();
        }
    }


}
