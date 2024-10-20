package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.JpaAuditingConfig;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void find() {
        // given
        MemberEntity member = makeDummyMemberEntity(OAuthType.KAKAO, false);
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
        MemberEntity member = makeDummyMemberEntity(OAuthType.KAKAO, isDeleted);
        member = memberRepository.save(member);

        // when
        Optional<MemberEntity> actual = memberRepository.findByIdAndDeleteYn(member.getId(), false);

        // then
        if (isDeleted) {
            assertThat(actual).isEmpty();
        } else {
            assertThat(actual).isPresent();
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(member);
        }
    }

    @ParameterizedTest
    @EnumSource(value = OAuthType.class, names = {"KAKAO", "APPLE"})
    void findByOauthTypeAndOauthUserId(OAuthType oauthType) {
        // given
        MemberEntity member = makeDummyMemberEntity(oauthType, false);
        member = memberRepository.save(member);

        // when
        Optional<MemberEntity> actual = memberRepository.findByOauthTypeAndOauthUserId(OAuthType.KAKAO, member.getOauthUserId());

        // then
        if (OAuthType.KAKAO.equals(oauthType)) {
            assertThat(actual).isPresent();
            assertThat(actual.get()).usingRecursiveComparison().isEqualTo(member);
        } else {
            assertThat(actual).isEmpty();
        }
    }

    private MemberEntity makeDummyMemberEntity(OAuthType oAuthType, boolean isDeleted) {
        return Instancio.of(MemberEntity.class)
                .set(field(MemberEntity::getOauthType), oAuthType)
                .set(field(MemberEntity::getDeleteYn), isDeleted)
                .create();
    }
}