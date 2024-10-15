package com.puremadeleine.viewith.domain.member;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.dto.member.OAuthType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_member")
public class MemberEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 20)
    private OAuthType oauthType;

    @Column(nullable = false, unique = true)
    private String oauthUserId;

    @Column(nullable = false, unique = true)
    private String oauthEmail;

    @Column(name = "delete_yn", nullable = false)
    private Boolean deleteYn;
}
