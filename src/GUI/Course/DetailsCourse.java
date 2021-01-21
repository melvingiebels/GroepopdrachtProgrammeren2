package GUI.Course;

import Domain.Course;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsCourse extends GenericGUI {
    
    public Scene getScene(Course course, Stage window) {
        
        // Create view for back button
        OverviewCourse courseOverview = new OverviewCourse();

        // Main Layout
        BorderPane layout = new BorderPane();

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction((event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        backBtn.setMinSize(50, 30);

        // Main elements
        VBox mainGrid = new VBox();
        mainGrid.setPadding(new Insets(30, 50, 50, 50));
        mainGrid.setSpacing(10);
        // mainGrid.getChildren().add(getCourseModules(course));

        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        leftMenu.getChildren().addAll(backBtn);

        return new Scene(layout);
    }
}
