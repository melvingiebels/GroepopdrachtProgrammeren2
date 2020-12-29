package GUI;

import GUI.Course.OverviewCourse;
import GUI.Student.*;
import javafx.application.Application;
import javafx.stage.Stage;
import Domain.Course;
import Domain.Student;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu extends Application{

    Stage window;
    Scene mainMenuScene, studentScene, courseScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = primaryStage;
        BorderPane layout = new BorderPane();
        
        // Create menu for main layout
        VBox menu = new VBox();
        menu.setPadding(new Insets(20, 0, 20, 0));
        menu.setSpacing(10);

        // Create buttons for menu
        Button studentsBtn = new Button("Students");
        Button coursesBtn =  new Button("Courses");
        Button testBtn = new Button("test");
    
        // Add buttons to menu
        menu.getChildren().addAll(studentsBtn, coursesBtn, testBtn);
  
        layout.setCenter(menu);

        // Add button actions
        studentsBtn.setOnAction((event) -> {
            OverviewStudent.launch();   
            window.close();       
        });
        coursesBtn.setOnAction((event) -> OverviewCourse.launch());
        testBtn.setOnAction((event) ->  System.out.println("yes"));

        mainMenuScene = new Scene(layout,400,400);

        window.setScene(mainMenuScene);
        window.setTitle("main menu");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
