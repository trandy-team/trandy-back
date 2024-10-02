package org.trandy.trandy_server.post.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class EnrollVoteRequest {
    @NotNull
    private String title;
    @NotNull
    private String contents;
    @NotNull
    private long categoryId;
    private String hashtag;
    private MultipartFile image;

}
