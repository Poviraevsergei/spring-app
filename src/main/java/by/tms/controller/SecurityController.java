package by.tms.controller;

import by.tms.model.User;
import by.tms.service.SecurityService;
import by.tms.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/security")
public class SecurityController {

    public SecurityService securityService;
    public UserService userService;

    public SecurityController(SecurityService securityService, UserService userService) {
        this.securityService = securityService;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(@RequestParam("username") String username,
                               @RequestParam("age") int age,
                               @RequestParam("password") String password,
                               Model model) {
        Boolean result = securityService.registration(username, age, password);
        if (result) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("usersKey", users );
            return "users";
        }
        return "error";
    }
}
