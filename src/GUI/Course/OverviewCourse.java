package GUI.Course;

import java.util.ArrayList;

import javax.swing.table.TableColumn;

import Database.CourseDAO;
import Domain.Course;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewCourse extends Application {

    private AddCourse addView = new AddCourse();
    private ArrayList<Course> courses;
    private CourseDAO courseDAO = new CourseDAO();

    public OverviewCourse() {
    }

    // get + set for sync
    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    @Override
    public void start(Stage window) throws Exception {
        // Main layout
        BorderPane layout = new BorderPane();

        // Menu
        HBox menu = new HBox();
        menu.setPadding(new Insets(20, 20, 20, 20));
        menu.setSpacing(10);
        Button overviewBtn = new Button("Overview");
        overviewBtn.setOnAction((event) -> {
            layout.setCenter(addView.getView());
        });
        Button addBtn = new Button("Add course");
        menu.getChildren().addAll(overviewBtn, addBtn);

        // Overview view
        StackPane overview = new StackPane();

        // Placing elements inside the main layout elements
        layout.setTop(menu);
        // Placing elements inside the overview layout elements
    }

    // public StackPane createOverview() {
    //     courses = courseDAO.getAllCourses();
    //     StackPane overview = new StackPane();

    //     for (Course x : courses) {

    //     }
    // }

    public static void main(String[] args) {
        launch(OverviewCourse.class);
    }

}
