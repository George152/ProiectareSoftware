package service;

import entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

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
