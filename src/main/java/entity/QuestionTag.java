package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions_tags")
public class QuestionTag {

    @Id
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public QuestionTag() {
    }

    public QuestionTag(Question question, Tag tag) {
        this.question = question;
        this.tag = tag;
    }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Tag getTag() { return tag; }
    public void setTag(Tag tag) { this.tag = tag; }
}
