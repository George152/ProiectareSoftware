package controller;

import entity.Answer;
import service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/insert")
    public Answer insertAnswer(@RequestBody Answer answer) {
        return answerService.insertAnswer(answer);
    }

    @PutMapping("/update")
    public Answer updateAnswer(@RequestBody Answer answer) {
        return answerService.updateAnswer(answer);
    }

    @DeleteMapping("/deleteById")
    public String deleteAnswer(@RequestParam Long id) {
        return answerService.deleteAnswer(id);
    }
}
