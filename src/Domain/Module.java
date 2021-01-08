package Domain;

public class Module {
    private int contentItemId;
    private int version;
    private String contactName;
    private String contactMail;
    private int indexNumber;
    private String title;
    private String courseName;
    private Boolean active;

    public Module(int contentItemId, int version, String contactName, String contactMail, int indexNumber, String title, String courseName) {
        this.contentItemId = contentItemId;
        this.version = version;
        this.contactName = contactName;
        this.contactMail = contactMail;
        this.indexNumber = indexNumber;
        this.title = title;
        this.courseName = courseName;
        this.active = false;
    }

    public void toggleActive() {
        if (active) {
            active = false;
        } else {
            active = true;
        }
    }

    public boolean isActive() {
        return active;
    }

    public int getContentItemId() {
        return this.contentItemId;
    }

    public void setContentItemId(int contentItemId) {
        this.contentItemId = contentItemId;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactMail() {
        return this.contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public int getIndexNumber() {
        return this.indexNumber;
    }

    public void setIndexNumber(int indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
