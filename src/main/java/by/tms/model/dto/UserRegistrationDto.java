package by.tms.model.dto;

import by.tms.annotation.CustomAge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationDto {

    @NotBlank
    @Size(min = 5, max = 10)
    private String username;

    @Pattern(regexp = "[A-z]{6,}")
    private String password;

    @CustomAge
    private Integer age;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    //@Positive
    //@Negative
    //@PositiveOrZero
    //@NegativeOrZero
    //@NotNull
    //@Null
    //@Email
    //@Future
    //@Past
    //@FutureOrPresent
    //@PastOrPresent
    //@Digits
    //@DecimalMax("10.2")
    //@DecimalMin("10.2")
    //@AssertFalse
    //@AssertTrue
}
