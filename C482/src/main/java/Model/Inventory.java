package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Controller.MainController;

/** This is the Inventory class. The Inventory class holds methods used in other controllers to navigate / manipulate the allParts and allProducts tables. */
public class Inventory {

    /** This creates the allParts observable list. */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /** This creates the allProducts observable list. */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** This method adds a part to the allParts table */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /** This method adds a product to the allProducts table */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /** This method is used to search for a specific part using ID. */
    public static Part lookupPart(int partID) {
        for(Part part : allParts) {
            if(part.getId() == partID) {
                return part;
            }
        }
        return null;
    }

    /** This method is used to search for a specific product using ID. */
    public static Product lookupProduct(int productID) {
        for(Product product : allProducts) {
            if(product.getId() == productID) {
                return product;
            }
        }
        return null;
    }

    /** This method is used to search for a specific part using name or partial name. */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();

        for(Part part : allParts) {
            if(part.getName().contains(partName)) {
                namedParts.add(part);
            }

        }
        return namedParts;
    }

    /** This method is used to search for a specific product using name or partial name. */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProds = FXCollections.observableArrayList();

        for(Product product : allProducts) {
            if(product.getName().contains(productName)) {
                namedProds.add(product);
            }

        }
        return namedProds;
    }

    /** This method is used to update a specific part in the allParts table. */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);

    }

    /** This method is used to update a specific product in the allProducts table. */
    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }

    /** This method is used to remove a specific part from the allParts table. */
    public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;
    }

    /** This method is used to remove a specific product from the allProducts table. */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;
    }

    /** This method is used to return everything within the allParts table */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /** This method is used to return everything within the allProducts table */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

}
