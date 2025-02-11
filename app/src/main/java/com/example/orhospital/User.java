package com.example.orhospital;
public class User {
    public String userId;
    public String name;
    public int age;

    // Default constructor (required for Firebase)
    public User() {}

    // Parameterized constructor
    public User(String userId, String name, int age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }
}