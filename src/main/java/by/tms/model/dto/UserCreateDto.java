package by.tms.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Schema(description = "Это DTO для создания пользователя в системе")
@Data
public class UserCreateDto {
    private String firstName;
    private String secondName;
    private int age;

    @Schema(description = "Это email пользователя")
    private String email;


}
