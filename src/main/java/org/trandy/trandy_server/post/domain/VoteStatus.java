package org.trandy.trandy_server.post.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteStatus {

    IN_PROGRESS("IN_PROGRESS", "진행중"),
    FINISHED("FINISHED", "종료");

    private final String status;
    private final String desc;
}
