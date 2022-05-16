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
     * Creates a connection to a mySQL database using a JDBC driver. Assumes 
     *  the existence of Cockatoos database with the correct user name and 
     *  password. See readme.md document for instructions on how to properly
     *  set up database.
     * @return Connection con  Connection to database
     * @exception Exception if an error occurs loading the JDBC driver
     * @see Connection
     * @see DriverManager#getConnection(String, String, String)     
     */
    public static Connection createConnection() {
        Connection con = null;

        String user = "itp220";//MR 10/29 - Hard code
        String pass = "itp220";//MR 10/29 - Hard code
        String name = "cockatoos";
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/cockatoos";

        try { // load the driver 
            Class.forName(driver).getDeclaredConstructor().newInstance();
            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) { // problem loading driver, class not exist?
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Closes existing connection to database. Assumes connection has already
     *  been made. If no connection has been made this method will not do anything.
     * @param Connection con  Connection to the database
     * @exception SQLException If an error occurs closing a Connection or Statement
     * @see Connection
     * @see Statement
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
     * Checks for existing connection to database. If connection doesn't exist 
     *  calls createConnection(). Also creates a SQL Statement object.
     * @param Connection con  Connection to the database 
     * @return Connection con  Connection to the database
     * @exception SQLException If an error occurs creating the Statement
     * @see #createConnection()
     * @see Connection#createStatement()
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
