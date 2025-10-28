package by.tms.service;

import by.tms.model.User;
import by.tms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SecurityService {
    private UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registration(String username, int age, String password) {
        User user = new User();
        user.setUsername(username);
        user.setAge(age);
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());

        try {
            return userRepository.addUser(user, password) > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
