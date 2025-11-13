package by.tms.service;

import by.tms.exception.UsernameExistsException;
import by.tms.model.User;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.repository.SecurityRepository;
import by.tms.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SecurityService {
    private final UserRepository userRepository;
    private final SecurityRepository securityRepository;

    public SecurityService(UserRepository userRepository, SecurityRepository securityRepository) {
        this.userRepository = userRepository;
        this.securityRepository = securityRepository;
    }

    public boolean registration(UserRegistrationDto userRegistrationDto) throws UsernameExistsException {
        if (isUsernameUsed(userRegistrationDto.getUsername())){
            throw new UsernameExistsException(userRegistrationDto.getUsername());
        }
        //TODO: registration
        User user = new User();
        //user.setUsername(userRegistrationDto.getUsername());
        user.setAge(userRegistrationDto.getAge());
        user.setCreated(LocalDateTime.now());
        user.setChanged(LocalDateTime.now());

        try {
           // return userRepository.addUser(user, userRegistrationDto.getPassword()) > 0;
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isUsernameUsed(String username) {
        return securityRepository.getSecurityByUsername(username).isPresent();
    }
}
