package org.trandy.trandy_server.member.domain;

import org.trandy.trandy_server.common.BaseTimeEntity;
import org.trandy.trandy_server.member.domain.dto.request.MemberUpdateRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(nullable = false)
    private Boolean deleted;

    @Column
    private LocalDateTime deletedAt;

//    public void update(MemberUpdateRequest request){
//        this.password = request.getPassword();
//        this.memberRole = Role.ADMIN.getRole().equals(request.getMemberRole()) ? Role.ADMIN : Role.USER;
//        this.email = request.getEmail();
//    }

    public void delete(Boolean deletedFlag){
        this.deleted = !deletedFlag;
        this.deletedAt = LocalDateTime.now();
    }
}
