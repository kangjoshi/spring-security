package edu.til.springsecurity.util;

import com.auth0.jwt.interfaces.Claim;
import edu.til.springsecurity.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private static User user;

    @BeforeEach
    public void init(){
        user = User.builder().id("Jack").password("1234").age(31).height(178).weight(82).build();
    }

    @Test
    public void whenCreateTokenThenGetToken() {
        String token = JwtUtil.create(user);
        assertNotNull(token);
    }

    @Test
    public void whenGiveTokenThenGetAllPayloads() {
        String token = JwtUtil.create(user);

        Map<String, Claim> payloads = JwtUtil.getAllPayload(token);

        assertNotNull(payloads);
        assertEquals(user.getId(), payloads.get("userId").asString());
    }



}