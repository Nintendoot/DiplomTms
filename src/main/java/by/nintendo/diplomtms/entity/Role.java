package by.nintendo.diplomtms.entity;

public enum Role {
    USER("User"),
    MANAGER("Manager"),
    ADMIN("Admin");

    private String iteam;

    Role(String iteam) {
        this.iteam = iteam;
    }

    public String getIteam() {
        return iteam;
    }

    public void setIteam(String iteam) {
        this.iteam = iteam;
    }
}
