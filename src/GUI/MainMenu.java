package GUI;

import GUI.Course.OverviewCourse;
import GUI.Student.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenu{

    private BorderPane layout = new BorderPane();
    
    public Scene getScene(Stage mainStage) {

        mainStage.setTitle("Main menu");

        // Create views
        OverviewStudent studentScene = new OverviewStudent();
        OverviewCourse courseScene = new OverviewCourse();
        
        // Create menu for main layout
        VBox menu = new VBox();
        menu.setPadding(new Insets(100, 0, 0, 150));
        menu.setSpacing(10);

        // Create buttons for menu
        Button studentsBtn = new Button("Students");
        studentsBtn.setMinSize(200, 50);

        Button coursesBtn =  new Button("Courses");
        coursesBtn.setMinSize(200, 50);
    
        // Add buttons to menu
        menu.getChildren().addAll(studentsBtn, coursesBtn);        

        // Add button actions
        studentsBtn.setOnAction((event) -> {
            mainStage.setScene(studentScene.getScene(mainStage));       
        });
        
        coursesBtn.setOnAction((Event) -> {
            mainStage.setScene(courseScene.getScene(mainStage));
        });
        
        layout.setPrefSize(500, 310);
        layout.setCenter(menu);
        
        return new Scene(layout);
    }    
}