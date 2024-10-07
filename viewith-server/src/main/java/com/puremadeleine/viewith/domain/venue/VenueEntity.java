package com.puremadeleine.viewith.domain.venue;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_venue")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VenueEntity {

    @Id
    @GeneratedValue()
    @Column(name = "venue_id")
    Long id;

    String name;
    String location;
    String imageUrl;
}
