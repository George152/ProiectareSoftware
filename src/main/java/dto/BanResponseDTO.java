package dto;

import java.util.Date;

public class BanResponseDTO {
    private Long id;
    private Long userId;
    private Long moderatorId;
    private String reason;
    private Date bannedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getModeratorId() {
        return moderatorId;
    }

    public void setModeratorId(Long moderatorId) {
        this.moderatorId = moderatorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getBannedDate() {
        return bannedDate;
    }

    public void setBannedDate(Date bannedDate) {
        this.bannedDate = bannedDate;
    }
}
