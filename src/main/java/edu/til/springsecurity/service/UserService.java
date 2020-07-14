package edu.til.springsecurity.service;

import edu.til.springsecurity.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static Map<String, User> users = new HashMap<>();

    public UserService() {
        users.put("Jack", User.builder().id("Jack").password("1234").age(31).height(178).weight(82).build());
        users.put("Hugo", User.builder().id("Hugo").password("0987").age(30).height(172).weight(101).build());
        users.put("Kate", User.builder().id("Kate").password("3377").age(31).height(160).weight(53).build());
    }

    public User getUser(String userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new IllegalArgumentException("User id is wrong");
        }
    }
}
