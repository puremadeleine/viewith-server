package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.image.ImageEntity;
import com.puremadeleine.viewith.domain.image.SourceType;
import com.puremadeleine.viewith.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageProvider {

    ImageRepository imageRepository;

    public void saveAll(List<ImageEntity> images) {
        imageRepository.saveAll(images);
    }

    public List<ImageEntity> getImageList(Long sourceId, SourceType sourceType) {
        return imageRepository.findBySourceIdAndSourceType(sourceId, sourceType);
    }
}
