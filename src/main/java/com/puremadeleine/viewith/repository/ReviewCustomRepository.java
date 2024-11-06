package com.puremadeleine.viewith.repository;

import com.puremadeleine.viewith.domain.review.ReviewEntity;
import com.puremadeleine.viewith.dto.common.SortType;
import com.puremadeleine.viewith.dto.review.ReviewListReqDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.puremadeleine.viewith.domain.image.QImageEntity.imageEntity;
import static com.puremadeleine.viewith.domain.review.QReviewEntity.reviewEntity;
import static com.puremadeleine.viewith.domain.venue.QSeatEntity.seatEntity;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewCustomRepository {

    JPAQueryFactory queryFactory;

    public List<ReviewEntity> findReviewList(ReviewListReqDto req) {
        return queryFactory
                .select(reviewEntity)
                .from(reviewEntity)
                .join(reviewEntity.seat, seatEntity)
                .where(
                        floorEq(req.getFloor()),
                        sectionEq(req.getSection()),
                        seatRowEq(req.getSeatRow()))
                .orderBy(
                        getOrder(req.getSortType()),
                        reviewEntity.id.desc())
                .offset(getOffset(req))
                .limit(req.getSize())
                .fetch();
    }

    public List<ReviewEntity> findReviewListPrioritizingMedia(ReviewListReqDto req) {
        return queryFactory
                .select(reviewEntity)
                .from(reviewEntity)
                .join(reviewEntity.seat, seatEntity)
                .leftJoin(imageEntity)
                .on(reviewEntity.id.eq(imageEntity.sourceId) )
                .where(
                        floorEq(req.getFloor()),
                        sectionEq(req.getSection()),
                        seatRowEq(req.getSeatRow()))
                .orderBy(
                        getPrioritizingMediaDesc(),
                        reviewEntity.id.desc()
                )
                .offset(getOffset(req))
                .limit(req.getSize())
                .fetch();
    }

    private static OrderSpecifier<Integer> getPrioritizingMediaDesc() {
        return new CaseBuilder()
                .when(imageEntity.id.isNotNull()).then(1)
                .otherwise(0)
                .desc();
    }

    public int countReviewTotal(ReviewListReqDto req) {
        return queryFactory
                .select(reviewEntity.id)
                .from(reviewEntity)
                .join(reviewEntity.seat, seatEntity)
                .where(
                        floorEq(req.getFloor()),
                        sectionEq(req.getSection()),
                        seatRowEq(req.getSeatRow()))
                .fetch().size();
    }

    private int getOffset(ReviewListReqDto req) {
        return (req.getPage() - 1) * req.getSize();
    }

    private BooleanExpression floorEq(String floor) {
        if (Objects.isNull(floor)) return null;
        return seatEntity.floor.eq(floor);
    }

    private BooleanExpression sectionEq(String section) {
        if (Objects.isNull(section)) return null;
        return seatEntity.section.eq(section);
    }

    private BooleanExpression seatRowEq(Integer seatRow) {
        if (Objects.isNull(seatRow)) return null;
        return seatEntity.seatRow.eq(seatRow);
    }

    private OrderSpecifier<?> getOrder(SortType sortType) {
        if (Objects.isNull(sortType)) return null;
        return switch (sortType) {
            case LATEST -> reviewEntity.createTime.desc();
            case RATING -> reviewEntity.rating.desc();
            default -> reviewEntity.id.asc();
        };
    }
}
