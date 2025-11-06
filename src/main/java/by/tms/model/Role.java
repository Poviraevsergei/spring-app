package by.tms.model;

public enum Role {
    USER(1), ADMIN(10), MODERATOR(5);

    private int status;

    Role(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
