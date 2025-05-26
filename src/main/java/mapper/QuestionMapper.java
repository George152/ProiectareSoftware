package mapper;

import dto.*;
import entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionMapper {

    public static QuestionResponseDTO toDTO(Question question) {
        QuestionResponseDTO dto = new QuestionResponseDTO();
        dto.setId(question.getId());
        dto.setTitle(question.getTitle());
        dto.setContent(question.getContent());
        dto.setPicture(question.getPicture());
        dto.setStatus(question.getStatus());
        dto.setCreatedDate(question.getCreatedDate());

        // Author
        UserDTO authorDTO = new UserDTO();
        authorDTO.setId(question.getAuthor().getId());
        authorDTO.setUsername(question.getAuthor().getUsername());
        authorDTO.setEmail(question.getAuthor().getEmail());
        dto.setAuthor(authorDTO);

        // Tags
        //List<String> tags = question.getTags().stream().map(Tag::getName).collect(Collectors.toList());
        //dto.setTags(tags);

       /* dto.setTags(
                question.getTags().stream()
                        .map(Tag::getName)
                        .collect(Collectors.toList())
        );

        System.out.println("Question Tags: " + question.getTags());
*/
        if (question.getTags() != null && !question.getTags().isEmpty()) {
            dto.setTags(List.of(question.getTags().split(",")));
        } else {
            dto.setTags(List.of());
        }

        return dto;
    }
}
