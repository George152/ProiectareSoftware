package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "score", columnDefinition = "FLOAT DEFAULT 0")
    private Float score;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "picture")
    private String picture;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_banned", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isBanned;

    public User() {
    }

    public User(Long id, String email, String username, String role, Float score, String password, String picture, String phoneNumber, Boolean isBanned) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.role = role;
        this.score = score;
        this.password = password;
        this.picture = picture;
        this.phoneNumber = phoneNumber;
        this.isBanned = isBanned;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Float getScore() { return score; }
    public void setScore(Float score) { this.score = score; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPicture() { return picture; }
    public void setPicture(String picture) { this.picture = picture; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public Boolean getIsBanned() { return isBanned; }
    public void setIsBanned(Boolean isBanned) { this.isBanned = isBanned; }
}
