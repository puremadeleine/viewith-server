package com.puremadeleine.viewith.domain.member;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.domain.image.ProfileImageEntity;
import com.puremadeleine.viewith.dto.member.OAuthType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_member",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UN_OAUTH", columnNames = {"oauth_type", "viewith_oauth_user_id"}
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_image_id")
    ProfileImageEntity profileImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    OAuthType oauthType;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(nullable = false, unique = true, length = 40)
    String viewithOauthUserId;

    @Setter(value = AccessLevel.PRIVATE)
    @Column(name = "delete_yn", nullable = false)
    Boolean deleteYn;

    public static MemberEntity createAppleMember(String oauthUserId, String nickname, ProfileImageEntity profileImage) {
        return MemberEntity.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .oauthType(OAuthType.APPLE)
                .viewithOauthUserId(oauthUserId)
                .deleteYn(false)
                .build();
    }

    public static MemberEntity createKakaoMember(long oauthUserId, String nickname, ProfileImageEntity profileImage) {
        return MemberEntity.builder()
                .nickname(nickname)
                .profileImage(profileImage)
                .oauthType(OAuthType.KAKAO)
                .viewithOauthUserId(String.valueOf(oauthUserId))
                .deleteYn(false)
                .build();
    }

    public void updateNickname(String nickname) {
        this.setNickname(nickname);
    }

    public void delete() {
        this.setDeleteYn(true);
        this.setViewithOauthUserId(UUID.randomUUID().toString());
    }
}
