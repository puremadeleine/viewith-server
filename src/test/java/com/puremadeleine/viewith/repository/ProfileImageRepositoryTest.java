package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.image.ProfileImageEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfileImageRepositoryTest {
    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Test
    void findRandom() {
        // given
        ProfileImageEntity entity1 = makeDummyProfileImageEntity();
        ProfileImageEntity entity2 = makeDummyProfileImageEntity();
        ProfileImageEntity entity3 = makeDummyProfileImageEntity();
        ProfileImageEntity entity4 = makeDummyProfileImageEntity();
        ProfileImageEntity entity5 = makeDummyProfileImageEntity();
        profileImageRepository.saveAll(List.of(entity1, entity2, entity3, entity4, entity5));

        // when
        Optional<ProfileImageEntity> actual = profileImageRepository.findRandom();

        // then
        assertThat(actual).isPresent();
    }

    private ProfileImageEntity makeDummyProfileImageEntity() {
        return Instancio.of(ProfileImageEntity.class)
                .ignore(field(ProfileImageEntity::getId))
                .create();
    }
}