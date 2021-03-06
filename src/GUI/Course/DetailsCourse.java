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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        Label rightMenuHeader = new Label("Course info");
        rightMenuHeader.setStyle("-fx-font-weight: bold");
        
        Label courseName = new Label("Name: " + course.getName());
        Label topic = new Label("Topic: " + course.getTopic());
        Label difficulty = new Label("Difficulty: " + course.getDifficulty());
        Label description = new Label("Description: " + course.getDescription());
        Label total = new Label(
                "Total students that completed course: " + courseDAO.getTotalCompleted(course.getName()));
        Label genderPercentage = new Label();
        Label genderLabel = new Label("Percentage of gender that has a certificate");
        ComboBox<String> genderInput = new ComboBox<>();
        genderLabel.setStyle("-fx-font-weight: bold");

        genderInput.setValue("Choose one option");
        genderInput.getItems().addAll("Male", "Female");

        genderInput.setOnAction((event) -> {
            int percentage = courseDAO.getGenderPercentage(genderInput.getValue(), course.getName());
            genderPercentage.setText(genderInput.getValue() + ": " + percentage + "% has a certificate");
        });

        rightMenu.getChildren().addAll(rightMenuHeader, courseName, topic, difficulty, description, total, genderLabel, genderInput,
                genderPercentage);

        layout.setLeft(leftMenu);
        layout.setCenter(mainGrid);
        layout.setRight(rightMenu);

        Scene scene = new Scene(layout, 1200, 300);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    private VBox getCourseModulesOverview(Course course) {
        // Sync with database
        ArrayList<Module> modules = contentItemDAO.getCourseModules(course.getName());
        HashMap<Integer, Integer> avgPercentages = contentItemDAO.getAverageModulePercentage(course.getName());

        Label header = new Label(course.getName() + " modules:");
        header.setStyle("-fx-font-weight: bold");
        header.setMinWidth(200);

        HBox headRow = new HBox(header);

        VBox table = new VBox();
        table.getChildren().add(headRow);

        if (modules.size() != 0) {

            Label indexLabel = new Label("INDEX");
            Label moduleLabel = new Label("MODULE");

            indexLabel.setMinWidth(50);
            moduleLabel.setMinWidth(250);

            HBox tableColumns = new HBox(20, indexLabel, moduleLabel);
            tableColumns.setStyle("-fx-font-weight: bold");

            table.getChildren().addAll(tableColumns);

            // Fill table with modules
            for (Module module : modules) {
                HBox row = new HBox();

                Label index = new Label(String.valueOf(module.getIndexNumber()));
                Label title = new Label(module.getTitle());

                Button detailBtn = new Button("Details");


                detailBtn.setOnAction((event) -> {
                    this.getModuleDetailModal(module, avgPercentages);
                });

                index.setMinWidth(50);
                index.setMaxWidth(50);
                title.setMinWidth(250);
                title.setMaxWidth(250);

                row.getChildren().addAll(index, title, detailBtn);
                row.setSpacing(20);

                table.getChildren().add(row);
            }
        } else {
            Label message = new Label("No modules found");
            table.getChildren().add(message);
        }

        table.setMinHeight(100);
        table.setMinWidth(500);
        table.setPadding(new Insets(0, 0, 0, 20));
        return table;
    }

    private void getModuleDetailModal(Module module, HashMap<Integer, Integer> avgPercentages) {
        // Modal elements
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Details: " + module.getTitle());

        HBox list = new HBox();
        list.setPadding(new Insets(30, 50, 50, 50));

        // List elements
        VBox listSubjects = new VBox();

        Label versionHeader = new Label("Version: ");
        Label indexHeader = new Label("Index: ");
        Label titleHeader = new Label("Title: ");
        Label contactNameHeader = new Label("Contact name: ");
        Label contactMailHeader = new Label("Contact email: ");
        Label avgLabel = new Label("Average progress: ");
   
        listSubjects.getChildren().addAll(versionHeader, indexHeader, titleHeader, contactNameHeader, contactMailHeader, avgLabel);
        listSubjects.setStyle("-fx-font-weight: bold");

        VBox listValues = new VBox();

        Label version = new Label(String.valueOf(module.getVersion()));
        Label index = new Label(String.valueOf(module.getIndexNumber()));
        Label title = new Label(module.getTitle());
        Label contactName = new Label(module.getContactName());
        Label contactMail = new Label(module.getContactMail());
        Label avg = new Label(String.valueOf(avgPercentages.get(module.getContentItemId())));

        listValues.getChildren().addAll(version, index, title, contactName, contactMail, avg);

        // Make list
        list.getChildren().addAll(listSubjects, listValues);
        list.setSpacing(10);

        // Adding table to the scene
        Scene scene = new Scene(list);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}
