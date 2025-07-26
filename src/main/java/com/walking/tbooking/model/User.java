package com.walking.tbooking.model;

import java.time.LocalDateTime;

public class User {

    private Long id;
    private String email;
    private String password;
    private String fullName;
    private Role role;
    private LocalDateTime lastLogin;
    private boolean isBlocked;


    public User() {
    }

    public User(Long id, String email, String password,
                String fullName, Role role, LocalDateTime lastLogin,
                boolean isBlocked) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.lastLogin = lastLogin;
        this.isBlocked = isBlocked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public enum Role {
        USER, ADMIN
    }
}
