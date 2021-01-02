package GUI.Student;

import java.util.ArrayList;

import Database.StudentDAO;
import Domain.Student;
import GUI.MainMenu;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class OverviewStudent{
    
    private ArrayList<Student> students;
    private StudentDAO studentDAO = new StudentDAO();
    private BorderPane layout = new BorderPane();

    public Scene getScene(Stage window){
        
        // Create views
        AddStudent addView = new AddStudent();
        UpdateStudent updateView = new UpdateStudent();
        MainMenu mainMenuScene = new MainMenu();

        // Create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 0, 20, 0));
        menu.setSpacing(10);

        // Create buttons for menu
        Button overviewBtn = new Button("Overview");
        Button addBtn =  new Button("Add student");
        Button mainMenuBtn = new Button("Back");
    
        // Add buttons to menu
        menu.getChildren().addAll(overviewBtn, addBtn);

        layout.setTop(menu);
        layout.setLeft(mainMenuBtn);
        layout.setCenter(createOverView(updateView, window));

        // Add button actions
        overviewBtn.setOnAction((event) ->  {
            layout.setCenter(createOverView(updateView, window));
            window.setTitle("Student overview");
        });
        
        addBtn.setOnAction((event) ->  {
            layout.setCenter(addView.getView());
            window.setTitle("Add new student");
        });

        mainMenuBtn.setOnAction((Event) ->{
            window.setScene(mainMenuScene.getScene(window));
        });
    

        // window.setScene(view);
        window.setTitle("Student overview");
        return new Scene(layout);
    }

    private StackPane createOverView(UpdateStudent updateView, Stage window) {

        // Sync with database
        students = studentDAO.getAllStudents();
            
        StackPane overviewlayout = new StackPane();

        Label emailLabel = new Label("EMAIL");
        Label nameLabel =  new Label("NAME");

        emailLabel.setStyle("-fx-font-weight: bold");
        emailLabel.setMinWidth(150);

        nameLabel.setStyle("-fx-font-weight: bold");

        HBox headRow = new HBox(20, emailLabel, nameLabel);

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
            row.setPadding(new Insets(10, 0, 0, 0));

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

        overviewlayout.getChildren().add(table);
        overviewlayout.setPrefSize(500, 600);
        overviewlayout.setAlignment(Pos.CENTER);

        return overviewlayout;
    }
}