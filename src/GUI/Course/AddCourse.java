package GUI.Course;

import Database.CourseDAO;
import Domain.Course;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        // Inputfields & properties
        TextField nameInput = new TextField();
        TextField topicInput = new TextField();
        TextArea descriptionInput = new TextArea();
        ComboBox difficultyInput = new ComboBox();
        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");

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

        overviewBtn.setOnAction((Event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        submitBtn.setOnAction((event) -> {
            CourseDAO courseDAO = new CourseDAO();
            Course newCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                    (String) difficultyInput.getValue());
            courseDAO.addCourse(newCourse);
            succesMsg.setText(nameInput.getText() + " Has been added");
        });

        // Placing elements inside the layout elements
        form.getChildren().addAll(title, succesMsg, name, nameInput, topic, topicInput, description, descriptionInput,
                difficulty, difficultyInput, submitBtn);
        leftMenu.getChildren().addAll(overviewBtn);
        layout.setLeft(leftMenu);
        layout.setCenter(form);
        window.setTitle("Add new student");
        return new Scene(layout);
    }

}
