package controller;

import dto.VoteRequestDTO;
import dto.VoteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.VoteService;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<VoteResponseDTO> getAllVotes() {
        return voteService.getAllVotes();
    }

    @PostMapping
    public VoteResponseDTO createVote(@RequestBody VoteRequestDTO voteRequestDTO) {
        return voteService.createVote(voteRequestDTO);
    }
}
