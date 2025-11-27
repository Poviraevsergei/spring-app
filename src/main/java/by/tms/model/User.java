package by.tms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity(name = "users")
@Data
@EqualsAndHashCode(exclude = "security")
@ToString(exclude = "security")
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

    @JsonIgnore //не учитывает это поле в JSON
    @OneToOne(optional = false, mappedBy = "user", cascade = CascadeType.ALL)
    private Security security;
}
