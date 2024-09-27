package org.trandy.trandy_server.category.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.post.domain.Post;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = false")
@Getter
@Builder
public class Category extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String categoryName;

    @Column(nullable = false)
    @Builder.Default()
    private Boolean deleted = false;

    @Column
    private LocalDateTime deletedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void delete(Boolean deletedFlag){
        this.deleted = !deletedFlag;
        this.deletedAt = LocalDateTime.now();
    }

    public void update(String categoryName) {
        this.categoryName = categoryName;
    }
}
