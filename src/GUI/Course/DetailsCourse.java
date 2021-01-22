package GUI.Course;

import java.util.ArrayList;

import Domain.Course;
import Domain.Module;
import GUI.GenericGUI;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

        rightMenu.getChildren().addAll(courseName, topic, difficulty, description);

        layout.setLeft(leftMenu);
        layout.setCenter(mainGrid);
        layout.setRight(rightMenu);

        return new Scene(layout);
    }

    private VBox getCourseModulesOverview(Course course) {
        // Sync with database
        ArrayList<Module> modules = contentItemDAO.getCourseModules(course.getName());

        Label header =  new Label(course.getName() + " modules:");
        header.setStyle("-fx-font-weight: bold");

        HBox headRow = new HBox(header);

        VBox table = new VBox();
        table.getChildren().add(headRow);
        
        if (modules.size() != 0) {

            Label indexLabel = new Label("INDEX");
            Label moduleLabel = new Label("MODULE");

            indexLabel.setMinWidth(50);

            HBox tableColumns = new HBox(20, indexLabel, moduleLabel);
            tableColumns.setStyle("-fx-font-weight: bold");

            table.getChildren().addAll(tableColumns);

            // Fill table with modules
            for (Module module : modules) {
                HBox row = new HBox();

                Label index = new Label(String.valueOf(module.getIndexNumber()));
                Label title = new Label(module.getTitle());

                index.setMinWidth(50);
                index.setMaxWidth(50);
                title.setMinWidth(250);
                title.setMaxWidth(250);

                row.getChildren().addAll(index, title);
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
