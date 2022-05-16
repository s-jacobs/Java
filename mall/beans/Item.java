package beans;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Used to create items to add to Shopping Cart
 * <br> Created 2018
 * <br> Revised by SJ 5/15/2022 - Changed formating and updated Java Doc
 * @author M Royal and S Jacobs
 */
public class Item implements Serializable, Cloneable{
    /**  Item name  */
    private String itemName;
    /**  Item identification code  */
    private String itemID;
    /**  Store identification code where item was purchased  */
    private String storeID;
    /**  Item price  */
    private double price;
    /**  Item quantity  */
    private int number;

    /**  No argument constructor of Item object  */
    public Item() { }

    /**
     * Full constructor of Item object
     * @param itemName The name of item
     * @param itemID   The ID code of item
     * @param storeID  The ID code for store where item is located
     * @param price    The price of item
     * @param number   The number of items purchased
     */
    public Item(String itemName, String itemID, String storeID, double price, int number) {
        this.itemName = itemName;
        this.itemID = itemID;
        this.storeID = storeID;
        this.price = price;
        this.number = number;
    }
    
    // Use for store view
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
     * To string method used to print the item ID, item name, store ID, price, 
     * and quantity 
     * @return String  String representation of the sale to show customer
     */
    public String toStringC()
    {
       NumberFormat nf = NumberFormat.getCurrencyInstance();
        return itemID + ": " + itemName  + " from store " +
                storeID + " cost " + nf.format(price) +
                " and you bought " + getNumber() + " of them";
    }
    
    //getters and setters
    /**  @return Returns Item Name  */
    public String getItemName() {
        return itemName;
    }

    /**  @param itemName Sets Item Name  */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**  @return Returns Item ID  */
    public String getItemID() {
        return itemID;
    }

    /**  @param itemID Sets Item ID  */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**  @return Returns Store ID of Item  */
    public String getStoreID() {
        return storeID;
    }

    /**  @param storeID Sets Store ID of Item  */
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    /**  @return Returns Price of item  */
    public double getPrice() {
        return price;
    }

    /**  @param price Sets Price of item  */
    public void setPrice(double price) {
        this.price = price;
    }

    /**  @return Returns Number of items  */
    public int getNumber() {
        return number;
    }

    /**  @param number Sets number of items  */
    public void setNumber(int number) {
        this.number = number;
    }
}
