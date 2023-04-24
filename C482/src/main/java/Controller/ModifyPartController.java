package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/** This is the ModifyPartController class. This class controls everything in the ModifyPart screen. */

public class ModifyPartController {
    public Button modifyPartCancel;
    public RadioButton modifyRadioInHouse;
    public RadioButton modifyRadioOutSource;
    public Label modifyPartMachId;
    public TextField modifyPartIDField;
    public TextField modifyPartNameField;
    public TextField modifyPartInvField;
    public TextField modifyPartPriceField;
    public TextField modifyPartMaxField;
    public TextField modifyPartMachField;
    public TextField modifyPartMinField;
    public boolean isOutSourced;
    private int index;

    /** This method controls what happens when the OutSourced radio button is selected. */
    public void onModOutSourced(ActionEvent actionEvent) {

        modifyPartMachId.setText("Company Name");
        isOutSourced = true;
    }

    /** This method controls what happens when the InHouse radio button is selected. */
    public void onModInHouse(ActionEvent actionEvent) {

        modifyPartMachId.setText("Machine ID");
        isOutSourced = false;
    }

    /** This method controls what happens when the cancel button is pressed. */
    public void onModifyPartCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirecting");
        alert.setHeaderText(null);
        alert.setContentText("You are being redirected to the main screen");

        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /** This method grabs the information from a selected part, and transfers it to the modify part text fields. */
    public void selectPartToModify(Part selectedPart) {
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You must select a part to modify.");

            alert.showAndWait();
            return;
   } else
        index = Inventory.getAllParts().indexOf(selectedPart);
        modifyPartIDField.setText(String.valueOf(selectedPart.getId()));
        modifyPartNameField.setText(String.valueOf(selectedPart.getName()));
        modifyPartInvField.setText(String.valueOf(selectedPart.getStock()));
        modifyPartPriceField.setText(String.valueOf(selectedPart.getPrice()));
        if (selectedPart instanceof OutSourced) {
            isOutSourced = true;
            modifyRadioOutSource.setSelected(true);
            modifyPartMachId.setText("Company Name");
            modifyPartMachField.setText(((OutSourced) selectedPart).getCompanyName());
        } else if (selectedPart instanceof InHouse) {
            isOutSourced = false;
            modifyRadioInHouse.setSelected(true);
            modifyPartMachId.setText("Machine ID");
            modifyPartMachField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        modifyPartMaxField.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMinField.setText(String.valueOf(selectedPart.getMin()));

    }

    /** <b>RUNTIME ERROR, this method controls what happens when the save button is pressed. </b>
     * <p>When saving the part modification, I originally called Inventory.updatePart(id, newPart). When doing this, I received a runtime error telling me that the ID of my test data was not a valid index.
     * I realized that since I chose arbitrary ID numbers for my test data, that the ID number did not equal the index needed to call updatePart. </p>
     * <p>To fix this, I added a new private integer called index, and set it to the selectedPart's index when selectPartToModify is called.
     * I changed the call to Inventory.updatePart(index, newPart), and received the desired result.</p> */
    public void onModPartSave(ActionEvent actionEvent) throws IOException {
        String idS = modifyPartIDField.getText();
        String name = modifyPartNameField.getText();
        String invS = modifyPartInvField.getText();
        String priceS = modifyPartPriceField.getText();
        String maxS = modifyPartMaxField.getText();
        String minS = modifyPartMinField.getText();
        String machIdS = modifyPartMachField.getText();

        if (name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Name cannot be blank.");

            alert.showAndWait();
            return;
        }

        int id = Integer.parseInt(idS);

        int inv = 0;
        try {
            inv = Integer.parseInt(invS);
        } catch (NumberFormatException e) {
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
        } catch (NumberFormatException e) {
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
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Min must be a valid integer.");

            alert.showAndWait();
            return;
        }

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Min cannot be more than max.");

            alert.showAndWait();
            return;
        }

        if (inv > max || inv < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Inv cannot be less than min or more than max.");

            alert.showAndWait();
            return;
        }

        if (!isOutSourced) {
            int machId = 0;
            try {
                machId = Integer.parseInt(machIdS);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Input Error");
                alert.setContentText("Machine ID must be a valid integer.");

                alert.showAndWait();
                return;
            }

            Part newPart = new InHouse(id, name, price, inv, min, max, machId);

            Inventory.updatePart(index, newPart);
        } else {

            Part newPart = new OutSourced(id, name, price, inv, min, max, machIdS);

            Inventory.updatePart(index, newPart);
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirecting");
        alert.setHeaderText(null);
        alert.setContentText("Part updated. You are being redirected to the main screen");

        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }
}

