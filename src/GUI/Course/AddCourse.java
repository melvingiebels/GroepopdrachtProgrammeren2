package GUI.Course;

import java.util.ArrayList;

import Database.CourseDAO;
import Domain.Course;
import Domain.Module;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddCourse {

    private BorderPane layout = new BorderPane();
    private CourseDAO courseDAO = new CourseDAO();

    public Scene getScene(Stage window) {
        
        // create views
        OverviewCourse courseOverview = new OverviewCourse();

        // main layout
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));;
        Label title = new Label("CREATE A NEW COURSE");
        title.setStyle("-fx-font-weight: bold");

        // Layout for the form elements
        VBox form = new VBox();
        form.setPadding(new Insets(30, 50, 50, 50));
        form.setSpacing(10);
        layout.setCenter(form);

        // Get all avaible modules
        ArrayList<Module> modules = courseDAO.getAvaibleModules();

        // Inputfields & properties
        TextField nameInput = new TextField();
        TextField topicInput = new TextField();
        TextArea descriptionInput = new TextArea();
        ComboBox<String> difficultyInput = new ComboBox<>();
        difficultyInput.setValue("Beginner");
        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");

        for(Module module : modules) {
            CheckBox checkBox = new CheckBox(module.getTitle());
            checkBox.setOnAction((event) -> System.out.println("Ja"));
        }

        // Labels for the inputfields
        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");
        Label name = new Label("Name: ");
        Label topic = new Label("Topic: ");
        Label description = new Label("Description: ");
        Label difficulty = new Label("Difficulty: ");

        // Button + addEvent
        Button overviewBtn = new Button("Back");        
        Button submitBtn = new Button("SUBMIT");

        // Switch to overview
        overviewBtn.setOnAction((Event) -> {
            window.setScene(courseOverview.getScene(window));
        });
        
        // submit action
        submitBtn.setOnAction((event) -> {
            Course newCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                    difficultyInput.getValue());
            courseDAO.addCourse(newCourse);
            succesMsg.setText(nameInput.getText() + " Has been added");
        });

        // Placing elements inside the layout elements
        form.getChildren().addAll(title, succesMsg, name, nameInput, topic, topicInput, description, descriptionInput,
                difficulty, difficultyInput, submitBtn);
        leftMenu.getChildren().addAll(overviewBtn);
        layout.setLeft(leftMenu);
        layout.setCenter(form);
        window.setTitle("Add new course");
        return new Scene(layout);
    }

}
