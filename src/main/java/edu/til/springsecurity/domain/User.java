package edu.til.springsecurity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    private String id;
    @JsonIgnore
    private String password;
    private int age;
    private int height;
    private int weight;

    @Builder
    public User(String id, String password, int age, int height, int weight) {
        this.id = id;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

}
