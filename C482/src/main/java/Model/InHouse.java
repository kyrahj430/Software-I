package Model;

/** This is the InHouse Part class. This class is a child of the Part class. */
public class InHouse extends Part {
    private int machineId;

    /** This is the constructor for the InHouse Part class. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** This method is used to set the MachineID. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /** This method is used to get the MachineID. */
    public int getMachineId() {
        return machineId;
    }
}
