package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.VenueStageEntity;
import com.puremadeleine.viewith.repository.VenueStageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueStageProvider {

    VenueStageRepository venueStageRepository;

    public List<VenueStageEntity> getVenueStages(long venueId) {
        return venueStageRepository.findAllByVenue_Id(venueId);
    }


}
