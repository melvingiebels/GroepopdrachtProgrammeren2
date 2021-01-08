package GUI.Course;

import java.util.ArrayList;
import Database.CourseDAO;
import Domain.Course;
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

public class OverviewCourse {

    private AddCourse addView = new AddCourse();
    private UpdateCourse updateView = new UpdateCourse();
    private MainMenu mainMenuScene = new MainMenu();

    private ArrayList<Course> courses;
    private CourseDAO courseDAO = new CourseDAO();
    private BorderPane layout = new BorderPane();

    public OverviewCourse() {
        courses = courseDAO.getAllCourses();
    }

    public Scene getScene(Stage window) {
        window.setTitle("Overview course");

        // Create menu for main layout
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 0, 20, 20));
        topMenu.setSpacing(10);

        // Create buttons
        Button addBtn = new Button("Add course");
        Button mainMenuBtn = new Button("Back");

        addBtn.setMinSize(100, 30);
        mainMenuBtn.setMinSize(50, 30);

        // Add button functionality
        addBtn.setOnAction((event) -> {
            window.setScene(addView.getScene(window));
        });

        mainMenuBtn.setOnAction((event) -> {
            window.setScene(mainMenuScene.getScene(window));
        });

        topMenu.getChildren().addAll(mainMenuBtn, addBtn);

        layout.setTop(topMenu);
        layout.setCenter(createOverview(window));
        layout.setPrefSize(500, 600);

        return new Scene(layout);
    }

    public ScrollPane createOverview(Stage window) {
        // sync
        courses = courseDAO.getAllCourses();
        // layout
        ScrollPane overviewlayout = new ScrollPane();

        // column headers
        Label nameLabel = new Label("NAME");
        Label descriptionLabel = new Label("DESCRIPTION");
        nameLabel.setStyle("-fx-font-weight: bold");
        nameLabel.setMinWidth(150);
        descriptionLabel.setStyle("-fx-font-weight: bold");
        HBox tableColumns = new HBox(20, nameLabel, descriptionLabel);

        // Table
        VBox table = new VBox();
        table.getChildren().add(tableColumns);

        // Create records with course values
        for (Course course : courses) {
            // Course values
            Label name = new Label(course.getName());
            Label description = new Label(course.getDescription());
            name.setMinWidth(150);
            description.setMinWidth(150);

            // buttons
            Button updateBtn = new Button("Update");
            Button deleteBtn = new Button("Delete");

            // Make row
            HBox row = new HBox(20, name, description, updateBtn, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 0));

            // Add button events
            updateBtn.setOnAction((event) -> {
                layout.setCenter(updateView.getView(course));
                window.setTitle("Update course");
            });
            deleteBtn.setOnAction((event) -> {
                courseDAO.removeCourse(course.getName());
                table.getChildren().remove(row);
            });

            // add records inside the table
            table.getChildren().addAll(row);
        }

        overviewlayout.setContent(table);
        overviewlayout.setPrefSize(500, 600);
        overviewlayout.setPadding(new Insets(0, 0, 0, 20));
        return overviewlayout;
    }
}
