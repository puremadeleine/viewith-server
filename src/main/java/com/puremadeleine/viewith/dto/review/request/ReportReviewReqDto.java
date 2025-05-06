package com.puremadeleine.viewith.dto.review.request;

import com.puremadeleine.viewith.domain.review.ReportReason;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReportReviewReqDto(
        @NotNull ReportReason reportReason,
        String reportReasonDetail
) {}
