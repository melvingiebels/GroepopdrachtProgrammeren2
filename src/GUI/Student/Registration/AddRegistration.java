package GUI.Student.Registration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Database.CourseDAO;
import Database.StudentDAO;
import Domain.Course;
import Domain.Registration;
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

public class AddRegistration {
    private StudentDAO studentDAO = new StudentDAO();

    public Scene getScene(Stage window, Student student) {
        // layout
        BorderPane layout = new BorderPane();

        // left menu
        HBox leftMenu = new HBox();
        leftMenu.setPadding(new Insets(20, 0, 0, 20));
        Button backBtn = new Button("Back");
        OverviewRegistration overviewRegistration = new OverviewRegistration();
        backBtn.setOnAction((event) -> {
            window.setScene(overviewRegistration.getScene(window, student, studentDAO));
        });
        leftMenu.getChildren().add(backBtn);
        layout.setLeft(leftMenu);
        // Layout for the form elements
        VBox form = new VBox();
        form.setPadding(new Insets(30, 50, 50, 50));
        form.setSpacing(10);
        layout.setCenter(form);

        // Inputfields & properties
        Label emailValue = new Label("Email of student: " + student.getEmail());
        
        HBox dateInput = new HBox();
        dateInput.setSpacing(20);

        TextField dayInput = new TextField();
        TextField monthInput = new TextField();
        TextField yearInput = new TextField();

        dayInput.setPromptText("Day: 00");
        monthInput.setPromptText("Month: 00");
        yearInput.setPromptText("Year: 0000");

        ComboBox<String> coursesInput = new ComboBox<>();
        ArrayList<String> courseList = createCourseList();
        for (String courseName : courseList) {
            coursesInput.getItems().add(courseName);
        }
        coursesInput.setValue(courseList.get(0));
        // HIER MOET NOG EEN IMPLEMENTATIE KOMEN VOOR DE CERTIFICATEN !!!!!!!!!!!!!

        // Labels for the inputfields
        Label title = new Label("Add a new registration");
        title.setStyle("-fx-font-weight: bold");
        Label responseMsg = new Label("");
        Label registrationDateLabel = new Label("Registration Date:");
        Label coursesLabel = new Label("Select a course:");
        Label line1 = new Label("-");
        Label line2 = new Label("-");

        // submit
        Button submitBtn = new Button("SUBMIT");

        submitBtn.setOnAction((event) -> {
            try {
                // Check if input is a number and make it correct date format
                String strDate = String.format("%s-%s-%s", Integer.parseInt(yearInput.getText()), Integer.parseInt(monthInput.getText()), Integer.parseInt(dayInput.getText()));

                // create registration
                Registration registration = new Registration(strDate, coursesInput.getValue(), student.getEmail());

                // add it to the database
                studentDAO.addRegistration(registration);

                // add it to the student itself
                student.addNewRegistration(registration);

                responseMsg.setText("Registration for " + student.getEmail() + " Has been added");
                responseMsg.setStyle("-fx-text-fill: green");
            } catch (NumberFormatException e) {
                responseMsg.setText("Date is not a number");
                responseMsg.setStyle("-fx-text-fill: red");
            }
            
        });

        dateInput.getChildren().addAll(dayInput, line1, monthInput, line2, yearInput);
        form.getChildren().addAll(title, emailValue, responseMsg, registrationDateLabel, dateInput,
                coursesLabel, coursesInput, submitBtn);

        return new Scene(layout);
    }

    private ArrayList<String> createCourseList() {
        ArrayList<String> list = new ArrayList<>();
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Course> courses = courseDAO.getAllCourses();
        for (Course course : courses) {
            list.add(course.getName());
        }

        return list;
    }
}
