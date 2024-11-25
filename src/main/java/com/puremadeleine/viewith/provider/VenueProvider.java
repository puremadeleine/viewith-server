package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.VenueEntity;
import com.puremadeleine.viewith.exception.ViewithException;
import com.puremadeleine.viewith.repository.VenueRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.puremadeleine.viewith.exception.ViewithErrorCode.NO_VENUE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VenueProvider {

    VenueRepository venueRepository;

    public List<VenueEntity> getVenues() {
        return venueRepository.findAll();
    }

    public VenueEntity getVenue(Long venueId) {
        return venueRepository.findById(venueId)
                .orElseThrow(() -> new ViewithException(NO_VENUE, "The venue with ID " + venueId + " was not found."));
    }


}
