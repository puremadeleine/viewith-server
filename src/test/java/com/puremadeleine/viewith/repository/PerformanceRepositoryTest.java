package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
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
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PerformanceRepositoryTest {
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private PerformanceRepository performanceRepository;

    @Test
    void find() {
        // given
        VenueEntity venue = venueRepository.save(makeDummyVenueEntity());
        PerformanceEntity performance = performanceRepository.save(makePerformanceEntity(venue));

        // when
        Optional<PerformanceEntity> actual = performanceRepository.findById(performance.getId());

        // then
        assertThat(actual).isPresent();
        assertThat(actual.get()).usingRecursiveComparison().isEqualTo(performance);
        assertThat(actual.get().getVenue()).usingRecursiveComparison().isEqualTo(venue);
    }

    private PerformanceEntity makePerformanceEntity(VenueEntity venue) {
        return Instancio.of(PerformanceEntity.class)
                .ignore(field(PerformanceEntity::getId))
                .set(field(PerformanceEntity::getVenue), venue)
                .create();
    }

    private VenueEntity makeDummyVenueEntity() {
        return Instancio.of(VenueEntity.class)
                .ignore(field(VenueEntity::getId))
                .create();
    }
}