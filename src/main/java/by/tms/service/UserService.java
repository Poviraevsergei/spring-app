package by.tms.service;

import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import by.tms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public boolean addUser(UserCreateDto user) {
        return userRepository.addUser(user);
    }

    public boolean removeUserById(int id) {
        Optional<User> userFromDb = getUserById(id);
        if (userFromDb.isPresent() && userRepository.removeUserById(id)) {
            userFromDb = getUserById(id);
            return userFromDb.isEmpty();
        }
        return false;
    }
}
