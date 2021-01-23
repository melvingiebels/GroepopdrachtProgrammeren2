package GUI.Course;

import java.util.ArrayList;
import java.util.HashMap;

import Domain.Course;
import Domain.Module;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetailsCourse extends GenericGUI {
    
    public Scene getScene(Course course, Stage window) {
        
        // Create view for back button
        OverviewCourse courseOverview = new OverviewCourse();

        // Main Layout
        BorderPane layout = new BorderPane();

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction((event) -> {
            window.setScene(courseOverview.getScene(window));
        });

        backBtn.setMinSize(50, 30);

        // Main elements
        VBox mainGrid = new VBox();
        mainGrid.setPadding(new Insets(30, 50, 50, 50));
        mainGrid.setSpacing(10);
        mainGrid.getChildren().add(getCourseModulesOverview(course));

        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        leftMenu.getChildren().addAll(backBtn);

        // RightMenu Courese information
        VBox rightMenu = new VBox();
        rightMenu.setPadding(new Insets(30, 50, 50, 50));
        rightMenu.setSpacing(10);
        Label rightMenuHeader = new Label("Student details");
        rightMenuHeader.setStyle("-fx-font-weight: bold");
        Label courseName = new Label("Name: " + course.getName());
        Label topic = new Label("Topic: " + course.getTopic());
        Label difficulty = new Label("Difficulty: " + course.getDifficulty());
        Label description = new Label("Description: " + course.getDescription());
        Label total = new Label("Total students that completed course: " + courseDAO.getTotalCompleted(course.getName()));
        Label genderPercentage = new Label();

        ComboBox<String> genderInput = new ComboBox<>();

        genderInput.setValue("Choose one option");
        genderInput.getItems().addAll("Male", "Female");

        genderInput.setOnAction((event) -> {
            int percentage = courseDAO.getGenderPercentage(genderInput.getValue());
            genderPercentage.setText(genderInput.getValue() + " percentage: " + percentage);
        });

        rightMenu.getChildren().addAll(courseName, topic, difficulty, description, total, genderInput, genderPercentage);

        layout.setLeft(leftMenu);
        layout.setCenter(mainGrid);
        layout.setRight(rightMenu);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);

        return new Scene(scrollPane, 1500, 400);
    }

    private VBox getCourseModulesOverview(Course course) {
        // Sync with database
        ArrayList<Module> modules = contentItemDAO.getCourseModules(course.getName());
        HashMap<Integer, Integer> avgPercentages = contentItemDAO.getAverageModulePercentage(course.getName());

        Label header =  new Label(course.getName() + " modules:");
        header.setStyle("-fx-font-weight: bold");

        HBox headRow = new HBox(header);

        VBox table = new VBox();
        table.getChildren().add(headRow);
        
        if (modules.size() != 0) {

            Label indexLabel = new Label("INDEX");
            Label moduleLabel = new Label("MODULE");
            Label avgLabel = new Label("AVERAGE PROGRESS");

            indexLabel.setMinWidth(50);
            moduleLabel.setMinWidth(200);

            HBox tableColumns = new HBox(20, indexLabel, moduleLabel, avgLabel);
            tableColumns.setStyle("-fx-font-weight: bold");

            table.getChildren().addAll(tableColumns);

            // Fill table with modules
            for (Module module : modules) {
                HBox row = new HBox();

                Label index = new Label(String.valueOf(module.getIndexNumber()));
                Label title = new Label(module.getTitle());
                Label avg = new Label(String.valueOf(avgPercentages.get(module.getContentItemId())));

                index.setMinWidth(50);
                index.setMaxWidth(50);
                title.setMinWidth(200);
                title.setMaxWidth(250);

                row.getChildren().addAll(index, title, avg);
                row.setSpacing(20);

                table.getChildren().add(row);
            }
        } else {
            Label message = new Label("No modules found");
            table.getChildren().add(message);
        }
        
        table.setMinHeight(100);
        table.setMinWidth(700);
        table.setPadding(new Insets(0, 0, 0, 20));
        return table;
    }
}
