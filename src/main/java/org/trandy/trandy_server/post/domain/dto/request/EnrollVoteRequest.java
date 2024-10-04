package org.trandy.trandy_server.post.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
public class EnrollVoteRequest {
    @NotNull
    @Schema(description = "투표 제목", example = "뉴진스는 대세다?")
    private String title;

    @NotNull
    @Schema(description = "투표 상세", example = "뉴진스는 대세입니다.")
    private String contents;

    @NotNull
    @Schema(description = "카테고리 id", example = "3")
    private long categoryId;

    @Schema(description = "해시태그", example = "#여자아이돌 #월클")
    private String hashtag;

    @NotNull
    private MultipartFile image;

}
