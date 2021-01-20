package GUI.Student;

import java.util.ArrayList;

import Domain.Student;
import Domain.Module;
import GUI.GenericGUI;
import GUI.Student.Registration.OverviewRegistration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsStudent extends GenericGUI {

    private OverviewRegistration overviewRegistration = new OverviewRegistration();

    public Scene getScene(Stage window, Student student) {
        // create overview page for back button
        OverviewStudent overviewStudent = new OverviewStudent();

        // main layout
        BorderPane layout = new BorderPane();
        window.setTitle("Student details");

        // Back button
        Button backBtn = new Button("Back");
        backBtn.setOnAction((event) -> {
            window.setScene(overviewStudent.getScene(window));
        });

        backBtn.setMinSize(50, 30);

        // CENTER - center lists (courses, webcasts)
        VBox mainGrid = new VBox();
        mainGrid.setPadding(new Insets(30, 50, 50, 50));
        mainGrid.setSpacing(10);
        mainGrid.getChildren().add(getCoursesOverview(student));

        // LEFT - back button
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        leftMenu.getChildren().addAll(backBtn);

        // RIGHT - properties pane
        VBox rightMenu = new VBox();
        rightMenu.setPadding(new Insets(30, 50, 50, 50));
        rightMenu.setSpacing(10);
        Label rightMenuHeader = new Label("Student properties");
        rightMenuHeader.setStyle("-fx-font-weight: bold");
        Label studentName = new Label("Name: " + student.getName());
        Label studentEmail = new Label("Email: " + student.getEmail());
        Label studentBirthdate = new Label("Birthday: " + student.getBirthdate());
        Label studentGender = new Label("Gender: " + student.getGender());
        Label studentAddress = new Label(
                "Address: " + student.getAddress() + ", " + student.getCity() + "," + student.getCountry());

        // registration button
        Button registrationsBtn = new Button("Registrations");
        registrationsBtn.setOnAction((event) -> {
            window.setScene(overviewRegistration.getScene(window, student));
        });
        rightMenu.getChildren().addAll(rightMenuHeader, studentName, studentEmail, studentBirthdate, studentGender,
                studentAddress, registrationsBtn);

        // Certificate button
        Button certificateBtn = new Button("Certificates");
        // Mainlayout assignment
        layout.setLeft(leftMenu);
        layout.setCenter(mainGrid);
        layout.setRight(rightMenu);

        return new Scene(layout);
    }

    public ScrollPane getCoursesOverview(Student student) {
        // Sync with database
        ArrayList<String> courses = new ArrayList<>();
        courses = studentDAO.getCourses(student.getEmail());

        // main layout scrollpane
        ScrollPane overviewlayout = new ScrollPane();

        // "table" headers
        Label header = new Label("Courses followed by: " + student.getName());
        header.setStyle("-fx-font-weight: bold");
        header.setMinWidth(150);

        // Individual record
        HBox headRow = new HBox(20, header);
        headRow.setPadding(new Insets(0, 0, 0, 20));

        // Table grid
        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with students with update and delete buttons
        for (String course : courses) {
            Label courseName = new Label(course);
            courseName.setMinWidth(150);

            Button progressBtn = new Button("Progress");
            progressBtn.setId("orangeBtn");

            HBox row = new HBox(20, courseName, progressBtn);
            row.setPadding(new Insets(10, 0, 0, 20));

            progressBtn.setOnAction((event) -> {
                this.toggleModal(course, student.getEmail());
            });

            table.getChildren().add(row);
        }
        overviewlayout.setContent(table);
        overviewlayout.setPrefSize(600, 600);
        overviewlayout.setPadding(new Insets(0, 0, 0, 20));
        return overviewlayout;
    }

    public void toggleModal(String courseName, String email) {
        // create list of modules per course
        ArrayList<Module> modules = courseDAO.getModulesPerCourse(courseName);
        Stage popupwindow = new Stage();

        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Progress on each module");

        ScrollPane scrollPane = new ScrollPane();
        VBox list = new VBox();
        list.setSpacing(20);
        HBox listHeader = new HBox();
        listHeader.getChildren().addAll(new Label("Version"), new Label("Index"), new Label("Title"),
                new Label("Progress"));
        list.getChildren().add(listHeader);
        listHeader.setSpacing(20);
        for (Module module : modules) {
            Label version = new Label(String.valueOf(module.getVersion()));
            Label index = new Label(String.valueOf(module.getIndexNumber()));
            Label title = new Label(module.getTitle());
            Label progress = new Label(
                    String.valueOf(studentDAO.getProgressPerModulePerStudent(email, module.getContentItemId())));

            HBox row = new HBox();
            row.setSpacing(20);
            row.getChildren().addAll(version, index, title, progress);

            list.getChildren().add(row);
        }

        scrollPane.setContent(list);
        Scene scene = new Scene(scrollPane);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}
