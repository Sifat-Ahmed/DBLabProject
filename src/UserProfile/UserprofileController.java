/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserProfile;

import DatabaseHandler.Database;
import static DatabaseHandler.Database.statement;
import indicator.Indicator;
import indicator.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class UserprofileController implements Initializable {

    @FXML
    private Text usernameTxt;
    @FXML
    private Button updateBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Text rankTxt;
    @FXML
    private Text scoreTxt;
    @FXML
    private ImageView userImg;
    @FXML
    private Button problemBtn;
    @FXML
    private Button submissionBtn;
    @FXML
    private Button rankBtn;
    @FXML
    private Button selectBtn;
    @FXML
    private Button deleteBtn;

    User user;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        prepareUser();
    }    
    void prepareUser()
    {
        User.createUser();
        user = User.getUser();
        
        usernameTxt.setText(user.getName());
        rankTxt.setText("Rank:    " + user.getRank());
        scoreTxt.setText("Score:    " + user.getScore());
        userImg.setImage(user.getImage());
    }
    @FXML
    private void udateBtn_clicked(MouseEvent event) 
    {
    }

    @FXML
    private void logoutBtn_clicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void problemBtn_clicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void submissionBtn_clicked(MouseEvent event) 
    {
    }

    @FXML
    private void rankBtn_clicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void selectBtn_clicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void deleteBtn_clicked(MouseEvent event) 
    {
        
    }
    
    @FXML
    private void img_clicked(MouseEvent event) 
    {
        String query;
        PreparedStatement statement;
        ResultSet rs;
        
        FileInputStream fin = null;
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            File file = chooser.showOpenDialog(new Stage());
            fin = new FileInputStream(file);
            
            query = "UPDATE userDetails set img = ? where userId = ?";
            // if everything is all right then insert them into database
            statement = Database.conn.prepareStatement(query);
            statement.setBinaryStream(1,fin,(int)file.length());
            statement.setInt(2, Indicator.getUser_id());
            statement.executeUpdate();
            System.out.println("executed");
            
            prepareUser();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
    
}
