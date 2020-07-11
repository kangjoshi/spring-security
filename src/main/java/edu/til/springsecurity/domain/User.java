package edu.til.springsecurity.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class User {

    private String id;
    private String password;
    private String name;
    private int age;
    private int height;
    private int weight;

    @Builder
    public User(String id, String password, String name, int age, int height, int weight) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

}
