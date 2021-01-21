package Domain;

public class Webcast {
    private int contentItemId;
    private int duration;
    private String url;
    private String lector;
    private String organisation;
    private String title;

    public Webcast(int contentItemId, int duration, String url, String lector, String organisation, String title) {
        this.contentItemId = contentItemId;
        this.duration = duration;
        this.url = url;
        this.lector = lector;
        this.organisation = organisation;
        this.title = title;
    }

    public int getContentItemId() {
        return contentItemId;
    }

    public void setContentItemId(int contentItemId) {
        this.contentItemId = contentItemId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
