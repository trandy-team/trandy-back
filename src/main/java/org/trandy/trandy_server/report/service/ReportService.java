package org.trandy.trandy_server.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.exception.CustomException;
import org.trandy.trandy_server.exception.ExceptionStatus;
import org.trandy.trandy_server.member.domain.Member;
import org.trandy.trandy_server.member.service.MemberService;
import org.trandy.trandy_server.post.domain.Post;
import org.trandy.trandy_server.post.service.PostService;
import org.trandy.trandy_server.report.domain.Report;
import org.trandy.trandy_server.report.domain.ReportCategory;
import org.trandy.trandy_server.report.domain.ReportStatus;
import org.trandy.trandy_server.report.domain.request.RegisterReportRequest;
import org.trandy.trandy_server.report.domain.request.UpdateReportRequest;
import org.trandy.trandy_server.report.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final MemberService memberService;
    private final PostService postService;
    private final ReportRepository reportRepository;

    public ResponseDto registerReport(RegisterReportRequest request, int memberId) {
        // 멤버 객체 영속화 (개발용)
        Member member = memberService.retrieveMemberMockData(memberId);

        // Post 객체 영속화
        Post post = postService.retrievePostById(request.getPostId());

        reportRepository.save(Report.builder()
                .reportStatus(ReportStatus.PENDING)
                .reportCategory(request.getReportCategory().equals(ReportCategory.VIOLATION_INAPPROPRIATE_NAME.getDesc())
                        ? ReportCategory.VIOLATION_INAPPROPRIATE_NAME
                        : ReportCategory.PORNOGRAPHIC_POSTS
                )
                .build());

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }

    public ResponseDto updateReport(UpdateReportRequest request, int memberId) {
        // 멤버 객체 영속화 (개발용)
        Member member = memberService.retrieveMemberMockData(memberId);

        Report report = reportRepository.findById(request.getReportId()).orElseThrow(
                () -> new CustomException(ExceptionStatus.DataNotFoundException));

        report.updateReviewComment(request, member);

        reportRepository.save(report);

        return ResponseDto.success(Constants.API_RESPONSE_SUCCESSED);
    }
}
