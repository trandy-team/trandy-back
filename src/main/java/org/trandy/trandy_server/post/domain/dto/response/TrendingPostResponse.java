package org.trandy.trandy_server.post.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.trandy.trandy_server.post.domain.VoteResult;
import org.trandy.trandy_server.post.domain.VoteStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TrendingPostResponse {
    private Long id;
    private String title;
    private String contents;
    private VoteStatus voteStatus;
    private VoteResult voteResult;
    private String hashtag;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    private String nickname;
    private String categoryName;
    private String image;
}
