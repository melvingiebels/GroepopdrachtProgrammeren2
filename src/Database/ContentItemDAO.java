package Database;

import java.sql.*;
import java.util.ArrayList;

import Domain.Course;
import Domain.Module;

public class ContentItemDAO extends GenericDAO {
    // Get all available modules that are not connected to a course yet
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

    public ArrayList<Module> getCourseModules(Course course) {
        ArrayList<Module> modules = new ArrayList<>();
        SQL = String.format("SELECT * FROM Module WHERE CourseName='%s'", course.getName());

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                modules.add(new Module(rs.getInt("ContentItemId"), rs.getInt("Version"), rs.getString("ContactName"),
                        rs.getString("ContactMail"), rs.getInt("IndexNumber"), rs.getString("Title"),
                        rs.getString("CourseName")));
            }
        } catch (Exception e) {
            System.out.println("failed to getCourseModules");
        }
        return modules;
    }

    // Adding a coursename to a module
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
