package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.image.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    @Query(value = "SELECT * FROM tb_profile_image ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<ProfileImageEntity> findRandom();
}