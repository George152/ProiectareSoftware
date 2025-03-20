package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @Column(name = "vote_type", nullable = false)
    private Integer voteType;

    public Vote() {
    }

    public Vote(Long id, User user, Question question, Answer answer, Integer voteType) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.answer = answer;
        this.voteType = voteType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Answer getAnswer() { return answer; }
    public void setAnswer(Answer answer) { this.answer = answer; }

    public Integer getVoteType() { return voteType; }
    public void setVoteType(Integer voteType) { this.voteType = voteType; }
}
