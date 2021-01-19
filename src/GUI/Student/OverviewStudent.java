package GUI.Student;

import java.util.ArrayList;

import Domain.Student;
import GUI.GenericGUI;
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

public class OverviewStudent extends GenericGUI {

    private ArrayList<Student> students;

    // Create views
    private AddStudent addView = new AddStudent();
    private UpdateStudent updateView = new UpdateStudent();
    private MainMenu mainMenuScene = new MainMenu();
    private DetailsStudent detailsStudent = new DetailsStudent();

    public Scene getScene(Stage window) {

        // main layout
        BorderPane layout = new BorderPane();

        // Create menu for overview and add student buttons
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 0, 20, 20));

        // Create buttons for menu
        Button mainMenuBtn = new Button("Back");
        Button addBtn = new Button("Add student");
        addBtn.setId("greenBtn");

        // Button styling
        mainMenuBtn.setMinSize(50, 30);
        addBtn.setMinSize(100, 30);

        // Add buttons to menu
        topMenu.getChildren().addAll(mainMenuBtn, addBtn);
        topMenu.setSpacing(20);

        // Add button actions
        // Switch to addStudent view
        addBtn.setOnAction((event) -> {
            window.setScene(addView.getScene(window));
            window.setTitle("Add new student");
        });

        // Switch to go back to the mainmenu
        mainMenuBtn.setOnAction((event) -> {
            window.setScene(mainMenuScene.getScene(window));
        });

        layout.setTop(topMenu);
        layout.setCenter(createOverView(window));
        window.setTitle("Student overview");

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    // create overview with students + buttons
    private ScrollPane createOverView(Stage window) {

        // Sync with database
        students = studentDAO.getAllStudents();

        // main layout scrollpane
        ScrollPane overviewlayout = new ScrollPane();

        // "table" headers
        Label emailLabel = new Label("EMAIL");
        Label nameLabel = new Label("NAME");
        emailLabel.setStyle("-fx-font-weight: bold");
        emailLabel.setMinWidth(150);
        nameLabel.setStyle("-fx-font-weight: bold");

        // Individual record
        HBox headRow = new HBox(20, emailLabel, nameLabel);
        headRow.setPadding(new Insets(0, 0, 0, 20));

        // Table grid
        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with students with update and delete buttons
        for (Student student : students) {
            Label email = new Label(student.getEmail());
            Label name = new Label(student.getName());
            email.setMinWidth(150);
            name.setMinWidth(150);

            Button updateBtn = new Button("Update");
            updateBtn.setId("orangeBtn");
            Button deleteBtn = new Button("Delete");
            deleteBtn.setId("redBtn");
            Button detailBtn = new Button("Details");

            HBox row = new HBox(20, email, name, updateBtn, detailBtn, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 20));

            updateBtn.setOnAction((event) -> {
                window.setScene(updateView.getScene(student, window));
                window.setTitle("Update student");
            });

            deleteBtn.setOnAction((event) -> {
                studentDAO.removeStudent(student.getEmail());
                table.getChildren().remove(row);
            });

            detailBtn.setOnAction((event) -> {
                window.setScene(detailsStudent.getScene(window, student));
            });

            table.getChildren().add(row);
        }
        overviewlayout.setContent(table);
        overviewlayout.setPrefSize(600, 600);
        overviewlayout.setPadding(new Insets(0, 0, 0, 20));
        return overviewlayout;
    }
}