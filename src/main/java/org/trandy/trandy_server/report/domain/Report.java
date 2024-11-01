package org.trandy.trandy_server.report.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.report.domain.request.UpdateReportRequest;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Report extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportCategory reportCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reportReviewComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private Member reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private Member approver;

    public void updateReviewComment(UpdateReportRequest request, Member member) {
        this.reportStatus = request.getReportStatus();
        this.reportReviewComment = request.getReportReviewComment();
        this.reporter = member;
    }
}
