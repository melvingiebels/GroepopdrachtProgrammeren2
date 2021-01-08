package GUI.Student;

import java.time.format.DateTimeFormatter;

import Database.StudentDAO;
import Domain.Student;
import javafx.geometry.Insets;
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

public class AddStudent {

    private BorderPane layout = new BorderPane();

    public Scene getScene(Stage window) {

        // Create View
        OverviewStudent overviewStudent = new OverviewStudent();

        // create labels
        Label title = new Label("CREATE A NEW STUDENT");
        title.setStyle("-fx-font-weight: bold");

        Label succesMsg = new Label("");
        succesMsg.setStyle("-fx-text-fill: green");

        // Form elements
        VBox form = new VBox();
        form.setPadding(new Insets(30, 50, 50, 50));
        form.setSpacing(10);
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        layout.setCenter(form);
        layout.setLeft(leftMenu);

        // add button
        Button overviewBtn = new Button("Back");
        Button submitBtn = new Button("SUBMIT");

        overviewBtn.setMinSize(50, 30);

        // add textfields and labels
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        DatePicker birthdateInput = new DatePicker();
        ComboBox<String> genderInput = new ComboBox<>();

        genderInput.setValue("Male");
        genderInput.getItems().addAll("Male", "Female", "Other");

        TextField addressInput = new TextField();
        TextField cityInput = new TextField();
        TextField countryInput = new TextField();

        Label emailLabel = new Label("Email:");
        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: ");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");

        // Add button actions
        overviewBtn.setOnAction((event) -> {
            window.setScene(overviewStudent.getScene(window));
            window.setTitle("Student overview");
        });

        submitBtn.setOnAction((event) -> {
            StudentDAO studentDAO = new StudentDAO();

            String strDate = birthdateInput.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            studentDAO.addStudent(new Student(emailInput.getText(), nameInput.getText(), strDate,
                    genderInput.getValue(), addressInput.getText(), cityInput.getText(), countryInput.getText()));

            succesMsg.setText(nameInput.getText() + " has been added");

            emailInput.clear();
            nameInput.clear();
            birthdateInput.getEditor().clear();
            genderInput.setValue("Male");
            addressInput.clear();
            cityInput.clear();
            countryInput.clear();
        });

        leftMenu.getChildren().addAll(overviewBtn);

        form.getChildren().addAll(title, succesMsg, emailLabel, emailInput, nameLabel, nameInput, birthdateLabel,
                birthdateInput, genderLabel, genderInput, addressLabel, addressInput, cityLabel, cityInput,
                countryLabel, countryInput, submitBtn);

        window.setTitle("Student overview");
        layout.setPrefSize(500, 600);
        return new Scene(layout);
    }

}