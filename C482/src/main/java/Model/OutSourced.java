package Model;

/** This is the OutSourced Part class. This class is a child of the Part class. */
public class OutSourced extends Part {
    private String companyName;

    /** This is the constructor for the OutSourced Part class. */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** This method is used to set the companyName. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** This method is used to get the companyName. */
    public String getCompanyName() {
        return companyName;
    }
}
