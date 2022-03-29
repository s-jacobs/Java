
package ATM;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Sara
 */
public class ServerCust {
    public static Scanner scan = new Scanner(System.in);
    
    
    public static void custMenu(int id, ArrayList<Customer> c, ArrayList<Account> a, ArrayList<Bills> b){
        
        boolean cont = true;
        int choice = 0;
        boolean valid = true;
        
        do{
            for(int i=0;i<c.size();i++){
                if(c.get(i).getId() == id){
                    // Print out customer menu
                    System.out.println("\n\nWelcome " + c.get(i).getName() + "! What would you like to do?"
                            + "\n\n1.  View your balance"
                            + "\n2.  Withdraw money"
                            + "\n3.  Deposit money"
                            + "\n4.  Transfer money between your checking and savings accounts"
                            + "\n5.  Transfer money from your checking account to another checking account"
                            + "\n6.  Logout");
                    System.out.print("CHOICE:  ");
                } // END IF STATEMENT 
            } // END FOR LOOP TO GET CUSTOMER NAME
            
            //VALIDATE menu choice input
            do{
                try{
                    choice = scan.nextInt();

                    if(choice <1 || choice >6){
                        System.out.print("\nError! Please enter the whole number in front of the menu option you would like to select (1-6)\nCHOICE:  ");
                        valid = false;
                    }else{
                        valid = true;
                        System.out.println("\n");
                    }                  
                    
                    
                    switch(choice){
                        case 1:
                            printBal(id, a, 1);
                            break;
                        case 2:
                            withdraw(id, a, b);
                            break;
                        case 3:
                            deposit(id, a);
                            break;
                        case 4:
                            transBtwOne(a, id);
                            break;
                        case 5:
                            transBtwTwo(a, id);
                            break;
                        case 6:
                            System.out.println("You are officially logged out.\nThank you for banking with us! Have a nice day!\n\n");
                            cont = false;
                            break;
                        default:

                    }// end switch

                // catch Input Mismatch Exceptions and ask for user choice again
                }catch(InputMismatchException e){
                    System.out.print("\nError! Please enter the whole number in front of the menu option you would like to select (1-6)\nCHOICE:  ");
                    scan.next();
                    valid = false;
                }
            }while(valid == false);
        }while(cont == true);
        
        
    }// END custMenu()
    
    
    /*
    1. if regular print out bal
    2. if printing out after change
    Your account has been updated
    Your balance is now...
    */
    public static void printBal(int id, ArrayList<Account> a, int type){
        if(type==1){
            for(int i=0;i<a.size();i++){
                if(a.get(i).getCust().getId() == id){
                    System.out.println("Your checking account balance is: " + a.get(i).getcBal() + "\nYour savings account balance is: " + a.get(i).getsBal());
                }
            }
        }else if(type==2){
            for(int i=0;i<a.size();i++){
                if(a.get(i).getCust().getId() == id){
                    System.out.println("\nYour account has been updated.\nYour checking account balance is: " + a.get(i).getcBal() + "\nYour savings account balance is: " + a.get(i).getsBal());
                }
            }
        }
        
        
        //System.out.println("This is PRINT BALANCE");
    }// end printBal()
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Withdraw funds from account
    public static void withdraw(int id, ArrayList<Account> a, ArrayList<Bills> b){
        printBal(id, a, 1);
        int choice = 0;
        int amtChoice = 0;
        int amt = 0;
        boolean valid = true;
        boolean validChoice = true;
        
        
        // VALIDATE user input for choice
        do{
            System.out.print("\nWhich account do you want to withdraw from?\n\t1.  Checkinng\n\t2.  Savings\n\t3.  Back to menu\nCHOICE:  ");

            // --------------------------------VALIDATE
            try{
                choice = scan.nextInt();
                
                if(choice<1 || choice >3){
                    System.out.println("Error! Please enter either 1 or 2 or 3.\nTry again...");
                    valid = false;
                }else{
                    valid = true;
                }
            }catch(InputMismatchException e){
                System.out.println("Error! Please enter either 1 or 2 or 3.\nTry again...");
                scan.next();
                valid = false;
            }
        }while(valid == false);
        
        // VALIDATE withdraw amount  -  possitive number
        if(choice!=3){
        validChoice = true;
               
          
            do{  //while validCoice == false
                System.out.print("\nHow much do you want to withdraw:"
                        + "\n\t1.  $10\n\t2.  $20\n\t3.  $50\n\t4.  $100\n\t5.  Cancel\n\nCHOICE:  ");

                try{
                    amtChoice = scan.nextInt();

                    if(amtChoice<1 || amtChoice>5){
                        System.out.println("\nError! Please enter a number 1-5.\nTry again...");
                        validChoice = false;
                    }else{
                        System.out.println(amtChoice);
                        validChoice = true;
                    }

                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter a number 1-5.\nTry again...");
                    scan.next();
                    validChoice = false;
                }
            }while(validChoice == false);

            boolean bills = true;
            boolean acctValid = true;
            
            
            // GET WD AMT
            // CHECK bills in ATM
            switch(amtChoice){
                case 1:
                    amt = 10;
                    int fiveBills = b.get(3).getNumber();
                    
                    if(fiveBills<2){
                        System.out.println("Error! Not enought money in ATM. Please see teller inside for funds.\nAccount has not been updated.");
                        bills = false;
                    }else{
                        acctValid = withdrawFromAcct(a, amt, choice, id);
                        if(acctValid == true){
                            b.get(3).setNumber(fiveBills-2);
                        }
                    }

                    break;
                case 2:
                    amt = 20;

                    // alternative way to give bills if 20 not available  (4 five dillar bills)
                    //int alt = b.get(3).getNumber();
                    int twBill = b.get(2).getNumber();

                    // if the number of bills is less than 2 display error
                    // else subtract amt from cust acct and ATM
                    if(twBill<1){
                        System.out.println("Error! Not enough money in ATM. Please see teller inside for funds.\nAccount not updated.");
                  
                    }else{//if num 20 bills is >= 1
                        acctValid = withdrawFromAcct(a, amt, choice, id);
                        if(acctValid == true){

                            b.get(2).setNumber(twBill-1);
                        }
                    }


                    break;
                case 3:
                    amt = 50;

                    int fifBill = b.get(1).getNumber();

                    // if num 50 bills is less than 1 display error
                    // else subtract amt from cust acct and ATM
                    if(b.get(1).getNumber() <1){
                        System.out.println("Error! Not enough money in ATM. Please see teller inside for funds.\nAccount not updated.");

                    }else{
                        acctValid = withdrawFromAcct(a, amt, choice, id);
                        if(acctValid == true){
                            b.get(1).setNumber(fifBill - 1);
                        }
                    }



                    break;
                case 4:
                    amt = 100;
                    int hunBill = b.get(0).getNumber();

                    if(hunBill < 1){
                        System.out.println("Error! Not enough movey in ATM. Please see teller inside for funds.\nAccount not updated.");
                    }else{
                        acctValid = withdrawFromAcct(a, amt, choice, id);
                        if(acctValid == true){
                            b.get(0).setNumber(hunBill-1);
                        }
                    }

                    break;
            }// END SWITCH 

            //Break out of loop to cancel
            if(amtChoice == 5){
                System.out.println("\nReturning to main menu...\n\n");
            }else{
                if(bills == false){
                    printBal(id, a, 1);
                }else{
                    printBal(id, a, 2);
                }
            }
                
        }//END if choice not 3
    
        
        //System.out.println("THIS IS WITHDRAW");
    }// END withdraw
    
    
    
    
    
    public static boolean withdrawFromAcct(ArrayList<Account> a, int amt, int choice, int id){
        boolean valid = true;
        // Withdraw from checking
            if(choice == 1){

                // Check amt in acct
                for(int i=0;i<a.size();i++){
                    // Get acct info based on cust ID
                    if(a.get(i).getCust().getId() == id){
                        double oldBal = a.get(i).getcBal();

                        // Check amt < bal in acct
                        if(amt<oldBal){
                            double newBal = oldBal - amt;
                            a.get(i).setcBal(newBal);
                            valid = true;
                        }else{
                            System.out.println("Error! You do not have enough money in your account.\n");
                            valid = false;
                        }
                    }//END if
                }//END for

            // Withdraw from savings
            }else if (choice == 2){
                // Check amt in acct
                for(int i=0;i<a.size();i++){

                    if(a.get(i).getCust().getId() == id){
                        double oldBal = a.get(i).getsBal();

                        // Check amt < bal in acct
                        if(amt<oldBal){
                            double newBal = oldBal - amt;
                            a.get(i).setsBal(newBal);
                            valid = true;
                        }else{
                            System.out.println("Error! You do not have enough money in your account.");
                            valid = false;
                        }
                    }//END if
                }//END for
            } // END IF/ELSE
        
        return valid;
    }
    
    
    
    
    
    // Deposit money to account
    public static void deposit(int id, ArrayList<Account> a){
        printBal(id,a, 1);
        int choice = 0;
        double amt = 0;
        boolean cancel = false;
        boolean valid = true;
        
        // DEPOSITING A CHECK OR CASH
        // --------------------------------VALIDATE
        do{
            System.out.print("\nWhich account do you want to deposit to?\n\t1.  Checkinng\n\t2.  Savings\n\t3.  Back to menu\n\nCHOICE:  ");
            try{
                choice = scan.nextInt();
                
                if(choice<1 || choice>3){
                    System.out.println("Error! Please enter a number greater than 0.\nTry again...");
                    
                    valid = false;
                }else{
                    if(choice == 3){
                        cancel = true;
                    }
                    valid = true;
                }
                
            }catch(InputMismatchException e){
                System.out.println("Error! Please enter a number greater than 0.\nTry again...");
                scan.next();
                valid = false;
            }
        }while(valid == false);
        
        do{
            if(cancel == true){
                break;
            }
            try{
                    System.out.print("\nEnter amount to deposit:  ");

                    // --------------------------------VALIDATE
                    amt = scan.nextDouble();
                    
                    if(amt<0){
                        System.out.println("Error! Please enter a number greater than 0.\nTry again...");
                        valid = false;
                    }else{
                        valid = true;
                    }
            }catch(InputMismatchException e){
                System.out.println("Error! Please enter a number greater than 0.\nTry again...");
                scan.next();
                valid = false;
            }
        }while(valid == false);
        
        
        // Deposit to checking
        if(choice == 1){
            
            for(int i=0;i<a.size();i++){
                if(a.get(i).getCust().getId() == id){
                    double newBal = a.get(i).getcBal() + amt;
                    a.get(i).setcBal(newBal);
                    printBal(id, a, 2);
                }//END if
            }//END for
            
        // Deposit to savings
        }else if (choice == 2){
            for(int i=0;i<a.size();i++){
                if(a.get(i).getCust().getId() == id){
                    double newBal = a.get(i).getsBal() + amt;
                    a.get(i).setsBal(newBal);
                    printBal(id, a, 2);
                }//END if
            }//END for
        }
        
        /*
        
        add thing to create a new printBal
        1. if regular print out bal
        2. if printing out after change
            Your account has been updated
            Your balance is now...
        */
        
        //System.out.println("THIS IS DEPOSIT");
    }// END deposit()
    
    
    
    
    
    
    
    
    
    
    public static void transBtwOne(ArrayList<Account> a, int id){
        boolean valid = true;
        int choice = 0;
        printBal(id, a, 1);
        
        do{
            try{
                System.out.print("Which account would you like to transfer from:\n\t1.  Checking\n\t2.  Savings\n\t3.  Cancel\nCHOICE:  ");
                choice = scan.nextInt();
                
                if(choice <1 || choice>3){
                    System.out.println("\nError! Please enter a number 1-3.\nTry again...");
                    valid = false;
                }else{
                    valid = true;
                }
            }catch(InputMismatchException e){
                System.out.println("\nError! Please enter a number 1-3.\nTry again...");
                scan.next();
                valid = false;
            }
        }while(valid == false);
        
        
        double trsAmt = 0;
        boolean amtValid = true;
        do{
            if(choice == 3){
                break;
            }
            try{
                System.out.print("\nEnter the transfer amount:  ");
                trsAmt = scan.nextDouble();
                
                if(trsAmt <=0 && trsAmt != -999){
                    System.out.println("\nError! Please enter a positive number greater than 0.\nTry again...");
                    amtValid = false;
                }else{
                    for(int i=0;i<a.size();i++){
                        if(a.get(i).getCust().getId() == id){
                            // If transfer from checking to savings
                            if(choice == 1){
                                double oldBal = a.get(i).getcBal();

                                // VALIDATE enough money in acct
                                if(oldBal < trsAmt){
                                    System.out.println("\nError! You don't have enough money in your account.\n\t1.  Enter new amount\n\t2.  Cancel");
                                    amtValid = false;
                                }else{
                                    // sub trsAmt from checking
                                    a.get(i).setcBal(oldBal-trsAmt);

                                    // add trsAmt to savings
                                    a.get(i).setsBal(a.get(i).getsBal()+trsAmt);
                                    printBal(id, a, 2);
                                    amtValid = true;
                                }// END IF/ELSE


                                //END IF CHOICE = 1
                            // If transfer from savings to checking
                            }else if(choice == 2){ 
                                double oldBal = a.get(i).getsBal();

                                // VALIDATE enough money in acct
                                if(oldBal < trsAmt){
                                    System.out.println("\nError! You don't have enough money in your account.\n\t1.  Enter new amount\n\t2.  Cancel");
                                    amtValid = false;
                                }else{
                                    // sub trsAmt from checking
                                    a.get(i).setsBal(oldBal-trsAmt);

                                    // add trsAmt to savings
                                    a.get(i).setcBal(a.get(i).getcBal()+trsAmt);
                                    amtValid = true;

                                    printBal(id, a, 2);

                                    break;
                                }// END IF/ELSE
                            } // END choice = 2
                        }// END get acct info based on cust id
                    }// END FOR
                } 
            }catch(InputMismatchException e){
                System.out.println("\nError! Please enter a positive number greater than 0.\n Try again...");
                scan.next();
                amtValid = false;
            }
        }while(amtValid == false);
        
        //System.out.println("THIS IS TRANSFER BETWEEN S AND C");
    }// END transBtwOne()
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void transBtwTwo(ArrayList<Account> a, int id){
        boolean valid = true;
        double amt = 0;
        int otherAcct = 0;
        int acct = 0;
        //String otherUser = "";
        //String otherPass = "";
        boolean cancel = false;
        int where = 0;
        do{
            try{
                System.out.print("Enter the account number you would like to transfer to or -999 to cancel:  ");
                otherAcct = scan.nextInt();
                scan.nextLine();
                
                // GET acct num of user to VALIDATE not trs to own acct
                for(int j=0;j<a.size();j++){
                    if(a.get(j).getCust().getId() == id){
                        acct = j;
                    }
                }
                
                boolean found = false;
                if(otherAcct == -999){
                    cancel = true;
                    break;
                }else if(a.get(acct).getAcctNum() == otherAcct){
                    System.out.println("\nError! You entered your own account number."
                            + "\nIf you would like to transfer between your savings and checking, cancel and choose number 4 on the menu."
                            + "\nOr enter a different account number.\n");
                    valid = false;
                }else{
                    // VALIDATE acct exists
                    for(int i=0;i<a.size();i++){
                        if(a.get(i).getAcctNum() == otherAcct){
                            where = i;
                            found = true;
                            //System.out.println("Account Valid\nUser:  " + a.get(i).getUser() + "\nPass:  " + a.get(i).getPass());
                            valid = true;
                            break;
                        }else{
                            found = false;
                        }
                    }// END FOR
                    if(found == false){
                        System.out.println("\nError! That account number doesn't exists.\nTry again...\n");
                            valid = false;
                    }
                }//END IF/ELSE not -999

            }catch(InputMismatchException e){
                System.out.println("\nError! Please enter a whole number greater than 0 or -999 to cancel.\nTry again... \n");
                scan.next();
                valid = false;
            }
        }while(valid == false);
        
        
        if(cancel == false){
            /*boolean userValid = true;
            do{
                // VALITDATE user and pass of second acct
                
                System.out.println("\nEnter the username for this account (or -999 to cancel):  ");
                otherUser = scan.nextLine();

                if(otherUser.equals("-999")){
                    cancel = true;
                    break;
                }

                System.out.println("Enter the password for this account (or -999 to cancel:");
                otherPass = scan.nextLine();
                if(otherPass.equals("-999")){
                    break;
                }


                if(a.get(where).getUser().equals(otherUser) && a.get(where).getPass().equals(otherPass)){

                    userValid = true;
                }else{
                    System.out.println("\n\nError! That username or password is incorrect. Try again...");
                    userValid = false;
                }// END IF/ELSE check user valid
            }while(userValid == false);*/

            if(cancel == false){
                double oldBal = 0;
                int i = 0;
                boolean amtValid = true;
                do{
                    try{
                        System.out.print("\nEnter transfer amount (or -999 to cancel):  ");
                        amt = scan.nextDouble(); 

                        if(amt == -999){
                            System.out.println("\nReturning to main menu...");
                            cancel = false;
                            break;
                        }

                        // VALIDATE not negative
                        if(amt<0){
                            System.out.println("\nError! Please enter a number greater than 0.\nTry again...");
                            amtValid = false;
                        }else{
                            int conf = 0;
                            boolean confValid = true;
                            
                            for(i=0;i<a.size();i++){
                                if(a.get(i).getCust().getId() == id){
                                    oldBal = a.get(i).getcBal();

                                    //VALIDATE enough money in acct
                                    if(oldBal<amt){
                                        System.out.println("\nError! You don't have enough money in your account.\nTry again...");
                                        amtValid = false;
                                    }else{
                                        OUTER:
                                        do {
                                            NumberFormat nf = NumberFormat.getCurrencyInstance();
                                            System.out.print("\nTransfer " + nf.format(amt) + " to account number " + otherAcct + "\n\t1.  Confirm\n\t2.  Cancel\nCHOICE:");
                                            try {
                                                conf = scan.nextInt();
                                                switch (conf) {
                                                    case 1:
                                                        // SUBTRACT from user account
                                                        a.get(i).setcBal(oldBal-amt);
                                                        // ADD to second user account
                                                        a.get(where).setcBal(a.get(where).getcBal()+amt);
                                                        printBal(id, a, 2);
                                                        amtValid = true;
                                                        break OUTER;
                                                    case 2:
                                                        System.out.println("\nCanceling...\nAccount has not been updated.");
                                                        confValid = true;
                                                        break OUTER;
                                                    default:
                                                        System.out.println("\nError! Please enter a whole number 1 or 2.\nTry again...");
                                                        confValid = false;
                                                        break OUTER;
                                                }
                                            }catch(InputMismatchException e){
                                                System.out.println("\nError! Please enter a while number (1 or 2).\nTry again...");
                                                scan.next();
                                                confValid = false;
                                            }
                                        } while (confValid == false);
                                    }// END IF/ELSE VALIDATE enough money
                                }// END IF get account
                            }//END FOR
                        }//END IF/ELSE   VALIDATE not neg
                    }catch(InputMismatchException e){
                        System.out.println("Error! Please enter a number greater than 0\nTry again...");
                        scan.next();
                        amtValid = false;
                    }// END TRY/CATCH transfer amt

                }while(amtValid == false);
            }// END SECOND IF cancel == false
        }// END IF cancel == false
        
        

        
        
        //System.out.println("THIS IS TRANSFER BETWEEN TWO CHECKING");
    }// END transBtwTow()
}// END ServerCust CLASS
