package controller;

import by.tms.controller.UserController;
import by.tms.model.User;
import by.tms.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;

@DisplayName("UCT")
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private User user;
    private User userDefault;

    @BeforeAll //1 раз
    static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll  //1 раз
    static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setFirstName("Test");
        user.setId(1);
        user.setAge(18);

        userDefault = new User();
        userDefault.setFirstName("Test");
        userDefault.setId(1);
        userDefault.setAge(18);
    }

    @AfterEach
    void afterEach() {
        System.out.println("After each");
    }

    @RepeatedTest(1)
    void getAllUsers_Success() {
        System.out.println("test getAllUsers");
    }

    @Tag("byIdTag")
    //@Disabled
    @Test
    void getUserById_Success() {
        //1. Настройка данных
        Mockito.when(userService.getUserById(anyInt())).thenReturn(Optional.of(user));

        //2. запускаем метод который тестируем
        ResponseEntity<User> result = userController.getUserById(1);

        //3. мы сравниваем то что вернул/сделал метод с ожидаемым результатом
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCodeValue());
        Assertions.assertEquals(userDefault, result.getBody());
    }

    @Test
    void getUserById_NotFound() {
        //1. Настройка данных
        Mockito.when(userService.getUserById(anyInt())).thenReturn(Optional.empty());

        //2. запускаем метод который тестируем
        ResponseEntity<User> result = userController.getUserById(1);

        //3. мы сравниваем то что вернул/сделал метод с ожидаемым результатом
        Assertions.assertNull(result.getBody());
        Assertions.assertEquals(404, result.getStatusCodeValue());
    }
}


/*
Assertions.assertTrue(2 + 2 == 4);
        Assertions.assertFalse(2 + 2 == 5);
        Assertions.assertNull(null);
        Assertions.assertNotNull("Hello World");
        Assertions.assertArrayEquals(new int[]{1,2,3}, new int[]{1,2,3});
        Assertions.assertEquals(10,10);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Illegal argument");
        });
 */