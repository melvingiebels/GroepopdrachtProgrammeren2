package Database;

import java.sql.*;
import java.util.ArrayList;

import Domain.Course;
import Domain.Module;

public class CourseDAO extends GenericDAO {
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

    public void removeCourse(String courseName) {
        SQL = "DELETE FROM Course WHERE CourseName= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, courseName);

            // execute statement
            stmt.executeUpdate();
            System.out.println(stmt);
        } catch (Exception e) {
            System.out.println("failed to remove course");
        }
    }

    public ArrayList<Module> getAvaibleModules() {
        ArrayList<Module> modules = new ArrayList<>();
        SQL = "SELECT * FROM Module WHERE CourseName IS NULL";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                modules.add(new Module(rs.getInt("ContentItemId"), rs.getInt("Version"), rs.getString("ContactName"),
                        rs.getString("ContactMail"), rs.getInt("IndexNumber"), rs.getString("Title"),
                        rs.getString("CourseName")));
            }
        } catch (Exception e) {
            System.out.println("failed to getAvailableModules");
        }
        return modules;
    }

    public void updateModule(Module module, String courseName) {
        SQL = "UPDATE Module SET CourseName= ? WHERE ContentItemId= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, courseName);
            stmt.setInt(2, module.getContentItemId());

            // execute statement
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update module");
        }
    }
}
