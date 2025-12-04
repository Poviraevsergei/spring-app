package by.tms.service;

import by.tms.exception.UserNotFoundException;
import by.tms.model.User;
import by.tms.model.dto.UserCreateDto;
import by.tms.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User addUser(UserCreateDto userDto) {
        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setFirstName(userDto.getFirstName());
        newUser.setSecondName(userDto.getSecondName());
        newUser.setAge(userDto.getAge());
        newUser.setCreated(LocalDateTime.now());
        newUser.setChanged(LocalDateTime.now());
        return userRepository.save(newUser);
    }

    public boolean removeUserById(int id) {
        if (getUserById(id).isEmpty()) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        Optional<User> userFromDb = getUserById(id);
        return userFromDb.isEmpty();
    }

    public Optional<User> updateUser(User user) {
        Optional<User> userFromDbOptional = getUserById(user.getId());
        if (userFromDbOptional.isPresent()) {
            return Optional.of(userRepository.saveAndFlush(user));
        } else {
            throw new UserNotFoundException(user);
        }
    }

    //Sort
    public List<User> getSortedUsersByField(String field, String order) {
        if (order != null && !order.isBlank() && order.equalsIgnoreCase("desc")) {
            return userRepository.findAll(Sort.by(Sort.Direction.DESC, field));
        }
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, field));
    }

    //Pagination
    public Page<User> getAllUsersWithPagination(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }
}
