package org.trandy.trandy_server.comment.domain.converter;

import org.springframework.stereotype.Component;
import org.trandy.trandy_server.comment.domain.VoteComment;
import org.trandy.trandy_server.comment.domain.response.CommentByIdResponse;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentConverter {

    public List<CommentByIdResponse> entityListToDtoList(List<VoteComment> comments) {
        List<CommentByIdResponse> responses = new ArrayList<>();

        for(VoteComment comment : comments){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
            String createdAt = comment.getCreatedAt().format(formatter);

            responses.add(CommentByIdResponse.builder()
                            .commentId(comment.getId())
                            .votePosition(comment.getVotePosition().getDesc())
                            .contents(comment.getContents())
                            .createdAt(createdAt)
                    .build());
        }

        return responses;
    }
}
