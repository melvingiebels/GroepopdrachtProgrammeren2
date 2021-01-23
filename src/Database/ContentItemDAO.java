package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import Domain.Certificate;
import Domain.Course;
import Domain.Module;
import Domain.Webcast;

public class ContentItemDAO extends GenericDAO {
    private CourseDAO courseDAO = new CourseDAO();

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

    public ArrayList<Module> getCourseModules(String courseName) {
        ArrayList<Module> modules = new ArrayList<>();
        SQL = "SELECT * FROM Module WHERE CourseName=?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            stmt.setString(1, courseName);
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

    public int getProgressPerWebcastPerStudent(int contentItemId) {
        SQL = "SELECT Percentage FROM Progress WHERE ContentItemId= ?";
        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setInt(1, contentItemId);
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

    public void updateWebcastProgress(String email, int contentItemId, int progress) {
        SQL = "UPDATE Progress SET Percentage=? WHERE Email=? AND ContentItemId=?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setInt(1, progress);
            stmt.setString(2, email);
            stmt.setInt(3, contentItemId);
            // excecute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update progress");
        }
    }

    // update progress of modules per course per student & automatically create
    // certificate when progress is 100 (DetailsStudent)
    public void updateModuleProgress(String email, int contentItemId, int progress, String courseName,
            String registrationDate) {
        // Change progress
        SQL = "UPDATE Progress SET Percentage=? WHERE Email=? AND ContentItemId=?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setInt(1, progress);
            stmt.setString(2, email);
            stmt.setInt(3, contentItemId);
            rs = stmt.executeQuery();
            // excecute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update progress");
        }
        // check if student already has a certificate
        ArrayList<Certificate> certificates = this.getCertificatesPerUser(email);
        boolean hasCertificate = false;
        Certificate completedCertificate = null;
        for (Certificate certificate : certificates) {
            if (certificate.getRegistrationDate().equals(registrationDate)
                    && certificate.getCourseName().equals(courseName) && certificate.getEmail().equals(email)) {
                hasCertificate = true;
                completedCertificate = certificate;
            }
        }

        // Check if student completed all modules
        ArrayList<Module> modules = courseDAO.getModulesPerCourse(courseName);
        boolean allCompleted = true;
        for (Module module : modules) {
            if (this.getProgressPerModulePerStudent(email, module.getContentItemId()) != 100) {
                allCompleted = false;
            }
        }

        // Create certificate if student completed all modules & doesn't already have a
        // certificate
        if (allCompleted == true && hasCertificate == false) {
            this.createCertificate(email, courseName, registrationDate);
        }

        // Remove certificate when progress is set back under 100
        if (allCompleted == false && hasCertificate == true) {
            this.removeCertificate(completedCertificate.getCertiticateId());
        }

    }

    // Create certificate (used in updateProgress())
    private void createCertificate(String email, String courseName, String registrationDate) {
        SQL = "INSERT INTO Certificate (RegistrationDate, CourseName, Email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setString(1, registrationDate);
            stmt.setString(2, courseName);
            stmt.setString(3, email);
            // excecute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to create certificate");
        }
    }

    // Get all certificates from user (DetailsStudent)
    public ArrayList<Certificate> getCertificatesPerUser(String email) {
        SQL = "SELECT * FROM certificate WHERE Email=?";
        ArrayList<Certificate> certificates = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setString(1, email);

            // Execute query
            rs = stmt.executeQuery();
            while (rs.next()) {
                certificates.add(new Certificate(rs.getInt("CertificateId"), rs.getString("RegistrationDate"),
                        rs.getString("CourseName"), rs.getString("Email")));
            }
        } catch (Exception e) {
            System.out.println("Failed to get certificates");
        }

        return certificates;
    }

    public void removeCertificate(int CertificateId) {
        SQL = "DELETE FROM certificate WHERE CertificateId =?";

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to statement
            stmt.setInt(1, CertificateId);

            // Execute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Failed to remove certificate");
        }
    }

    public ArrayList<HashMap<Webcast, Integer>> getTop3ViewedWebcasts() {
        SQL = "SELECT * FROM Webcast JOIN (SELECT TOP 3 Webcast.ContentItemId, COUNT(*) AS 'Views' FROM Webcast JOIN Progress ON Webcast.ContentItemId = Progress.ContentItemId GROUP BY Webcast.ContentItemId ORDER BY Views DESC) AS best ON Webcast.ContentItemId = best.ContentItemId ";
        ArrayList<HashMap<Webcast, Integer>> webcasts = new ArrayList<>();

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {

            // Execute query
            rs = stmt.executeQuery();

            while (rs.next()) {
                HashMap<Webcast, Integer> map = new HashMap<>();
                map.put(new Webcast(rs.getInt("contentItemId"), rs.getInt("duration"), rs.getString("url"),
                        rs.getString("lector"), rs.getString("organisation"), rs.getString("title")),
                        rs.getInt("Views"));

                webcasts.add(map);
            }
        } catch (Exception e) {
            System.out.println("Failed to get top 3 most viewd webcasts");
        }

        return webcasts;
    }

    public HashMap<Integer, Integer> getAverageModulePercentage(String courseName) {
        SQL = "SELECT module.ContentItemId, (SUM(Progress.Percentage) / COUNT(Progress.Email)) AS 'AvgPercentage' FROM Progress JOIN Module ON Progress.ContentItemId = module.ContentItemId WHERE module.CourseName=? GROUP BY module.ContentItemId";
        
        HashMap<Integer, Integer> percentages = new HashMap<>();

        try (PreparedStatement stmt = con.prepareStatement(SQL)) {
            // Add values to prepared statement
            stmt.setString(1, courseName);
            // execute statement
            rs = stmt.executeQuery();

            while (rs.next()) {
                percentages.put(rs.getInt("ContentItemId"), rs.getInt("AvgPercentage"));
            }
        } catch (Exception e) {
            System.out.println("failed to get webcasts");
        }
        return percentages;
    }   
}
