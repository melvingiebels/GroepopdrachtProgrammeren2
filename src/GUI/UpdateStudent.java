package GUI;

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

        TextField emailInput = new TextField(student.getEmail());
        TextField nameInput = new TextField(student.getName());
        DatePicker birthdateInput = new DatePicker();
        TextField genderInput = new TextField();
        TextField addressInput = new TextField();
        TextField cityInput = new TextField();
        TextField countryInput = new TextField();
        

        Label emailLabel = new Label("Email:");
        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: (yyyy-mm-dd)");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");

        Button submit = new Button("SUBMIT");
        submit.setOnAction((event) -> {

            succesMsg.setText(nameInput.getText() + " has been added");

            emailInput.clear();
            nameInput.clear();
            birthdateInput.getEditor().clear();
            genderInput.clear();
            addressInput.clear();
            cityInput.clear();
            countryInput.clear();
        });

        layout.setTop(title);
        layout.setBottom(submit);
        form.getChildren().addAll(succesMsg, emailLabel, emailInput, nameLabel, nameInput, birthdateLabel, birthdateInput,
                genderLabel, genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel, countryInput);
        
        return layout;
    }
}
