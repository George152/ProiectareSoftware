package controller;

import entity.Question;
import service.QuestionService;
import service.UserService;
import entity.User;
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

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public Question insertQuestion(@RequestBody Question question, @RequestParam Long authorId) {
        User author = userService.findUserById(authorId);

        if (author != null) {
            question.setAuthor(author);
        } else {
            throw new RuntimeException("User not found with id: " + authorId);
        }

        return questionService.insertQuestion(question);
    }


    @PutMapping("/update")
    public Question updateQuestion(@RequestBody Question question, @RequestParam Long questionId, @RequestParam Long authorId) {
        Question existingQuestion = questionService.findQuestionById(questionId);

         if (existingQuestion == null) {
            throw new RuntimeException("Question not found with id: " + questionId);
        }

         User author = userService.findUserById(authorId);
        if (author != null) {
            existingQuestion.setAuthor(author);
        } else {
            throw new RuntimeException("User not found with id: " + authorId);
        }

        existingQuestion.setContent(question.getContent());

        return questionService.updateQuestion(existingQuestion);
    }


    @DeleteMapping("/deleteById")
    public String deleteQuestion(@RequestParam Long id) {
        return questionService.deleteQuestion(id);
    }
}
