package GUI.Student;

import Domain.Student;
import GUI.GenericGUI;
import GUI.Student.Registration.OverviewRegistration;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateStudent extends GenericGUI {

    public Scene getScene(Student student, Stage window) {

        // create overview page for back button
        OverviewStudent overviewStudent = new OverviewStudent();

        BorderPane layout = new BorderPane();

        // Create labels
        Label title = new Label("Update student: " + student.getEmail());
        title.setStyle("-fx-font-weight: bold");

        Label responseMsg = new Label("");

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

        HBox dateInput = new HBox();
        dateInput.setSpacing(20);

        String[] date = student.getBirthdate().split("-");

        TextField dayInput = new TextField(date[2]);
        TextField monthInput = new TextField(date[1]);
        TextField yearInput = new TextField(date[0]);

        dayInput.setPromptText("Day: 00");
        monthInput.setPromptText("Month: 00");
        yearInput.setPromptText("Year: 0000");

        ComboBox<String> genderInput = new ComboBox<>();

        genderInput.setValue(student.getGender());
        genderInput.getItems().addAll("Male", "Female");

        TextField addressInput = new TextField(student.getAddress());
        TextField cityInput = new TextField(student.getCity());
        TextField countryInput = new TextField(student.getCountry());

        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: ");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");
        Label line1 = new Label("-");
        Label line2 = new Label("-");

        Button registrations = new Button("Registrations");
        OverviewRegistration overviewRegistration = new OverviewRegistration();
        registrations.setOnAction((event) -> {
            window.setScene(overviewRegistration.getScene(window, student));
        });

        Button submit = new Button("Save changes");

        submit.setOnAction((event) -> {

            try {
                // Check if input is a number and make it correct date format
                String strDate = String.format("%s-%s-%s", Integer.parseInt(yearInput.getText()),
                        Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));

                studentDAO.updateStudent(new Student(student.getEmail(), nameInput.getText(), strDate,
                        genderInput.getValue(), addressInput.getText(), cityInput.getText(), countryInput.getText()));

                responseMsg.setText("Student has been successfully updated");
                responseMsg.setStyle("-fx-text-fill: green");
            } catch (NumberFormatException e) {
                responseMsg.setText("Date is not a number");
                responseMsg.setStyle("-fx-text-fill: red");
            }
        });

        dateInput.getChildren().addAll(dayInput, line1, monthInput, line2, yearInput);
        form.getChildren().addAll(title, responseMsg, nameLabel, nameInput, birthdateLabel, dateInput, genderLabel,
                genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel, countryInput,
                registrations, submit);

        return new Scene(layout);
    }
}
