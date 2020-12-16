package GUI;

import Database.StudentDAO;
import Domain.Student;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddStudent extends Application {

    @Override
    public void start(Stage window) throws Exception {
        BorderPane layout = new BorderPane();
        Label title = new Label("CREATE A NEW STUDENT");

        // Form elements
        VBox form = new VBox();
        layout.setCenter(form);

        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        TextField birthdateInput = new TextField();
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
            StudentDAO studentDAO = new StudentDAO();
            studentDAO.addStudent(createNewStudent(emailInput.getText(), nameInput.getText(), birthdateInput.getText(),
                    genderInput.getText(), addressInput.getText(), cityInput.getText(), countryInput.getText()));
        });

        layout.setTop(title);
        layout.setBottom(submit);
        form.getChildren().addAll(emailLabel, emailInput, nameLabel, nameInput, birthdateLabel, birthdateInput,
                genderLabel, genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel, countryInput);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public static Student createNewStudent(String email, String name, String birthdate, String gender, String address,
            String city, String country) {
        Student newStudent = new Student();
        newStudent.setAddress(address);
        newStudent.setBirthdate(birthdate);
        newStudent.setCity(city);
        newStudent.setCountry(country);
        newStudent.setEmail(email);
        newStudent.setGender(gender);
        newStudent.setName(name);
        return newStudent;
    }

    public static void main(String[] args) {
        launch(AddStudent.class);
    }

}