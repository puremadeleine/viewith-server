package com.puremadeleine.viewith.domain.venue;

import com.puremadeleine.viewith.dto.venue.VenueStageType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_venue_stage")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueStageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venue_stage_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    VenueEntity venue;
    @Enumerated(EnumType.STRING)
    VenueStageType type;
    String name;
    String svgUrl;

}
