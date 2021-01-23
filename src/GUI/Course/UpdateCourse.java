package GUI.Course;

import java.util.ArrayList;

import Domain.Course;
import Domain.Module;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateCourse extends GenericGUI {

    private ArrayList<Module> modulesList = new ArrayList<>();

    public Scene getScene(Course course, Stage window) {

        // create views
        OverviewCourse courseOverview = new OverviewCourse();

        modulesList = contentItemDAO.getCourseModules(course.getName());

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
        Label moduleLabel = new Label("Total modules: " + modulesList.size());

        TextField nameInput = new TextField(course.getName());
        TextField topicInput = new TextField(course.getTopic());
        TextArea descriptionInput = new TextArea(course.getDescription());
        ComboBox<String> difficultyInput = new ComboBox<>();

        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");
        difficultyInput.setValue(course.getDifficulty());

        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");

        // Buttons
        Button modalBtn = new Button("Add modules");
        Button overviewBtn = new Button("Back");
        Button submit = new Button("SUBMIT");

        // Switch to overview
        overviewBtn.setOnAction((event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        // Change modules
        modalBtn.setOnAction((event) -> {
            modulesList = toggleModal(modulesList);
            moduleLabel.setText("Total modules: " + modulesList.size());
        });

        // update event
        submit.setOnAction((event) -> {
            Course updatedCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                    (String) difficultyInput.getValue());

            courseDAO.updateCourse(updatedCourse);
            for (Module module : modulesList) {
                contentItemDAO.updateModule(module, updatedCourse.getName());
            }

            succesMsg.setText("Student has been successfully updated");
        });

        leftMenu.getChildren().addAll(overviewBtn);
        form.getChildren().addAll(title, succesMsg, nameLabel, nameInput, topicLabel, topicInput, descriptionLabel,
                descriptionInput, difficultyLabel, difficultyInput, moduleLabel, modalBtn, submit);

        layout.setLeft(leftMenu);
        layout.setCenter(form);

        return new Scene(layout);
    }

    private ArrayList<Module> toggleModal(ArrayList<Module> test) {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add modules to course");

        Label title = new Label("Choose a avaible module: ");
        title.setStyle("-fx-font-weight: bold");

        Button saveBtn = new Button("Save");
        VBox moduleLayout = new VBox(10);

        moduleLayout.setPadding(new Insets(30, 50, 50, 50));

        moduleLayout.getChildren().add(title);

        // Make arrayList of the selected modules
        ArrayList<Module> availableModules = contentItemDAO.getAvaibleModules();
        ArrayList<Module> selectedModules = new ArrayList<>();

        for (Module module : test) {
            if (!module.isActive()) {
                module.toggleActive();
            }
            availableModules.add(module);
        }

        for (Module module : availableModules) {
            CheckBox checkBox = new CheckBox(module.getTitle());

            if (module.isActive()) {
                checkBox.setSelected(true);
                selectedModules.add(module);
            }

            checkBox.setOnAction((event) -> {
                module.toggleActive();

                if (module.isActive()) {
                    selectedModules.add(module);
                } else {
                    selectedModules.remove(module);
                }
            });
            moduleLayout.getChildren().add(checkBox);
        }
        moduleLayout.getChildren().add(saveBtn);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(moduleLayout);

        saveBtn.setOnAction(e -> popupwindow.close());

        Scene scene1 = new Scene(scrollPane, 400, 400);

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

        return selectedModules;
    }
}
