package indicator;

import DatabaseHandler.Database;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.image.Image;


public class User 
{
    private final int id;
    private final String name;
    private final String email;
    private final String password;
    private final int rank;
    private final int score; 
    private final Image profileImg;
    static String query;
    static PreparedStatement statement;
    static ResultSet rs;
    static User user;
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

    public int getRank() {
        return rank;
    }

    public int getScore() {
        return score;
    }
    
    public Image getImage()
    {
        return profileImg;
    }
    private User(int id, String name, String email, String password, int rank, int score , Image img) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.rank = rank;
        this.score = score;
        this.profileImg= img;
    }

    public static void createUser()
    {
        try 
        {
            query = "SELECT d.id , d.img, d.score, d.rank , u.username , u.email , u.password "
                    + " FROM user AS u INNER JOIN userDetails AS d"
                    + " ON d.userId = u.id where u.id = ? ";
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
                user = new User(uid , username , email, password, rank, score , img);
            }
           
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        
    }
    
    public static User getUser()
    {
        return user;
    }
}
