package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This is the Product class. The Product class defines what a Product is, and methods to retrieve / manipulate information about a Product object. */
public class Product {

    /** This table contains Parts associated with a Product. */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** This is the id attribute. This is the unique identifier for the Product. */
    private int id;

    /** This is the name attribute. This is what the Product is called. */
    private String name;

    /** This is the price attribute. This is how much the Product costs. */
    private double price;

    /** This is the stock or inventory attribute. This is how many Products are in stock. */
    private int stock;

    /** This is the min attribute. This is the minimum stock of a Product */
    private int min;

    /** This is the max attribute. This is the maximum stock of a Product */
    private int max;


    /** This is the constructor for the Product class. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** This sets the ID for a Product object. */
    public void setId(int id) {
        this.id = id;
    }

    /** This sets the name for a Product object. */
    public void setName(String name) {
        this.name = name;
    }

    /** This sets the price for a Product object. */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This sets the stock or inventory for a Product object. */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** This sets the minimum stock for a Product object. */
    public void setMin(int min) {
        this.min = min;
    }

    /** This sets the maximum stock for a Product object. */
    public void setMax(int max) {
        this.max = max;
    }

    /** This gets the ID for a Product object. */
    public int getId() {
        return id;
    }

    /** This gets the name for a Product object. */
    public String getName() {
        return name;
    }

    /** This gets the price for a Product object. */
    public double getPrice() {
        return price;
    }

    /** This gets the stock or inventory for a Product object. */
    public int getStock() {
        return stock;
    }

    /** This gets the minimum stock for a Product object. */
    public int getMin() {
        return min;
    }

    /** This gets the maximum stock for a Product object. */
    public int getMax() {
        return max;
    }

    /** This method adds a Part to the associatedParts of a Product object */
    public void addAssociatedPart(Part part) {
        associatedParts.addAll(part);
    }

    /** This method removes a Part to the associatedParts of a Product object */
    public void deleteAssociatedPart(Part associatedPart) {
        associatedParts.remove(associatedPart);

    }

    /** This method gets all items on the associatedParts table of a Product object */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }


}
