package service;

import entity.Answer;
import entity.User;
import repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    public Answer findAnswerById(Long id) {
        Optional<Answer> answerOptional = answerRepository.findById(id);
        return answerOptional.orElseThrow(() -> new RuntimeException("Answer not found with id: " + id));
    }

    public List<Answer> getAllAnswers() {
        return (List<Answer>) answerRepository.findAll();
    }

    public Answer insertAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer updateAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public String deleteAnswer(Long id) {
        try {
            answerRepository.deleteById(id);
            return "Deletion Successfully";
        } catch (Exception e) {
            return "Failed to delete answer with id " + id;
        }
    }
}
