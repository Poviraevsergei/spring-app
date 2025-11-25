package by.tms.controller;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import by.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Успешный статус код, значит пользователей нет в системе"),
            @ApiResponse(responseCode = "200", description = "Успешный, вернет лист из пользователей"),
            @ApiResponse(responseCode = "500", description = "Какие-то проблемы со стороны сервера")
    })
    @Operation(method = "GET",
            summary = "Возвращает всех пользователей",
            description = "Делает запрос в БД и возвращает лист из пользователей(не проходит фильтрацию)")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(method = "GET",
            summary = "Возвращает пользователя по id",
            description = "Делает запрос в БД и возвращает пользователя по id(не проходит фильтрацию)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@Parameter(description = "Id пользователя в системе") @PathVariable("id") int id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<HttpStatusCode> addUser(@RequestBody UserCreateDto user) {
        if (userService.addUser(user)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @Tag(name = "remove-endpoints")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatusCode> deleteUser(@PathVariable("id") int id) {
        if (userService.removeUserById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        Optional<User> userOptional = userService.updateUser(user);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}
