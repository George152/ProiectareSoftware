package service;

import dto.VoteRequestDTO;
import dto.VoteResponseDTO;
import entity.Answer;
import entity.Question;
import entity.User;
import entity.Vote;
import repository.AnswerRepository;
import repository.QuestionRepository;
import repository.UserRepository;
import repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public VoteResponseDTO createVote(VoteRequestDTO dto) {
        System.out.println("Creating vote: " + dto);

        Optional<User> userOpt = userRepository.findById(dto.getUserId());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        if (dto.getVoteType() == null || (dto.getVoteType() != 1 && dto.getVoteType() != -1)) {
            System.out.println("Invalid vote type: " + dto.getVoteType());
            throw new IllegalArgumentException("Vote type must be 1 (upvote) or -1 (downvote).");
        }

        User voter = userOpt.get();
        System.out.println("User found: " + voter.getUsername());

        Question question = null;
        Answer answer = null;
        User author = null;

        // ======================== QUESTION VOTE ========================
        if (dto.getQuestionId() != null) {
            question = questionRepository.findById(dto.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            author = question.getAuthor();

            if (author.getId().equals(voter.getId())) {
                throw new IllegalArgumentException("You cannot vote your own question.");
            }

            if (voteRepository.existsByUserAndQuestion(voter, question)) {
                throw new IllegalArgumentException("You have already voted this question.");
            }

            // Scor pentru autorul întrebării
            if (dto.getVoteType() == 1) {
                author.setScore((float) (author.getScore() + 2.5));
            } else {
                author.setScore((float) (author.getScore() - 1.5));
            }

            userRepository.save(author);
        }

        // ======================== ANSWER VOTE ========================
        if (dto.getAnswerId() != null) {
            answer = answerRepository.findById(dto.getAnswerId())
                    .orElseThrow(() -> new IllegalArgumentException("Answer not found"));

            author = answer.getAuthor();

            if (author.getId().equals(voter.getId())) {
                throw new IllegalArgumentException("You cannot vote your own answer.");
            }

            if (voteRepository.existsByUserAndAnswer(voter, answer)) {
                throw new IllegalArgumentException("You have already voted this answer.");
            }

            // Scor pentru autorul răspunsului
            if (dto.getVoteType() == 1) {
                author.setScore((float) (author.getScore() + 5.0));
            } else {
                author.setScore((float) (author.getScore() - 2.5));

                // Penalizează votantul pentru downvote pe răspunsul altuia
                if (!voter.getId().equals(author.getId())) {
                    voter.setScore((float) (voter.getScore() - 1.5));
                    userRepository.save(voter);
                }
            }

            userRepository.save(author);
        }

        // ======================== SALVARE VOT ========================
        Vote vote = new Vote();
        vote.setUser(voter);
        vote.setQuestion(question);
        vote.setAnswer(answer);
        vote.setVoteType(dto.getVoteType());

        vote = voteRepository.save(vote);
        System.out.println("Vote saved with ID: " + vote.getId());

        return mapToDTO(vote);
    }


    public List<VoteResponseDTO> getAllVotes() {
        return voteRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    private VoteResponseDTO mapToDTO(Vote vote) {
        VoteResponseDTO dto = new VoteResponseDTO();
        dto.setId(vote.getId());
        dto.setUserId(vote.getUser().getId());
        dto.setQuestionId(vote.getQuestion() != null ? vote.getQuestion().getId() : null);
        dto.setAnswerId(vote.getAnswer() != null ? vote.getAnswer().getId() : null);
        dto.setVoteType(vote.getVoteType());
        return dto;
    }
}
