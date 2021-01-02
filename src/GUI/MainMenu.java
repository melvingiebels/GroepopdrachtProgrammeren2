package GUI;

import GUI.Course.*;
import GUI.Student.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainMenu{

    Stage window;
    Scene mainMenuScene;

    public Scene getScene(Stage mainStage) {

        mainStage.setTitle("Main menu");
        BorderPane layout = new BorderPane();

        // Create views
        OverviewStudent studentScene = new OverviewStudent();
        OverviewCourse courseScene = new OverviewCourse();
        
        // Create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 0, 20, 0));
        menu.setSpacing(10);

        // Create buttons for menu
        Button studentsBtn = new Button("Students");
        Button coursesBtn =  new Button("Courses");
    
        // Add buttons to menu
        menu.getChildren().addAll(studentsBtn, coursesBtn);
    
        layout.setCenter(menu);

        // Add button actions
        studentsBtn.setOnAction((event) -> {
            mainStage.setScene(studentScene.getScene(mainStage));
            // layout.setCenter(studentScene.getScene(mainStage));        
        });
        // coursesBtn.setOnAction((Event) -> {
        //     layout.setCenter(courseView.getview());
        // });
        layout.setPrefSize(500, 600);
        
        return new Scene(layout);
    }    
}