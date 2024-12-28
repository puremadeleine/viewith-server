package com.puremadeleine.viewith.domain.bookmark;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import com.puremadeleine.viewith.domain.member.MemberEntity;
import com.puremadeleine.viewith.domain.venue.SeatEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tb_bookmark",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UN_USER", columnNames = {"user_id", "seat_id"}
                )
        }
)
public class BookmarkEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    SeatEntity seat;
}
