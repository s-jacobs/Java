
package ATM;

/**
 *
 * @author Sara
 */


public class Account {
    private int acctNum;
    private Customer cust;
    private String user;
    private String pass;
    private double cBal;
    private double sBal;
    private static int nextNum = 1000;
    
    public Account(){
        acctNum = nextNum++;
    }
    
    public Account(Customer cu,String u, String p, double cb, double s){
        acctNum = nextNum++;
        this.cust = cu;
        this.user = u;
        this.pass = p;
        this.cBal = cb;
        this.sBal = s;
    }
    
    @Override public String toString(){
        return "Account Number: " + acctNum + "\nChecking Balance: " + cBal + "\nSavings Balance: " + sBal;
    }

    public Customer getCust() {
        return cust;
    }

    public void setCust(Customer cust) {
        this.cust = cust;
    }

    
    
    /**
     *
     * @return acctNum
     */
    public int getAcctNum() {
        return acctNum;
    }

    /**
     *
     * @param acctNum
     */
    public void setAcctNum(int acctNum) {
        this.acctNum = acctNum;
    }

    /**
     *
     * @return pass
     */
    public String getPass() {
        return pass;
    }

    /**
     *
     * @param pass
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     *
     * @return cBal
     */
    public double getcBal() {
        return cBal;
    }

    public void setcBal(double cBal) {
        this.cBal = cBal;
    }

    public double getsBal() {
        return sBal;
    }

    public void setsBal(double sBal) {
        this.sBal = sBal;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    
    
}
