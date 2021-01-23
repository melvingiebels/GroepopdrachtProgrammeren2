package Database;

import java.sql.*;
import Domain.Registration;
import Domain.Student;
import Domain.Module;
import java.util.ArrayList;

public class StudentDAO extends GenericDAO {
    private CourseDAO courseDAO = new CourseDAO();

    // Add a student to the database
    public void addStudent(Student newStudent) {
        SQL = "INSERT INTO Student VALUES(?, ?, ? ,?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, newStudent.getEmail());
            stmt.setString(2, newStudent.getName());
            stmt.setString(3, newStudent.getBirthdate());
            stmt.setString(4, newStudent.getGender());
            stmt.setString(5, newStudent.getAddress());
            stmt.setString(6, newStudent.getCity());
            stmt.setString(7, newStudent.getCountry());
            stmt.setString(8, newStudent.getZipCode());

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
                        rs.getString("Gender"), rs.getString("Address"), rs.getString("City"), rs.getString("Country"),
                        rs.getString("ZipCode")));
            }
        } catch (Exception e) {
            System.out.println("failed to getAllStudents");
        }

        return students;
    }

    // Update student
    public void updateStudent(Student student) {
        SQL = "UPDATE Student SET Name= ?, Birthdate= ?, Gender= ?, Address= ?, City= ?, Country= ?, ZipCode= ? WHERE Email= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getBirthdate());
            stmt.setString(3, student.getGender());
            stmt.setString(4, student.getAddress());
            stmt.setString(5, student.getCity());
            stmt.setString(6, student.getCountry());
            stmt.setString(7, student.getZipCode());
            stmt.setString(8, student.getEmail());

            // Execute query
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
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("failed to remove student");
        }
    }

    // Add a registration, set progress to 0 on all module and reset progress when
    // doing the course a second time (AddRegistration)
    public void addRegistration(Registration registration) {
        SQL = "INSERT INTO Registration VALUES(?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, registration.getRegistrationDate());
            stmt.setString(2, registration.getCourseName());
            stmt.setString(3, registration.getEmail());

            // Excecute query
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("failed to add registration");
        }

        // Set progress on all modules at 0 after a registration
        // Get a list of contentItemID's(modules) from the course
        ArrayList<Module> modules = courseDAO.getModulesPerCourse(registration.getCourseName());

        // Add progress to database per module
        for (Module module : modules) {
            // first check if there is already process made & set it back to 0
            SQL = "UPDATE Progress SET Percentage= 0 WHERE Email=? AND contentItemId=?";
            try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                stmt.setString(1, registration.getEmail());
                stmt.setInt(2, module.getContentItemId());
                // Excecute query
                stmt.executeUpdate();
            } catch (Exception e) {
                // Set progress to 0 per module
                SQL = "INSERT INTO Progress VALUES(?, ?, ?)";
                try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                    // Add values to prepared statement
                    stmt.setString(1, registration.getEmail());
                    stmt.setInt(2, module.getContentItemId());
                    stmt.setInt(3, 0);

                    // Excecute query
                    stmt.executeUpdate();

                } catch (Exception b) {
                    System.out.println("failed to add progress");
                }
            }

        }

    }

    // Remove a registration from the overview (OverviewRegistration)
    public void removeRegistration(Registration registration) {
        SQL = "DELETE FROM Registration WHERE registrationDate= ? AND CourseName= ? AND Email= ?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, registration.getRegistrationDate());
            stmt.setString(2, registration.getCourseName());
            stmt.setString(3, registration.getEmail());

            // Excecute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to remove registration");
        }

    }

    // get registrations of a student (DetailsStudent)
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

}
