package mall;

import java.util.Scanner;
import beans.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Driver for Menus available to the Customer
 * <br> as well as Menus available to Administrator
 * @author M Royal and S Jacobs
 */
public class MallDriver1 {
    /**
     * Connection to database used as needed in menu items
     */
    private static Connection con;
    /**
     * Scanner object used throughout methods of MallDriver1 class
     */
    private static Scanner scan = new Scanner(System.in);
    
    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        
        MenuItem mi = new MenuItem();
        ShoppingCartDB cart = null;
        
        ShoppingCartDB fullCart = null;
        String response = "";
        String exitResponse = "";
        String needCart = "";
        String checkOutComplete = "";
        Scanner scan = new Scanner(System.in);
        int choice = 0;
        while (choice >= 0) {
            try{
            choice = menu();
            }catch(InputMismatchException e){
                choice = 0;
                
            }
            
            if (choice == 1) {                                                  //connect to database
                con = DBUtilities.createConnection();
                System.out.println("\nConnection successful\n");
            }
            
            else if(choice == 2){                                     //admin login
                con = DBUtilities.checkConnect(con);
                int adminChoice = 0;
                boolean validLogin = false;
                 while (adminChoice >= 0) {
                    try{
                        if (validLogin != true){
                            validLogin = MenuItem.adminLogin(con);
                        }
                        adminChoice = adminMenu(validLogin);
                   }catch(InputMismatchException e){
                        adminChoice = 0;

                    }
                    if(adminChoice == 1){
                        con = DBUtilities.checkConnect(con);
                        mi.printInfo(con);
                    }
                    else if (adminChoice == 2) { //addItem
                        con = DBUtilities.checkConnect(con);
                        String storeID = null;
                        mi.addItem(con, storeID);
                    }  else if(adminChoice ==3){ //update
                        con = DBUtilities.checkConnect(con);
                        mi.updateItem(con);
                    } else if(adminChoice == 4){ //addStore
                        con = DBUtilities.checkConnect(con);
                        mi.addStore(con);
                    }else if (adminChoice == 5) { //listSales
                        con = DBUtilities.checkConnect(con);
                        mi.listSales1(con);
                    }
                    else if(adminChoice == 6){
                        con = DBUtilities.checkConnect(con);
                        mi.addAdmin(con);
                    }
                    else if(adminChoice ==7){
                        System.out.println("\nYou are now logged out\nReturning to main menu...\n\n");
                        break;
                    }
                    else if(adminChoice == -1){
                        break;
                    }
                }
                 
                 
            }else if (choice == 3) {                                             //add Customer
                con = DBUtilities.checkConnect(con);
                mi.addCustomer(con);
            }else if (choice == 4) {                                             //print into
                //SJ fixed checkConnect
                con = DBUtilities.checkConnect(con);
                mi.printStore(con);
            }else if (choice == 5) {                                             //search item
                con = DBUtilities.checkConnect(con);
                mi.searchItem(con);
            } else if (choice == 6) {                                             //get cart
                con = DBUtilities.checkConnect(con);
                needCart = MenuItem.checkCart(con, cart, fullCart);
                if (needCart.equals("yes")) {
                    cart = MenuItem.getCart(con);//MR added 11/9
                    fullCart = null;
                }
            }else if (choice == 7) {                                             //shop
                con = DBUtilities.checkConnect(con);
                fullCart = MenuItem.shop(con, cart, fullCart);
                cart = null;//remove original empty cart
            }else if (choice == 8) {                                             //checkout
                con = DBUtilities.checkConnect(con);
                if (fullCart == null) {
                    System.out.println("Your shopping cart is empty");
                } else {
                    checkOutComplete = MenuItem.checkout(con, fullCart);
                    if(checkOutComplete.equals("yes"))
                        fullCart = null;//empty the cart after checkout
                }
            }else if (choice == 9) {                                             //exit
                if (fullCart != null && fullCart.getActive().equals("active")) {
                    System.out.println("Wait! You still have a shopping cart with items!");
                    System.out.print("Are you sure you want to exit? You will lose your cart...(y/n) ");
                    //Sj added validation loop
                    boolean valid = false;
                    do{
                        exitResponse = scan.nextLine();
                        if(!exitResponse.equalsIgnoreCase("y") && !exitResponse.equalsIgnoreCase("n")){
                            System.out.println("Please only enter y or n");
                        }
                        else{
                            valid = true;
                            break;
                        }
                    }while(valid == false);
                } else {
                    try {//MR added to close database connection
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MallDriver1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Bye!!!!!");
                    System.exit(0);
                    choice = -1;
                }
                if (exitResponse.equalsIgnoreCase("y")) {
                    try {//MR added to close database connection
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(MallDriver1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println("Bye!!!!!");
                    System.exit(0);
                    choice = -1;
                }
            }
        }  // end while
    }

    /**
     * Method to display Main Menu
     * @return Returns the users menu choice
     */
    public static int menu() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nChoice:");
        System.out.println("   1.  Create a connection to the database");
        System.out.println("   2.  Admin log in");
        System.out.println("   3.  Add customer");
        System.out.println("   4.  Print store data");
        System.out.println("   5.  Search for an item");
        System.out.println("   6.  Get a shopping cart");
        System.out.println("   7.  Shop");
        System.out.println("   8.  Check out ");
        System.out.println("   9.  QUIT  ");
        System.out.println("\n CHOICE:");

        boolean validMenu = false;
        int ans = 0;
        while(validMenu ==false){
            try{
                ans = scan.nextInt();
                if(ans>0&&ans<10){
                    validMenu = true;
                    break;
                }
                else{
                    System.out.println("\nYour choices are 1 through 9 only\nTry again");
                    //SJ changed to false
                    validMenu = false;

                }
            }catch(InputMismatchException e){
                System.out.println("\nPlease only enter numbers between 1 and 9");
                scan.next();
            }      
        }
        return ans;
    }
    
    /**
     * Method that displays Administrator's Menu
     * @param validLogin Administrator login details
     * @return Returns Administrator's menu choice
     */
    public static int adminMenu(boolean validLogin){
        int ans = 0;

        if(validLogin == true){
            Scanner scan = new Scanner(System.in);
            System.out.println("\nChoice:");
            System.out.println("   1.  View mall info");
            System.out.println("   2.  Add an item into inventory ");
            System.out.println("   3.  Update an item");
            System.out.println("   4.  Add a store");
            System.out.println("   5.  List all sales ");
            System.out.println("   6.  Add admin");
            System.out.println("   7.  Log out and return to main menu");
            System.out.println("\n CHOICE:");

            boolean validMenu = false;
            
            while(validMenu ==false){
                try{
                    ans = scan.nextInt();
                    if(ans>0&&ans<8){
                        validMenu = true;
                        break;
                    }
                    else{
                        System.out.println("\nYour choices are 1 through 7 only\nTry again");
                        //SJ changed to false
                        validMenu = false;

                    }
                }catch(InputMismatchException e){
                    System.out.println("\nPlease only enter numbers between 1 and 7");
                    scan.next();
                }      
            }//while
            
        }// if valid
        else{
            ans = -1;
        }
        return ans;
    }//end adminMenu
}
