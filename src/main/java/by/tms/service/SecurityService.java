package by.tms.service;

import by.tms.model.User;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SecurityService {
    private final UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registration(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setAge(userRegistrationDto.getAge());
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());

        try {
            return userRepository.addUser(user, userRegistrationDto.getPassword()) > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
