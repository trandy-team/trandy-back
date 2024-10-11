package org.trandy.trandy_server.post.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.trandy.trandy_server.category.domain.Category;
import org.trandy.trandy_server.comment.domain.VoteComment;
import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.image.domain.Image;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.report.domain.Report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = false")
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
    private VoteStatus voteStatus;

    @Column
    @Enumerated(EnumType.STRING)
    private VoteResult voteResult;

    @Column
    private String hashtag;

    @Column(nullable = false)
    private Boolean deleted;

    @Column
    private LocalDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    private Image image;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    @Builder.Default
    @JsonIgnore
    private List<Report> reports = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "post")
    @Builder.Default
    @JsonIgnore
    private List<VoteComment> voteComments = new ArrayList<>();

    public void updateImage(Image image){
        this.image = image;
    }
}
