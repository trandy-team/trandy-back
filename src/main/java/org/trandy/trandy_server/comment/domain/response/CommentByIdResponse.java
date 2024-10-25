package org.trandy.trandy_server.comment.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CommentByIdResponse {
    private Long commentId;
    private String contents;
    private String votePosition;
    private String createdAt;
}
