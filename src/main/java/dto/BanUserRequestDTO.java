package dto;

public class BanUserRequestDTO {
    private Long userId;
    private String reason;

    // Constructors
    public BanUserRequestDTO() {}

    public BanUserRequestDTO(Long userId, String reason) {
        this.userId = userId;
        this.reason = reason;
    }

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return "BanUserRequestDTO{" +
                "userId=" + userId +
                ", reason='" + reason + '\'' +
                '}';
    }
}
