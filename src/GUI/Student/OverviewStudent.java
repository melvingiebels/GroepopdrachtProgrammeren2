package GUI;

import java.util.ArrayList;

import Database.StudentDAO;
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

public class OverviewStudent extends Application {

    private StudentDAO studentDAO = new StudentDAO();
    private ArrayList<Student> students;
    private UpdateStudent updateView = new UpdateStudent();
    private BorderPane layout = new BorderPane();

    @Override
    public void start(Stage window) throws Exception {
        AddStudent addView = new AddStudent();

        // Create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);

        // Create buttons for menu
        Button overviewBtn = new Button("Overview");
        Button addBtn =  new Button("Add student");
    
        // Add buttons to menu
        menu.getChildren().addAll(overviewBtn, addBtn);

        layout.setTop(menu);

        // Add subviews
        StackPane overviewLayout = createOverView();
        
        layout.setCenter(overviewLayout);
    

        // Add button actions
        overviewBtn.setOnAction((event) -> layout.setCenter(overviewLayout));
        addBtn.setOnAction((event) -> layout.setCenter(addView.getView()));
    
        layout.setCenter(overviewLayout);

        Scene view = new Scene(layout);

        window.setScene(view);
        window.show();

    }

    private StackPane createOverView() {
        students = studentDAO.getAllStudents();

        StackPane overviewlayout = new StackPane();

        VBox table = new VBox();
        Label emailLabel = new Label("EMAIL");
        Label nameLabel =  new Label("NAME");

        emailLabel.setMinWidth(150);

        HBox headRow = new HBox(20, emailLabel, nameLabel);

        table.getChildren().add(headRow);
        
        overviewlayout.setPrefSize(500, 600);

     
        for(Student student : students) {
            Label email = new Label(student.getEmail());
            Label name = new Label(student.getName());
            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");

            email.setMinWidth(150);
            name.setMinWidth(150);
            HBox row = new HBox(20, email, name, updateBtn, deleteBtn);

            updateBtn.setOnAction((event) -> {
                layout.setCenter(updateView.getView(student));
            });

            deleteBtn.setOnAction((event) ->  {
                studentDAO.removeStudent(student.getEmail());
                table.getChildren().remove(row);
            });
            
            table.getChildren().add(row);
        }

        overviewlayout.getChildren().add(table);
        overviewlayout.setAlignment(Pos.CENTER);

        return overviewlayout;
    }

    public static void main(String[] args) {
        launch(OverviewStudent.class);
    }

}
