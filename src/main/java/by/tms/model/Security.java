package by.tms.model;

import lombok.Data;

@Data
public class Security {
    private int id;
    private String username;
    private String password;
    private int userId;
    private Role role;
}
