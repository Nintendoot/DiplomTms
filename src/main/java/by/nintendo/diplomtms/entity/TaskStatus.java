package by.nintendo.diplomtms.entity;

public enum TaskStatus {
    NOT_STARTED("Not started"),
    IN_PROGRESS("In progress"),
    CHECKING("Checking"),
    COMPLETED("Completed"),
    POSTPONED("Postponed");

    private String name;

    TaskStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
