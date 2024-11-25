package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaAuditingConfig;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
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
class VenueRepositoryTest {
    @Autowired
    private VenueRepository venueRepository;

    @Test
    void find() {
        // given
        VenueEntity venue = venueRepository.save(makeDummyVenueEntity());

        // when
        Optional<VenueEntity> actual = venueRepository.findById(venue.getId());

        // then
        assertThat(actual).isPresent();
        VenueEntity actualVenue = actual.get();
        assertThat(actualVenue.getId()).isEqualTo(venue.getId());
        assertThat(actualVenue.getName()).isEqualTo(venue.getName());
        assertThat(actualVenue.getLocation()).isEqualTo(venue.getLocation());
        assertThat(actualVenue.getImageUrl()).isEqualTo(venue.getImageUrl());
    }

    private VenueEntity makeDummyVenueEntity() {
        return Instancio.of(VenueEntity.class)
                .ignore(field(VenueEntity::getId))
                .create();
    }
}