package edu.til.springsecurity.web;

import edu.til.springsecurity.domain.User;
import edu.til.springsecurity.service.UserService;
import edu.til.springsecurity.util.JwtUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody User user) {

        try {
            if (StringUtils.isEmpty(user.getId())) {
                throw new IllegalArgumentException("User id is Empty");
            }

            String token = JwtUtil.create(userService.getUser(user.getId()));
            return ResponseEntity.ok(token);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<User> getUser() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

}
