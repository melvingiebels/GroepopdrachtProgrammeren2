package Database;

import Domain.Student;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO extends GenericDAO {
    public void addStudent(Student newStudent) {
        try {
            // addStudent query
            String SQL = "INSERT INTO Student VALUES(";
            String[] getters = { newStudent.getEmail(), newStudent.getName(), newStudent.getBirthdate(),
                    newStudent.getGender(), newStudent.getAddress(), newStudent.getCity(), newStudent.getCountry() };
            String[] formattedGetters = new String[7];
            for (int i = 0; i < getters.length; i++) {
                formattedGetters[i] = "'" + getters[i] + "'";
            }
            SQL += String.join(",", formattedGetters);
            SQL += ")";

            // Excecute query
            this.excecuteQeury(SQL);

        } catch (Exception e) {
        }
    }

    public ArrayList<Student> getAllStudents() {
        return null;
    }

    public void getStudent() {

    }

    public void updateStudent() {

    }

    public void removeStudent() {

    }

}
