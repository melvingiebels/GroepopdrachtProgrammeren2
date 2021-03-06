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

    public Scene getScene(Stage window) {
        // Create views for changing the scene
        MainMenu mainMenuScene = new MainMenu();
        AddStudent addView = new AddStudent();

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
        layout.setPrefSize(700, 600);
        window.setTitle("Student overview");

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    // create overview with students + buttons
    private ScrollPane createOverView(Stage window) {
        // Create views for changing the scene
        DetailsStudent detailsStudent = new DetailsStudent();
        UpdateStudent updateView = new UpdateStudent();

        // Sync with database
        ArrayList<Student> students = studentDAO.getAllStudents();

        // main layout scrollpane
        ScrollPane overviewlayout = new ScrollPane();

        // "table" headers
        Label emailLabel = new Label("EMAIL");
        Label nameLabel = new Label("NAME");
        emailLabel.setStyle("-fx-font-weight: bold");
        emailLabel.setMinWidth(220);
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setMinWidth(150);

        // Individual record
        HBox headRow = new HBox(20, emailLabel, nameLabel);

        // Table grid
        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with students with update and delete buttons
        for (Student student : students) {
            Label email = new Label(student.getEmail());
            Label name = new Label(student.getName());
            email.setMinWidth(220);
            name.setMinWidth(150);

            Button updateBtn = new Button("Update");
            updateBtn.setId("orangeBtn");
            Button deleteBtn = new Button("Delete");
            deleteBtn.setId("redBtn");
            Button detailBtn = new Button("Details");

            HBox row = new HBox(20, email, name, updateBtn, detailBtn, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 0));

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
        overviewlayout.setPadding(new Insets(0, 0, 0, 20));
        return overviewlayout;
    }
}