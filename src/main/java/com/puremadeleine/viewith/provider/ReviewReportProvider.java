package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.review.ReviewReportEntity;
import com.puremadeleine.viewith.repository.ReviewReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewReportProvider {

    ReviewReportRepository reviewReportRepository;

    public void saveReviewReport(ReviewReportEntity reviewReport) {
        reviewReportRepository.save(reviewReport);
    }

}
