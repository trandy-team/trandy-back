package org.trandy.trandy_server.comment.domain.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class CommentByPostIdResponse {
    private Long commentId;
    private String contents;
    private String votePosition;
    private String createdAt;
}
