package GUI.Course;

import java.util.ArrayList;
import Domain.Course;
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

public class OverviewCourse extends GenericGUI {

    public Scene getScene(Stage window) {
        // Create views for changing the scene
        AddCourse addView = new AddCourse();
        MainMenu mainMenuScene = new MainMenu();

        window.setTitle("Overview course");
        BorderPane layout = new BorderPane();

        // Create menu for main layout
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 0, 20, 20));
        topMenu.setSpacing(10);

        // Create buttons
        Button addBtn = new Button("Add course");
        addBtn.setId("greenBtn");
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
        layout.setPrefSize(650, 600);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    public ScrollPane createOverview(Stage window) {
        DetailsCourse detailsView = new DetailsCourse();
        UpdateCourse updateView = new UpdateCourse();
        // sync
        ArrayList<Course> courses = courseDAO.getAllCourses();
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

            // Action Buttons
            Button updateBtn = new Button("Update");
            updateBtn.setId("orangeBtn");
            Button detailsBtn = new Button("Details");
            Button deleteBtn = new Button("Delete");
            deleteBtn.setId("redBtn");

            // Make row
            HBox row = new HBox(20, name, description, updateBtn, detailsBtn, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 0));

            // Add button events
            updateBtn.setOnAction((event) -> {
                window.setScene(updateView.getScene(course, window));
                window.setTitle("Update course");
            });
            detailsBtn.setOnAction((event) -> {
                window.setScene(detailsView.getScene(course, window));
                window.setTitle(course.getName() + " details");
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
