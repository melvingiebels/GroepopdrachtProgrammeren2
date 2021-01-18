package GUI.Student;

import Domain.Student;
import GUI.GenericGUI;
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

public class AddStudent extends GenericGUI {

    public Scene getScene(Stage window) {

        // Create View
        OverviewStudent overviewStudent = new OverviewStudent();

        BorderPane layout = new BorderPane();

        // create labels
        Label title = new Label("CREATE A NEW STUDENT");
        title.setStyle("-fx-font-weight: bold");

        Label responseMsg = new Label("");

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

        HBox dateInput = new HBox();
        dateInput.setSpacing(20);

        TextField dayInput = new TextField();
        TextField monthInput = new TextField();
        TextField yearInput = new TextField();

        dayInput.setPromptText("Day: 00");
        monthInput.setPromptText("Month: 00");
        yearInput.setPromptText("Year: 0000");

        ComboBox<String> genderInput = new ComboBox<>();

        genderInput.setValue("Male");
        genderInput.getItems().addAll("Male", "Female");

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
        Label line1 = new Label("-");
        Label line2 = new Label("-");

        // Add button actions
        overviewBtn.setOnAction((event) -> {
            window.setScene(overviewStudent.getScene(window));
            window.setTitle("Student overview");
        });

        submitBtn.setOnAction((event) -> {

            try {
                // Check if input is a number and make it correct date format
                String strDate = String.format("%s-%s-%s", Integer.parseInt(yearInput.getText()),
                        Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));

                studentDAO.addStudent(new Student(emailInput.getText(), nameInput.getText(), strDate,
                        genderInput.getValue(), addressInput.getText(), cityInput.getText(), countryInput.getText()));

                responseMsg.setText(nameInput.getText() + " has been added");
                responseMsg.setStyle("-fx-text-fill: green");

                emailInput.clear();
                nameInput.clear();
                dayInput.clear();
                monthInput.clear();
                yearInput.clear();
                genderInput.setValue("Male");
                addressInput.clear();
                cityInput.clear();
                countryInput.clear();
            } catch (NumberFormatException e) {
                responseMsg.setText("Date is not a number");
                responseMsg.setStyle("-fx-text-fill: red");
            }
        });

        leftMenu.getChildren().addAll(overviewBtn);
        dateInput.getChildren().addAll(dayInput, line1, monthInput, line2, yearInput);
        form.getChildren().addAll(title, responseMsg, emailLabel, emailInput, nameLabel, nameInput, birthdateLabel,
                dateInput, genderLabel, genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel,
                countryInput, submitBtn);

        window.setTitle("Student overview");
        layout.setPrefSize(600, 600);
        return new Scene(layout);
    }

}