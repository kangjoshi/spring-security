package edu.til.springsecurity.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.til.springsecurity.domain.User;
import edu.til.springsecurity.service.UserService;
import edu.til.springsecurity.util.JwtUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenWrongUserIdForUserTokenThenGetResponseStatus400() throws Exception {
        String userId = "";
        String body = objectMapper.writeValueAsString(User.builder().id(userId).build());

        mockMvc.perform(post("/users/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest())
        .andDo(print());
    }

    @Test
    public void whenGoodUserIdForUserTokenThenGetUserTokenAndResponseStatus200() throws Exception {
        String userId = "Jack";
        User user = User.builder().id(userId).build();

        when(userService.getUser(userId)).thenReturn(user);

        String body = objectMapper.writeValueAsString(User.builder().id(userId).build());

        mockMvc.perform(post("/users/login")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
                .andExpect(result -> assertNotNull(result.getResponse().getContentAsString()))
        .andDo(print());
    }

    @Test
    public void whenAccessUserInfoWithoutUserTokenThenGetResponseStatus401() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    public void whenAccessUserInfoWithUserTokenThenGetUserInfoAndResponseStatus200() throws Exception {
        String userId = "Jack";
        User user = User.builder().id(userId).password("1234").age(31).height(178).weight(82).build();

        when(userService.getUser(userId)).thenReturn(user);

        String token = JwtUtil.create(user);

        mockMvc.perform(get("/users")
                .header("Authorization", "Bearer " + token)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(user.getId()))
        .andExpect(jsonPath("$.age").value(user.getAge()))
        .andExpect(jsonPath("$.height").value(user.getHeight()))
        .andExpect(jsonPath("$.weight").value(user.getWeight()))
        .andDo(print());
    }

}