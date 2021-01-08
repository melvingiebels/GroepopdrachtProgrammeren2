package GUI.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Database.StudentDAO;
import Domain.Student;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateStudent {

    public Scene getScene(Student student, StudentDAO studentDAO, Stage window) {

        // create overview page for back button
        OverviewStudent overviewStudent = new OverviewStudent();

        BorderPane layout = new BorderPane();

        Label title = new Label("Update student: " + student.getEmail());
        title.setStyle("-fx-font-weight: bold");

        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");

        Button backToUpdatePage = new Button("Back");
        backToUpdatePage.setOnAction((event) -> {
            window.setScene(overviewStudent.getScene(window));
        });

        backToUpdatePage.setMinSize(50, 30);

        // Form elements
        VBox form = new VBox();
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        leftMenu.getChildren().addAll(backToUpdatePage);
        form.setPadding(new Insets(30, 50, 50, 50));
        form.setSpacing(10);
        layout.setLeft(leftMenu);
        layout.setCenter(form);

        TextField nameInput = new TextField(student.getName());
        DatePicker birthdateInput = new DatePicker(LocalDate.parse(student.getBirthdate()));
        ComboBox<String> genderInput = new ComboBox<>();

        genderInput.setValue(student.getGender());
        genderInput.getItems().addAll("Male", "Female", "Other");

        TextField addressInput = new TextField(student.getAddress());
        TextField cityInput = new TextField(student.getCity());
        TextField countryInput = new TextField(student.getCountry());

        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: ");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");

        Button registrations = new Button("Registrations");

        Button submit = new Button("Save changes");

        submit.setOnAction((event) -> {

            String strDate = birthdateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            studentDAO.updateStudent(new Student(student.getEmail(), nameInput.getText(), strDate,
                    genderInput.getValue(), addressInput.getText(), cityInput.getText(), countryInput.getText()));

            succesMsg.setText("Student has been successfully updated");

        });

        form.getChildren().addAll(title, succesMsg, nameLabel, nameInput, birthdateLabel, birthdateInput, genderLabel,
                genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel, countryInput,
                registrations, submit);

        return new Scene(layout);
    }
}
