package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.image.ProfileImageEntity;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.dto.member.OAuthType;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Test
    void find() {
        // given
        ProfileImageEntity profileImageEntity = profileImageRepository.save(makeDummyProfileImageEntity());
        MemberEntity member = makeDummyMemberEntity(OAuthType.KAKAO, false, profileImageEntity);
        member = memberRepository.save(member);

        // when
        Optional<MemberEntity> actual = memberRepository.findById(member.getId());

        // then
        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(member);
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void findByIdAndDeleteYn(boolean isDeleted) {
        // given
        ProfileImageEntity profileImageEntity = profileImageRepository.save(makeDummyProfileImageEntity());
        MemberEntity member = makeDummyMemberEntity(OAuthType.KAKAO, isDeleted, profileImageEntity);
        member = memberRepository.save(member);

        // when
        Optional<MemberEntity> actual = memberRepository.findByIdAndDeleteYn(member.getId(), false);

        // then
        if (isDeleted) {
            assertThat(actual).isEmpty();
        } else {
            assertThat(actual).isPresent();
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(member);
            assertThat(actual.get().getProfileImage().getImageUrl())
                    .isEqualTo(profileImageEntity.getImageUrl());
        }
    }

    @ParameterizedTest
    @EnumSource(value = OAuthType.class, names = {"KAKAO", "APPLE"})
    void findByOauthTypeAndOauthUserIdAndDeleteYn(OAuthType oauthType) {
        // given
        ProfileImageEntity profileImageEntity = profileImageRepository.save(makeDummyProfileImageEntity());
        MemberEntity member = makeDummyMemberEntity(oauthType, false, profileImageEntity);
        member = memberRepository.save(member);

        // when
        Optional<MemberEntity> actual = memberRepository.findByOauthTypeAndViewithOauthUserIdAndDeleteYn(OAuthType.KAKAO, member.getViewithOauthUserId(), member.getDeleteYn());

        // then
        if (OAuthType.KAKAO.equals(oauthType)) {
            assertThat(actual).isPresent();
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(member);
        } else {
            assertThat(actual).isEmpty();
        }
    }

    private MemberEntity makeDummyMemberEntity(OAuthType oAuthType, boolean isDeleted, ProfileImageEntity profileImageEntity) {
        Long oauthId = new Random().nextLong();
        return Instancio.of(MemberEntity.class)
                .ignore(field(MemberEntity::getId))
                .set(field(MemberEntity::getProfileImage), profileImageEntity)
                .set(field(MemberEntity::getOauthType), oAuthType)
                .set(field(MemberEntity::getDeleteYn), isDeleted)
                .set(field(MemberEntity::getViewithOauthUserId), oauthId.toString())
                .create();
    }

    private ProfileImageEntity makeDummyProfileImageEntity() {
        return Instancio.of(ProfileImageEntity.class)
                .ignore(field(ProfileImageEntity::getId))
                .create();
    }
}