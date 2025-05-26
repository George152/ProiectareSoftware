package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteRequestDTO {
    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("answerId")
    private Long answerId;

    @JsonProperty("voteType")
    private Integer voteType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Integer getVoteType() {
        return voteType;
    }

    public void setVoteType(Integer voteType) {
        this.voteType = voteType;
    }
}
