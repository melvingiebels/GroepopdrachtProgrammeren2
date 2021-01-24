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

public class AddCourse extends GenericGUI {

    private ArrayList<Module> availableModules = contentItemDAO.getAvaibleModules();
    private ArrayList<Module> modulesList = new ArrayList<>();

    public Scene getScene(Stage window) {

        // Create view
        OverviewCourse courseOverview = new OverviewCourse();

        // Main layout
        BorderPane layout = new BorderPane();
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));

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
        ComboBox<String> difficultyInput = new ComboBox<>();
        difficultyInput.setValue("Beginner");
        difficultyInput.getItems().addAll("Beginner", "Advanced", "Expert");

        // Labels for the inputfields
        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");
        Label name = new Label("Name: ");
        Label topic = new Label("Topic: ");
        Label description = new Label("Description: ");
        Label difficulty = new Label("Difficulty: ");
        Label moduleLabel = new Label("Aantal modules: 0");

        // Button + addEvent
        Button overviewBtn = new Button("Back");
        Button modalBtn = new Button("Add modules");
        Button submitBtn = new Button("SUBMIT");
        submitBtn.setId("greenBtn");

        // Switch to overview
        overviewBtn.setOnAction((event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        // Change modules
        modalBtn.setOnAction((event) -> {
            modulesList = toggleModal();
            moduleLabel.setText("Aantal modules: " + modulesList.size());
        });

        // submit action
        submitBtn.setOnAction((event) -> {
            // reset tags / messages when trying again
            name.setText("Name: ");
            name.setStyle(null);
            topic.setText("Topic: ");
            topic.setStyle(null);
            description.setText("Description: ");
            description.setStyle(null);

            // Valid switch
            boolean isValidCourse = true;

            // Check name
            if (nameInput.getText().equals("")) {
                name.setText("Name: cannot be empty!");
                name.setStyle("-fx-text-fill: red");
                nameInput.setStyle("-fx-text-box-border: red");
                isValidCourse = false;
            } else {
                nameInput.setStyle("-fx-text-box-border: green");
            }

            if (topicInput.getText().equals("")) {
                topic.setText("Topic: cannot be empty!");
                topic.setStyle("-fx-text-fill: red");
                topicInput.setStyle("-fx-text-box-border: red");
                isValidCourse = false;
            } else {
                topicInput.setStyle("-fx-text-box-border: green");
            }

            if (descriptionInput.getText().equals("")) {
                description.setText("Description: cannot be empty!");
                description.setStyle("-fx-text-fill: red");
                descriptionInput.setStyle("-fx-text-box-border: red");
                isValidCourse = false;
            } else {
                descriptionInput.setStyle("-fx-text-box-border: green");
            }

            if (isValidCourse) {
                Course newCourse = new Course(nameInput.getText(), topicInput.getText(), descriptionInput.getText(),
                        difficultyInput.getValue());
                courseDAO.addCourse(newCourse);

                for (Module module : modulesList) {
                    contentItemDAO.updateModule(module, newCourse.getName());
                }

                succesMsg.setText(nameInput.getText() + " Has been added");
            }

        });

        // Placing elements inside the layout elements
        form.getChildren().addAll(title, succesMsg, name, nameInput, topic, topicInput, description, descriptionInput,
                difficulty, difficultyInput, moduleLabel, modalBtn, submitBtn);
        leftMenu.getChildren().addAll(overviewBtn);

        layout.setLeft(leftMenu);
        layout.setCenter(form);
        window.setTitle("Add new course");
        layout.setPrefSize(600, 600);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    private ArrayList<Module> toggleModal() {

        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Add modules to course");

        Label title = new Label("Choose a avaible module: ");
        title.setStyle("-fx-font-weight: bold");

        Button saveBtn = new Button("Save");
        saveBtn.setId("greenBtn");
        VBox moduleLayout = new VBox(10);

        moduleLayout.setPadding(new Insets(30, 50, 50, 50));

        moduleLayout.getChildren().add(title);

        // Make arrayList of the selected modules
        ArrayList<Module> selectedModules = new ArrayList<>();

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
        scene1.getStylesheets().add("./GUI/Stylesheet.css");

        popupwindow.setScene(scene1);
        popupwindow.showAndWait();

        return selectedModules;
    }

}
