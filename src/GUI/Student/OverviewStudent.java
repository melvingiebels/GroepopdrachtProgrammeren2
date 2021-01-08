package GUI.Student;

import java.util.ArrayList;

import Database.StudentDAO;
import Domain.Student;
import GUI.MainMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewStudent{
    
    private ArrayList<Student> students;
    private StudentDAO studentDAO = new StudentDAO();
    private BorderPane layout = new BorderPane();

    public Scene getScene(Stage window){
        
        // Create views
        AddStudent addView = new AddStudent();
        UpdateStudent updateView = new UpdateStudent();
        MainMenu mainMenuScene = new MainMenu();

        // Create menu for overview and add student buttons
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 0, 20, 20));

        // Create buttons for menu
        Button mainMenuBtn = new Button("Back");
        Button addBtn =  new Button("Add student");

        // Button styling
        mainMenuBtn.setMinSize(50, 30);
        addBtn.setMinSize(100, 30);
    
        // Add buttons to menu
        topMenu.getChildren().addAll(mainMenuBtn, addBtn);
        topMenu.setSpacing(20);

        // Add button actions
        addBtn.setOnAction((event) ->  {
            window.setScene(addView.getScene(window));;
            window.setTitle("Add new student");
        });

        mainMenuBtn.setOnAction((Event) ->{
            window.setScene(mainMenuScene.getScene(window));
        });
    
        layout.setTop(topMenu);
        layout.setCenter(createOverView(updateView, window));
        window.setTitle("Student overview");
        
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    private ScrollPane createOverView(UpdateStudent updateView, Stage window) {

        // Sync with database
        students = studentDAO.getAllStudents();
            
        ScrollPane overviewlayout = new ScrollPane();

        Label emailLabel = new Label("EMAIL");
        Label nameLabel =  new Label("NAME");


        emailLabel.setStyle("-fx-font-weight: bold");
        emailLabel.setMinWidth(150);

        nameLabel.setStyle("-fx-font-weight: bold");

        HBox headRow = new HBox(20, emailLabel, nameLabel);
        headRow.setPadding(new Insets(0, 0, 0, 20));

        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with students with update and delete buttons
        for(Student student : students) {
            Label email = new Label(student.getEmail());
            Label name = new Label(student.getName());

            email.setMinWidth(150);
            name.setMinWidth(150);

            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");

            HBox row = new HBox(20, email, name, updateBtn, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 20));

            updateBtn.setOnAction((event) -> {
                layout.setCenter(updateView.getView(student, studentDAO));
                window.setTitle("Update student");
            });

            deleteBtn.setOnAction((event) ->  {
                studentDAO.removeStudent(student.getEmail());
                table.getChildren().remove(row);
            });
        
            table.getChildren().add(row);
        }
        overviewlayout.setContent(table);
        overviewlayout.setPrefSize(500, 600);
        overviewlayout.setPadding(new Insets(0,0,0,20));
        return overviewlayout;
    }
}