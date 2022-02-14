
package ATM;

/**
 *
 * @author Sara
 */
public class Bills {
    private int value;
    private int number;
    
    public Bills(){
        
    }
    
    public Bills(int v, int n){
        this.number = n;
        this.value = v;
    }
    
    @Override public String toString(){
        return "$" + value + " - " + number;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    
}
