package com.puremadeleine.viewith.domain.review;

import com.puremadeleine.viewith.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_review_report")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewReportEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "review_report_id")
    Long id;

    Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    ReviewEntity review;

    @Enumerated(EnumType.STRING)
    ReportReason reportReason;

    String reportReasonDetail;

    public static ReviewReportEntity createReviewReport(ReviewEntity review,
                                                        ReportReason reportReason,String detail) {
        return ReviewReportEntity.builder()
                .review(review)
                .reportReason(reportReason)
                .reportReasonDetail(detail)
                .build();
    }
}
