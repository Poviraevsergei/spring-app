package by.tms.model;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class User {
    private int id;
    private String username;
    private int age;
    private LocalDateTime created;
    private LocalDateTime changed;

    public User() {}

    public User(int id, String username, LocalDateTime created, LocalDateTime changed, int age) {
        this.id = id;
        this.username = username;
        this.created = created;
        this.changed = changed;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getChanged() {
        return changed;
    }

    public void setChanged(LocalDateTime changed) {
        this.changed = changed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", created=" + created +
                ", changed=" + changed +
                ", age=" + age +
                '}';
    }
}
