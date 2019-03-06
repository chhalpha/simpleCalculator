package persitance;

import java.sql.*;

/**
* @author Christian Held
*
* */

public class ConnectionHelper {

    public static Connection CONNECTION = null;

    /**
     * Creates a connection to a PostgresSQL database hosted for free by ElephantSQL
     *
     * @param url  an absolute URL giving the base location of the database
     * @param username the username to connect to the database
     * @param passowrd the passowrd to connect to the database
     *
     */
    public void connect(String url,  String username, String passowrd){

        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (java.lang.ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            if(CONNECTION == null) {
                CONNECTION = DriverManager.getConnection(url, username, passowrd);
                System.out.println("Connected to PostgreSQL database!");
            }else{
                System.out.println("Already connected to PostgreSQL database!");
            }

        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Statement statement;
    public ResultSet resultSet;
    public ResultSet executeQuery(String sql){

        resultSet = null;
        try {
            statement = CONNECTION.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (SQLException e) {
            //System.out.println(""e.getMessage());
        }
        return resultSet;

    }

}
