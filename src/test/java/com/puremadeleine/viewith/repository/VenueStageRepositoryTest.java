package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.config.jpa.JpaConfig;
import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.domain.venue.VenueStageEntity;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;

@DataJpaTest
@Import(JpaConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VenueStageRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private VenueStageRepository venueStageRepository;

    @Test
    void findAllByVenue_Id() {
        // given
        VenueEntity venue = venueRepository.save(makeDummyVenueEntity());
        VenueStageEntity stage1 = venueStageRepository.save(makeDummyVenueStageEntity(venue));
        VenueStageEntity stage2 = venueStageRepository.save(makeDummyVenueStageEntity(venue));

        // when
        List<VenueStageEntity> actual = venueStageRepository.findAllByVenue_Id(venue.getId());

        // then
        assertThat(actual).isNotEmpty()
                .hasSize(2);
        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(List.of(stage1, stage2));
        assertThat(actual.getFirst().getVenue()).usingRecursiveComparison()
                .isEqualTo(venue);
        assertThat(actual.getLast().getVenue()).usingRecursiveComparison()
                .isEqualTo(venue);
    }

    private VenueStageEntity makeDummyVenueStageEntity(VenueEntity venue) {
        return Instancio.of(VenueStageEntity.class)
                .ignore(field(VenueStageEntity::getId))
                .set(field(VenueStageEntity::getVenue), venue)
                .create();

    }

    private VenueEntity makeDummyVenueEntity() {
        return Instancio.of(VenueEntity.class)
                .ignore(field(VenueEntity::getId))
                .create();
    }

}