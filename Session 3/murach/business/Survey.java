package murach.business;

import java.io.Serializable;

public class Survey implements Serializable {
    private String hearAboutUs;
    private String contactMethod;
    private boolean receiveAnnouncements;

    public Survey() {
        this("", "", false);
    }

    public Survey(String hearAboutUs, String contactMethod, boolean receiveAnnouncements) {
        this.hearAboutUs = hearAboutUs;
        this.contactMethod = contactMethod;
        this.receiveAnnouncements = receiveAnnouncements;
    }

    public String getHearAboutUs() {
        return hearAboutUs;
    }

    public void setHearAboutUs(String hearAboutUs) {
        this.hearAboutUs = hearAboutUs;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public boolean isReceiveAnnouncements() {
        return receiveAnnouncements;
    }

    public void setReceiveAnnouncements(boolean receiveAnnouncements) {
        this.receiveAnnouncements = receiveAnnouncements;
    }
}
