package indicator;


import javafx.stage.Stage;

public class Indicator 
{
    
    // this class will track in which class user is in currently
    // currentStage will hold current page
    // previousStage will hold previous page from where I came to current page
    
    // PreviousPage will be used to dispose it when I hit currentPage
    private static int user_id;
    
    private static Stage currentPage;
    private static Stage previousPage;

    public static Stage getCurrentPage() {
        return currentPage;
    }

    public static int getUser_id() {
        return user_id;
    }

    public static void setUser_id(int user_id) {
        Indicator.user_id = user_id;
    }

    public static void setCurrentPage(Stage currentPage) {
        Indicator.currentPage = currentPage;
    }

    public static Stage getPreviousPage() {
        return previousPage;
    }

    public static void setPreviousPage(Stage previousPage) {
        Indicator.previousPage = previousPage;
    }
    
    
    
}
