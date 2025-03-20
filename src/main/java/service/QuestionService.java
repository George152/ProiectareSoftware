package service;

import entity.Question;
import repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getAllQuestions() {
        return (List<Question>) questionRepository.findAll();
    }

    public Question insertQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    public String deleteQuestion(Long id) {
        try {
            questionRepository.deleteById(id);
            return "Deletion Successfully";
        } catch (Exception e) {
            return "Failed to delete question with id " + id;
        }
    }
}
