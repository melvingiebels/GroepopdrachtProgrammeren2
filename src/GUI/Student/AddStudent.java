package GUI.Student;

import Domain.Student;
import GUI.GenericGUI;
import Logic.Validation.DateValidation;
import Logic.Validation.MailValidation;
import Logic.Validation.NameValidation;
import Logic.Validation.ZipCodeValidation;
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
        emailInput.setPromptText("example@email.com");
        TextField nameInput = new TextField();
        nameInput.setPromptText("Firstname Lastname");

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
        addressInput.setPromptText("Streetname 123");
        TextField cityInput = new TextField();
        cityInput.setPromptText("Cityname");
        TextField countryInput = new TextField();
        countryInput.setPromptText("Countryname");
        TextField zipCodeInput = new TextField();
        zipCodeInput.setPromptText("1234 AB");

        Label emailLabel = new Label("Email:");
        Label nameLabel = new Label("Name: ");
        Label birthdateLabel = new Label("Birthdate: ");
        Label genderLabel = new Label("Gender: ");
        Label addressLabel = new Label("Address:");
        Label cityLabel = new Label("City: ");
        Label countryLabel = new Label("Country: ");
        Label zipCodeLabel = new Label("Zip code: ");
        Label line1 = new Label("-");
        Label line2 = new Label("-");

        // Add button actions
        overviewBtn.setOnAction((event) -> {
            window.setScene(overviewStudent.getScene(window));
            window.setTitle("Student overview");
        });

        submitBtn.setOnAction((event) -> {
            // reset previous errors (for multiple attempts)
            emailLabel.setText("Email: ");
            emailLabel.setStyle(null);
            zipCodeLabel.setText("Zip code: ");
            zipCodeLabel.setStyle(null);
            nameLabel.setText("Name: ");
            nameLabel.setStyle(null);
            birthdateLabel.setText("Birthdate: ");
            birthdateLabel.setStyle(null);
            addressLabel.setText("Address: ");
            addressLabel.setStyle(null);
            cityLabel.setText("City: ");
            cityLabel.setStyle(null);
            countryLabel.setText("Country: ");
            countryLabel.setStyle(null);

            Boolean isValidStudent = true;
            String strDate = "";

            // Check mail
            if (MailValidation.validateMailAddress(emailInput.getText())) {
                emailInput.setStyle("-fx-text-box-border: green");
            } else {
                emailLabel.setText("Email: Invalid Email!");
                emailLabel.setStyle("-fx-text-fill: red");
                emailInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }

            // Check firstname + lastname
            if (NameValidation.validateName(nameInput.getText())) {
                nameInput.setStyle("-fx-text-box-border: green");
                String[] parts = nameInput.getText().split(" ");
                String formattedName = "";
                boolean hasSpace = false;
                for (String x : parts) {
                    if (!hasSpace) {
                        formattedName += x.substring(0, 1).toUpperCase() + x.substring(1) + " ";
                        hasSpace = true;
                    } else {
                        formattedName += x.substring(0, 1).toUpperCase() + x.substring(1);
                    }

                }
                nameInput.setText(formattedName);
            } else {
                nameLabel.setText("Name: Invalid name! (Don't forget capital letters)");
                nameLabel.setStyle("-fx-text-fill: red");
                nameInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }

            // Check date
            try {
                strDate = String.format("%s-%s-%s", yearInput.getText(), monthInput.getText(), dayInput.getText());
                String validateDate = String.format("%s-%s-%s", dayInput.getText(), monthInput.getText(),
                        yearInput.getText());

                if (DateValidation.validateDate(validateDate)) {
                    dayInput.setStyle("-fx-text-box-border: green");
                    monthInput.setStyle("-fx-text-box-border: green");
                    yearInput.setStyle("-fx-text-box-border: green");
                } else {
                    birthdateLabel.setText("Birthdate: Invalid birthdate!");
                    birthdateLabel.setStyle("-fx-text-fill: red");
                    dayInput.setStyle("-fx-text-box-border: red");
                    monthInput.setStyle("-fx-text-box-border: red");
                    yearInput.setStyle("-fx-text-box-border: red");
                    isValidStudent = false;
                }
            } catch (NumberFormatException e) {
                birthdateLabel.setText("Birthdate: Invalid birthdate!");
                birthdateLabel.setStyle("-fx-text-fill: red");
                dayInput.setStyle("-fx-text-box-border: red");
                monthInput.setStyle("-fx-text-box-border: red");
                yearInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }
            // check location infromation
            // Address
            if (!addressInput.getText().equals("")) {
                addressInput.setStyle("-fx-text-box-border: green");
            } else {
                addressLabel.setText("Address: Address is empty");
                addressLabel.setStyle("-fx-text-fill: red");
                addressInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }
            // Country
            if (!countryInput.getText().equals("")) {
                countryInput.setStyle("-fx-text-box-border: green");
            } else {
                countryLabel.setText("Country: Country is empty");
                countryLabel.setStyle("-fx-text-fill: red");
                countryInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }
            // City
            if (!cityInput.getText().equals("")) {
                cityInput.setStyle("-fx-text-box-border: green");
            } else {
                cityLabel.setText("City: City is empty");
                cityLabel.setStyle("-fx-text-fill: red");
                cityInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }

            // Check ZipCode
            if (ZipCodeValidation.validateZipCode(zipCodeInput.getText())) {
                zipCodeInput.setText(ZipCodeValidation.formatZipCode(zipCodeInput.getText()));
                zipCodeInput.setStyle("-fx-text-box-border: green");
            } else {
                zipCodeLabel.setText("Zip code: Invalid zip code!");
                zipCodeLabel.setStyle("-fx-text-fill: red");
                zipCodeInput.setStyle("-fx-text-box-border: red");
                isValidStudent = false;
            }

            // Add student
            if (isValidStudent) {
                studentDAO.addStudent(new Student(emailInput.getText(), nameInput.getText(), strDate,
                        genderInput.getValue(), addressInput.getText(), cityInput.getText(), countryInput.getText(),
                        zipCodeInput.getText()));

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
                emailLabel.setText("Email: ");
                emailLabel.setStyle(null);
                zipCodeLabel.setText("Zip code: ");
                zipCodeLabel.setStyle(null);
                nameLabel.setText("Name: ");
                nameLabel.setStyle(null);
                birthdateLabel.setText("Birthdate: ");
                birthdateLabel.setStyle(null);
                addressLabel.setText("Address: ");
                addressLabel.setStyle(null);
                cityLabel.setText("City: ");
                cityLabel.setStyle(null);
                countryLabel.setText("Country: ");
                countryLabel.setStyle(null);
            }
        });

        leftMenu.getChildren().addAll(overviewBtn);
        dateInput.getChildren().addAll(dayInput, line1, monthInput, line2, yearInput);
        form.getChildren().addAll(title, responseMsg, emailLabel, emailInput, nameLabel, nameInput, birthdateLabel,
                dateInput, genderLabel, genderInput, addressLabel, addressInput, cityLabel, cityInput, countryLabel,
                countryInput, zipCodeLabel, zipCodeInput, submitBtn);

        window.setTitle("Student overview");
        layout.setPrefSize(600, 600);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("./GUI/Stylesheet.css");
        return scene;
    }

}