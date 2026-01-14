package org.example.ingineriesoftwareparkinglot.common;

public class UserDto {
    private Long id;
    private String username;
    private String email;

    public UserDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    // If your JSP specifically uses ${user.name}, add this helper:
    public String getName() {
        return username;
    }
    public String getEmail() {
        return email;
    }
}