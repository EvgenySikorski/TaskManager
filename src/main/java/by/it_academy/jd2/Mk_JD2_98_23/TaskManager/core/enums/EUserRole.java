package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums;

public enum EUserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    MANAGER("ROLE_MANAGER"),
    SYSTEM("ROLE_SYSTEM");

    ;

    private final String userRole;

    EUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }
}
