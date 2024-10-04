package org.trandy.trandy_server.image.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.trandy.trandy_server.post.domain.Post;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "image") // 주인의 필드 이름 명시
    private Post post; // 외래 키 정의 없음
}
