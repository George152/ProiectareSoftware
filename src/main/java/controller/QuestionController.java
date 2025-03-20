package controller;

import entity.Question;
import service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/getAll")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @PostMapping("/insert")
    public Question insertQuestion(@RequestBody Question question) {
        return questionService.insertQuestion(question);
    }

    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/deleteById")
    public String deleteQuestion(@RequestParam Long id) {
        return questionService.deleteQuestion(id);
    }
}
