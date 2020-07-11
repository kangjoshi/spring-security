package edu.til.springsecurity.web;

import edu.til.springsecurity.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<User> getUser(String id) {

        User user1 = User.builder()
                            .id("kangjoshi")
                            .password("1234")
                            .age(31)
                            .height(178)
                            .weight(82)
                        .build();


        return ResponseEntity.ok(user1);
    }


}
