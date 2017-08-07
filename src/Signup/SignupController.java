
package Signup;

import AdminProfile.AdminProfileController;
import indicator.Indicator;
import DatabaseHandler.Database;
import UserProfile.UserprofileController;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class SignupController implements Initializable {

    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField emailTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField passwordAgainTxt;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private Label msgLabel;        
    
    String username, email , password, cpassword;
    boolean canRegister = true;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeCombo.getItems().removeAll(typeCombo.getItems());
        typeCombo.getItems().addAll("Admin", "User");
    }    

    @FXML
    private void signupBtnClicked(MouseEvent event) throws Exception
    {
        // trimmng the properties to remove blank spaces
        username = usernameTxt.getText().trim();
        email = emailTxt.getText().trim();
        password = passwordTxt.getText().trim();
        cpassword = passwordAgainTxt.getText().trim();
        
        // checking if user has made any empty inputs and hit okay
        
        if(!username.isEmpty() && !email.isEmpty() && 
                !password.isEmpty() && !cpassword.isEmpty() && !(typeCombo.getValue() == null))
        {   
            // Checking if both passwords match
            if(password.equals(cpassword))
            {
                if(typeCombo.getValue().equals("User"))
                {
                    registration("User");
                    
                    try 
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(UserprofileController.class.getResource("userprofile.fxml"));
                        Parent loginDir = (Parent) fxmlLoader.load();
                        Stage userprofile = new Stage();
                        userprofile.setScene(new Scene(loginDir));  
                        userprofile.setTitle("Welcome " + username);
                        userprofile.setResizable(false);
                        userprofile.show();

                        Indicator.getCurrentPage().close();
                        Indicator.setCurrentPage(userprofile);

                    } 
                    catch(Exception e) 
                    {
                        e.printStackTrace();
                    }
                  
                }
                else if(typeCombo.getValue().equals("Admin"))
                {
                    registration("Admin");
                    
                    
                    try 
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(AdminProfileController.class.getResource("AdminProfile.fxml"));
                        Parent loginDir = (Parent) fxmlLoader.load();
                        Stage adminprofile = new Stage();
                        adminprofile.setScene(new Scene(loginDir));  
                        adminprofile.setTitle("Welcome " + username);
                        adminprofile.setResizable(false);
                        adminprofile.show();

                        Indicator.getCurrentPage().close();
                        Indicator.setCurrentPage(adminprofile);

                    } 
                    catch(Exception e) 
                    {
                        e.printStackTrace();
                    }
                    
                }
            }
            else
            {
                msgLabel.setText("% password doesn\'t match %");
                System.out.println("password doesn\'t match");
            }
        }
        else
        {
            // Check your credintials again
            msgLabel.setText("% Check your credintials again %");
            System.out.println("Check your credintials again");
        }
        
    }
    
    private void registration(String table) throws Exception
    {
        String query;
        PreparedStatement statement;
        File file;
        FileInputStream fin;
 
        file = new File("profile.jpg");
        System.out.println(file.getAbsolutePath());
        fin = new FileInputStream(file);

        // username , email are primary keys
        // that means these values need to be unique
        // on user can't register with same username and email
        // before lettng user register it needs to checked whether
        // the username or email already exists in the database

        query = "SELECT * from "+ table +" where username = ?";
        statement = Database.conn.prepareStatement(query);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        if(rs.next())
        {
            msgLabel.setText("% username exists %");
            System.out.println("username exists");
            canRegister = false;
        }
        else
            canRegister = true;

        query = "SELECT * from "+ table +" where email = ?";
        statement = Database.conn.prepareStatement(query);
        statement.setString(1, email);
        rs = statement.executeQuery();
        if(rs.next())
        {
            msgLabel.setText("% email exists %");
            System.out.println("email exists");
            canRegister = false;
        }
        else
            canRegister = true;

        if(canRegister)
        {
            if(table.equals("Admin"))
            {
                query = "INSERT INTO "+ table +"(username,email,password,img) VALUES (?,?,?,?)";
            }
            else
                query = "INSERT INTO "+ table +"(username,email,password) VALUES (?,?,?)";
                
            // if everything is all right then insert them into database
            statement = Database.conn.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            if(table.equals("Admin"))
                statement.setBinaryStream(4,(InputStream)fin,(int)file.length());
            statement.execute();
            msgLabel.setText("");
            System.out.println("Registration successful as " + typeCombo.getValue() );
            
            rs = statement.getGeneratedKeys();
            if(rs.next())
            {
                if (table.equals("User"))
                {
                   
                    System.out.println("executing...");
                    query = "INSERT INTO userDetails(score,rank,userId,img) VALUES (?,?,?,?)";
                    // if everything is all right then insert them into database
                    statement = Database.conn.prepareStatement(query);
                    statement.setInt(1, 0);
                    statement.setInt(2, 0);
                    statement.setInt(3, rs.getInt(1));
                    statement.setBinaryStream(4,(InputStream)fin,(int)file.length());
                    Indicator.setUser_id(rs.getInt(1));
                    statement.execute();
                    msgLabel.setText("");
                    System.out.println("Registration successful as " + typeCombo.getValue() );

                }
            }
           
        }
    }
 
}
