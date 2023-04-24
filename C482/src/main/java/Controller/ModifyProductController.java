package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the ModifyProductController class. This class controls everything in the ModifyProduct screen. */
public class ModifyProductController implements Initializable {
    public TextField ModProdIDField;
    public TextField ModProdNameField;
    public TextField ModProdInvField;
    public TextField ModProdPriceField;
    public TextField ModProdMaxField;
    public TextField ModProdMinField;
    public TableView<Part> ModProdPartTableView;
    public TableView<Part> ModAssocPartTableView;
    public TableColumn<Part, Integer> ModAssocPartID;
    public TableColumn<Part, String> ModAssocPartName;
    public TableColumn<Part, Integer> ModAssocPartInv;
    public TableColumn<Part, Double> ModAssocPartPrice;
    public TableColumn<Part, Integer> ModProdPartID;
    public TableColumn<Part, String> ModProdPartName;
    public TableColumn<Part, Integer> ModProdPartInv;
    public TableColumn<Part, Double> ModProdPartPrice;
    public TextField ModProdPartSearch;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int index;

    /** This method controls what happens when the cancel button is pressed. */
    public void onModProdCancel(ActionEvent actionEvent) throws IOException {
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

    /** This method grabs the information from a selected product, and transfers it to the modify product text fields. */
    public void selectProdToModify(Product selectedProd) {
        if(selectedProd == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You must select a product to modify.");

            alert.showAndWait();
            return;
            } else
            index = Inventory.getAllProducts().indexOf(selectedProd);
            ModProdIDField.setText(String.valueOf(selectedProd.getId()));
            ModProdNameField.setText(selectedProd.getName());
            ModProdInvField.setText(String.valueOf(selectedProd.getStock()));
            ModProdPriceField.setText(String.valueOf(selectedProd.getPrice()));
            ModProdMaxField.setText(String.valueOf(selectedProd.getMax()));
            ModProdMinField.setText(String.valueOf(selectedProd.getMin()));
            associatedParts.addAll(selectedProd.getAssociatedParts());
        }

    /** This method initializes the tables and connects the TableViews and TableColumns to the actual allParts and associatedParts tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModProdPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProdPartTableView.setItems(Inventory.getAllParts());

        ModAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModAssocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModAssocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModAssocPartTableView.setItems(associatedParts);
    }

    /** This method controls what happens when the Add Associated Part button is pressed. */
    public void onModAddAssocPart(ActionEvent actionEvent) {
        Part selectedPart;
        selectedPart = ModProdPartTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You must select a part to add.");

            alert.showAndWait();
            return;
        }
        associatedParts.add(selectedPart);
    }

    /** This method controls what happens when the save button is pressed. */
    public void onModProdSave(ActionEvent actionEvent) throws IOException {
        String idS = ModProdIDField.getText();
        String name = ModProdNameField.getText();
        String invS = ModProdInvField.getText();
        String priceS = ModProdPriceField.getText();
        String maxS = ModProdMaxField.getText();
        String minS = ModProdMinField.getText();

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


        Product newProduct = new Product(id, name, price, inv, min, max);
        for (Part part : associatedParts) {
            newProduct.addAssociatedPart(part);
        }

        Inventory.updateProduct(index, newProduct);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Redirecting");
        alert.setHeaderText(null);
        alert.setContentText("New product saved. You are being redirected to the main screen");

        alert.showAndWait();

        Parent root = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 927, 347);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }

    /** This method controls what happens when the Remove Associated Part button is pressed. */
    public void onRemoveModAssocPart(ActionEvent actionEvent) {
        Part selectedPart;
        selectedPart = ModAssocPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You must select a part from the associated part list to remove.");

            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirm removal");
            alert.setContentText("Are you sure you want to remove this part association?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Part association removed");
                alert2.setHeaderText(null);
                alert2.setContentText("The selected part is no longer associated with this product.");

                alert2.showAndWait();

            } else {
                return;
            }
        }
    }

    /** This method handles the search function for the allParts table. */
    public void onModProdPartSearch(ActionEvent actionEvent) {
        String partName = ModProdPartSearch.getText();
        ObservableList<Part> partSearchResults = Inventory.lookupPart(partName);

        if (partSearchResults.size() == 0) {
            try {
                int partID = Integer.parseInt(partName);
                Part matchingPart = Inventory.lookupPart(partID);
                if (matchingPart != null) {
                    partSearchResults.add(matchingPart);
                }
            } catch (NumberFormatException e) {

            }

        }

        if (partSearchResults.isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("No parts found");
            alert2.setHeaderText(null);
            alert2.setContentText("No parts were found matching your search.");

            alert2.showAndWait();
            ModProdPartTableView.setItems(Inventory.getAllParts());

        } else {
            ModProdPartTableView.setItems(partSearchResults);
            ModProdPartSearch.setText("");
        }
    }
}

