package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.review.ReportReason;
import com.puremadeleine.viewith.domain.review.ReviewReportEntity;
import com.puremadeleine.viewith.repository.ReviewReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class ReviewReportProviderTest {

    ReviewReportRepository reviewReportRepository = mock(ReviewReportRepository.class);
    ReviewReportProvider reviewReportProvider = new ReviewReportProvider(reviewReportRepository);

    @DisplayName("save review report entity successfully test")
    @Test
    void save_review_report_successfully_test() {
        // given
        ReviewReportEntity reviewReport = getReviewEntity();

        // when, then
        reviewReportProvider.saveReviewReport(reviewReport);
    }

    ReviewReportEntity getReviewEntity() {
        return ReviewReportEntity.builder()
                .id(1L)
                .reportReason(ReportReason.OFFENSIVE)
                .build();
    }
}