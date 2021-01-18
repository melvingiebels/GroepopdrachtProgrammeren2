package GUI.Course;

import Domain.Course;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UpdateCourse extends GenericGUI {

    public Parent getView(Course course) {
        // Layout
        BorderPane layout = new BorderPane();

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
        ComboBox<String> difficultyInput = new ComboBox<String>();
        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");
        difficultyInput.setValue(course.getDifficulty());

        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");

        Button submit = new Button("SUBMIT");

        // update event
        submit.setOnAction((event) -> {
            Course updatedCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                    (String) difficultyInput.getValue());

            courseDAO.updateCourse(updatedCourse);
            succesMsg.setText("Student has been successfully updated");
        });

        form.getChildren().addAll(title, succesMsg, nameLabel, nameInput, topicLabel, topicInput, descriptionLabel,
                descriptionInput, difficultyLabel, difficultyInput, submit);

        return layout;
    }
}
