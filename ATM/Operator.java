
package ATM;

/**
 *
 * @author Sara
 */
public class Operator {
    private int id;
    private String name;
    private String user;
    private String pass;
    private static int next = 100;
    
    /**
     *   no argument constructor
     */
    public Operator(){
        id = next++;
    }
    
    /**
     *   constructor
     * 
     * @param n
     * @param u
     * @param p
     */
    public Operator(String n, String u, String p){
        id = next++;
        this.name = n;
        this.user = u;
        this.pass = p;
    }
    
    @Override public String toString(){
        return "Operator ID: " + id + "\nName: " + name + "\nUser: " + user;
    }

    
    //--------------------- JAVADOC ----------------------------
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public int getId(){
        return id;
    }
    
    
    
    
    
        
}
