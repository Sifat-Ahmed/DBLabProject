import indicator.Indicator;
import DatabaseHandler.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application 
{
    @Override
    public void start(Stage stage) throws Exception 
    {
        // Connecting to database as the application starts
        Database.connectDatabase();
        // accessing the FXML File from project directory
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        // Creating a new Secene with the pointer
        Scene scene = new Scene(root);
        //Setting the scene as default
        stage.setScene(scene);
        // Setting the title of the window
        stage.setTitle("Online Judge");
        // The Window can not be resized like make it small or big
        // This will break the design
        stage.setResizable(false);
        stage.show();
        // keeping the previous page tracked so the it can be disabled 
        // when movved to the next page
        Indicator.setCurrentPage(stage);
        
       
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
