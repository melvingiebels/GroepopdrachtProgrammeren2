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
            this.excecuteQuery(SQL);

        } catch (Exception e) {
        }
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();

        try {
            // getAllStudents query
            String SQL = "SELECT * FROM Student";
            stmt = con.createStatement();
            // Query to database
            rs = stmt.executeQuery(SQL);

            // If resultset contains values make new students and add them to arrayList
            while (rs.next()) {
                students.add(new Student(rs.getString("Email"), rs.getString("Name"), rs.getString("Birthdate"),
                        rs.getString("Gender"), rs.getString("Address"), rs.getString("City"),
                        rs.getString("Country")));
            }
        } catch (Exception e) {
        }

        return students;
    }

    public void getStudent() {

    }

    public void updateStudent(Student student) {
        String SQL = String.format(
                "UPDATE Student SET Name='%s', Birthdate='%s', Gender='%s', Address='%s', City='%s', Country='%s' WHERE Email='%s'",
                student.getName(), student.getBirthdate(), student.getGender(), student.getAddress(), student.getCity(),
                student.getCountry(), student.getEmail());
        this.excecuteQeury(SQL);
    }

    public void removeStudent(String email) {
        try {
            // addStudent query
            String SQL = "DELETE FROM Student WHERE Email='" + email + "'";
            // Excecute query
            this.excecuteQeury(SQL);

        } catch (Exception e) {
        }
    }

}
