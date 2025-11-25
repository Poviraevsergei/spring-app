package by.tms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "security")
@Data
@NoArgsConstructor
public class Security {

    @Id
    @SequenceGenerator(name = "security_generator", sequenceName = "security_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_generator")
    private Integer id;
    private String username;
    private String password;

    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    private Role role;
}
