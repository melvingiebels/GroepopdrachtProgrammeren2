package GUI.Student;

import java.util.ArrayList;
import Domain.Student;
import Domain.Webcast;
import Domain.Certificate;
import Domain.Module;
import Domain.Registration;
import GUI.GenericGUI;
import GUI.Student.Registration.OverviewRegistration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DetailsStudent extends GenericGUI {

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
        VBox centerView = new VBox();
        centerView.setPadding(new Insets(30, 50, 50, 50));
        centerView.setSpacing(10);
        centerView.getChildren().add(getCoursesOverview(student));
        centerView.getChildren().add(getWebcastOverview(student));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(centerView);
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
        OverviewRegistration overviewRegistration = new OverviewRegistration();
        registrationsBtn.setOnAction((event) -> {
            window.setScene(overviewRegistration.getScene(window, student));
        });

        // Certificate button
        Button certificateBtn = new Button("Certificates");
        certificateBtn.setOnAction((event) -> {
            this.getCertificateModal(student.getEmail());
        });
        rightMenu.getChildren().addAll(rightMenuHeader, studentName, studentEmail, studentBirthdate, studentGender,
                studentAddress, registrationsBtn, certificateBtn);

        // Mainlayout assignment
        layout.setLeft(leftMenu);
        layout.setCenter(scrollPane);
        layout.setRight(rightMenu);

        return new Scene(layout);
    }

    private VBox getCoursesOverview(Student student) {
        // Sync with database
        ArrayList<Registration> registrations = studentDAO.getRegistrations(student);

        // "table" headers
        Label header = new Label("Courses followed by: " + student.getName());
        header.setStyle("-fx-font-weight: bold");

        // Individual record
        HBox headRow = new HBox(header);

        // Table grid
        VBox table = new VBox();
        table.getChildren().add(headRow);

        // Fill table with students with update and delete buttons
        for (Registration registration : registrations) {
            Label courseName = new Label(registration.getCourseName());
            courseName.setMinWidth(150);

            Button progressBtn = new Button("Progress");
            progressBtn.setId("orangeBtn");

            HBox row = new HBox(20, courseName, progressBtn);
            row.setPadding(new Insets(10, 0, 0, 20));

            progressBtn.setOnAction((event) -> {
                this.getModuleProgressModal(registration);
            });

            table.getChildren().add(row);
        }

        table.setMinHeight(100);
        table.setMinWidth(700);
        table.setPadding(new Insets(0, 0, 0, 20));
        return table;
    }

    private VBox getWebcastOverview(Student student) {
        // data
        ArrayList<Webcast> webcasts = contentItemDAO.getWebcastsPerStudent(student.getEmail());

        // title
        Label title = new Label("Webcasts");
        title.setStyle("-fx-font-weight: bold");

        // Table elements
        VBox table = new VBox();

        // "table" headers
        HBox headRow = new HBox();
        headRow.setStyle("-fx-font-weight: bold");
        headRow.getChildren().addAll(new Label("ID"), new Label("Webcast"), new Label("Progress"));
        headRow.setSpacing(50);
        table.getChildren().addAll(title, headRow);

        // Individual records
        for (Webcast webcast : webcasts) {
            HBox row = new HBox();

            Label contentItemId = new Label(String.valueOf(webcast.getContentItemId()));
            Label titleWebcast = new Label(webcast.getTitle());
            TextField progress = new TextField();
            progress.setText(String.valueOf(studentDAO.getProgressPerWebcastPerStudent(webcast.getContentItemId())));
            Button saveBtn = new Button("Save");

            saveBtn.setOnAction((event) -> {
                studentDAO.updateWebcastProgress(student.getEmail(), webcast.getContentItemId(),
                        Integer.valueOf(progress.getText()));
                progress.setStyle("-fx-text-fill: green");
            });

            Button detailBtn = new Button("Details");
            detailBtn.setOnAction((event) -> {
                this.getWebcastDetailsModal(webcast);
            });

            row.getChildren().addAll(contentItemId, titleWebcast, progress, saveBtn, detailBtn);
            row.setSpacing(50);
            table.getChildren().addAll(row);
        }

        table.setMinHeight(100);
        table.setMinWidth(700);
        table.setPadding(new Insets(0, 0, 0, 20));
        return table;

    }

    private void getWebcastDetailsModal(Webcast webcast) {
        // Modal elements
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Progress on each module");

        HBox list = new HBox();

        // List elements
        VBox listSubjects = new VBox();
        listSubjects.getChildren().addAll(new Label("ID: "), new Label("Title: "), new Label("Duration: "),
                new Label("Url: "), new Label("Lector: "), new Label("Organisation: "));
        listSubjects.setStyle("-fx-font-weight: bold");

        VBox listValues = new VBox();
        listValues.getChildren().addAll(new Label(String.valueOf(webcast.getContentItemId())),
                new Label(webcast.getTitle()), new Label(String.valueOf(webcast.getDuration()) + "Minutes"),
                new Label(webcast.getUrl()), new Label(webcast.getLector()), new Label(webcast.getOrganisation()));

        // Make list
        list.getChildren().addAll(listSubjects, listValues);
        list.setSpacing(10);

        // Adding table to the scene
        Scene scene = new Scene(list);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }

    private void getModuleProgressModal(Registration registration) {
        // Data
        ArrayList<Module> modules = courseDAO.getModulesPerCourse(registration.getCourseName());
        Stage popupwindow = new Stage();

        // Modal elements
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Progress on each module");

        // Table elements
        ScrollPane scrollPane = new ScrollPane();
        Label label = new Label("Progress");
        label.setFont(new Font("Arial", 20));
        label.setStyle("-fx-font-weight: bold");

        VBox list = new VBox();
        list.setSpacing(20);
        HBox listHeader = new HBox();
        listHeader.getChildren().addAll(new Label("ID"), new Label("Version"), new Label("Index"), new Label("Title"),
                new Label("Progress"));
        list.getChildren().addAll(label, listHeader);
        listHeader.setSpacing(50);

        // Creating rows
        for (Module module : modules) {
            // Values
            Label id = new Label(String.valueOf(module.getContentItemId()));
            Label version = new Label(String.valueOf(module.getVersion()));
            Label index = new Label(String.valueOf(module.getIndexNumber()));
            Label title = new Label(module.getTitle());
            TextField progress = new TextField();
            progress.setText(String.valueOf(
                    studentDAO.getProgressPerModulePerStudent(registration.getEmail(), module.getContentItemId())));

            Button update = new Button("Update");
            update.setOnAction((event) -> {
                studentDAO.updateProgressModule(registration.getEmail(), module.getContentItemId(),
                        Integer.valueOf(progress.getText()), registration.getCourseName(),
                        registration.getRegistrationDate());
                progress.setStyle("-fx-text-fill: green");
            });

            // Adding values to row
            HBox row = new HBox();
            row.setSpacing(50);
            row.getChildren().addAll(id, version, index, title, progress, update);

            list.getChildren().add(row);
        }

        // Adding table to the scene
        scrollPane.setContent(list);
        Scene scene = new Scene(scrollPane);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }

    private void getCertificateModal(String email) {
        // Modal elements
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Certificates");

        // Title
        Label label = new Label("Certificates");
        label.setFont(new Font("Arial", 20));

        // Data
        ArrayList<Certificate> certificates = studentDAO.getCertificatesPerUser(email);

        // Table elemtens
        VBox table = new VBox();
        HBox header = new HBox();
        header.getChildren().addAll(new Label("Certificate ID"), new Label("Registration date"),
                new Label("Course name"), new Label("Email"));
        header.setSpacing(50);
        header.setStyle("-fx-font-weight: bold");
        table.getChildren().add(header);

        // Creating rows
        for (Certificate certificate : certificates) {
            HBox row = new HBox();

            // Values
            Label idLabel = new Label(String.valueOf(certificate.getCertiticateId()));
            Label dateLabel = new Label(certificate.getRegistrationDate());
            Label courseLabel = new Label(certificate.getCourseName());
            Label emailLabel = new Label(certificate.getEmail());

            Button deleteCertificate = new Button("Delete");
            deleteCertificate.setOnAction((event) -> {
                studentDAO.removeCertificate(certificate.getCertiticateId());
                table.getChildren().remove(row);
            });

            // Adding values to the row
            row.setSpacing(50);
            row.getChildren().addAll(idLabel, dateLabel, courseLabel, emailLabel, deleteCertificate);
            table.getChildren().add(row);
        }

        // Adding table to the scene
        Scene scene = new Scene(table);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();
    }
}
