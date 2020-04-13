package it.polito.tdp.anagrammi;

import javafx.application.Application;

import static javafx.application.Application.launch;

import it.polito.tdp.anagrammi.FXMLController;
import it.polito.tdp.anagrammi.model.Model;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class EntryPoint extends Application {

	@Override
    public void start(Stage stage) throws Exception {
        
    	FXMLController controller;
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        controller = loader.getController();
    	
        Model modello = new Model();
        controller.setModel(modello);
        
        stage.setTitle("Anagrammando in allegria!");
        stage.setScene(scene);
        stage.show();
	}

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
