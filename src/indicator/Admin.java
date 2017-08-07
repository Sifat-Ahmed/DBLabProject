/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indicator;

import DatabaseHandler.Database;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.Image;

/**
 *
 * @author Sifat Ahmed
 */
public class Admin {
    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final Image profileImg;
    static String query;
    static PreparedStatement statement;
    static ResultSet rs;
    static Admin admin;
    static Blob image;
    static byte byteImage[];
    static Image img;
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    
    public Image getImage()
    {
        return profileImg;
    }
    private Admin(int id, String name, String email, String password, Image img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImg= img;
    }

    public static void createUser()
    {
        try 
        {
            query = "SELECT * from admin where id = ?";
            statement = Database.conn.prepareStatement(query);
            System.out.println("Indicator.getUser_id() returns " + Indicator.getUser_id());
            statement.setInt(1, Indicator.getUser_id() ); // Indicator.getUser_id() returns 3
            rs = statement.executeQuery();
            
            if(rs.next())
            {
                String username = rs.getString("username");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int score = rs.getInt("score");
                int rank = rs.getInt("rank");
                int uid = rs.getInt("id");
                image = rs.getBlob("img");
                byteImage = image.getBytes(1,(int)image.length());
                img = new Image(new ByteArrayInputStream(byteImage));
                admin = new Admin(uid , username , email, password, img);
            }
           
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
    }
    
    public static Admin getAdmin()
    {
        return admin;
    }
}
