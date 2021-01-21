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

        mainStage.setTitle("Qiang Loozen(2168252), Melvin Giebels(2173543), Pieter Bakker(2160778)");

        // Create views
        OverviewStudent studentScene = new OverviewStudent();
        OverviewCourse courseScene = new OverviewCourse();
        
        // Create menu for main layout
        VBox menu = new VBox();
        menu.setPadding(new Insets(100, 0, 0, 225));
        menu.setSpacing(20);

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
        
        layout.setPrefSize(650, 350);
        layout.setCenter(menu);
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }    
}