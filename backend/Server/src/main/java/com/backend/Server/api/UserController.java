package com.backend.Server.api;

import com.backend.Server.model.User;
import com.backend.Server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("api/v1/user")  // request go to this link
@RestController  // this says the class serves as the first layer in the stack: api -> service -> data access
// This is an endpoint prioritized for user authentication
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Set this header response for all controller
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    // This serves as the method handles Post request
    @PostMapping
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    // This serves as the method handles Get request
    @GetMapping
    public List<User> getAllUser() {
        return userService.getAllUser();
    }
}
