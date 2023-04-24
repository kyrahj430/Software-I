package Controller;
import Model.Inventory;
import Model.Part;
import Model.Product;
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

/** This is the class for the MainController. This class controls everything in the MainScreen. */
public class MainController implements Initializable {
    public TableView<Product> ProductTableView;
    public TableColumn<Product, Integer> TableProdID;
    public TableColumn<Product, String> TableProdName;
    public TableColumn<Product, Integer> TableProdInv;
    public TableColumn<Product, Double> TableProdPrice;
    public TableView<Part> PartTableView;
    public TableColumn<Part, Integer> TablePartID;
    public TableColumn<Part, String> TablePartName;
    public TableColumn<Part, Integer> TablePartInv;
    public TableColumn<Part, Double> TablePartPrice;
    public TextField PartSearch;
    public TextField ProductSearch;

    /** This method updates the part table when called. */
    public void updatePartTable() {
        PartTableView.setItems(Inventory.getAllParts());
    }

    /** This method updates the product table when called. */
    public void updateProductTable() {
        ProductTableView.setItems(Inventory.getAllProducts());
    }

    /** This method controls the result of pressing the add part button. */
    public void OnAddPartClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddPart.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the result of pressing the modify part button. */
    public void OnModifyPartClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPart.fxml"));
        loader.load();
        ModifyPartController ModPartController = loader.getController();
        ModPartController.selectPartToModify(PartTableView.getSelectionModel().getSelectedItem());
        if (PartTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Parent root = loader.getRoot();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the result of pressing the delete part button. */
    public void OnDeletePartClicked(ActionEvent actionEvent) {
        Part selectedPart;
        selectedPart = PartTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection error");
            alert.setContentText("You must select a part to delete.");

            alert.showAndWait();
            return;
        } else {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm deletion");
        alert.setContentText("Are you sure you want to delete this part?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
            updatePartTable();

            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Part deleted");
            alert2.setHeaderText(null);
            alert2.setContentText("The selected part has been deleted.");

            alert2.showAndWait();
        }

        }
    }

    /** This method controls the result of pressing the add product button. */
    public void OnAddProdClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/View/AddProduct.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1088, 608);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the result of pressing the modify product button. */
    public void OnModifyProdClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyProduct.fxml"));
        loader.load();
        ModifyProductController ModProdController = loader.getController();
        ModProdController.selectProdToModify(ProductTableView.getSelectionModel().getSelectedItem());
        if (ProductTableView.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Parent root = loader.getRoot();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1088, 608);
        stage.setTitle("Modify Product");
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the result of pressing the delete product button. */
    public void OnDeleteProdClicked(ActionEvent actionEvent) {
        Product selectedProd;
        selectedProd = ProductTableView.getSelectionModel().getSelectedItem();
        if(selectedProd == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Selection error");
            alert.setContentText("You must select a part to delete.");

            alert.showAndWait();
            return;
        } else {
        if (!selectedProd.getAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Associated parts found");
            alert.setContentText("A product with associated parts cannot be deleted.");

            alert.showAndWait();
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirm deletion");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProd);
                updateProductTable();

                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Product deleted");
                alert2.setHeaderText(null);
                alert2.setContentText("The selected product has been deleted.");

                alert2.showAndWait();

            } else {
                return;
            }
        }
        }
    }

    /** This method initializes the tables and connects the TableViews and TableColumns to the actual allParts and allProducts tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableProdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableProdName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableProdInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TableProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductTableView.setItems(Inventory.getAllProducts());

        TablePartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        TablePartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TablePartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TablePartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartTableView.setItems(Inventory.getAllParts());
    }

    /** This method controls the Part search functionality */
    public void onPartSearch(ActionEvent actionEvent) {

        String partName = PartSearch.getText();
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
            PartTableView.setItems(Inventory.getAllParts());

        } else {
            PartTableView.setItems(partSearchResults);
            PartSearch.setText("");
        }
    }

    /** This method controls the Product search functionality */
    public void onProductSearch(ActionEvent actionEvent) {
        String productName = ProductSearch.getText();
        ObservableList<Product> productSearchResults = Inventory.lookupProduct(productName);

        if (productSearchResults.size() == 0) {
            try {
                int productID = Integer.parseInt(productName);
                Product matchingProduct = Inventory.lookupProduct(productID);
                if (matchingProduct != null) {
                    productSearchResults.add(matchingProduct);
                }
            } catch (NumberFormatException e) {

            }
        }
        if (productSearchResults.isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("No products found");
            alert2.setHeaderText(null);
            alert2.setContentText("No products were found matching your search.");

            alert2.showAndWait();
            ProductTableView.setItems(Inventory.getAllProducts());
        } else {
            ProductTableView.setItems(productSearchResults);
            ProductSearch.setText("");


        }
    }

    /** This method controls the exit functionality */
    public void onExitButton(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirm exit");
        alert.setContentText("Are you sure you want to exit the program?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            return;
        }
    }
}

