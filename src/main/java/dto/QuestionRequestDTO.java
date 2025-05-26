package dto;

import java.util.ArrayList;
import java.util.List;

public class QuestionRequestDTO {
    private String title;
    private String content;
    private String picture;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
    private List<String> tagNames= new ArrayList<>();;
    private UserDTO author;


    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(List<String> tagNames) {
        this.tagNames = tagNames;
    }


    public Long getAuthorId() {
        if (author == null) {
            return null; // sau aruncă o excepție personalizată dacă vrei
        }return author.getId();
    }
}
