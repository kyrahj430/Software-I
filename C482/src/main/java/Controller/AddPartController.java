package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** This is the class for the AddPartController. This class controls everything in the AddPart screen.  */
public class AddPartController {
    public Label addPartName;
    public RadioButton addRadioInHouse;
    public RadioButton addRadioOutSource;
    public Label addPartMachId;
    public TextField addPartIDField;
    public TextField addPartNameField;
    public TextField addPartInvField;
    public TextField addPartPriceField;
    public TextField addPartMaxField;
    public TextField addPartMachField;
    public Label addPartMin;
    public TextField addPartMinField;

    public boolean isOutSourced;
    private static int partID = 1;

    /** This method generates a unique partID. */
    public int getNewPartID() {
        partID = partID + 2;
        return partID;
    }

    /** This method adds functionality to when the InHouse radio button is pressed. */
    public void onAddInHouse(ActionEvent actionEvent) {

        addPartMachId.setText("Machine ID");
        isOutSourced = false;
    }

    /** This method adds functionality to when the OutSourced radio button is pressed. */
    public void onAddOutSourced(ActionEvent actionEvent) {

        addPartMachId.setText("Company Name");
        isOutSourced = true;
    }

    /** This method adds functionality to when the cancel button is pressed. */
    public void onAddPartCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirecting");
        alert.setHeaderText(null);
        alert.setContentText("You are being redirected to the main screen");

        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method adds functionality to when the save button is pressed. This validates the data entered and then saves it to the allParts table. */
    public void onAddPartSave(ActionEvent actionEvent) throws IOException {
        String name = addPartNameField.getText();
        String invS = addPartInvField.getText();
        String priceS = addPartPriceField.getText();
        String maxS = addPartMaxField.getText();
        String minS = addPartMinField.getText();
        String machIdS = addPartMachField.getText();

        if(name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Name cannot be blank.");

            alert.showAndWait();
            return;
        }

        int id = getNewPartID();
        int inv = 0;
        try {
            inv = Integer.parseInt(invS);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Inv must be a valid integer.");

            alert.showAndWait();
            return;
        }
        double price = 0.00;
        try {
            price = Double.parseDouble(priceS);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Price must be a valid double.");

            alert.showAndWait();
            return;
        }
        int max = 0;
        try {
            max = Integer.parseInt(maxS);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Max must be a valid integer.");

            alert.showAndWait();
            return;
        }
        int min = 0;
        try {
            min = Integer.parseInt(minS);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Min must be a valid integer.");

            alert.showAndWait();
            return;
        }

        if(min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Min cannot be more than max.");

            alert.showAndWait();
            return;
        }

        if(inv > max || inv < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Inv cannot be less than min or more than max.");

            alert.showAndWait();
            return;
        }

        if(!isOutSourced) {
            int machId = 0;
            try {
                machId = Integer.parseInt(machIdS);
            } catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Input Error");
                alert.setContentText("Machine ID must be a valid integer.");

                alert.showAndWait();
                return;
            }

            Part newPart = new InHouse(id, name, price, inv, min, max, machId);

            Inventory.addPart(newPart);
        } else {

            Part newPart = new OutSourced(id, name, price, inv, min, max, machIdS);

            Inventory.addPart(newPart);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirecting");
        alert.setHeaderText(null);
        alert.setContentText("New part saved. You are being redirected to the main screen");

        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();



    }
}
