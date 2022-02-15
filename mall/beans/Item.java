
package beans;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Item Class used in MallDriver1
 * Used to create items to add to Shopping Cart
 * @author M Royal and S Jacobs
 */
public class Item implements Serializable, Cloneable{
    /**
     * Name of item
     */
    private String itemName;
    /**
     * ID code of item
     */
    private String itemID;
    /**
     * Store ID where item is located
     */
    private String storeID;
    /**
     * Price of item
     */
    private double price;
    /**
     * Quantity of item
     */
    private int number;

    /**
     * No argument constructor of Item object
     */
    public Item() {
    }

    /**
     * Full constructor of Item object
     * @param itemName Name of item
     * @param itemID ID of item
     * @param storeID Store where item is located
     * @param price Price of item
     * @param number Number of items
     */
    public Item(String itemName, String itemID, String storeID, double price, int number) {
        this.itemName = itemName;
        this.itemID = itemID;
        this.storeID = storeID;
        this.price = price;
        this.number = number;
    }
    
    @Override
    public String toString()
    {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return itemID + ": " + itemName  + " in store " +
                storeID + " cost " + nf.format(price) +
                " and there are " + getNumber() + " in stock.";
    }

    // used for the customer to see

    /**
     *
     * @return Returns toString for customer view
     */
    public String toStringC()
    {
       NumberFormat nf = NumberFormat.getCurrencyInstance();
        return itemID + ": " + itemName  + " from store " +
                storeID + " cost " + nf.format(price) +
                " and you bought " + getNumber() + " of them";
    }
    
    //needs getters and setters

    /**
     *
     * @return Returns Item Name
     */

    public String getItemName() {
        return itemName;
    }

    /**
     *
     * @param itemName Sets Item Name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     *
     * @return Returns Item ID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     *
     * @param itemID Sets Item ID
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     *
     * @return Returns Store ID of Item
     */
    public String getStoreID() {
        return storeID;
    }

    /**
     *
     * @param storeID Sets Store ID of Item
     */
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    /**
     *
     * @return Returns Price of item
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price Sets Price of item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return Returns Number of items
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @param number Sets number of items
     */
    public void setNumber(int number) {
        this.number = number;
    }

    

}
