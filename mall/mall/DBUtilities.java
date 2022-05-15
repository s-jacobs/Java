package mall;

import java.sql.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to establish connection with database
 * @author M Royal and S Jacobs
 * Created 2018
 * Revised by SJ 5/15/2022 - Changed formating and updated Java Doc
 */
public class DBUtilities {
    /**  Connection to database  */
    private static Connection con;
    /**  Statement to be used to query database  */
    private static Statement stmt;
    /**  * Scanner object to be used throughout class  */
    private static Scanner scan = new Scanner(System.in);
    //private static int which;

    /**
     * Method to create Connection to database
     * <br>Assumes existence of Cockatoos database 
     * with userID of itp220 and password of itp220
     * @return Returns Connection to database
     */
    public static Connection createConnection() {
        Connection con = null;

        String user = "itp220";//MR 10/29 - Hard code
        String pass = "itp220";//MR 10/29 - Hard code
        String name = "cockatoos";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/cockatoos";

        try { // load the driver 
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) { // problem loading driver, class not exist?
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Method to close existing Connection to database
     * @param Connection con  Connection to the database 
     */
    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to check for existing Connection to database
     * 		If connection doesn't exist call createConnection()
     * @param Connection con  Connection to the database 
     * @see createConnection()
     * @return Returns Connection con  Connection to the database
     */
    public static Connection checkConnect(Connection con) {
        if (con == null) { con = createConnection(); }
        if (stmt == null) {
            try {  stmt = con.createStatement();
            } catch (SQLException e) {
            	System.out.println("Cannot create the statement");
            }
        }//end if
        return con;
    }


}
