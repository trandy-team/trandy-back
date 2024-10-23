package org.trandy.trandy_server.post.domain.converter;

import org.springframework.stereotype.Component;
import org.trandy.trandy_server.post.domain.dto.response.PostByCategoryResponse;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostConverter {
    public List<PostByCategoryResponse> objectToDto(List<Object[]> postList) {
        return postList.stream()
                .map(result -> new PostByCategoryResponse(
                        ((BigInteger) result[0]).longValue(),  // postId
                        ((Integer) result[1]),                 // voteCount
                        (String) result[2],                    // nickname
                        (Boolean) result[3],                   // hasVoted
                        (String) result[4],                    // voteDeadline
                        (String) result[5]                     // createdAt
                ))
                .collect(Collectors.toList());
    }
}
