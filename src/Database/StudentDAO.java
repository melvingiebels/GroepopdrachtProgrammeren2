package Database;

import java.sql.*;
import Domain.Registration;
import Domain.Student;
import java.util.ArrayList;

public class StudentDAO extends GenericDAO {
    // Add a student to the database
    public void addStudent(Student newStudent) {
        SQL = "INSERT INTO Student VALUES(?, ?, ? ,?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, newStudent.getEmail());
            stmt.setString(2, newStudent.getName());
            stmt.setString(3, newStudent.getBirthdate());
            stmt.setString(4, newStudent.getGender());
            stmt.setString(5, newStudent.getAddress());
            stmt.setString(6, newStudent.getCity());
            stmt.setString(7, newStudent.getCountry());

            // execute statement
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("failed to add student");
        }
    }

    // Get list of students
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        SQL = "SELECT * FROM Student";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // execute statement
            rs = stmt.executeQuery();

            // If resultset contains values make new students and add them to arrayList
            while (rs.next()) {
                students.add(new Student(rs.getString("Email"), rs.getString("Name"), rs.getString("Birthdate"),
                        rs.getString("Gender"), rs.getString("Address"), rs.getString("City"),
                        rs.getString("Country")));
            }
        } catch (Exception e) {
            System.out.println("failed to getAllStudents");
        }

        return students;
    }

    // Update student
    public void updateStudent(Student student) {
        SQL = "UPDATE Student SET Name= ?, Birthdate= ?, Gender= ?, Address= ?, City= ?, Country= ? WHERE Email= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getBirthdate());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getAddress());
            stmt.setString(5, student.getCity());
            stmt.setString(6, student.getCountry());
            stmt.setString(7, student.getEmail());

            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update student");
        }
    }

    // Remove student
    public void removeStudent(String email) {
        SQL = "DELETE FROM Student WHERE Email= ?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, email);
            // Excecute query
            stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("failed to remove student");
        }
    }

    // Add a registration and set the progress on 0 after (also set progress back to
    // 0 by multiple registrations for the same course)
    public void addRegistration(Registration registration) {
        SQL = "INSERT INTO Registration (RegistrationDate, Coursename, Email) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, registration.getRegistrationDate());
            stmt.setString(2, registration.getCourseName());
            stmt.setString(3, registration.getEmail());

            // Excecute query
            stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("failed to add registration");
        }

        // Set progress on all modules at 0 after a registration
        // Get a list of contentItemID's(modules) from the course
        CourseDAO courseDAO = new CourseDAO();
        ArrayList<Integer> modules = courseDAO.getContentItemIdModule(registration.getCourseName());

        // Add progress to database per module

        for (int module : modules) {
            // first check if there is already process made & set it back to 0
            SQL = "UPDATE Progress SET Percentage= 0 WHERE Email=? AND contentItemId=?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setString(1, registration.getEmail());
                stmt.setInt(2, module);
                // Excecute query
                stmt.executeQuery();
            } catch (Exception e) {
                SQL = "INSERT INTO Progress VALUES(?, ?, ?)";
                try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                    // Add values to prepared statement
                    stmt.setString(1, registration.getEmail());
                    stmt.setInt(2, module);
                    stmt.setInt(3, 0);

                    // Excecute query
                    stmt.executeQuery();

                } catch (Exception b) {
                    System.out.println("failed to add progress");
                }
            }

        }

    }

    public void removeRegistration(Registration registration) {
        SQL = "DELETE FROM Registration WHERE registrationDate= ? AND CourseName= ? AND Email= ?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, registration.getRegistrationDate());
            stmt.setString(2, registration.getCourseName());
            stmt.setString(3, registration.getEmail());

            // Excecute query
            stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("failed to remove registration");
        }

    }

    public ArrayList<Registration> getRegistrations(Student student) {
        SQL = "SELECT * FROM Registration WHERE Email= ?";
        ArrayList<Registration> registrations = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, student.getEmail());

            // Excecute query
            rs = stmt.executeQuery();

            while (rs.next()) {
                registrations.add(new Registration(rs.getString("RegistrationDate"), rs.getString("CourseName"),
                        rs.getString("Email")));
            }
        } catch (Exception e) {
            System.out.println("failed to get registrations");
        }
        return registrations;
    }

    public ArrayList<String> getCourses(String email) {
        SQL = "SELECT DISTINCT CourseName FROM Registration WHERE Email= ?";
        ArrayList<String> courses = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, email);

            // Excecute query
            rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(rs.getString("CourseName"));
            }
        } catch (Exception e) {
            System.out.println("failed to retrieve courses");
        }
        return courses;
    }

    public int getProgressPerModulePerStudent(String email, int contentItemId) {
        SQL = "SELECT * FROM Progress WHERE Email=? AND ContentItemId= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setString(1, email);
            stmt.setInt(2, contentItemId);
            rs = stmt.executeQuery();
            // excecute query
            while (rs.next()) {
                return rs.getInt("Percentage");
            }
        } catch (Exception e) {
            System.out.println("failed to get progress");
        }
        return 0;

    }

}
