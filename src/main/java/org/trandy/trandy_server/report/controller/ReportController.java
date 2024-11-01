package org.trandy.trandy_server.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.trandy.trandy_server.comment.domain.request.RegisterCommentRequest;
import org.trandy.trandy_server.common.Constants;
import org.trandy.trandy_server.common.ResponseDto;
import org.trandy.trandy_server.common.jwt.UserDetailsImpl;
import org.trandy.trandy_server.report.domain.request.RegisterReportRequest;
import org.trandy.trandy_server.report.domain.request.UpdateReportRequest;
import org.trandy.trandy_server.report.service.ReportService;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "투표 글 신고", description = "투표 게시물에 대한 신고글 작성")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping(value = "/registerReport")
    public ResponseEntity<ResponseDto> registerReport(@RequestBody @Valid RegisterReportRequest request,
                                                           @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        reportService.registerReport(request, userDetails.getMember());

        // 로그인 구현 전 개발용
        reportService.registerReport(request, 2);

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }

    @Operation(summary = "[ADMIN] 신고 건 업데이트", description = "신고된 건에 대한 승인 / 거절")
    @ApiResponse(responseCode = "200", description = "SUCCESSED")
    @PostMapping(value = "/updateReport")
    @Secured("ADMIN")
    public ResponseEntity<ResponseDto> updateReport(@RequestBody @Valid UpdateReportRequest request,
                                                      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails){

//        reportService.updateReport(request, userDetails.getMember());

        // 로그인 구현 전 개발용
        reportService.updateReport(request, 2);

        return ResponseEntity.ok(ResponseDto.success(Constants.API_RESPONSE_SUCCESSED));
    }
}
