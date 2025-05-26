package repository;

import entity.Answer;
import entity.Question;
import entity.User;
import entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByUserIdAndQuestionId(Long userId, Long questionId);
    Optional<Vote> findByUserIdAndAnswerId(Long userId, Long answerId);

    boolean existsByUserAndQuestion(User user, Question question);
    boolean existsByUserAndAnswer(User user, Answer answer);

}
