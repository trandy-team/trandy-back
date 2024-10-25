package org.trandy.trandy_server.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostByMemberIdResponse {
    private long postId;
    private String title;
    private String categoryName;
}
