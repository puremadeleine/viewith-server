package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.venue.SeatEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.puremadeleine.viewith.constants.SeatConstants.UNSELECTED_STRING;
import static com.puremadeleine.viewith.domain.venue.QSeatEntity.seatEntity;


@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SeatCustomRepository {

    JPAQueryFactory queryFactory;

    public Optional<SeatEntity> findSeat(Long venueId, String section, String seatRow, String seatColumn) {
        return Optional.ofNullable(
            queryFactory
                .select(seatEntity)
                .from(seatEntity)
                .where(
                    venueIdEq(venueId),
                    sectionEq(section),
                    seatRowEq(seatRow),
                    seatColumnEq(seatColumn))
                .fetchFirst()
        );
    }

    private BooleanExpression venueIdEq(Long venueId) {
        return Objects.isNull(venueId) ? null : seatEntity.venue.id.eq(venueId);
    }

    private BooleanExpression sectionEq(String section) {
        if (Objects.isNull(section)) return null;
        return seatEntity.section.eq(section);
    }

    private BooleanExpression seatRowEq(String seatRow) {
        if (Objects.isNull(seatRow)) return null;
        return seatEntity.seatRow.eq(seatRow);
    }

    private BooleanExpression seatColumnEq(String seatColumn) {
        if (Objects.isNull(seatColumn)) {
            return seatEntity.seatColumn.eq(UNSELECTED_STRING);
        }
        return seatEntity.seatColumn.eq(seatColumn);
    }
}
