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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewStudent extends Application {

    private StudentDAO studentDAO = new StudentDAO();
    private ArrayList<Student> students = studentDAO.getAllStudents();

    @Override
    public void start(Stage window) throws Exception {
        AddStudent addView = new AddStudent();

        // Create main layout
        BorderPane layout = new BorderPane();

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
        StackPane overviewLayout = createView("Overview students");
        
        layout.setCenter(overviewLayout);
    

        // Add button actions
        overviewBtn.setOnAction((event) -> layout.setCenter(overviewLayout));
        addBtn.setOnAction((event) -> layout.setCenter(addView.getView()));
    
        layout.setCenter(overviewLayout);

        Scene view = new Scene(layout);

        window.setScene(view);
        window.show();

    }

    private StackPane createView(String text) {

        StackPane layout = new StackPane();

        TableView<Student> table = new TableView<>();
        
        TableColumn<Student, String> column1 = new TableColumn<>("Email");
        column1.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Student, String> column2 = new TableColumn<>("Update");
        column1.setCellValueFactory(new PropertyValueFactory<>("update"));

        table.getColumns().add(column1);
        table.getColumns().add(column2);


        for(Student student : students) {
            System.out.println(student);
            table.getItems().add(student);  
        }

        VBox vbox = new VBox(table);

        layout.setPrefSize(500, 600);


        // for(Student student : students) {
        //     layout.getChildren().add(new Label(student.getEmail()));
        // }
        layout.getChildren().add(vbox);
        layout.getChildren().add(new Label(text));
        layout.setAlignment(Pos.CENTER);

        return layout;
    }

    public static void main(String[] args) {
        launch(OverviewStudent.class);
    }

}
