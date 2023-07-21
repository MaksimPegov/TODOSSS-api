package com.maksimpegov.users.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "user_id_unique", columnNames = "id"),
        @UniqueConstraint(name = "user_username_unique", columnNames = "username")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // to avoid hibernate extra properties
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    // creating sequence generator
    @GeneratedValue(strategy = SEQUENCE, generator = "user_sequence") // every new user will be getting auto-increace id
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    private Boolean passwordValidation() {
        setPassword(password.trim());
        return this.password.length() >= 6;
    }

    private Boolean usernameValidation() {
        setUsername(username.trim());
        return this.username.length() >= 3;
    }

    public boolean userValidation() {
        return passwordValidation() && usernameValidation();
    }

    public void hidePassword() {
        setPassword(null);
    }

    public User(String username, String password) {
        this.username = username.trim();
        this.password = password.trim();
    }

    public void encryptPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
