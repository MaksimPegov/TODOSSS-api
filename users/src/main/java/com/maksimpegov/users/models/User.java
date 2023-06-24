package com.maksimpegov.users.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id"),
        @UniqueConstraint(name = "user_username_unique", columnNames = "username")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // to avoid hibernate extra properties
public class User {
    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public Boolean passwordValidation() {
        setPassword(password.trim());
        return this.password.length() >= 6;
    }

    public Boolean usernameValidation() {
        setUsername(username.trim());
        return this.username.length() >= 3;
    }

    public User(String username, String password) {
        this.username = username.trim();
        this.password = password.trim();
        this.id = UUID.randomUUID().toString();
    }

    public User() {
        this.id = UUID.randomUUID().toString();
    }
}
