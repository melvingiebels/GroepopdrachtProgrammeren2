package GUI.Student.Registration;

import java.util.ArrayList;
import Domain.Registration;
import Domain.Student;
import GUI.GenericGUI;
import GUI.Student.DetailsStudent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewRegistration extends GenericGUI {

    public Scene getScene(Stage window, Student student) {
        window.setTitle("Registrations of: " + student.getEmail());
        // Layouts of the overview
        BorderPane layout = new BorderPane();
        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(20, 0, 20, 20));
        layout.setTop(topMenu);

        // menu elements
        DetailsStudent detailsStudent = new DetailsStudent();
        Button backBtn = new Button("Back");
        backBtn.setMinSize(50, 30);
        backBtn.setOnAction((event) -> {
            window.setScene(detailsStudent.getScene(window, student));
        });

        Button addBtn = new Button("Add registration");
        addBtn.setId("greenBtn");
        AddRegistration addregistration = new AddRegistration();
        addBtn.setOnAction((event) -> {
            window.setScene(addregistration.getScene(window, student));
        });
        addBtn.setMinSize(100, 30);
        topMenu.getChildren().addAll(backBtn, addBtn);
        topMenu.setSpacing(20);

        // overview element
        layout.setCenter(this.createOverview(student));

        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

    private ScrollPane createOverview(Student student) {
        // get fresh list of registrations
        ArrayList<Registration> registrations = studentDAO.getRegistrations(student);

        // list columns headers
        Label courseNameLabel = new Label("COURSE NAME");
        Label registrationDateLabel = new Label("REGISTRATION DATE");
        courseNameLabel.setStyle("-fx-font-weight: bold");
        courseNameLabel.setMinWidth(150);
        registrationDateLabel.setStyle("-fx-font-weight: bold");

        HBox headRow = new HBox(20, courseNameLabel, registrationDateLabel);
        headRow.setPadding(new Insets(0, 0, 0, 20));

        // table
        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with registrations with update and delete buttons
        for (Registration registration : registrations) {
            // Get the values
            Label courseName = new Label(registration.getCourseName());
            Label registrationDate = new Label(registration.getRegistrationDate());

            courseName.setMinWidth(150);
            registrationDate.setMinWidth(150);

            // add button
            Button deleteBtn = new Button("Delete");
            deleteBtn.setId("redBtn");

            // Build row
            HBox row = new HBox(20, courseName, registrationDate, deleteBtn);
            row.setPadding(new Insets(10, 0, 0, 20));

            // Button events
            deleteBtn.setOnAction((event) -> {
                studentDAO.removeRegistration(registration);
                registrations.remove(registration);
                student.setRegistrations(registrations);
                table.getChildren().remove(row);
            });

            table.getChildren().add(row);
        }

        // Build table
        ScrollPane overview = new ScrollPane();
        overview.setContent(table);
        overview.setPrefSize(500, 600);
        overview.setPadding(new Insets(0, 0, 0, 20));
        return overview;
    }
}
