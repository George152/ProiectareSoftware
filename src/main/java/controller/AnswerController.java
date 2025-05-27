// controller/AnswerController.java
package controller;

import dto.AnswerRequestDTO;
import dto.AnswerResponseDTO;
import entity.Answer;
import entity.Question;
import entity.User;
import service.AnswerService;
import service.QuestionService;
import service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public List<AnswerResponseDTO> getAnswersByQuestionId(@PathVariable Long questionId) {
        return answerService.getAnswersByQuestionId(questionId);
    }


    @GetMapping("/getAll")
    public List<AnswerResponseDTO> getAllAnswers() {
        return answerService.getAllAnswers().stream()
                .map(answerService::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/insert")
    public AnswerResponseDTO insertAnswer(
            @RequestBody AnswerRequestDTO answerDTO,
            @RequestParam Long authorId,
            @RequestParam Long questionId) {

        User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionEntityById(questionId);

        Answer answer = new Answer();
        answer.setAuthor(author);
        answer.setQuestion(question);
        answer.setContent(answerDTO.getContent());
        answer.setPicture(answerDTO.getPicture());

        return answerService.mapToResponseDTO(answerService.insertAnswer(answer));

    }

    @PutMapping("/update")
    public AnswerResponseDTO updateAnswer(
            @RequestBody AnswerRequestDTO answerDTO,
            @RequestParam Long answerId,
            @RequestParam Long authorId,
            @RequestParam Long questionId) {

        User author = userService.findUserById(authorId);
        Question question = questionService.findQuestionEntityById(questionId);

        Answer existingAnswer = answerService.findAnswerById(answerId);
        existingAnswer.setAuthor(author);
        existingAnswer.setQuestion(question);
        existingAnswer.setContent(answerDTO.getContent());
        existingAnswer.setPicture(answerDTO.getPicture());

        return answerService.mapToResponseDTO(answerService.updateAnswer(existingAnswer));

    }

    @DeleteMapping("/deleteById")
    public String deleteAnswer(@RequestParam Long id) {
        return answerService.deleteAnswer(id);
    }

}
