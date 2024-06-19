package com.tps.components;

import com.tps.dto.CommentDTO;
import com.tps.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {
    @Autowired
    UserConverter userConverter;

    public CommentDTO toDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setUser(userConverter.convertToDTO(comment.getUser()));
        commentDTO.setCreatedAt(comment.getCreatedDate().toString());
        return commentDTO;
    }


}
