package UserLogin;

import AdminProfile.AdminProfileController;
import DatabaseHandler.Database;
import UserProfile.UserprofileController;
import indicator.Indicator;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class UserLoginPageController implements Initializable {

    @FXML
    private Button loginBtn;
    @FXML
    private ComboBox<String> typeCombo;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private Label msgLabel;      
    
    String username, password;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       typeCombo.getItems().removeAll(typeCombo.getItems());
       typeCombo.getItems().addAll("Admin", "User");
    }    

    @FXML
    private void login_btnClicked(MouseEvent event) throws Exception
    {
        username = usernameTxt.getText().trim();
        password = passwordTxt.getText().trim();
        
        if(!username.isEmpty() && !password.isEmpty())
        {
            if(typeCombo.getValue().equals("User"))
            {
                login("User");
            }
            else if(typeCombo.getValue().equals("Admin"))
            {
                login("Admin");
            }
        }
    }
    
    private void login(String table) throws Exception
    {
        String query;
        PreparedStatement statement;

        // username , email are primary keys
        // that means these values need to be unique
        // on user can't register with same username and email
        // before lettng user register it needs to checked whether
        // the username or email already exists in the database

        query = "SELECT * from "+ table +" where username = ? and password = ?";
        statement = Database.conn.prepareStatement(query , Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        
        if(rs.next())
        {
            System.out.println(rs.getInt(1));
            Indicator.setUser_id(rs.getInt(1));
            
            try 
            {
                FXMLLoader fxmlLoader;
                if(table.equals("Admin"))
                    fxmlLoader = new FXMLLoader(AdminProfileController.class.getResource("AdminProfile.fxml"));
                else
                    fxmlLoader = new FXMLLoader(UserprofileController.class.getResource("userprofile.fxml"));
                Parent loginDir = (Parent) fxmlLoader.load();
                Stage userprofile = new Stage();
                userprofile.setScene(new Scene(loginDir));  
                userprofile.setTitle(username + " - "+ table + "Panel");
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
        else
            msgLabel.setText("% Check your credentials again! %");
    }
    
    
}
