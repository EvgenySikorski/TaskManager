package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums;

public enum EUserRole {
    ADMIN("admin"),
    USER("user")
    ;

    private final String userRole;

    EUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
