package by.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity(name = "users")
@Data
@Component
public class User {

    @Id
    @SequenceGenerator(name = "user_generator", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "user_generator")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    private int age;
    private String email;
    private LocalDateTime created;
    private LocalDateTime changed;
}
