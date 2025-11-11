package by.tms.model.dto;

import lombok.Data;

@Data
public class UserCreateDto {
    private String firstName;
    private String secondName;
    private int age;
    private String email;
}
