package org.trandy.trandy_server.post.domain.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.trandy.trandy_server.category.domain.Category;
import org.trandy.trandy_server.comment.domain.VoteComment;
import org.trandy.trandy_server.image.domain.Image;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.post.domain.VoteResult;
import org.trandy.trandy_server.post.domain.VoteStatus;
import org.trandy.trandy_server.report.domain.Report;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<VoteComment> voteComments;
}
