module Model {
    requires javafx.controls;
    requires javafx.fxml;

    opens Model to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Main to javafx.fxml;
    exports Model;
    exports Main;
    exports Controller;
}