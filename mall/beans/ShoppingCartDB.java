package beans;

import java.util.ArrayList;

/**
 * Class to create ShoppingCart Object
 * @author M Royal and S Jacobs
 */
public class ShoppingCartDB {
    /**
     * First Name of customer attached to Shopping Cart
     */
    private String firstName;
    /**
     * Last Name of customer attached to Shopping Cart
     */
    private String lastName;
    /**
     * Customer ID of customer attached to Shopping Cart
     */
    private int custID;
    /**
     * ArrayList of Items in Shopping Cart
     */
    private ArrayList<Item> items;
    /**
     * Active status of Shopping Cart
     */
    private String active;

    /**
     * No argument constructor for Shopping Cart
     */
    public ShoppingCartDB() {
    }

    /**
     * Constructor for a Shopping Cart assigned to a customer but empty
     * @param custID Customer ID for Shopping Cart
     * @param lastName Last Name of customer in Shopping Cart
     * @param firstName First Name of customer in Shopping Cart
     * @param active Active status of Shopping Cart
     */
    public ShoppingCartDB(int custID, String lastName, String firstName,String active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.custID = custID;
        this.active = active;
    }
    
    /**
     * Full Constructor for a Shopping Cart assigned to a customer and 
     * filled with items
     * @param custID Customer ID for Shopping Cart
     * @param lastName Last Name of customer in Shopping Cart
     * @param firstName First Name of customer in Shopping Cart
     * @param items Array of Items in Shopping Cart
     * @param active Active status of Shopping Cart
     */
    public ShoppingCartDB(int custID, String lastName, String firstName,ArrayList<Item> items,String active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.custID = custID;
        this.items = items;
        this.active = active;
    }

    @Override
    public String toString() {
        return "ShoppingCartDB{" + "firstName=" + firstName + ", lastName=" + lastName + ", custID=" + custID + ", items=" + items + ", active=" + active + '}';
    }

    /**
     *
     * @return Returns Active status of Shopping Cart
     */
    public String getActive() {
        return active;
    }

    /**
     *
     * @param active Sets Active status of Shopping Cart
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     *
     * @return Returns customer First Name of Shopping Cart
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName Sets customer First Name of Shopping Cart
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return Returns customer Last Name of Shopping Cart
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName Sets customer Last Name of Shopping Cart
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return Returns Customer ID of Shopping Cart
     */
    public int getCustID() {
        return custID;
    }

    /**
     *
     * @param custID Sets Customer ID of Shopping Cart
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     *
     * @return Returns Array List of Items in Shopping Cart
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     *
     * @param items Sets Array List of Items in Shopping Cart
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
    
    
}
