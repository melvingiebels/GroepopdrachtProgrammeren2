package Domain;

public class Registration {
    private String registrationDate;
    private String courseName;
    private String email;

    public Registration(String registrationDate, String courseName, String email) {
        this.registrationDate = registrationDate;
        this.courseName = courseName;
        this.email = email;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
