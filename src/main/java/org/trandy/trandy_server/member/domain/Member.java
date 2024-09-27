package org.trandy.trandy_server.member.domain;

import org.hibernate.annotations.SQLRestriction;
import org.trandy.trandy_server.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.trandy.trandy_server.post.domain.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted = false")
@Builder
public class Member extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    private String profileImageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role memberRole;

    private long score;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean deleted = false;

    @Column
    private LocalDateTime deletedAt;

    public void delete(Boolean deletedFlag){
        this.deleted = !deletedFlag;
        this.deletedAt = LocalDateTime.now();
    }
//    public void update(MemberUpdateRequest request){
//        this.password = request.getPassword();
//        this.memberRole = Role.ADMIN.getRole().equals(request.getMemberRole()) ? Role.ADMIN : Role.USER;
//        this.email = request.getEmail();
//    }
}
