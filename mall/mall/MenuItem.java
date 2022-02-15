
/**
 * Mall package containing the MallDriver, MenuItem, and DBUtilities
 */
package mall;

import beans.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class containing methods for all menu items
 * @author M Royal and S Jacobs
 */
public class MenuItem {

    /**
     * Statement used to query database
     */
    public static Statement stmt;

    /**
     * Scanner object used throughout MenuItem
     */
    public static Scanner scan = new Scanner(System.in);

    /**
     * Scanner object used throughout MenuItem
     */
    public static Scanner input = new Scanner(System.in);


    //MR added print method 10/30
    //SJ renamed printStore
    //used for customer view (doesn't print customers)

    /**
     * Method to query database and display all stores
     * @param con Connection to database
     */
    public static void printStore(Connection con) {
        CallableStatement ca;
        CallableStatement storeIDs;
        CallableStatement custCa;
        ArrayList<String> storeArray = new ArrayList<String>();
        ResultSet stores = null;
        String storeNameString = "";

        System.out.println("\nSHOP AT THE Cockatoo's Shopping Mall!");
        System.out.println("Current stores and items:");

        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }

        try {
            //Create ArrayList of storeID's

            storeIDs = con.prepareCall("SELECT storeID FROM Store");
            storeIDs.execute();
            stores = storeIDs.getResultSet();

            while (stores.next()) {
                int i = 0;
                storeArray.add(i++, stores.getString(1));
            }

        } catch (SQLException ex) {
            //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        //loop thru each storeID and pass in as parameter
        for (int j = 0; j < storeArray.size(); j++) {

            String storeID = storeArray.get(j);

            String stored = "{call printItems('" + storeID + "')}";
            try {
                String storeStmt = "SELECT storeName FROM Store WHERE storeID='" + storeID + "'";
                CallableStatement storeName = con.prepareCall(storeStmt);
                storeName.execute();
                ResultSet storeRs = storeName.getResultSet();
                while (storeRs.next()) {
                    storeNameString = storeRs.getString(1);
                }
            } catch (SQLException ex) {
                //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                ca = con.prepareCall(stored);

                ca.execute();
                ResultSet rs = ca.getResultSet();

                System.out.println("\nStore ID: " + storeID + " Store Name: " + storeNameString);
                System.out.println("Items:");
                while (rs.next()) {
                    System.out.println("     " + rs.getString("display"));
                }

            } catch (SQLException e) {
                System.out.println("problem!");
            }
        }//end of for loop
    }
    
        //MR added print method 10/30
    //used for admin view

    /**
     * Method to query database and print out all information 
     * <br> including stores, items, and customers
     * @param con Connection to database
     */
    public static void printInfo(Connection con) {
        CallableStatement ca;
        CallableStatement storeIDs;
        CallableStatement custCa;
        ArrayList<String> storeArray = new ArrayList<String>();
        ResultSet stores = null;
        String storeNameString = "";

        System.out.println("\nSHOP AT THE Cockatoo's Shopping Mall!");
        System.out.println("Current stores and items:");

        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }

        try {
            //Create ArrayList of storeID's

            storeIDs = con.prepareCall("SELECT storeID FROM Store");
            storeIDs.execute();
            stores = storeIDs.getResultSet();

            while (stores.next()) {
                int i = 0;
                storeArray.add(i++, stores.getString(1));
            }

        } catch (SQLException ex) {
            //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
        }

        //loop thru each storeID and pass in as parameter
        for (int j = 0; j < storeArray.size(); j++) {

            String storeID = storeArray.get(j);

            String stored = "{call printItems('" + storeID + "')}";
            try {
                String storeStmt = "SELECT storeName FROM Store WHERE storeID='" + storeID + "'";
                CallableStatement storeName = con.prepareCall(storeStmt);
                storeName.execute();
                ResultSet storeRs = storeName.getResultSet();
                while (storeRs.next()) {
                    storeNameString = storeRs.getString(1);
                }
            } catch (SQLException ex) {
                //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                ca = con.prepareCall(stored);

                ca.execute();
                ResultSet rs = ca.getResultSet();

                System.out.println("\nStore ID: " + storeID + " Store Name: " + storeNameString);
                System.out.println("Items:");
                while (rs.next()) {
                    System.out.println("     " + rs.getString("display"));
                }

            } catch (SQLException e) {
                System.out.println("problem!");
            }
        }//end of for loop

        //added by SJ
        try{
            ca = con.prepareCall("select * from deletedItems");
            ca.execute();
            ResultSet rdi = ca.getResultSet();
            
            System.out.println("\nRemoved items:");
            while(rdi.next()){
                System.out.println("    " + rdi.getString(2) + ": " + rdi.getString(1) + " in store " + rdi.getString(3) + " cost $" + rdi.getDouble(4) + " and ther are " + rdi.getInt(5) + " in stock.");
            }
        }catch(SQLException e){
            
        }
        //print removed items
        
        
        ///PRINT CUSTOMER INFO
        try {
            String customerCall = "{call printCustomers}";
            custCa = con.prepareCall(customerCall);

            custCa.execute();
            ResultSet custRs = custCa.getResultSet();

            System.out.println("\nCurrent Mall Customers:");
            while (custRs.next()) {
                System.out.println("     " + custRs.getString("display"));
            }

        } catch (SQLException e) {
            System.out.println("problem!");
        }

    }
    
    //searchItem = menu item 3

    /**
     * Method to determine which type of search will be conducted
     * <br> Search either by item code or part of item description
     * @param con Connection to database
     */
    public void searchItem(Connection con) {
        
        int searchChoice = 0;

        do {
            try {
                System.out.print("\nDo you want to search for (1) an item number or (2) part of an item name?  ");
                searchChoice = scan.nextInt();
                if (searchChoice == 1) {
                    searchNumber(con);
                } else if (searchChoice == 2) {
                    searchName(con);
                } else {
                    System.out.println("\nMust enter either 1 or 2... try again.");
                    searchChoice = 0;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nMust enter either 1 or 2... try again.");
                searchChoice = 0;
                scan.nextLine();//scanner problem
            }
        } while (searchChoice == 0);

    }

    //MR added searchItemNum method 11/9
    // searchNumber - used in submenu of searchItem

    /**
     * Method to search for an item in the database by ItemID
     * <br> Called from searchItem method
     * @param con Connection to database
     */
    public void searchNumber(Connection con) {
       
        CallableStatement ca;

        String itemNumString = "";

        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }

        System.out.print("\nWhat is the item id? ");
        scan.nextLine();
        itemNumString = scan.nextLine();

        try {
            String itemSearchCall = "{call searchItemNum('" + itemNumString + "')}";
            ca = con.prepareCall(itemSearchCall);

            ca.execute();
            ResultSet itemRs = ca.getResultSet();

            int found = -1;
            while (itemRs.next()) {
                found = 1;
                System.out.println("     " + itemRs.getString("display"));
            }
            if (found == -1) {
                System.out.println("Item not found");
            }

        } catch (SQLException e) {
            System.out.println("problem!");
        }
    }

    // searchName - used in submenu of searchItem

    /**
     * Method to search for item in database based on partial description
     * Called from searchItem method
     * @param con Connection to database
     */
    public void searchName(Connection con) {
        
        CallableStatement ca;

        String itemNameString = "";

        if (con == null) {
            System.out.println("\n**Need to create connection before selecting "
                    + "menu items.");
            con = DBUtilities.createConnection();
        }

        System.out.print("\nWhat is the item name? ");
        scan.nextLine();
        itemNameString = scan.nextLine();

        try {
            String itemSearchCall = "{call searchItemName('" + itemNameString + "')}";
            ca = con.prepareCall(itemSearchCall);

            ca.execute();
            ResultSet itemRs = ca.getResultSet();

            int found = -1;
            while (itemRs.next()) {
                found = 1;
                System.out.println("     " + itemRs.getString("display"));
            }
            if (found == -1) {
                System.out.println("Item not found");
            }

        } catch (SQLException e) {
            System.out.println("problem!");
        }
        
    }


    //addItem - menu item 4
    
    //CP3 by SJ 
    //stored procedures by MR

    /**
     * Method to add items to database
     * @param con Connection to database
     * @param storeID StoreID to add item
     */
    public void addItem(Connection con, String storeID) {
        //ArrayList of itemIDs just to check if already existing
        ArrayList<String> storeIDs = new ArrayList<String>();
        ArrayList<String> itemIDs = new ArrayList<String>();
        ArrayList<String> removedID = new ArrayList<String>();

        int storeNum = 0;
        int i=1;
        CallableStatement cs;
        boolean valid = false;
        if(storeID == null){
            boolean good = false;
            ResultSet rs = null;
                try{
                    cs = con.prepareCall("Select storeName, storeID from Store");
                    cs.execute();
                    //stmt = con.createStatement();
                    //ResultSet rs = stmt.executeQuery(sQuery);
                    rs = cs.getResultSet();
                    System.out.println("\nHere are the stores:");
                    while(rs.next()){
                        System.out.println("\t" +i  + ".  " + rs.getString(2) + " - " + rs.getString(1));
                        storeIDs.add(rs.getString(2));
                        i++;
                    }          
                    rs.beforeFirst();            
                }catch(SQLException e){
                    System.out.println("An SQL Exception occurred!");
                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                }
                
                
                    System.out.println("\nWhich store number?");

                    do{
                        try{
                            storeNum = scan.nextInt();
                            if(storeNum <1){
                                System.out.println("Please only enter numbers between 1 and " + (i-1));
                                good = false;
                            }
                            else{
                                rs.absolute(storeNum);
                                if(rs.getRow() == storeNum){
                                    storeID = rs.getString(2);
                                }                
                                /*while(rs.next()){
                                    if(rs.getRow() == storeNum){
                                        storeID = rs.getString(2);
                                    }
                                }*/
                                do{
                                    if(storeIDs.get(storeNum - 1).equalsIgnoreCase(storeID)){
                                        valid = true;
                                        good = true;
                                    }
                                    else{
                                        System.out.println("That wasn't a vaild ID. Please choose again.");
                                        storeNum = scan.nextInt();
                                        good = false;
                                    }
                                }while(valid == false);
                            }
                        }catch(SQLException e){
                            System.out.println("An SQL Exception occurred!");
                            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                        } catch(InputMismatchException e){
                            System.out.println("Please only enter numbers between 1 adn " + (i-1));
                            scan.next();
                        }catch(IndexOutOfBoundsException e){
                            System.out.println("Please only enter integers between 1 and " + (i-1));
                        }
            }while(good != true);
        }//end if storeID = null
          
        boolean newItem = true;
        String itemID = "";
        CallableStatement addItem;

        try{
            cs = con.prepareCall("select itemID from item where storeID != '" + storeID + "'");
            cs.execute();
            ResultSet rid = cs.getResultSet();
            
            cs = con.prepareCall("Select itemName, itemID from item where item.storeID = '" + storeID + "'");
            cs.execute();
            ResultSet rsi = cs.getResultSet();
            //stmt = con.createStatement();
            
            System.out.println("What is the item id?");
            scan.nextLine();//scanner problem
            boolean init = true;
            do{
                init = false;
                itemID = scan.nextLine();
            
                   //stmt.executeQuery(iQuery);
                   rid.beforeFirst();
                while(rid.next()){
                    if(rid.getString(1).equals(itemID)){
                        System.out.println("This item id already exists in another store. Please enter a new ID.");
                        init = true;
                    }
                }
                if(init == false){
                    break;
                }
            }while(init !=false);
            
            while(rsi.next()){
                //System.out.println(rsi.getString(2));
                if(rsi.getString(2).equalsIgnoreCase(itemID)){
                    //System.out.println("not new item");
                    newItem = false;
                }
            }
        }catch(SQLException e){
            System.out.println("An SQL Excpetion occurred!");
            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
        }
        int numInStock = 0;
        int itemPosition = -1;      

        String newIN = "";
        int newIQ = 0;
        double newIP = 0;
        String dId = "";
        boolean deleted = false;
        if (newItem == true) {

            System.out.println("What is the item name?");
            newIN = scan.nextLine();
            try{
                cs = con.prepareCall("Select itemID, price, number, itemName, storeID from deletedItems where itemName = '" + newIN + "' or itemID like '" + itemID + "%'");
                cs.execute();
                ResultSet rdi = cs.getResultSet();
                if(rdi.next()){
                    rdi.beforeFirst();
                    System.out.println("\n**There are item(s) in removed items that are similar\n");
                    int j = 1;
                    while(rdi.next()){
                        System.out.println("   " + j + ". " +rdi.getString(1) + ": " + rdi.getString(4) + " in store " + rdi.getString(5) + " cost $" + rdi.getDouble(2) + " and there are " + rdi.getInt(3) + " in stock");
                        j++;
                    }
                    rdi.beforeFirst();
                    rdi.next();
                    System.out.println("\nWhich item or -1 for new");
                    int which = 0;
                    valid = false;
                    do{
                        try{
                            which = scan.nextInt();
                            if(which != -1 && which<1 || which>j){
                                System.out.println("You can only choose 1 - " + j + " or -1 if you want a new item");
                                j++;
                            }
                            else{
                                valid = true;
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("please enter a number");
                            scan.next();
                        }
                    }while(valid == false);
                    
                    
                    if(which !=-1){
                        rdi.absolute(which);
                        dId = rdi.getString(1);
                        newIQ = rdi.getInt(3);
                        newIP = rdi.getDouble(2);
                        deleted = true;
                        valid = false;
                        boolean correct = false;
                        System.out.println("\nDo you want to change the price or quantity? (true/false)");
                        do{
                            try{
                                correct = scan.nextBoolean();
                                valid = true;
                                int choice = 0;
                                boolean valid2 = false;
                                if(correct == true){
                                    System.out.println("\nwhat would you like to change?\n   1. quantity\n   2. price\n   3. both");
                                    do{
                                        try{
                                            choice = scan.nextInt();
                                            if(choice > 3 ||choice <1){
                                                System.out.println("Only enter 1 -3");
                                            }
                                            else{
                                                valid2 = true;
                                                break;
                                            }
                                        }catch(InputMismatchException e){
                                            System.out.println("Please enter a number");
                                            scan.next();
                                        }
                                    }while(valid2 == false);


                                    if(choice ==1 || choice == 3){
                                        int aORs = 0;
                                        valid = false;
                                        newIQ = rdi.getInt(3);
                                        int oldQuantity = rdi.getInt(3);                                    

                                            System.out.println("\nAre you adding or removing?");
                                            System.out.println("   1.  Adding\n   2.  removing");
                                        do{
                                            try{
                                                aORs = scan.nextInt();
                                                if(aORs < 1 || aORs > 2){
                                                    System.out.println("Please enter a 1 if you want to add or 2 if you want to remove! Try again");
                                                    valid = false;
                                                }
                                                else{
                                                    valid = true;
                                                    break;
                                                }
                                            }catch(InputMismatchException e){
                                                System.out.println("Please enter a 1 if you want to add or 2 if you want to remove! Try again");
                                                scan.next();
                                            }
                                        }while(valid==false);

                                        //1.1 add stock
                                        if(aORs == 1){
                                            System.out.print("How many are you adding:  ");
                                            int adding = 0;
                                            valid = false;
                                            do{
                                                try{
                                                    adding = scan.nextInt();
                                                    if(adding <= 0){
                                                            System.out.print("Invaild. Please enter a number greater than 0:  ");
                                                            valid = false;
                                                    }
                                                    else{
                                                        newIQ = oldQuantity + adding;
                                                        //store.getItems().get(itemNum-1).setNumber(newQuantity);
                                                        try {
                                                            cs = con.prepareCall("update deletedItems set number = " + newIQ + " where itemID = '" + rdi.getString(1) + "'");
                                                            cs.execute();
                                                            System.out.println("You added " + adding +". The quantity is now " + newIQ);
                                                            
                                                        } catch (SQLException ex) {
                                                            System.out.println("An SQL Exception occurred! quantity not updated.");
                                                            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                                                        }

                                                        valid = true;
                                                        break;
                                                    }

                                                }catch(InputMismatchException e){
                                                    System.out.println("Please only enter whole numbers greater than 0!");
                                                    scan.next();
                                                }
                                            }while(valid==false);
                                        }//end adding

                                        //choice 1.2 remove stock
                                        else if(aORs == 2){
                                            System.out.print("How many are you removing:  ");
                                            int remove = 0;
                                            valid = false;
                                            do{
                                                try{
                                                    remove = scan.nextInt();
                                                    if(remove <=0){
                                                        System.out.print("Invaild. Please enter a number greater than 0:  ");
                                                        valid = false;
                                                    }
                                                    else if(remove > oldQuantity){
                                                        //or remove item----------------------
                                                        System.out.println("Error! There are only " + oldQuantity + " in stock!\nPlease enter a number less than the current stock:  ");
                                                        valid = false;
                                                    }
                                                    else{
                                                        valid = true;
                                                    }
                                                }catch(InputMismatchException e){
                                                    System.out.println("Please only enter whole numbers greater than 0!");
                                                    scan.next();
                                                }
                                            }while(valid != true);
                                            if(valid = true){
                                                newIQ = oldQuantity - remove;
                                                //.getItems().get(itemNum-1).setNumber(newQuantity);
                                                try {
                                                    cs = con.prepareCall("update deletedItems set number = " + newIQ + " where itemID = '" + rdi.getString(1) + "'");
                                                    cs.execute();
                                                    System.out.println("Item successfully updated\nYou removed " + remove +". The quantity added is " + newIQ);
                                                    valid = true;
                                                } catch (SQLException ex) {
                                                    System.out.println("An SQL Exception occurred! quantity not updated.");
                                                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                                                }

                                            }
                                        }//end removing
                                    }//end update quantity

                                    //choice 2 change price
                                    if(choice ==2 || choice ==3){
                                        System.out.print("Enter the new price:  ");
                                        newIP = rdi.getDouble(2);
                                        valid = false;
                                        do{
                                            try{
                                                newIP = scan.nextDouble();
                                                if(newIP < 0){
                                                    System.out.println("Please enter a number greater than 0");
                                                }
                                                else{
                                                    try{

                                                        cs = con.prepareCall("update deletedItems set price = " + newIP +" where itemID = '" + rdi.getString(1) + "'");
                                                        cs.execute();
                                                        System.out.println("Item successfully updated! The price is now $" + newIP);

                                                        valid = true;
                                                        break;
                                                    }catch(SQLException ex){
                                                        System.out.println("An SQL Exception occurred! quantity not updated.");
                                                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    //store.getItems().get(itemNum-1).setPrice(newPrice);

                                                }
                                            }catch(InputMismatchException e){    
                                                System.out.println("Error! Please enter a number");
                                                scan.next();
                                            }
                                        }while(valid==false); 
                                    }
                                }
                                else{
                                    newIP = rdi.getDouble(2);
                                    newIQ = rdi.getInt(3);
                                    
                                }



    ////////////////////////////////////////////////////////////////////////////////////////////////////


                            }catch(InputMismatchException e){
                                System.out.println("Please enter true or false");
                                scan.next();
                            }
                        }while(valid != true);
                    }//end which 1 - j
                    else if(which == -1){
                        deleted = false;
                        System.out.println("How many are you putting in stock?");
                        valid = false;
                        do{
                            try{
                                newIQ = scan.nextInt();
                                if(newIQ <=0){
                                    System.out.println("Please enter a number that is greater than 0!");
                                }
                                else{
                                    valid = true;
                                    break;
                                }
                            }catch(InputMismatchException e){
                                System.out.println("Please enter a number that is greater than 0");
                                scan.next();
                            }
                        }while(valid==false);

                        System.out.println("What is the price of each?");
                        valid = false;
                        do{
                            try{
                                newIP = scan.nextDouble();
                                if(newIP <=0){
                                    System.out.println("Please enter a number that is greater than 0!");
                                } 
                                else{
                                    valid = true;
                                    break;
                                }
                            }catch(InputMismatchException e){
                                System.out.println("Please enter a number that is greater than 0");
                                scan.next();
                            } 
                        }while(valid!=true);
                        }
                    }
                else{
                    System.out.println("How many are you putting in stock?");
                    valid = false;
                    do{
                        try{
                            newIQ = scan.nextInt();
                            if(newIQ <=0){
                                System.out.println("Please enter a number that is greater than 0!");
                            }
                            else{
                                valid = true;
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please enter a number that is greater than 0");
                            scan.next();
                        }
                    }while(valid==false);

                    System.out.println("What is the price of each?");
                    valid = false;
                    do{
                        try{
                            newIP = scan.nextDouble();
                            if(newIP <=0){
                                System.out.println("Please enter a number that is greater than 0!");
                            } 
                            else{
                                valid = true;
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please enter a number that is greater than 0");
                            scan.next();
                        } 
                    }while(valid!=true);
                }
            }catch(SQLException e){
                System.out.println("An SQL Excpetion occurred!");
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }
            

            try{
                //addItem = con.prepareCall("Insert into item values('"+  newIN +"', '" + itemID +"', '" + storeID +"', " + newIP +", " + newIQ +")");
                //SJ added procedure addItem
                System.out.println(itemID);
                addItem = con.prepareCall("{call addItem('" + newIN + "','" + itemID + "','" + storeID + "'," + newIP +"," + newIQ + ")}");
                addItem.execute();
                if(deleted == true){
                    try{
                        System.out.println(dId);
                        cs = con.prepareCall("delete from deletedItems where itemID = '" + dId +"'");
                        cs.execute();
                    }catch(SQLException e) {
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
 
                    }
                }
                //stmt = con.createStatement();
                //ResultSet rsAdd = stmt.executeQuery(qAddNew);
                System.out.println("Item successfully added to inventory\n");
            }catch(SQLException e){
                System.out.println("Error adding in item. Please try again.");
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }

        }
        else{
            System.out.println("\nThis item is already in stock!\nDo you want to add more?");
            System.out.println("  1. Yes");
            System.out.println("  2. No");
            int addMore = 0;
            valid = false;
            do{
                try{
                    addMore = scan.nextInt();
                    if(addMore <=0){
                        System.out.println("Please enter 1 for yes or 2 for no!");
                    } 
                    else{
                        valid = true;
                        break;
                    }
                }catch(InputMismatchException e){
                    System.out.println("Please enter either 1 for yes or 2 for no");
                    scan.next();
                }
            }while(valid!=true);
            
            if(addMore == 1){
                //String qInStock = "Select number from items where itemID = " + itemID;

                try{
                    cs = con.prepareCall("Select inStock from item where itemID = '" + itemID + "'");
                    cs.execute();
                    //stmt = con.createStatement();
                    ResultSet rsis = cs.getResultSet();
                    rsis.next();
                    numInStock = rsis.getInt(1);
                    System.out.println("\nThere are "+numInStock+ " of these items in stock.");
                    System.out.println("How many do you want to add?");
                    valid = false;
                    do{
                        try{
                            newIQ = scan.nextInt();
                            if(newIQ < 0){
                                System.out.println("Invalid input! You entered a number less than zero\nTo remove items please go to Update an Item (number 6 on the main menu)"
                                        + "\nPlease enter a number greater than 0 or 0 to return to the main menu");
                            }
                            else{
                                valid = true;
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please only enter integers greater than 0");
                            scan.next();
                        }
                    }while(valid!=true);
                    
                    if(newIQ == 0){
                        System.out.println("Returning to main menu.\nIf you want to make a change to this item, choose update item (number 6 on the menu)");
                    }
                    else{
                        numInStock = newIQ+numInStock;
                        //m.getStores().get(storeNum-1).getItems().get(itemPosition).setNumber(numInStock);
                        //String qUpdate = "Update item set number = " + numInStock;
                        try{
                            cs = con.prepareCall("{call addQuantity('" + storeID + "','" + itemID + "'," + numInStock + ")}");
                            //Statement stmt2 = con.createStatement();
                            cs.execute();
                            System.out.println("Stock has been added\n");
                        }catch(SQLException e){
                            System.out.println("An SQL exception occurred. Item not updated.");
                            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }catch(SQLException e){
                    System.out.println("SQL Exception occurred");
                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

     // addCustomer - menu item 5
     // CP3 by SJ     
     /**
     * Method to add new Customer to the database
     * @param con Connection to the database
     */
    //took out the second custNum (not the auto incremented one) from SQL file
    public void addCustomer(Connection con) {
        CallableStatement cs;
        CallableStatement cs2;
        String fName;

        System.out.println("What is your first name?");
        fName = scan.nextLine();
        
        //resolves minor error preventing the user from entering their first name
        //this error is rare but very annoying when it happens SM 10/12
        if(fName.isEmpty())
            fName = scan.nextLine();

        System.out.println("What is your last name?");
        String lName = scan.nextLine();


        try {

            int custID = 0;
            try{

                //SJ added procedure insertCust
                cs = con.prepareCall("{call insertCust('" + lName + "','" + fName + "')}");
                cs.execute();
                cs2 = con.prepareCall("Select custID from customers where firstName = '" + fName+ "' and lastName = '" + lName +"'");
                cs2.execute();
                ResultSet cNum = cs2.getResultSet();
                cNum.last();
                custID = cNum.getInt(1);
                System.out.println(custID);
                System.out.println("Thank you for shopping with us " + fName + " " + lName + "!\n\t Your customer Id is: " + custID + "\n");
            }catch(SQLException e){
                System.out.println("SQL Exception occurred");
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }
            
        } catch (Exception e) {
            System.out.println("There was an error when trying to add in your information. Please try again.");
        }
    }
    
    
    //added by SJ 11/03

    /**
     * Method to update an item already in the database
     * @param con Connection to the database
     */
    public void updateItem(Connection con){
    CallableStatement cs;
        boolean more = true;
        do{
            String queryStores = "SELECT storeName FROM Store";
            ArrayList<String> itemID = new ArrayList<String>();
            ArrayList<String> store = new ArrayList<String>();
            try{
                cs = con.prepareCall("Select storeName, storeID from store");
                cs.execute();
                
                ResultSet rs = cs.getResultSet();
                System.out.println("\nStores:");
                int i = 1;
                while(rs.next()){
                    System.out.println("   "+ i + ". StoreID: " + rs.getString(2) + " Store Name: " + rs.getString(1));
                    store.add(rs.getString(2));
                    //String queryItems = "SELECT * FROM item as i INNER JOIN store as s WHERE i.storeID = s.storeID";
                    i++;
                }
            }catch(SQLException e){
                System.out.println("There was a SQL exception. Try again.");
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }

            System.out.println("What store is the item in?");
            int storeNum = 0;
            String storeID = "";
            boolean valid = false;
            do{
                try{
                    storeNum = scan.nextInt();

                    if(storeNum<1 || storeNum >store.size()){
                        System.out.println("Error! Please enter a number between 1 and " + store.size());
                    }
                    else{
                        storeID = store.get(storeNum-1);
                        valid = true;
                        break;
                    } 
                }catch(InputMismatchException e){
                    System.out.println("Error! Please enter a number between 1 and " + store.size());
                    scan.next(); 
                }
            }while(valid ==false);

            System.out.println("\nHere are the items in that store:");
                    try{
                        cs = con.prepareCall("Select * from item where storeID = '" + storeID + "'");
                        cs.execute();
                        ResultSet rsItem = cs.getResultSet();
                        int j = 1;
                        while(rsItem.next()){
                            System.out.println("   " + j + ". " + rsItem.getString(2) + ": " + rsItem.getString(1) + "\n    Price: " + rsItem.getDouble(4) + "\n    Num in Stock: " + rsItem.getInt(5));
                            itemID.add(rsItem.getString(2));
                            j++;
                        }
                    }catch(SQLException e){
                        System.out.println("An SQL exception occurred.");
                    }
            

            System.out.println("");

            System.out.print("Enter the number beside the item that you want to update: ");
            int number = 0;
            valid = false;
            do{
                try{
                    number = scan.nextInt();
                    if(number <=0 || number >itemID.size()){
                        System.out.println("if Invalid! Please enter the number beside the item you would like to update");
                        valid = false;
                    }
                    else{
                        valid = true;
                        break;
                    }
                }catch(InputMismatchException e){
                    System.out.println("catch Invalid! Please enter the number beside the item you would like to update");
                    scan.next();
                }
            }while(valid == false);
            boolean cont = true;
            do{
                System.out.println("\nDo you want to:");
                System.out.println("   1.  Update the quantity\n   2.  Update the price\n   3.  Remove the item\n   4.  Return to main menu");
                int choice = 0;
                valid = false;
                do{
                    try{
                        choice = scan.nextInt();
                        if(choice <0 || choice > 4){
                            System.out.println("Error! Your can only choose a number 1 - 4.\nTry again");
                            
                        }
                        else{
                            valid = true;
                            break;
                        }
                    }catch(InputMismatchException e){
                        System.out.println("Error! Your can only choose a number 1 - 4.\nTry again");
                        scan.next();
                    }
                }while(valid==false);
                String itemNum = itemID.get(number-1);

                //choice 1 add.remove quantity
                if(choice ==1){
                    int aORs = 0;
                    valid = false;
                    int newQuantity = 0;
                    int oldQuantity =0;
                    try{
                        
                        cs = con.prepareCall("Select number from item where itemID = '" + itemNum + "'");
                        cs.execute();
                        
                        ResultSet num = cs.getResultSet();
                        num.next();
                        oldQuantity = num.getInt(1);
                        System.out.println("\nThere are currently " + oldQuantity + " in stock.");
                    }catch(SQLException e){
                        System.out.println("An SQL Exception occurred!");
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                    }
                    
                        System.out.println("Are you adding or removing?");
                        System.out.println("   1.  Adding\n   2.  removing");
                    do{
                        try{
                            aORs = scan.nextInt();
                            if(aORs < 1 || aORs > 2){
                                System.out.println("Please enter a 1 if you want to add or 2 if you want to remove! Try again");
                                valid = false;
                            }
                            else{
                                valid = true;
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please enter a 1 if you want to add or 2 if you want to remove! Try again");
                            scan.next();
                        }
                    }while(valid==false);

                    //1.1 add stock
                    if(aORs == 1){
                        System.out.print("How many are you adding:  ");
                        int adding = 0;
                        valid = false;
                        do{
                            try{
                                adding = scan.nextInt();
                                if(adding <= 0){
                                        System.out.print("Invaild. Please enter a number greater than 0:  ");
                                        valid = false;
                                }
                                else{
                                    newQuantity = oldQuantity + adding;
                                    //store.getItems().get(itemNum-1).setNumber(newQuantity);
                                    try {
                                        cs = con.prepareCall("{call addQuantity('" + storeID + "','" + itemNum + "'," + newQuantity + ")}");
                                        cs.execute();
                                        System.out.println("You added " + adding +". The quantity is now " + newQuantity);
                                    } catch (SQLException ex) {
                                        System.out.println("An SQL Exception occurred! quantity not updated.");
                                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    
                                    valid = true;
                                    break;
                                }
                                
                            }catch(InputMismatchException e){
                                System.out.println("Please only enter whole numbers greater than 0!");
                                scan.next();
                            }
                        }while(valid==false);
                    }//end adding

                    //choice 1.2 remove stock
                    else if(aORs == 2){
                        System.out.print("How many are you removing:  ");
                        int remove = 0;
                        valid = false;
                        do{
                            try{
                                remove = scan.nextInt();
                                if(remove <=0){
                                    System.out.print("Invaild. Please enter a number greater than 0:  ");
                                    valid = false;
                                }
                                else if(remove > oldQuantity){
                                    //or remove item----------------------
                                    System.out.println("Error! There are only " + oldQuantity + " in stock!\nPlease enter a number less than the current stock:  ");
                                    valid = false;
                                }
                                else{
                                    valid = true;
                                }
                            }catch(InputMismatchException e){
                                System.out.println("Please only enter whole numbers greater than 0!");
                                scan.next();
                            }
                        }while(valid != true);
                        if(valid = true){
                            newQuantity = oldQuantity - remove;
                            //.getItems().get(itemNum-1).setNumber(newQuantity);
                            try {
                                cs = con.prepareCall("{call addQuantity('" + storeID + "','" + itemNum + "'," + newQuantity + ")}");
                                cs.execute();
                                System.out.println("Item successfully updated\nYou removed " + remove +". The quantity is now " + newQuantity);
                                valid = true;
                            } catch (SQLException ex) {
                                System.out.println("An SQL Exception occurred! quantity not updated.");
                                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }//end removing
                }//end update quantity

                //choice 2 change price
                else if(choice ==2){
                    System.out.print("Enter the new price:  ");
                    double newPrice = 0;
                    valid = false;
                    do{
                        try{
                            newPrice = scan.nextDouble();
                            if(newPrice < 0){
                                System.out.println("Please enter a number greater than 0");
                            }
                            else{
                                try{
                                    
                                    cs = con.prepareCall("update item set price = " + newPrice +" where itemID = '" + itemNum + "'");
                                    cs.execute();
                                    System.out.println("Item successfully updated! The price is now $" + newPrice);
                                    valid = true;
                                    break;
                                }catch(SQLException e){
                                    
                                }
                                //store.getItems().get(itemNum-1).setPrice(newPrice);
                                
                            }
                        }catch(InputMismatchException e){    
                            System.out.println("Error! Please enter a number");
                            scan.next();
                        }
                    }while(valid==false); 
                }

                //choice 3 remove item from store
                else if(choice == 3){
                    ArrayList<Item> deletedItems = new ArrayList<Item>();
                    System.out.println("Are you sure you want to delete this item? (true/false)");
                    boolean sure = false;
                    valid = false;
                     do{
                        try{
                            sure = scan.nextBoolean();
                            valid = true;
                            if(sure ==true){

                                try{
                                    cs = con.prepareCall("select * from item where itemID = '" + itemNum + "'");
                                    cs.execute();
                                    ResultSet ri = cs.getResultSet();
                                    ri.next();
                                    cs = con.prepareCall("select itemID from deletedItems");
                                    cs.execute();
                                    ResultSet rd = cs.getResultSet();
                                    ArrayList<String> alID = new ArrayList<String>();
                                     while(rd.next()){
                                         alID.add(rd.getString(1));
                                     }
                                    rd.beforeFirst();
                                    rd.next();
                                    boolean inDelete = true;
                                    
                                    try{
                                        cs = con.prepareCall("insert into deletedItems values('" + ri.getString(1) + "','" + ri.getString(2) + "','" + ri.getString(3) + "'," + ri.getDouble(4) + "," + ri.getInt(5) + ")");
                                        cs.execute();

                                        cs = con.prepareCall("delete from item where itemID = '" + itemNum + "'");
                                        cs.execute();
                                        System.out.println("\nItem removed");
  
                                    }catch(SQLException e){
                                        boolean stillAdd = false;
                                            System.out.println("\nThis item has already been stored in the delete table of the database.\nDo you still want to add it to database? (true/false)");
                                            boolean valid2 = false;
                                            do{
                                                try{
                                                    stillAdd = scan.nextBoolean();
                                                    if(stillAdd == true){
                                                        boolean validID = false;
                                                        int i = 0;
                                                        String ID = ri.getString(2);

                                                        do{
                                                            i++;
                                                            ID = ri.getString(2) + ("-" + i);
                                                            for(int j=0;j<alID.size();j++){

                                                                if(ID.equalsIgnoreCase(alID.get(j))){
                                                                    validID = false;
                                                                    break;
                                                                }
                                                                else{
                                                                    validID = true;
                                                                }
                                                            }
                                                        }while(validID == false);
                                                        try{
                                                            cs = con.prepareCall("insert into deletedItems values('" + ri.getString(1) + "','" + ID + "','" + ri.getString(3) + "'," + ri.getDouble(4) + "," + ri.getInt(5) + ")");
                                                            cs.execute();

                                                            cs = con.prepareCall("delete from item where itemID = '" + itemNum + "'");
                                                            cs.execute();
                                                            System.out.println("\nItem removed from shelf and added to deleted items\nThe ID for this item is now " + ID); 
                                                            inDelete = false;
                                                            valid = true;
                                                            break;
                                                        }catch(SQLException ex){
                                                            System.out.println("3");
                                                            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                                                        }

                                                    }
                                                    else{
                                                        System.out.println("Item will not be removed.");
                                                        inDelete = false;
                                                        break;
                                                    }
                                                }catch(InputMismatchException ex){
                                                    System.out.println("Please enter true or false");
                                                    scan.next();
                                                }
                                            }while(valid2 != true);
                                        
                                        
                                    }//end catch 2

                                }catch(SQLException e){
                                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                                }

                            }
                            else{
                                System.out.println("Item will not be removed.");
                                break;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Error! Please enter true or false!");
                            scan.next();
                        }
                    }while(valid==false);
                     
                     cont = false;

                }//end choice 3

                else if(choice ==4){
                    more = false;
                    break;
                }
                
                if(choice ==1 || choice ==2){
                    System.out.println("\nDo you want to make another change to this item? (true/false)");
                    valid = false;
                    do{
                        try{
                            cont = scan.nextBoolean();
                            break;
                        }catch(InputMismatchException e){
                            System.out.println("Error! Please enter true or false!");
                            scan.next();
                        }
                    }while(valid==false);
                }
            }while(cont != false);

            if(more != false){
                System.out.println("\nDo you want to update another item? (true/false)");
                do{
                    try{
                        more = scan.nextBoolean();
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("Error! Please enter true or false!");
                        scan.next();
                    }
                }while(valid==false);
            }
        }while(more != false);   
    }//end updateItem
    
    

    //added by SJ 11/06

    /**
     * Method to add a store to the database
     * @param con Connection to the database
     */
	public void addStore(Connection con){
		String type = "";
		String typeID = "";
                CallableStatement cs;
		
		System.out.println("Here are the store types:");
		
		int n = 1;
                ArrayList<String> alid = new ArrayList<String>();
                ResultSet rsgt = null;
		try{
                    cs = con.prepareCall("Select storeType, storeTypeID from storeType");
                    cs.execute();
                    rsgt = cs.getResultSet();
                    while(rsgt.next()){
                            System.out.println("   " + n + ".  Type: " + rsgt.getString(1) + "\n\tID: " + rsgt.getString(2) + "\n");
                            alid.add(rsgt.getString(2));
                            n++;
                    }
                    rsgt.beforeFirst();
		}catch(SQLException e){
			System.out.println("An SQL exception occurred");
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
		}

		System.out.println("Which store type? (or -1 for a new store type)");
		int choice = 0;
                boolean valid = false;
                do{
                    try{
                        choice = scan.nextInt();
                        if(choice == -1){
                            scan.nextLine();
                            System.out.println("What is the store type?");
                            boolean valid2 = false;
                            do{
                                try{
                                    rsgt.beforeFirst();
                                    type = scan.nextLine();
                                    valid2 = true;
                                    while(rsgt.next()){
                                        if(type.equalsIgnoreCase(rsgt.getString(1))){
                                            System.out.println("This type already exists. Enter a new type.");
                                            valid2 = false;
                                        }
                                    }
                                    if(valid2 == true){
                                        break;
                                    }
                                }catch(SQLException e){
                                    System.out.println("An SQL exception occurred");
                                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                                }
                            }while(valid2 == false);
                            
                            System.out.println("What is the store type id?");
                            valid2 = false;
                            do{
                                try{
                                    rsgt.beforeFirst();
                                    typeID = scan.nextLine().toUpperCase();
                                    valid2 = true;
                                    while(rsgt.next()){
                                        if(typeID.equalsIgnoreCase(rsgt.getString(2))){
                                            System.out.println("This type ID already exists. Enter a new type.");
                                            valid2 = false;
                                        }
                                    }
                                    if(valid2 == true){
                                        break;
                                    }
                                }catch(SQLException e){
                                    System.out.println("An SQL exception occurred");
                                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                                }
                            }while(valid2 == false);
                            valid = true;
                            break;
                            
                        }
                        else if(choice >= 1 ||choice <= n){
                            try{
                                String id = alid.get(choice-1);
                                while(rsgt.next()){
                                    if(rsgt.getString(2).equalsIgnoreCase(id)){
                                        type = rsgt.getString(1);
                                        typeID = rsgt.getString(2);
                                        break;
                                    }
                                }
                                valid = true;
                            }catch(SQLException e){
                                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                            }
                            scan.nextLine();
                        }
                        else{
                            System.out.println("Error! Please enter the number beside the store type or -1 to create a new one.");
                        }
                    }catch(InputMismatchException e){
                            System.out.println("Please only enter numbers");
                            scan.next();
                    }
                }while(valid != true);
		//String insertType = "INSERT INTO storeType VALUES(type, typeID)";
                ResultSet rsid = null;
                try{
                    cs = con.prepareCall("select storeName, storeID from store");
                    cs.execute();
                    rsid = cs.getResultSet();
                }catch(SQLException e){
                    System.out.println("An SQL exception occurred! Store not added");
                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                }
                
                System.out.println("What is the storeID?");
		String storeID = "";
		valid = false;
                boolean init = false;
                do{
                    init = false;
                    try{
                        storeID = scan.nextLine();
                        
                        if(storeID.equals("")){
                            storeID = scan.nextLine();
                        }
                        rsid.beforeFirst();
                        while(rsid.next()){
                            if(storeID.equals(rsid.getString(2))){
                                System.out.println("This store ID already exists.\nEnter a new store ID: ");
                                init = true;
                                break;
                            }

                        }
                        if(init == false){
                            valid = true;
                            break;
                        }
                    }catch(SQLException e){
                        System.out.println("An SQL exception occurred! Store not added");
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                    }
                }while(valid != true);
                
                
                
		String name = null;
                init = false;
		System.out.println("What is the name of the store that you want to add?");
		do{
                    init = false;
                    try{
                        name = scan.nextLine();
                        if(name == null){
                            name = scan.nextLine();
                        }
                        rsid.beforeFirst();
                        while(rsid.next()){
                            if(name.equals(rsid.getString(1))){
                                System.out.println("This store name already exists.\n   Name: " + rsid.getString(1) + " ID: " + rsid.getString(2) + " \n\nEnter a new store name: ");
                                init = true;
                                break;
                            }
                        }
                    }catch(SQLException e){
                        System.out.println("An SQL exception occurred! Store not added");
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                    }
                }while(init != false);
		
                boolean added = false;
                try{
                    if(choice == -1){
                        cs = con.prepareCall("{call addStoreAndType('" + name + "','" + storeID + "','" + typeID + "','" + type + "')}");
                        cs.execute();
                    }
                    else{
                        cs = con.prepareCall("{call addStore('" + name + "','" + storeID + "','" + typeID + "')}");
                        cs.execute();
                    }
                    System.out.println(name + " " + storeID + " has been added.");
                    added = true;
                }catch(SQLException e){
                    System.out.println("An SQL exception occurred! Store not added");
                    Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
                }
                valid = false;
                boolean valid2 = false;
                boolean addItems = false;
                if(added == true){
                    System.out.println("\n\nWould you like to add item to this store? (true/false)");
                    do{
                        try{
                            addItems = scan.nextBoolean();
                            valid = true;
                            if(addItems == true){
                                boolean more = true;
                                do{
                                    addItem(con, storeID);
                                    System.out.println("\nDo you want to add another item? (true/false)");
                                    try{
                                        more = scan.nextBoolean();
                                        if(more ==false){
                                            valid = true;
                                            break;
                                        }
                                        else{
                                            continue;
                                        }
                                    }catch(InputMismatchException e){
                                        System.out.println("Error! Please only enter true or false!");
                                        scan.next();
                                    }
                                }while(more == true);
                            }
                        }catch(InputMismatchException e){
                            System.out.println("Please enter true or false!");
                            scan.next();
                        }
                    }while(valid == false);
		//String addStore = "";
                }

	}
        
    /**
     * Method to check that the Administrator has entered a valid login
     * <br> add accepts the Administrator's menu choice
     * @param con Connection to the database
     * @return Returns Administrator's menu choice
     */
    public static boolean adminLogin(Connection con){
            boolean validLogin = false;
            CallableStatement cs;
            try{
                boolean valid = false;
                boolean again = false;
                String username = "";
                String password = "";

                System.out.print("\nEnter your ID:  ");
                int id = 0;
                do{
                    try{
                        id = scan.nextInt();
                        valid = true;
                        break;
                    }catch(InputMismatchException e){
                        System.out.println("please enter a number!");
                        scan.next();
                    }
                }while(valid == false);
                
                scan.nextLine();
                System.out.print("Username:  ");
                username = scan.nextLine();
                if(username == null){
                    username = scan.nextLine();
                }

                System.out.print("Password:  ");
                password = scan.nextLine();
                
                cs = con.prepareCall("Select user, pass from admin where adminID = " + id);
                cs.execute();
                ResultSet rs = cs.getResultSet();
                while(rs.next()){
                    if(username.equals(rs.getString(1))){
                        if(password.equals(rs.getString(2))){
                            validLogin = true;
                            System.out.println("\nWelcome " + username + "!");
                            break;
                        }
                    }
                    else{
                        
                        validLogin = false;
                    }
                }
                if(validLogin == false){
                    System.out.println("That id does not exists, or the username, or password wasn't correct. They are case and space sensitive\n");
                }

            }catch(SQLException e){
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }
            return validLogin;
        }
        
    /**
     * Method to add a new Administrator to the database
     * @param con Connection to the database
     */
    public void addAdmin(Connection con) {
        CallableStatement cs;
        CallableStatement cs2;
        String user = null;

        System.out.println("Enter a username?");
        boolean valid = false;
        do{
            try{
                valid = true;
                cs = con.prepareCall("select user from admin");
                cs.execute();
                ResultSet rs = cs.getResultSet();
                user = scan.nextLine();

                if(user == null){
                    user = scan.nextLine();
                }
                rs.beforeFirst();
                while(rs.next()){
                    if(user.equals(rs.getString(1))){
                        System.out.println("This username already exists! Please enter a new one.");
                        valid = false;
                        break;
                    }
                }
                if(valid == true){
                    break;
                }
            }catch(SQLException e){
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);

            }
        }while(valid == false);
        System.out.println("Enter a password?");
        String pass = scan.nextLine();
        
         try {
 
            
            int adminID = 0;
            try{

                //SJ added procedure addAdmin
                cs = con.prepareCall("{call addAdmin('" + pass + "','" + user + "')}");
                cs.execute();
                cs2 = con.prepareCall("Select adminID from admin where user = '" + user+ "' and pass = '" + pass +"'");
                cs2.execute();
                ResultSet cNum = cs2.getResultSet();
                cNum.last();
                adminID = cNum.getInt(1);
                System.out.println("Your admin Id is: " + adminID + "\n");
            }catch(SQLException e){
                System.out.println("SQL Exception occurred");
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, e);
            }


            
        } catch (Exception e) {
            System.out.println("There was an error when trying to add in your information. Please try again.");
        }
    }
    
        
        
        //MR adde checkCart method 11/12

    /**
     * Method to check if Shopping Cart is already in use
     * @param con Connection to database
     * @param cart Cart in use but without items
     * @param fullCart Cart in use containing items
     * @return Returns indicator if a cart is needed
     */
    public static String checkCart(Connection con, ShoppingCartDB cart, ShoppingCartDB fullCart) {
        String needCart = "yes";
        boolean valid = false;
        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }

        //check to see if there's already an active cart with items
        while (fullCart != null || cart != null) {
            if (fullCart != null && fullCart.getActive().equals("active")) {
                System.out.println("You still have an active cart");
                System.out.print("Do you want a new cart (y/n)? ");
                
                //exception handling for anything entered thats not y or n - SJ 11/20
                valid = false;
                do{
                    String response = input.nextLine();

                    if (response.equalsIgnoreCase("y")) {
                        fullCart = null;
                        needCart = "yes";
                        valid = true;
                        return needCart;
                    } else if(response.equalsIgnoreCase("n")){
                        System.out.println("Use existing cart");
                        needCart = "no";
                        valid = true;
                        return needCart;
                    }else{
                        System.out.println("Please enter y or n");
                        
                    }
                }while(valid==false);

            } else if (cart != null) {
                //exception handling for anything entered thats not y or n - SJ 11/20
                System.out.println("You already have a cart");
                System.out.print("Do you want a new cart (y/n)? ");
                valid = false;
                do{
                    String response = scan.nextLine();
                    if (response.equalsIgnoreCase("y")) {
                        cart = null;
                        needCart = "yes";
                        return needCart;
                    } else if (response.equalsIgnoreCase("n")){
                        System.out.println("Use existing cart\n");
                        needCart = "no";
                        return needCart;

                    }
                    else{
                        System.out.print("Please enter y or n!");

                    }
                }while(valid != true);
            }
        }
        //input.nextLine();//scanner problem
        return needCart;
    }

        
      //MR added ShoppingCart method 11/9

    /**
     * Method that allows user to get a new Shopping Cart
     * @param con Connection to the database
     * @return Returns Shopping Cart object
     */
    public static ShoppingCartDB getCart(Connection con) {
        CallableStatement ca = null;
        ResultSet rs = null;
        String firstName = "";
        String lastName = "";
        int custID = 0;
        String firstNameCart = "";
        String lastNameCart = "";
        int custIDCart = 0;
        ShoppingCartDB cart = null;
        String active = "active";

        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }


        do {
            
            //still have to put in custID just doesn't show all custID's - SJ 11/20
            System.out.println("What is your customer ID:");
            try {
                custID = input.nextInt();
                input.nextLine();//scanner problem
                ca = con.prepareCall("SELECT custID, firstName, lastName FROM customers WHERE custID=" + custID);
                ca.execute();
                rs = ca.getResultSet();
                while (rs.next()) {
                    firstNameCart = rs.getString(2);
                    lastNameCart = rs.getString(3);
                    custIDCart = Integer.parseInt(rs.getString(1));
                }
            } catch (SQLException ex) {
                //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
                Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("This customer does not exist");
            }//SM added 10/13
            catch (InputMismatchException e) {
                System.out.println("Please use only numbers");
                custIDCart = -1;
                custID = 0;
                input.nextLine();
            }

            //create a new shopping cart object
            if (custIDCart > 0) {
                cart = new ShoppingCartDB(custIDCart, lastNameCart, firstNameCart, active);
                System.out.println("\n****You now have a cart " + firstNameCart + " " + lastNameCart + "****");
                System.out.println("");

            } else {
                if (custID > 0) {
                    System.out.println("This customer does not exist... try again");
                    custIDCart = -1;
                } else {
                    System.out.println("Cart was not created... try again");
                }
            }

        } while (custIDCart < 0);
        return cart;
    }//end of ShoppingCart method  

    
    //MR added Shopping method

    /**
     * Method to allow user to shop with existing Shopping Cart
     * @param con Connection to database
     * @param cart Cart in use but without items
     * @param fullCart Cart in use containing items
     * @return Returns shopping cart with items
     */
    public static ShoppingCartDB shop(Connection con, ShoppingCartDB cart, ShoppingCartDB fullCart) {
        ShoppingCartDB shop;
        
        //if shopping cart is already full
        if(fullCart!=null){
            System.out.println("You already have a full shopping cart.");
            System.out.println("Check out before continuing");
            shop = fullCart;
            return shop;
        }
        
        //continue if shopping cart is empty
        shop = null;
        ArrayList<Item> items = new ArrayList<Item>();
        boolean more = true;
        CallableStatement ca = null;
        ResultSet stores = null;
        String storeID = "";
        //input.nextLine();
        boolean addItem = true;
        ArrayList<String> storeList = new ArrayList<String>();

        if (con == null) {
            System.out.println("\n**Need to create connection before continuing ");

            con = DBUtilities.createConnection();
        }
        if (cart != null) {
            if (cart.getActive() == "active") {
                //get the customer name out of the cart
                String custName = (cart.getFirstName() + " " + cart.getLastName());
                System.out.println("Welcome to our shopping mall " + custName + "\n");

                while (more) {
                    try {
                        //get a list of storeIDs
                        ca = con.prepareCall("SELECT storeID, storeName FROM Store");

                        ca.execute();
                        stores = ca.getResultSet();
                        System.out.println("\nHere are the stores in the mall:");

                        while (stores.next()) {
                            int i = 0;
                            System.out.println(stores.getString(1) + ": "
                                    + stores.getString(2));
                            storeList.add(stores.getString(1));

                        }
                    } catch (SQLException ex) {
                        //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //ask user what storeID to shop in
                    //input.nextLine();//scanner problem

                    boolean storeValid = false;
                    while (!storeValid) {
                        System.out.print("\nSelect a store: ");
                        storeID = input.nextLine().toUpperCase();
                        if (!storeList.contains(storeID)) {
                            System.out.println("Not a valid store... try again:");
                            //storeID = input.nextLine().toUpperCase();
                        } else {
                            storeValid = true;
                        }
                    }

                    //list the items in the store
                    try {
                        ca = con.prepareCall("CALL printItems('" + storeID + "')");

                        ca.execute();
                        ResultSet rs = ca.getResultSet();

                        System.out.println("Items:");
                        while (rs.next()) {
                            System.out.println("     " + rs.getString("display"));
                        }

                    } catch (SQLException e) {
                        System.out.println("problem!");
                    }

                    //ask what item and how many
                    System.out.print("\nSelect item number to add to cart: ");
                    //check that item exists and return quantity available
                    //input.nextLine();//scanner problem
                    String itemID = input.nextLine().toUpperCase();
                    int quantAvail = 0;
                    double price = 0;
                    String itemName = "";
                    int newQuant = 0;

                    //Sj changed == to .equals()
                    while (itemName.equals("")) {
                        try {
                            ca = con.prepareCall("SELECT itemID, itemName,number,price,storeID FROM item WHERE itemID='" + itemID + "'");
                            ca.execute();
                            ResultSet rs = ca.getResultSet();
                            boolean itemExists = false;
                            while (rs.next()) {
                                itemName = rs.getString(2);
                                quantAvail = rs.getInt(3);
                                price = rs.getDouble(4);
                                itemExists = true;
                                addItem = true;
                            }
                            rs.beforeFirst();
                        } catch (SQLException ex) {
                            //Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
                            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (itemName == "") {
                            System.out.println("Item does not exist");
                            addItem = false;
                            System.out.print("Select a different item: ");
                            itemID = input.nextLine();
                        }//end of while

                    }
                    int itemQuantity = 0;
                    //NEED TO CHECK TO SEE IF ITEM ALREADY ADDED TO CART
                    if (items.size() > 0) {
                        for (int i = 0; i < items.size(); i++) {
                            if (items.get(i).getItemID().equalsIgnoreCase(itemID)) {
                                System.out.println("\nItem already exists in your cart");
                                System.out.print("Change the quantity? (y/n) ");
                                String changeQuantity = input.nextLine();
                                if (changeQuantity.equalsIgnoreCase("y")) {

                                    try {
                                        System.out.print("How many to add? ");
                                        newQuant = input.nextInt();
                                        if (newQuant < 0) {
                                            throw new IllegalArgumentException("Quantity must be greater than 0");
                                        }
                                        
                                    } catch (InputMismatchException m) {
                                        System.out.println("Must enter an integer... try again");
                                        input.nextLine();//scanner problem
                                        newQuant = input.nextInt();

                                    } catch (IllegalArgumentException ie) {
                                        System.out.println("Quantity must be greater than 0... Try again");
                                        input.nextLine();//scanner problem
                                        newQuant = input.nextInt();
                                    }

                                    input.nextLine();
                                    int quantity = items.get(i).getNumber();
                                    while (quantity + newQuant > quantAvail) {
                                        System.out.println("Not enough in stock... enter new quantity to add");

                                        newQuant = input.nextInt();
                                        input.nextLine();//scanner problem
                                    }

                                    items.get(i).setNumber(newQuant + quantity);
                                    addItem = false;
                                    System.out.println("Quantity updated");
                                }
                                addItem = false;
                            } else {//if it's not on the list then add it
                                addItem = true;
                            }
                        }
                    }//end of checking if item already on ArrayList
                    if (addItem == true && quantAvail > 0) {
                        System.out.print("How many to add? ");
                        //Sj added loop for validation
                        boolean valid = false;
                        do{
                            try {
                                
                                itemQuantity = input.nextInt();
                                if (itemQuantity <= 0) {
                                    throw new IllegalArgumentException("Quantity must be greater than 0");
                                }
                                //Sj moved while and changed to else if beacuse of inputMismatchException
                                else if(itemQuantity > quantAvail){
                                    System.out.println("There are only " + quantAvail + " in stock");
                                    System.out.print("Choose a quantity that does not exceed stock: ");
                                }
                                else{
                                    valid = true;
                                    break;
                                }
                            } catch (InputMismatchException im) {
                                System.out.println("Must enter an integer... try again");
                                input.next();
                            } catch (IllegalArgumentException ie) {
                                System.out.println("Quantity must be greater than 0... Try again");
                                
                            }
                        }while(valid == false);
                        
                        input.nextLine();//scanner problem
                        Item newItem = new Item(itemName, itemID.toUpperCase(),
                                storeID.toUpperCase(), price, itemQuantity);
                        items.add(newItem);
                        System.out.println("Item added to cart.");
                    } else if (quantAvail==0){
                        System.out.println(quantAvail);
                        System.out.println("\nItem is out of stock\n");
                    }
                    System.out.print("\nWould you like to add more items to your cart (y/n)? ");
                    String response = "";
                    //SJ added validation loop
                    boolean valid = false;
                    do{
                        response = input.nextLine();
                        if (response.equalsIgnoreCase("y")) {
                            more = true;
                            valid = true;
                            break;
                        } else if(response.equalsIgnoreCase("n")) {
                            more = false;
                            valid = true;
                            break;
                        } else{
                            System.out.println("Please enter y or n!");
                        }
                    }while(valid == false);
                }//end of while
                
                System.out.println("\nHere are the items in your cart: ");
                for (int k = 0; k < items.size(); k++) {
                    System.out.println(items.get(k).toStringC());
                }
                String firstName = cart.getFirstName();
                String lastName = cart.getLastName();
                int custID = cart.getCustID();
                String active = "active";

                shop = new ShoppingCartDB(custID, lastName, firstName, items, active);

            } else {
                System.out.println("You must get a new shopping cart before continuing...");
            }
        } else {
            System.out.println("You must get a shopping cart before continuing...");
        }
        return shop;
    }// end of shopping method

    //MR added Checkout method

    /**
     * Method to checkout and add information to the Sales table
     * <br> and simultaneously update quantities on the items table
     * @param con Connection to the database
     * @param fullCart Cart in use containing items
     * @return Returns notification that checkout is complete
     */
    public static String checkout(Connection con, ShoppingCartDB fullCart) {

        CallableStatement ca = null;
        CallableStatement sID = null;
        ResultSet rs = null;
        String response = "";
        int lastSaleID = 0;
        int nextSaleID = 0;
        int saleLineID = 0;
        String checkOutComplete = "";
        try {
            //get last SaleID
            sID = con.prepareCall("SELECT MAX(saleID) AS lastSaleID FROM sale");
            sID.execute();
            rs = sID.getResultSet();
            while (rs.next()) {
                lastSaleID = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        nextSaleID = lastSaleID + 1;
        //don't need to check for connection because this method won't be called if
        //shopping cart is null
        System.out.println("\nHello " + fullCart.getFirstName() + " time to checkout!");

        //check to see if cart is still active
        //SJ changed == to .equals() 11/21
        if (fullCart.getActive().equals("active")) {

            System.out.println("\nHere's a summary of the items currently in your cart:\n");
            //list the items in the shopping cart
            int custID = fullCart.getCustID();
            for (int i = 0; i < fullCart.getItems().size(); i++) {
                System.out.println(fullCart.getItems().get(i).toStringC());
            }
            System.out.print("\nWould you like to continue with checkout? (y/n) ");
            //SJ added validation loop
            boolean valid = false;
            do{
                response = input.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    System.out.println("\nOkay... checkout in progress...");

                    //call transaction to update item quantity on item table and
                    //add sale to the sale table
                    try {
                        for (int i = 0; i < fullCart.getItems().size(); i++) {
                            String itemID = fullCart.getItems().get(i).getItemID();
                            String storeID = fullCart.getItems().get(i).getStoreID();
                            saleLineID = ++saleLineID;
                            int quantity = fullCart.getItems().get(i).getNumber();
                            ca = con.prepareCall("CALL completeSale(" + nextSaleID + ","
                                    + saleLineID + "," + custID + ",'"
                                    + itemID + "','" + storeID + "'," + quantity + ")");
                            ca.execute();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MenuItem.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("\n***Checkout is complete!***\n");
                    checkOutComplete = "yes";
                    fullCart.setActive("inactive");
                    valid = true;
                    break;
                } else if (response.equalsIgnoreCase("n")){
                    System.out.println("Checkout not complete");
                    checkOutComplete = "no";
                    valid = true;
                    MallDriver1.menu();
                } else{
                    System.out.println("Please enter y or n!");
                }
            }while(valid == false);
        } else {
            System.out.println("\nThis cart has already been checked out");
        }
        return checkOutComplete;
    }

    /**
     * Method to query database and list sales added to Sales table
     * @param con Connection to the database
     */
    public static void listSales1(Connection con) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        if (con == null) {
            con = DBUtilities.createConnection();
        }
        CallableStatement ca = null;
        ResultSet rs = null;
        int saleID = 0;
        int nextSaleID = 0;
        int saleLineID = 0;
        int custID = 0;
        String firstName = "";
        String lastName = "";
        String itemID = "";
        String itemName = "";
        String storeID = "";
        double price = 0.0;
        int numSold = 0;

        try {
            ca = con.prepareCall("SELECT * FROM saleSummary");
            ca.execute();
            rs = ca.getResultSet();
            while (rs.next()) {
                nextSaleID = saleID + 1;
                saleID = rs.getInt(1);

                saleLineID = rs.getInt(2);
                custID = rs.getInt(3);
                firstName = rs.getString(4);
                lastName = rs.getString(5);
                itemID = rs.getString(6);
                itemName = rs.getString(7);
                storeID = rs.getString(8);
                price = rs.getDouble(9);
                numSold = rs.getInt(10);

                if (nextSaleID == saleID) {
                    System.out.println("\nSaleID: " + saleID + " for CustID: " + custID + " " + firstName + " " + lastName);
                }
                System.out.println("-->" + itemID + ": " + itemName + " from store " + storeID
                        + " cost " + nf.format(price) + " and " + numSold + " where sold.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MenuMethods.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (saleID == 0) {
            System.out.println("There are no sales");
        }

    }

}
