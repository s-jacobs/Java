
package ATM;

/**
 *
 * @author Sara
 */
public class Customer {
    private String name;
    private int id;
    private static int next = 100;
    
    Customer(){
        id = next++;
    }
    
    Customer(String n){
        id = next++;
        this.name = n;
    }
    
    @Override public String toString(){
        return "Customer ID:  " + id + "\nName:  " + name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
