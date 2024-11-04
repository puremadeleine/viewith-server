package com.puremadeleine.viewith.domain.venue;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_performance")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerformanceEntity {

    @Id
    @GeneratedValue()
    @Column(name = "review_id")
    Long id;

    String title;
    String article;
    LocalDateTime startDate;
    LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;

    String imageUrl;
}
