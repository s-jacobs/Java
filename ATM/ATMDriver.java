
package ATM;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Sara
 */
public class ATMDriver {

    public static Scanner scan = new Scanner(System.in);
    /**
     *
     * @param args
     */
    public static void main(String[] args){
        ArrayList<Customer> cust = new ArrayList<Customer>();
        ArrayList<Operator> oper = new ArrayList<Operator>();
        ArrayList<Account> acct = new ArrayList<Account>();
        ArrayList<Bills> bills = new ArrayList<Bills>();
        
        cust = loadCust(cust);
        acct = loadAcct(acct, cust);
        oper = loadOper(oper);
        bills = loadBills(bills);
        
        
        //  ADD LOOP TO CONTINUE AFTER LOGOUT
        int who = 0;
        boolean valid = true;
        boolean cont = true;
        int id = -1;
        do{
            System.out.println("Hello! Welcome to this bank!\nPress:\n\t1. For Customers\n\t2. For Operators");
            System.out.print("CHOICE:  ");

            // ------------------ VALIDATE
            do{
                try{
                    who = scan.nextInt();
                    if(who <1 || who>2){
                        System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                        System.out.println("\nPress:\n\t1. For Customers\n\t2. For Operators");
                        System.out.print("CHOICE:  ");
                        valid = false;
                    }else{
                        valid = true;
                    }
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                    System.out.println("\nPress:\n\t1. For Customers\n\t2. For Operators");
                    System.out.print("CHOICE:  ");
                    scan.next();
                    valid = false;
                }
            }while(valid == false);

            
            switch (who) {
                case 1:
                    //CUST LOGIN
                    scan.nextLine();
                    id = custLogin(acct);
                    if(id!=0 && id!=-999){
                        ServerCust.custMenu(id, cust, acct, bills);
                    }   break;
                case 2:
                    //System.out.println("\n\nOperator");
                    scan.nextLine();
                    id = operatorLogin(oper);
                    if(id!=0 && id!=-999){
                        cont = ServerOper.operMenu(id, oper, bills, cont);
                    }
                    break;
            }//END SWITCH
        }while(cont != false);
    } // END MAIN()
    
    
    
    public static int custLogin(ArrayList<Account> a){
        
        int id=0;
        boolean found = false;
        
        do{
            System.out.println("\nEnter your username (or -999 to return to the main menu): ");
            String user = scan.nextLine();

            // Check if user wants to go back
            if(user.equals("-999")){
                break;
            }
            
            System.out.println("Enter your password: ");
            String pass = scan.nextLine();

            
            // Validate username and password
            // If found get the customer ID
            // Set variable found to true to get out of doWhile loop
            for(int i=0;i<a.size();i++){
                if(( a.get(i).getUser().equals(user)) && a.get(i).getPass().equals(pass)){
                    id = a.get(i).getCust().getId();
                    found = true;
                }
            } // end for 
            
            // If username and password doesn't match, inform user and ask for it again
            // If user wants to go back to the first menu enter -999
            if(found==false){
                System.out.println("\nThat username or password doesn't exist.\nPlease try again or enter -999 to exit:");
            }
        }while(found==false);
        
        //return customer ID or 0
        return id;
        
    }// END custLogin()
    
    public static int operatorLogin(ArrayList<Operator> o){
        boolean found = false;
        String user = "";
        String pass = "";
        int id = 0;
        
        do{
            System.out.println("\nEnter your username (or -999 to cancel):  ");
            user = scan.nextLine();
            // Check if user wants to go back
            if(user.equals("-999")){
                break;
            }
            
            System.out.println("Enter your password (or -999 to cancel):");
            pass = scan.nextLine();
            // Check if user wants to go back
            if(pass.equals("-999")){
                break;
            }
            
            for(int i=0;i<o.size();i++){
                if(o.get(i).getUser().equals(user) && o.get(i).getPass().equals(pass)){
                    id = o.get(i).getId();
                    found = true;
                }
            }// END FOR
            
            if(found == false){
                System.out.println("\nError! Username or password is incorrect.\nTry again (or enter -999 to cancel)...");
            }
            
        }while(found == false);
        
    return id;
    }// END operatorLogin()
    
    public static ArrayList<Bills> loadBills(ArrayList<Bills> b){
        b.add(new Bills(100, 100));
        b.add(new Bills(50, 100));
        b.add(new Bills(20, 100));
        b.add(new Bills(5, 100));
        return b;
    }
    
    
    
    public static ArrayList<Customer> loadCust(ArrayList<Customer> c){
        c.add(new Customer("John Doe"));
        c.add(new Customer("Jane Doe"));
        c.add(new Customer("Paul Jones"));
        c.add(new Customer("Mike Jones"));
        c.add(new Customer("Peter Thomas"));
        c.add(new Customer("June Kelly"));
        c.add(new Customer("Cooper Nelson"));
        c.add(new Customer("Roger Nelson"));
        c.add(new Customer("Joseph Jackson"));
        c.add(new Customer("Elizabeth Houston"));        
        
        return c;
    }// END loadCust()
    
    
    public static ArrayList<Account> loadAcct(ArrayList<Account> a, ArrayList<Customer> c){
        //ACCT NUM - 1000
        a.add(new Account(c.get(0), "JoDoe", "pass", 15000, 6000));
        a.add(new Account(c.get(1), "jadoe", "1234", 20000, 10000));
        a.add(new Account(c.get(2), "pjones", "2345", 10000, 1000));
        a.add(new Account(c.get(3), "mikejones", "3456", 10000, 5000));
        a.add(new Account(c.get(4), "pthomas", "4567", 12000, 7000));
        a.add(new Account(c.get(5), "jKelly", "5678", 40000, 6000));
        a.add(new Account(c.get(6), "coopNelson", "6789", 5000, 450));
        a.add(new Account(c.get(7), "rnelson", "7891", 13000, 8000));
        a.add(new Account(c.get(8), "joeJack", "8910", 50000, 8000));
        a.add(new Account(c.get(9), "lizhouston", "9101", 70000, 7000));
        
        return a;
    }// END loadAcct
    
    
    public static ArrayList<Operator> loadOper(ArrayList<Operator> o){
        o.add(new Operator("Richard Bobby", "admin", "pass"));
        o.add(new Operator("Sandy Patrick", "oper", "1234"));
        
        
        return o;
    }// END loadOper
}// END class  ATMDriver
