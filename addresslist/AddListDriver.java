
package srjacobsassignment4;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Sara
 */
public class AddListDriver {
    private static Scanner scan = new Scanner(System.in);
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AddressList al = new AddressList();
        
        boolean cont = true;
        do{
            int choice = menu();
            switch(choice){
                case 1:
                    boolean empty = al.isEmpty();
                    if(empty == true){
                        System.out.println("\n\nThe list is empty.\n");
                    }else{
                        System.out.println("\n\nThe list is not empty.\n");
                    }
                    break;
                    
                case 2:
                    boolean valid = true;
                    scan.nextLine();
                    System.out.println("\n\nEnter the name");
                    String name = scan.nextLine();
                    System.out.println("\nEnter the phone number");
                    String phone = scan.nextLine();
                    System.out.println("\nEnter the email");
                    String email = scan.nextLine();
                    System.out.println("\nEnter the address");
                    String addr = scan.nextLine();
                    System.out.println("\nEnter the date of birth");
                    String dob = scan.nextLine();
                    do{
                        try{
                            System.out.print("\nDo you want to add to the:\n"
                                    + "\t1.  Front\n\t2.  Back\n"
                                    + "CHOICE:  ");
                            int whereToAdd = scan.nextInt();
                            if(whereToAdd <1 || whereToAdd>2){
                                System.out.println("\n\nError! Please enter 1 or 2\nTry again...");
                                valid = false;
                            }else{
                                if(whereToAdd == 1){
                                    al.addToFront(name, phone, email, addr, dob);
                                }else{
                                    al.addToBack(name, phone, email, addr, dob);
                                }
                                valid = true;
                            }
                        }catch(InputMismatchException e){
                            System.out.println("\n\nError! Please enter 1 or 2\nTry again...");
                            scan.nextLine();
                            valid = false;
                        }
                    }while(valid == false);
                    break;
                    
                case 3:
                    System.out.println("\n\nAddress List (top to bottom):\n"+ al.toString());
                    break;
                    
                case 4:
                    System.out.println("\n\nAddress List (bottom to top):\n" + al.reverseToString());
                    break;
                    
                case 5:
                    System.out.println("\n");
                    al.reverse();
                    System.out.println("\n\nAddress List Reversed.");
                    break;
                    
                case 6:
                    System.out.println("\n\nThe size of the address list is " + al.sizeOf());
                    break;
                    
                case 7:
                    scan.nextLine();
                    System.out.print("\n\nEnter the name: ");
                    String pByName = scan.nextLine();
                    String pAns = al.phoneNumberByName(pByName);
                    if(pAns.equalsIgnoreCase("No matching data.")){
                        System.out.println(pAns);
                    }else{
                        System.out.println("\nThe phone number of " + pByName + " is " + pAns);
                    }
                    break;
                    
                case 8:
                    scan.nextLine();
                    System.out.print("\n\nEnter the name: ");
                    String eByName = scan.nextLine();
                    String eAns = al.emailByName(eByName);
                    if(eAns.equalsIgnoreCase("No matching data.")){
                        System.out.println(eAns);
                    }else{
                        System.out.println("\nThe email of " + eByName + " is " + eAns);
                    }
                    break;
                    
                case 9:
                    scan.nextLine();
                    System.out.print("\n\nEnter the Phone number: ");
                    String nByPhone = scan.nextLine();
                    String nAns = al.nameByPhone(nByPhone);
                    if(nAns.equalsIgnoreCase("No matching data.")){
                        System.out.println(nAns);
                    }else{
                        System.out.println("\n" + nByPhone + " belongs to " + nAns);
                    }
                    break;
                    
                case 10:
                    scan.nextLine();
                    System.out.print("\n\nEnter the name: ");
                    String dByName = scan.nextLine();
                    String dAns = al.dobByName(dByName);
                    if(dAns.equalsIgnoreCase("No matching data.")){
                        System.out.println(dAns);
                    }else{
                        System.out.println("\nThe dob of " + dByName + " is " + dAns);
                    }
                    break;
                    
                case 11:
                    System.exit(0);
                    break;
                
                
                
                
            }
        }while(cont==true);
        
        
        
//        al.addToFront("John doe", "5403148293", "jdoe@email.com", "123 Main Street", "01/01/01");
//        
//        al.addToFront("bob", "5405405404", "asdf@asdf.com", "123 Not Main Street", "02/02/02");
//        al.addToBack("obo obbo", "123456789", "fda@fdsa", "123 Jeff St.", "03/03/03");
//        al.addToFront("doe john", "987654321", "hjkl@hjkl", "123 Not Orange Ave.", "04/04/04");
//        //al.addToBack("Froggz", "5405405404", "asdf@asdf.com", "123 Not Main Street", "02/02/02");
//        
//        System.out.println(al.toString());
//        
//        String phone = al.phoneNumberByName("obo obbo");
//        System.out.println("\n\nPhone:  " + phone);
//        
//        String email = al.emailByName("doe john");
//        System.out.println("\n\nEmail:  " + email);
//        
//        String dob = al.dobByName("bob");
//        System.out.println("\n\nDOB:  " + dob);
//        
//        
//        String name = al.nameByPhone("5405405404");
//        System.out.println("\n\nName:  " + name);
//        
//        System.out.println("\n\nThe size is " + al.sizeOf());
//        
//        //al.reverse();
//        //System.out.println("\n\nal reversed...\n" + al.toString());
//        
//        System.out.println("\n\nThe reversed to string:\n" + al.reverseToString());
    }// END MAIN
    
    private static int menu(){
        int choice = 0;
        boolean valid = false;
        do{
            try{
                System.out.print("\n\nWhat would you like to do:\n"
                        + "\t1.  Check if the list is empty\n"
                        + "\t2.  Add a new address\n"
                        + "\t3.  Print out the list\n"
                        + "\t4.  Print out the list backwards\n"
                        + "\t5.  Reverse the list\n"
                        + "\t6.  Print the size of the list\n"
                        + "\t7.  Get a phone number by a name\n"
                        + "\t8.  Get an email by a name\n"
                        + "\t9.  Get a name by a phone number\n"
                        + "\t10. Get a date of birth by name\n"
                        + "\t11. Exit\n"
                        + "CHOICE:  ");
                choice = scan.nextInt();
                
                if(choice <1 || choice>11){
                    System.out.println("Error! Please enter the corresponding number of the menu item you would like to do (a number between 1 and 10)"
                        + "\nTry again...\n");
                    valid = false;
                }else{
                    valid = true;
                }
                
                
            }catch(InputMismatchException e){
                System.out.println("\nError! Please enter the corresponding number of the menu item you would like to do (a number between 1 and 10)"
                        + "\nTry again...\n");
                valid = false;
                scan.nextLine();
            }
        }while(valid == false);
        
        
        return choice;
    }
    
}
