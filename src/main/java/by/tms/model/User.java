package by.tms.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class User {
    private int id;
    private String firstName;
    private String secondName;
    private int age;
    private String email;
    private LocalDateTime created;
    private LocalDateTime changed;
}
