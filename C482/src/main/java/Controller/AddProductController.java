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

/** This is the AddProductController class. This class controls everything on the AddProduct screen. */
public class AddProductController implements Initializable {
    public TextField addProdNameField;
    public TextField addProdInvField;
    public TextField addProdPriceField;
    public TextField addProdMaxField;
    public TextField addProdMinField;
    public TextField addProdIdField;
    public TableView<Part> AddProdPartTableView;
    public TableColumn<Part, Integer> AddProdPartTableID;
    public TableColumn<Part, String> AddProdPartTableName;
    public TableColumn<Part, Integer> AddProdPartTableInv;
    public TableColumn<Part, Double> AddProdPartTablePrice;
    public TableView<Part> AddAssocPartTableView;
    public TableColumn<Part, Integer> AddAssocPartID;
    public TableColumn<Part, String> AddAssocPartName;
    public TableColumn<Part, Integer> AddAssocPartInv;
    public TableColumn<Part, Double> AddAssocPartPrice;
    public TextField AddProdPartSearch;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static int productID = 0;

    /** This method generates a unique productID. */
    public int getNewProductID() {
        productID = productID + 2;
        return productID;
    }

    /** This method controls the functionality when the cancel button is pressed. */
    public void onAddProdCancel(ActionEvent actionEvent) throws IOException {
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

    /** This method controls the functionality when the save button is pressed. */
    public void onAddProdSave(ActionEvent actionEvent) throws IOException {
        String name = addProdNameField.getText();
        String invS = addProdInvField.getText();
        String priceS = addProdPriceField.getText();
        String maxS = addProdMaxField.getText();
        String minS = addProdMinField.getText();

        if (name.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Input Error");
            alert.setContentText("Name cannot be blank.");

            alert.showAndWait();
            return;
        }

        int id = getNewProductID();
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
        Inventory.addProduct(newProduct);


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

    /** This method controls the functionality when the Add Associated Part button is pressed. */
    public void onAddAssocPart(ActionEvent actionEvent) {
        Part selectedPart;
        selectedPart = AddProdPartTableView.getSelectionModel().getSelectedItem();
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

    /** This method initializes the tables and connects the TableViews and TableColumns to the actual allParts and associatedParts tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddProdPartTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdPartTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdPartTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdPartTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProdPartTableView.setItems(Inventory.getAllParts());

        AddAssocPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddAssocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddAssocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddAssocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddAssocPartTableView.setItems(associatedParts);
    }

    /** This method controls the functionality when the Remove Associated Part button is pressed. */
    public void onRemoveAddAssocPart(ActionEvent actionEvent) {
        Part selectedPart;
        selectedPart = AddAssocPartTableView.getSelectionModel().getSelectedItem();
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

    /** This method controls the search functionality on the allParts table. */
    public void onAddProdPartSearch(ActionEvent actionEvent) {
        String partName = AddProdPartSearch.getText();
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
            AddProdPartTableView.setItems(Inventory.getAllParts());

        } else {
            AddProdPartTableView.setItems(partSearchResults);
            AddProdPartSearch.setText("");
        }
    }
}
