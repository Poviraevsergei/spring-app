package by.tms.controller;

import by.tms.exception.UsernameExistsException;
import by.tms.model.User;
import by.tms.model.dto.UserRegistrationDto;
import by.tms.service.SecurityService;
import by.tms.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/security")
public class SecurityController {

    public SecurityService securityService;
    public UserService userService;

    public SecurityController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    //TODO: CRUD Security

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute UserRegistrationDto userRegistrationDto,
                               BindingResult bindingResult) throws UsernameExistsException {
        if (bindingResult.hasErrors()) {
            List<String> errMessages = new ArrayList<>();

            for (ObjectError objectError : bindingResult.getAllErrors()) {
                System.out.println(objectError);
                errMessages.add(objectError.getDefaultMessage());
            }
            throw new ValidationException(String.valueOf(errMessages));
        }
        Boolean result = securityService.registration(userRegistrationDto);
        if (result) {
            List<User> users = userService.getAllUsers();
            //model.addAttribute("usersKey", users );
            return "users";
        }
        return "error-page";
    }
}
