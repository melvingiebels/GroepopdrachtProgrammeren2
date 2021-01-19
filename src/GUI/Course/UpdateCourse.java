package GUI.Course;

import Domain.Course;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Parent;
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

public class UpdateCourse extends GenericGUI {

    public Scene getScene(Course course, Stage window) {

        // create views
        OverviewCourse courseOverview = new OverviewCourse();

        // Layout
        BorderPane layout = new BorderPane();
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));

        // Form Layout
        VBox form = new VBox();
        form.setPadding(new Insets(10, 50, 50, 50));
        form.setSpacing(10);
        layout.setCenter(form);

        // Form elements
        Label title = new Label("Update course: " + course.getName());
        title.setStyle("-fx-font-weight: bold");

        Label nameLabel = new Label("Name: ");
        Label topicLabel = new Label("Topic: ");
        Label descriptionLabel = new Label("Description: ");
        Label difficultyLabel = new Label("Difficulty: ");

        TextField nameInput = new TextField(course.getName());
        TextField topicInput = new TextField(course.getTopic());
        TextArea descriptionInput = new TextArea(course.getDescription());
        ComboBox<String> difficultyInput = new ComboBox<>();

        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");
        difficultyInput.setValue(course.getDifficulty());

        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");

        Button overviewBtn = new Button("Back");
        Button submit = new Button("SUBMIT");

        // Switch to overview
        overviewBtn.setOnAction((event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        // update event
        submit.setOnAction((event) -> {
            Course updatedCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                    (String) difficultyInput.getValue());

            courseDAO.updateCourse(updatedCourse);
            succesMsg.setText("Student has been successfully updated");
        });

        leftMenu.getChildren().addAll(overviewBtn);;
        form.getChildren().addAll(title, succesMsg, nameLabel, nameInput, topicLabel, topicInput, descriptionLabel,
                descriptionInput, difficultyLabel, difficultyInput, submit);

        layout.setLeft(leftMenu);
        layout.setCenter(form);

        return new Scene(layout);
    }
}
