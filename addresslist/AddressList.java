
package srjacobsassignment4;

/**
 *
 * @author Sara
 */
public class AddressList {
    
    private static Node head;
    private static Node curNode;
    
    
    
    /**
     * This nested, private class represents a node of a singly linked list
     */
    private static class ListNode{
        private String name;
        private String tel; // Telephone number
        private String email;
        private String addr; // Address
        private String dob; // Date of birth
        
        private ListNode(String name, String tel, String email, String addr, String dob){
            this.name = name;
            this.tel = tel;
            this.email = email;
            this.addr = addr;
            this.dob = dob;
        } // end of the constructor
        
        public String getName() { return name; }
        public String getTel() { return tel; }
        public String getEmail() { return email; }
        public String getAddr() { return addr; }
        public String getDob() {return dob; }
        
        public void setName(String name) { this.name = name; }
        public void setTel(String tel) { this.tel = tel; }
        public void setEmail(String email) { this.email = email; }
        public void setAddr(String addr) { this.addr = addr; }
        public void setDob(String dob) { this.dob = dob; }

    } // end of class ListNode
    
    
    
// -------------------DONE -----------    
    public boolean isEmpty(){
        return head == null;  
    }
    
    private static class Node{
        private ListNode listNode;
        private Node nextPoint;


        
        
        public Node(){
            this.listNode = null;
            this.nextPoint = null;

        }
        
        public Node(ListNode listNode){
            this.listNode = listNode;
            this.nextPoint = null;
        }
        public Node(Node next, ListNode ln){
            this.nextPoint = next;
            this.listNode = ln;
        }
        
        public ListNode getLN(){
            return listNode;
        }
        public void setListNode(ListNode ln){
            this.listNode = ln;
        }
        
        
        public Node getNextPoint(){
            return nextPoint;
        }
        
        public void setNextPoint(Node p){
            this.nextPoint = p;
        }
    }
    
    
    
//----------------------- DONE ---------------
    
    
    public void addToFront(String name, String tel, String email, String add, String dob){
        ListNode ln = new ListNode(name, tel, email, add, dob);
        boolean empty = isEmpty();
        Node n = new Node(ln);
        if(empty == true){
            head = n;
        }else{
            Node temp = head;
            head = n;
            n.setNextPoint(temp);
            shift(temp.getNextPoint(), temp);
        }
    }

    private static void shift(Node next, Node n){
        if(next!=null){
            Node temp = next;
            n = next;
            shift(temp.getNextPoint(), temp);
            
        }
        
    }
    
    
//----------------------- DONE -----------------
    
    public void addToBack(String name, String tel, String email, String add, String dob){
        ListNode ln = new ListNode(name, tel, email, add, dob);
        boolean empty = isEmpty();
        
        if(empty == true){
            head = new Node(ln);
            //System.out.println("Empty true");
        }else{
            Node temp = new Node(ln);

            //returns the last node before null in the list 
            curNode = getLastNode(head);

            curNode.setNextPoint(temp);
        }
        
    }
    
    private static Node getLastNode(Node cn){
        Node temp = cn.getNextPoint();
        if(temp!=null){
            cn = cn.nextPoint;
            getLastNode(cn);
        }
        return cn;
    }
 
//----------------------- DONE ---------
    
    @Override
    public String toString(){
        String output = "";
        
        if(head!=null){
            output = getOutput(output, head, 0);
        }else{
            output = "The list is empty!";
        }
        
        return output;
    }
    
    private static String getOutput(String output, Node cn, int count){
        //WORKS
        if(cn == null && count == 0){
            output = "The list is empty.";
            
        }else if(cn != null){
            count++;
            output += count + ".  Name:    " + cn.getLN().getName() + "\n    Phone:   " +cn.getLN().getTel() + "\n    Email:   " + cn.getLN().getEmail() + "\n    Address: " + cn.getLN().getAddr() + "\n    DOB:     " + cn.getLN().getDob() + "\n\n";
            cn = cn.getNextPoint();
            output = getOutput(output, cn, count);
        }
        
        return output;
    }
    
    
    
    
    
    
    public String reverseToString(){
        String output = "";
        if(head == null){
            output = "The list is empty.";
        }else{
            output = getReverseOutput(output, head, 1);
        }
        return output;
    }
    private static String getReverseOutput(String output, Node cn, int count){
        if(cn==null){
            //System.out.println("inside");
            return"";
        }
        //System.out.println("outside");
        count++;
        output = getReverseOutput(output, cn.getNextPoint(), count);
        //System.out.println("after");
        count--;
        output += count + ".  Name:    " + cn.getLN().getName() + "\n    Phone:   " +cn.getLN().getTel() + "\n    Email:   " + cn.getLN().getEmail() + "\n    Address: " + cn.getLN().getAddr() + "\n    DOB:     " + cn.getLN().getDob() + "\n\n";
        
        return output;
        
        
        
    }
    
    
    
    
//------------------ DONE --------------
    public int sizeOf(){
        int count = 0;
        count = count(count, head);
        return count;
    }
    
    private static int count(int count, Node cn){
        Node temp = cn.getNextPoint();
        if(temp!=null){
            count++;
            //System.out.println("PASS  -  " + count);
            cn = cn.nextPoint;
            count = count(count, cn);
        }else{
            count++;
        }
        return count;
    }
    
    
    
    
//-------------------- DONE ------------------------    
    public void reverse(){
        head = swap(head);
    }
    
    private static Node swap(Node temp){
        if(temp==null){
            return temp;
        }
        if(temp.getNextPoint()==null){
            return temp;
        }
        
        //Node new = head.nextPoint;
        Node newHead = swap(temp.nextPoint);
        temp.nextPoint.nextPoint = temp;
        temp.nextPoint = null;
        return newHead;
            
        
    }
    
    
// -------------------- DONE ------------------    
    
    // Case 1
    public String phoneNumberByName (String name){
        String ans = searchByName(name, head, 1);
        return ans;
    }
    
    
    
    
    // Case 2
    public String emailByName(String name){
        String ans = searchByName(name, head, 2);
        return ans;

    }
    
    
    
    public String nameByPhone(String phone){
        String ans = searchByPhone(phone, head);
        return ans;
    }
    private static String searchByPhone(String phone, Node cn){
        if(cn == null){
            System.out.println("null");
            return "No matching data.";
        }
        else if(cn.getLN().getTel().equalsIgnoreCase(phone)){
            System.out.println(cn.getLN().getName());
            return cn.getLN().getName();
        }else{
            cn = cn.getNextPoint();
            String ans = searchByPhone(phone, cn);
            return ans;
        }
        
    }
    
    
    // Case 3
    public String dobByName(String name){
        String ans = searchByName(name, head, 3);
        return ans;
    }
    
    
    private static String searchByName(String name, Node cn, int num){
        //System.out.println("in method");
        if(cn == null){
            return "No matching data.";
        }else if(cn.getLN().getName().equalsIgnoreCase(name)){
            switch (num) {
                case 1:
                    return cn.getLN().getTel();
                case 2:
                    return cn.getLN().getEmail();
                case 3:
                    //System.out.println(cn.getLN().getDob());
                    return cn.getLN().getDob();
                default:
                    return "error";
            }
            
        }else if(cn.getNextPoint() == null){
            //System.out.println("in else if");
            return "No matching data.";
        }else{
            
            //System.out.println("else");
            cn = cn.getNextPoint();
            String ans = searchByName(name, cn, num);
            return ans;
        }
    }// END searchByName
}
