package Database;

import java.sql.*;
import java.util.ArrayList;
import Domain.Module;
import Domain.Webcast;

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

    // get all webcasts from a student that they are currently watching or have been
    // watched
    public ArrayList<Webcast> getWebcastsPerStudent(String email) {
        SQL = "SELECT * FROM Webcast JOIN Progress ON Webcast.contentItemId = Progress.contentItemId WHERE Email= ?";
        ArrayList<Webcast> webcasts = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, email);
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                webcasts.add(new Webcast(rs.getInt("contentItemId"), rs.getInt("duration"), rs.getString("url"),
                        rs.getString("lector"), rs.getString("organisation"), rs.getString("title")));
            }
        } catch (Exception e) {
            System.out.println("failed to get webcasts");
        }

        return webcasts;
    }
}
