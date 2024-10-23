package org.trandy.trandy_server.post.domain.converter;

import org.springframework.stereotype.Component;
import org.trandy.trandy_server.post.domain.dto.response.PostByCategoryResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostConverter {
    public List<PostByCategoryResponse> objectToDto(List<Object[]> postList) {
        List<PostByCategoryResponse> result = new ArrayList<>();

        for (Object[] post : postList) {
            result.add(PostByCategoryResponse.builder()
                    .postId(Long.parseLong(post[0].toString()))
                    .voteCount(Integer.parseInt(post[1].toString()))
                    .nickname(String.valueOf(post[2]))
                    .hasVoted(Boolean.parseBoolean(post[3].toString()))
                    .voteDeadline(String.valueOf(post[4]))
                    .createdAt(String.valueOf(post[5]))
                    .build());
        }

        return result;
    }
}
