package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.image.ProfileImageEntity;
import com.puremadeleine.viewith.repository.ProfileImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileImageService {

    ProfileImageRepository profileImageRepository;

    public ProfileImageEntity getRandom() {
        return profileImageRepository.findRandom()
                .orElseThrow(() -> new IllegalStateException("No profile image found"));
    }
}
