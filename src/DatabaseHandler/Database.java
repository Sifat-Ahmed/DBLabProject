package DatabaseHandler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Database 
{
    public static Connection conn = null;
    public static Statement statement = null;
    
    
    public static void connectDatabase()
    {
        try {

            String user = "root";
            String password = "";
            String dbName = "aust"; 
            String url = "jdbc:mysql://localhost/" + dbName + "?useSSL=false";

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.
                    getConnection(url, user, password);

            System.out.println("Successfully Connected..");

        }
        catch (ClassNotFoundException | SQLException e)
        {
            System.out.println("Problem in Connection: " + e.getLocalizedMessage());
        }

    }
    
    public void closeConnection() 
    {
        if (conn != null) 
        {
            try 
            {
                conn.close();
            } 
            catch (SQLException ex) 
            {
               System.out.println("Problem in Connection: " + ex.getLocalizedMessage());
            }
        }
    }
    
    
}
