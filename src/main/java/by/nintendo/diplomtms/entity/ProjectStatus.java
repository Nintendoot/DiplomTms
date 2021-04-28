package by.nintendo.diplomtms.entity;

public enum ProjectStatus {
    NOT_STARTED("Not started"),
    IN_PROGRESS("In progress"),
    COMPLETED("Completed"),
    POSTPONED("Postponed");

    private String iteam;

    ProjectStatus(String iteam) {
        this.iteam = iteam;
    }

    public String getIteam() {
        return iteam;
    }

    public void setIteam(String iteam) {
        this.iteam = iteam;
    }
}
