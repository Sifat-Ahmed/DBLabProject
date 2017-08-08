
package AdminProfile;

import DatabaseHandler.Database;
import indicator.Admin;
import indicator.Indicator;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class AdminProfileController implements Initializable {

    @FXML
    private Text usernameTxt;
    @FXML
    private ImageView img;
    @FXML
    private TableView <ObservableList<String>> table;
    
    private ObservableList<ObservableList<String>> data ;
    
    String query;
    PreparedStatement statement;
    ResultSet rs;
    FileInputStream fin;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        try 
        {
            prepareAdmin();
            query = "SELECT u.id , u.username , d.score  "
                    + " FROM user AS u INNER JOIN "
                    + " userDetails as d ON u.id = d.userId ";
            statement = Database.conn.prepareStatement(query);
            rs = statement.executeQuery();
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
            {
                final int j = i;                
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i+1));
                table.getColumns().addAll(col); 
                System.out.println("Column ["+i+"] ");
            }
            data = table.getItems();
            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                    System.out.println(rs.getString(i));
                }
                //System.out.println("Row [1] added " + row );
                data.add(row);
                System.out.println("data " + data);
            }
            table.setItems(data);
            
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        
    }    
    
    void prepareAdmin()
    {
        Admin.createAdmin();
        Admin admin = Admin.getAdmin();
        usernameTxt.setText(admin.getName());
        img.setImage(admin.getImage());
    }
    
    @FXML
    private void updateBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void logoutBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void profileImageClicked(MouseEvent event) {
        
        
        try {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Open File");
            File file = chooser.showOpenDialog(new Stage());
            fin = new FileInputStream(file);
            
            query = "UPDATE admin set img = ? where Id = ?";
            // if everything is all right then insert them into database
            statement = Database.conn.prepareStatement(query);
            statement.setBinaryStream(1,fin,(int)file.length());
            statement.setInt(2, Indicator.getUser_id());
            statement.executeUpdate();
            System.out.println("executed");
            
            prepareAdmin();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        
    }

    @FXML
    private void allProblemsBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void submissionsBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void rankListBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void deleteBtnClicked(MouseEvent event) 
    {
        
    }

    @FXML
    private void MyProblemsBtnClicked(MouseEvent event) 
    {
        
    }
    
    @FXML
    private void img_clicked(MouseEvent event) 
    {
        
    }
}
