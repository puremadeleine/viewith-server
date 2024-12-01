package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {

    List<PerformanceEntity> findByTitleContainsIgnoreCase(String keyword);

    @Query(value = """
                WITH RecentPerformance AS (
                    SELECT
                        p.performance_id AS id,
                        p.title AS title,
                        p.start_date AS startDate,
                        p.end_date AS endDate,
                        p.venue_id AS venueId,
                        p.image_url AS imageUrl
                        ROW_NUMBER() OVER (
                            PARTITION BY p.venue_id ORDER BY
                            ABS(TIMESTAMPDIFF(SECOND, p.start_date, CURRENT_TIMESTAMP())) ASC
                        ) AS row_num
                    FROM tb_performance p
                    JOIN tb_venue v ON p.venue_id = v.venue_id
                )
                SELECT rp.id, rp.title, rp.startDate, rp.endDate, rp.venueId, rp.imageUrl
                FROM RecentPerformance rp
                WHERE row_num <= :limit
            """, nativeQuery = true)
    List<PerformanceEntity> findTopPerformancesPerVenue(@Param("limit") int limit);
}
