package Domain;

public class Certificate {
    private int certiticateId;
    private String registrationDate;
    private String courseName;
    private String email;

    public Certificate(int certiticateId, String registrationDate, String courseName, String email) {
        this.certiticateId = certiticateId;
        this.registrationDate = registrationDate;
        this.courseName = courseName;
        this.email = email;
    }

    public int getCertiticateId() {
        return certiticateId;
    }

    public void setCertiticateId(int certiticateId) {
        this.certiticateId = certiticateId;
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
