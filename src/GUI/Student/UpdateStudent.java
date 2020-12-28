package GUI.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Database.StudentDAO;
import Domain.Student;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UpdateStudent {

    public Parent getView(Student student) {

        BorderPane layout = new BorderPane();
        Label title = new Label("Update student: " + student.getName());
        Label succesMsg = new Label("");

        // Form elements
        VBox form = new VBox();
        form.setPadding(new Insets(10, 50, 50, 50));
        form.setSpacing(10);
        layout.setCenter(form);

        TextField nameInput = new TextField(student.getName());
        DatePicker birthdateInput = new DatePicker(LocalDate.parse(student.getBirthdate()));
        TextField genderInput = new TextField(student.getGender());
        TextField addressInput = new TextField(student.getAddress());
        TextField cityInput = new TextField(student.getCity());
        TextField countryInput = new TextField(student.getCountry());

        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: ");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");

        Button submit = new Button("SUBMIT");
        submit.setOnAction((event) -> {

            StudentDAO studentDAO = new StudentDAO();

            String strDate = birthdateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            studentDAO.updateStudent(new Student(student.getEmail(), nameInput.getText(), strDate,
                    genderInput.getText(), addressInput.getText(), cityInput.getText(), countryInput.getText()));

            succesMsg.setText(nameInput.getText() + " has been updated");

            nameInput.clear();
            birthdateInput.getEditor().clear();
            genderInput.clear();
            addressInput.clear();
            cityInput.clear();
            countryInput.clear();
        });

        layout.setTop(title);
        layout.setBottom(submit);
        form.getChildren().addAll(succesMsg, nameLabel, nameInput, birthdateLabel, birthdateInput, genderLabel,
                genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel, countryInput);

        return layout;
    }
}
