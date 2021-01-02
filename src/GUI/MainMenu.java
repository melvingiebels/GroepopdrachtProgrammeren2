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

public class MainMenu extends Application{

    Stage window;
    Scene mainMenuScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = primaryStage;
        BorderPane layout = new BorderPane();

        // Create views
        OverviewStudent studentView = new OverviewStudent();
        OverviewCourse courseView = new OverviewCourse();
        
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
            layout.setCenter(studentView.getview());        
        });
        // coursesBtn.setOnAction((Event) -> {
        //     layout.setCenter(courseView.getview());
        // });

        mainMenuScene = new Scene(layout,600,600);

        window.setScene(mainMenuScene);
        window.setTitle("main menu");
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }        
}