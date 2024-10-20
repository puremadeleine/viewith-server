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
        uniqueConstraints = @UniqueConstraint(
                name = "UN_OAUTH", columnNames = {"oauth_type", "oauth_user_id"}
        )
)
public class MemberEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue()
    @Column(name = "member_id")
    private Long id;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false, length = 20)
    private OAuthType oauthType;

    @Column(nullable = false, unique = true)
    private Long oauthUserId;

    @Column(unique = true)
    private String oauthEmail;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "delete_yn", nullable = false)
    private Boolean deleteYn;

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
