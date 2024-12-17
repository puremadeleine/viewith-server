package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.image.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
}
