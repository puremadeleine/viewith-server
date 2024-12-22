package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.image.ImageEntity;
import com.puremadeleine.viewith.domain.image.SourceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    List<ImageEntity> findBySourceIdAndSourceType(Long sourceId, SourceType sourceType);

    List<ImageEntity> findBySourceIdInAndSourceType(List<Long> sourceIds, SourceType sourceType);
}
