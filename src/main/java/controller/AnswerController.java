package controller;

import entity.Answer;
import entity.Question;
import entity.User;
import service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.QuestionService;
import service.UserService;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @GetMapping("/getAll")
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;
    @PostMapping("/insert")
    public Answer insertAnswer(@RequestBody Answer answer, @RequestParam Long authorId, @RequestParam Long questionId) {
         User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionById(questionId);

         if (author == null) {
            throw new RuntimeException("User not found with id: " + authorId);
        }

        if (question == null) {
            throw new RuntimeException("Question not found with id: " + questionId);
        }

         answer.setAuthor(author);
        answer.setQuestion(question);

         return answerService.insertAnswer(answer);
    }

    @PutMapping("/update")
    public Answer updateAnswer(@RequestBody Answer answer, @RequestParam Long authorId, @RequestParam Long questionId) {

        User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionById(questionId);

       if (author == null) {
            throw new RuntimeException("User not found with id: " + authorId);
        }

        if (question == null) {
            throw new RuntimeException("Question not found with id: " + questionId);
        }

         Answer existingAnswer = answerService.findAnswerById(answer.getId());
        if (existingAnswer == null) {
            throw new RuntimeException("Answer not found with id: " + answer.getId());
        }

        existingAnswer.setAuthor(author);
        existingAnswer.setQuestion(question);
        existingAnswer.setContent(answer.getContent());

        return answerService.updateAnswer(existingAnswer);
    }

    @DeleteMapping("/deleteById")
    public String deleteAnswer(@RequestParam Long id) {
        return answerService.deleteAnswer(id);
    }
}
