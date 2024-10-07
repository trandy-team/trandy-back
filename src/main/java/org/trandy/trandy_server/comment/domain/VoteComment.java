package org.trandy.trandy_server.comment.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.post.domain.Post;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class VoteComment extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotePosition votePosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
