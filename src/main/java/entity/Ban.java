package entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "bans")
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "moderator_id", nullable = false)
    private User moderator;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "banned_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date bannedDate = new Date();

    public Ban() {
    }

    public Ban(Long id, User user, User moderator, String reason, Date bannedDate) {
        this.id = id;
        this.user = user;
        this.moderator = moderator;
        this.reason = reason;
        this.bannedDate = bannedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public User getModerator() { return moderator; }
    public void setModerator(User moderator) { this.moderator = moderator; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Date getBannedDate() { return bannedDate; }
    public void setBannedDate(Date bannedDate) { this.bannedDate = bannedDate; }
}
