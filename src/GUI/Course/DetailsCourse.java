package GUI.Course;

import GUI.GenericGUI;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DetailsCourse extends GenericGUI {
    
    public Scene getScene(Stage window) {
        
        // Create view for back button
        OverviewCourse courseOverview = new OverviewCourse();

        // Main Layout
        BorderPane layout = new BorderPane();

        // Back button
        

        return new Scene(layout);
    }
}
