package org.trandy.trandy_server.vote.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.member.domain.Member;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteType voteType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteStatus voteStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VoteResult voteResult;

    @Column(nullable = false)
    private Boolean deleted;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id")
//    private Category category;
}
