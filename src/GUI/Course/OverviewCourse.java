package GUI.Course;

import java.util.ArrayList;
import Database.CourseDAO;
import Domain.Course;
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

public class OverviewCourse extends Application {

    private AddCourse addView = new AddCourse();
    private UpdateCourse updateView = new UpdateCourse();

    private ArrayList<Course> courses;
    private CourseDAO courseDAO = new CourseDAO();
    private BorderPane mainLayout = new BorderPane();

    public OverviewCourse() {
        courses = courseDAO.getAllCourses();
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Overview course");

        // Create menu for main layout
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 0, 20, 0));
        menu.setSpacing(10);

        Button overviewBtn = new Button("Overview");
        Button addBtn = new Button("Add course");
        overviewBtn.setOnAction((event) -> {
            mainLayout.setCenter(this.createOverview(window));
            window.setTitle("Overview course");
        });
        addBtn.setOnAction((event) -> {
            mainLayout.setCenter(addView.getView());
            window.setTitle("Add new student");
        });

        menu.getChildren().addAll(overviewBtn, addBtn);

        mainLayout.setTop(menu);
        mainLayout.setCenter(createOverview(window));
        Scene scene = new Scene(mainLayout);
        window.setScene(scene);
        window.show();
    }

    public StackPane createOverview(Stage window) {
        // sync
        courses = courseDAO.getAllCourses();
        // layout
        StackPane overviewlayout = new StackPane();

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
                mainLayout.setCenter(updateView.getView(course));
                window.setTitle("Update course");
            });
            deleteBtn.setOnAction((event) -> {
                courseDAO.removeCourse(course.getName());
                table.getChildren().remove(row);
            });

            // add records inside the table
            table.getChildren().addAll(row);
        }

        overviewlayout.getChildren().add(table);
        overviewlayout.setPrefSize(500, 600);
        overviewlayout.setAlignment(Pos.CENTER);
        return overviewlayout;
    }

    public static void main(String[] args) {
        launch(OverviewCourse.class);
    }

}
