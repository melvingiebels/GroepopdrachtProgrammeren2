package Database;

import java.sql.*;
import Domain.Registration;
import Domain.Student;
import Domain.Certificate;
import Domain.Module;
import java.util.ArrayList;

public class StudentDAO extends GenericDAO {
    private CourseDAO courseDAO = new CourseDAO();

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
            stmt.executeQuery();

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
                stmt.executeQuery();
            } catch (Exception e) {
                // Set progress to 0 per module
                SQL = "INSERT INTO Progress VALUES(?, ?, ?)";
                try (PreparedStatement stmt = con.prepareStatement(SQL)) {
                    // Add values to prepared statement
                    stmt.setString(1, registration.getEmail());
                    stmt.setInt(2, module.getContentItemId());
                    stmt.setInt(3, 0);

                    // Excecute query
                    stmt.executeQuery();

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
            stmt.executeQuery();
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
            rs = stmt.executeQuery();
            // excecute query
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("failed to update progress");
        }
    }

    // update progress of modules per course per student & automatically create
    // certificate when progress is 100 (DetailsStudent)
    public void updateProgressModule(String email, int contentItemId, int progress, String courseName,
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
            rs = stmt.executeQuery();
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

}
