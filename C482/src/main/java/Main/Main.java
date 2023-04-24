package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.*;
import java.io.IOException;


/** This is the main class that executes the program.
 * <p> <b>FUTURE ENHANCEMENT:</b> For a future enhancement to this program, I would try to make it so parts that are deleted from the Parts table are also automatically disassociated from any products. </p> */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    /*
    The Javadoc folder for this project is titled "JavaDoc" and is located in: demo\src\main
    */

    /** This is the main method that launches the application. */
    public static void main(String[] args) {
        launch();
    }
}


