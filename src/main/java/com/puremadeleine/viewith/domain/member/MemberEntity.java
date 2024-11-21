package com.puremadeleine.viewith.domain.member;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.dto.client.UserInfoResDto;
import com.puremadeleine.viewith.dto.member.OAuthType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UN_OAUTH", columnNames = {"oauth_type", "oauth_user_id"}
                ),
                @UniqueConstraint(
                        name = "UN_NICKNAME", columnNames = {"nickname"}
                )
        }
)
public class MemberEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    Long id;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false, length = 10)
    String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    OAuthType oauthType;

    @Column(nullable = false, unique = true)
    Long oauthUserId;

    @Column(unique = true)
    String oauthEmail;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "delete_yn", nullable = false)
    Boolean deleteYn;

    public static MemberEntity createKakaoMember(UserInfoResDto kakaoUserInfo, String nickname) {
        return MemberEntity.builder()
                .nickname(nickname)
                .oauthType(OAuthType.KAKAO)
                .oauthUserId(kakaoUserInfo.getId())
                .oauthEmail(Optional.ofNullable(kakaoUserInfo.getKakaoAccount())
                        .map(UserInfoResDto.KakaoAccount::getEmail)
                        .orElse(null))
                .deleteYn(false)
                .build();
    }

    public void updateNickname(String nickname) {
        this.setNickname(nickname);
    }

    public void updateDeleteYn(boolean deleteYn) {
        this.setDeleteYn(deleteYn);
    }
}
