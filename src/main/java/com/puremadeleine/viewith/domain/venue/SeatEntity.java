package com.puremadeleine.viewith.domain.venue;

import com.puremadeleine.viewith.domain.review.Block;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_seat")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeatEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    VenueEntity venue;

    String floor;
    String section;
    Integer seatRow;
    Integer seatColumn;

    @Enumerated(EnumType.STRING)
    Block block;
}
