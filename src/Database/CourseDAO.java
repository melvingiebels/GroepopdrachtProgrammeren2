package Database;

import java.sql.*;
import java.util.ArrayList;
import Domain.Course;
import Domain.Module;

public class CourseDAO extends GenericDAO {

    // Adding new courses to the database
    public void addCourse(Course newCourse) {
        SQL = "INSERT INTO Course VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, newCourse.getName());
            stmt.setString(2, newCourse.getTopic());
            stmt.setString(3, newCourse.getDescription());
            stmt.setString(4, newCourse.getDifficulty());

            // execute statement
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to add course");
        }
    }

    // Get all courses from the database
    public ArrayList<Course> getAllCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        SQL = "SELECT * FROM Course";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                courses.add(new Course(rs.getString("CourseName"), rs.getString("Topic"), rs.getString("Description"),
                        rs.getString("Difficulty")));
            }
        } catch (Exception e) {
            System.out.println("failed to getAllCourses");
        }
        return courses;
    }

    // Update course in the database
    public void updateCourse(Course course) {
        SQL = "UPDATE Course SET CourseName= ?, Topic= ?, Description= ?, Difficulty= ? WHERE CourseName= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, course.getName());
            stmt.setString(2, course.getTopic());
            stmt.setString(3, course.getDescription());
            stmt.setString(4, course.getDifficulty());
            stmt.setString(5, course.getName());

            // execute statement
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update course");
        }
    }

    // remove a course from the database
    public void removeCourse(String courseName) {
        SQL = "DELETE FROM Course WHERE CourseName= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, courseName);

            // execute statement
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to remove course");
        }
    }

    // Get a list of modules in a course
    public ArrayList<Module> getModulesPerCourse(String courseName) {
        ArrayList<Module> modules = new ArrayList<>();
        SQL = "SELECT * FROM Module WHERE CourseName=?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, courseName);
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                modules.add(new Module(rs.getInt("ContentItemId"), rs.getInt("Version"), rs.getString("ContactName"),
                        rs.getString("ContactMail"), rs.getInt("IndexNumber"), rs.getString("Title"),
                        rs.getString("CourseName")));
            }

        } catch (Exception e) {
            System.out.println("failed to modules per course");
        }
        return modules;
    }

    public int getTotalCompleted(String courseName) {
        SQL = "SELECT COUNT(*) AS Total FROM Certificate WHERE CourseName=?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setString(1, courseName);
            rs = stmt.executeQuery();
            // excecute query
            while (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (Exception e) {
            System.out.println("failed to get progress");
        }
        return 0;
    }

    public int getGenderPercentage(String gender) {
        SQL = "SELECT (convert(decimal(5, 2),COUNT(*)) / (SELECT COUNT(*) FROM Registration) * 100) AS 'Percentage' FROM Certificate JOIN Student ON Certificate.Email = Student.Email WHERE student.Gender=?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setString(1, gender);
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
