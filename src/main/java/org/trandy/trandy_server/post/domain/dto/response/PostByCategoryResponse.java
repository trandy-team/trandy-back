package org.trandy.trandy_server.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostByCategoryResponse {
    private long postId;
    private long voteCount;
    private String nickname;
    private boolean hasVoted;
    private String voteDeadline;
    private String createdAt;
}
