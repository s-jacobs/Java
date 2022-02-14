
package ATM;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Sara
 */
public class ServerOper {
    public static Scanner scan = new Scanner(System.in);
    public static boolean operMenu(int id, ArrayList<Operator> o, ArrayList<Bills> b, boolean c){
        boolean valid = false;
        int choice = 0;
        boolean cont = true;
        do{
            

            do{
                try{
                    for(int i=0;i<o.size();i++){
                        if(o.get(i).getId() == id){
                            System.out.print("\nWelcome " + o.get(i).getName() + "! What would you like to do?"
                                    + "\n\t1.  Display number of bills"
                                    + "\n\t2.  Add bills to ATM"
                                    + "\n\t3.  Remove bills from ATM"
                                    + "\n\t4.  Shutdown ATM"
                                    + "\n\t5.  Logout\nCHOICE:  ");
                        }
                    }//END FOR get operator
                    choice = scan.nextInt();

                    if(choice<1 || choice>5){
                        System.out.println("\nError! Please enter a whole number 1-5.\nTry again...\n");
                        valid = false;
                    }else{
                        valid = true;
                    }

                    switch(choice){
                        case 1:
                            displayBills(b);
                            break;
                        case 2:
                            addBills(b);
                            break;
                        case 3:
                            removeBills(b);
                            break;
                        case 4:
                            boolean inValid = false;
                            int sure = 0;
                            OUTER:
                            do {
                                try {
                                    System.out.print("\n\nAre you sure you want to logout and shutdown the ATM?\n\t1. Shutdown\n\t2. Cancel\nCHOICE:  ");
                                    sure = scan.nextInt();
                                    switch (sure) {
                                        case 1:
                                            System.out.println("\nYou are offically logged out.\n\n");
                                            cont = false;
                                            c = false;
                                            inValid = true;
                                            System.out.println("Shutting ATM down...");
                                            break;
                                        case 2:
                                            System.out.println("\nCanceling...\n");
                                            cont = true;
                                            c=true;
                                            inValid = true;
                                            break OUTER;
                                        default:
                                            System.out.println("Error! Please enter 1 or 2.\nTry again...");
                                            inValid = false;
                                            break;
                                    }//END SWITCH
                                }catch(InputMismatchException e){
                                    System.out.println("Error! Please enter 1 or 2.\nTry again...");
                                    inValid = false;
                                    scan.next();
                                }
                            } while (inValid == false);
                            break;
                        case 5:
                            System.out.println("\nYou are offically logged out.\n\n");
                            c = true;
                            cont = false;
                            break;
                    }// END SWITCH
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter a whole number 1-5.\nTry again...");
                    scan.next();
                    valid = false;
                }
            }while(valid == false);
        }while(cont == true);
        
        return c;
    }// END operMenu
    
    public static void displayBills(ArrayList<Bills> b){
        //System.out.println("display bills");
        for(int i=0;i<b.size();i++){
            switch (b.get(i).getValue()) {
                case 100:
                    System.out.println("Value: " + b.get(i).getValue() + "  -  Number: " + b.get(i).getNumber());
                    break;
                case 50:
                case 20:
                    System.out.println("Value: " + b.get(i).getValue() + "   -  Number: " + b.get(i).getNumber());
                    break;
                default:
                    System.out.println("Value: " + b.get(i).getValue() + "    -  Number: " + b.get(i).getNumber());
                    break;
            }
        }
    }
    
    public static void addBills(ArrayList<Bills> b){
        boolean valid = false;
        boolean cont = true;
        int bill = 0;
        System.out.println("Current bills:");
        displayBills(b);
        
        do{// CONT == TRUE
            boolean morBilValid = false;
            do{// VALID == FALSE
                try{
                    System.out.print("\nWhich bill are you adding:"
                            + "\n\t1. 100\n\t2. 50\n\t3. 20\n\t4. 5\n\t5. Cancel"
                            + "\nCHOICE:  ");
                    bill = scan.nextInt();
                    
                    if(bill<1 || bill>5){
                        System.out.println("\nError! Please enter a number between 1 - 5.\nTry again...");
                        valid = false;
                    }else{
                        int num = 0;

                        boolean numValid = false;
                        do{
                            if(bill == 5){
                                valid = true;
                                morBilValid = true;
                                cont = false;
                                break;
                            }
                            try{
                                System.out.print("\nHow many are you adding (-999 to cancel):  ");
                                num = scan.nextInt();
                                if(num == -999){
                                    morBilValid = true;
                                    cont = false;
                                    break;
                                }
                                
                                if(num<1){
                                    System.out.println("\nError! Please enter a positive number greater than 0\nTry again...");
                                    numValid = false;
                                }else{
                                    switch(bill){
                                        case 1:
                                            b.get(bill-1).setNumber(b.get(bill-1).getNumber()+num);
                                            numValid = true;
                                            break;
                                        case 2:
                                            b.get(bill-1).setNumber(b.get(bill-1).getNumber()+num);
                                            numValid = true;
                                            break;
                                        case 3:
                                            b.get(bill-1).setNumber(b.get(bill-1).getNumber()+num);
                                            numValid = true;
                                            break;
                                        case 4:
                                            b.get(bill-1).setNumber(b.get(bill-1).getNumber()+num);
                                            numValid = true;
                                            break;
                                    }//END SWITCH

                                    valid = true;
                                }// END IF/ELSE numValid
                            }catch(InputMismatchException e){
                                System.out.println("\nError! Please enter a positive number greater than 0.\nTry again...");
                                scan.next();
                                numValid = false;
                            }
                        }while(numValid == false);
                        
                    }// END IF/ELSE
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter a whole number between 1 -5");
                    valid = false;
                    scan.next();
                }
            }while(valid == false);
            
            
            int moreBills = 0;
            do{//morBilValid == FALSE
                if(morBilValid == true){
                    break;
                }
                try{
                    System.out.print("\nEnter more bills?\n\t1.  Yes\n\t2.  No\nCHOICE:  ");
                    moreBills = scan.nextInt();
                    
                    // VALIDATE either 1 or 2
                    if(moreBills<1 || moreBills >2){
                        System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                        morBilValid = false;
                    }else{
                        switch(moreBills){
                            case 1:
                                cont = true;
                                break;
                            case 2:
                                cont = false;
                                System.out.println("\nBills have been updated:");
                                displayBills(b);
                                break;
                        }// END SWITCH
                        morBilValid = true;
                    }
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                    morBilValid = false;
                    scan.next();
                }
            
            }while(morBilValid == false);
        }while(cont == true);
        
        
        //System.out.println("add bills");
    }
    
    public static void removeBills(ArrayList<Bills> b){
        boolean cont = true;
        boolean whichValid = false;
        
        displayBills(b);
        
        do{// cont
            boolean morBilValid = false;
            int bill = 0;
            do{// whichValid
                try{
                    System.out.print("\nWhich bill are you removing:\n\t1. 100\n\t2. 50\n\t3. 20\n\t4. 5\n\t5. Cancel\nCHOICE:  ");
                    bill = scan.nextInt();
                    
                    if(bill<1 || bill >5){
                        System.out.println("\nError! Please enter a number betwee 1 - 5.\nTry again...");
                        whichValid = false;
                    }else{
                        if(bill == 5){
                            System.out.println("\nCanceling...");
                            cont = false;
                            break;
                        }
                        
                        boolean numValid = false;
                        int num = 0;
                        do{
                            try{
                                System.out.print("\nHow many do you want to remove (or -999 to cancel):  ");
                                num = scan.nextInt();
                                
                                if(num == -999){
                                    System.out.println("\nCanceling...");
                                    whichValid = true;
                                    cont = false;
                                    break;
                                }
                                
                                if(num<1){
                                    System.out.println("\nError! Please enter a positive number greater than 0.");
                                    numValid = false;
                                }else{
                                    int origNum = 0;
                                    switch(bill){
                                        case 1:
                                        case 2:
                                        case 3:
                                        case 4:
                                            // VALIDATE enought in ATM
                                            origNum = b.get(bill-1).getNumber();
                                            
                                            if(origNum<num){
                                                System.out.println("\nError! There aren't enough bills in the ATM.\n");
                                                displayBills(b);
                                                System.out.println("\nTry again...");
                                                numValid = false;
                                            }else{
                                                b.get(bill-1).setNumber(origNum - num);
                                                System.out.println("\nBills sucessfully updated.");
                                                numValid = true;
                                                whichValid = true;
                                            }
                                            break;
                                    }// END SWITCH bill
                                }// END IF/ELSE num < 1
                            }catch(InputMismatchException e){
                                System.out.println("\nError! Please enter a positive number greater than 0.\nTry again...");
                                scan.next();
                                numValid = false;
                            }// END TRY/CATCH num
                        }while(numValid == false);
                        
                       
                    }// END IF/ELSE which btx 1 - 5
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter a number betwee 1 - 5.\nTry again...");
                    whichValid = false;
                    scan.next();
                }// END TRY/CATCH which
                
            }while(whichValid == false);
            int moreBills = 0;
            do{//morBilValid == FALSE
                if(morBilValid == true || cont == false){
                    break;
                }
                try{
                    System.out.print("\nRemove more bills?\n\t1.  Yes\n\t2.  No\nCHOICE:  ");
                    moreBills = scan.nextInt();
                    
                    // VALIDATE either 1 or 2
                    if(moreBills<1 || moreBills >2){
                        System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                        morBilValid = false;
                    }else{
                        switch(moreBills){
                            case 1:
                                cont = true;
                                break;
                            case 2:
                                cont = false;
                                System.out.println("\nBills have been updated:");
                                displayBills(b);
                                break;
                        }// END SWITCH
                        morBilValid = true;
                    }
                }catch(InputMismatchException e){
                    System.out.println("\nError! Please enter 1 or 2.\nTry again...");
                    morBilValid = false;
                    scan.next();
                }
            
            }while(morBilValid == false);
        }while(cont == true);
        //System.out.println("remove bills");
    
        
    }// END removeBills
}// END class ServerOper
