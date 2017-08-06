
import indicator.Indicator;
import UserLogin.UserLoginPageController;
import Signup.SignupController;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class FXMLDocumentController implements Initializable {

    @FXML
    private Button registerBtn;
    @FXML
    private Button loginBtn;

    private Stage prev;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void signup_btnClicked(MouseEvent event) throws Exception
    {
         try {
                FXMLLoader fxmlLoader = new FXMLLoader(SignupController.class.getResource("signup.fxml"));
                Parent signupDir = (Parent) fxmlLoader.load();
                Stage signupPage = new Stage();
                signupPage.setScene(new Scene(signupDir));  
                signupPage.setTitle("Sign up Here");
                signupPage.setResizable(false);
                signupPage.show();
                
                Indicator.getCurrentPage().close();
                Indicator.setCurrentPage(signupPage);
                
        } catch(Exception e) {
           e.printStackTrace();
          }
    }

    @FXML
    private void login_btnClicked(MouseEvent event) throws Exception
    {
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(UserLoginPageController.class.getResource("UserLoginPage.fxml"));
                Parent loginDir = (Parent) fxmlLoader.load();
                Stage loginPage = new Stage();
                loginPage.setScene(new Scene(loginDir));  
                loginPage.setTitle("Login Here");
                loginPage.setResizable(false);
                loginPage.show();
                
                Indicator.getCurrentPage().close();
                Indicator.setCurrentPage(loginPage);
                
        } catch(Exception e) {
           e.printStackTrace();
          }
    }
    
}
