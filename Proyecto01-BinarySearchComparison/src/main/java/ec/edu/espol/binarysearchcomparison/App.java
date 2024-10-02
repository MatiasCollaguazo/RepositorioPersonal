package ec.edu.espol.binarysearchcomparison;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.image.Image;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static int PREDET_WIDTH =653;
    public static int PREDET_HEIGHT=435;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), PREDET_WIDTH, PREDET_HEIGHT);
        stage.setTitle("Comparación de algoritmos de búsqueda");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/ec/edu/espol/binarysearchcomparison/icon.png")));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}