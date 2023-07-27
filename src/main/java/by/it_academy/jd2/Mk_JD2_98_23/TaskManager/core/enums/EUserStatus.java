package by.it_academy.jd2.Mk_JD2_98_23.TaskManager.core.enums;

public enum EUserStatus {
    WAITING_ACTIVATION("waiting_activation"),
    ACTIVATED("activated"),
    DEACTIVATED("deactivated")
    ;

    private final String userStatus;

    EUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }
}
