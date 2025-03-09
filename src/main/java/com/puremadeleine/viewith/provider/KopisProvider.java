package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.config.kopis.KopisProperties;
import com.puremadeleine.viewith.dto.client.kopis.KospiResponse;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceInfoResDto;
import com.puremadeleine.viewith.repository.client.KopisRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class KopisProvider {

    KopisRepository kopisRepository;
    KopisProperties kopisProperties;
    static final String KSPO_DOME_CODE = "FC001247-01";     // 올림픽 체조 경기장
    static final String JANGCHUNG_CODE = "FC001247-01";     // 장충체육관
    static final String JAMSIL_ARENA = "FC001247-01";       // 잠실 실내체육관

    public List<PerformanceInfoResDto> getPerformanceList(String venueCode, String genreCode, String startDate, String endDate, int page, int size) {

        KospiResponse<List<PerformanceInfoResDto>> res = kopisRepository.getPerformanceList(kopisProperties.getServiceKey(),
                startDate, endDate, page, size, genreCode, "", venueCode);

        if (checkSuccess(res)) {
            return res.getValue();
        }
        return Collections.emptyList();
    }

    public List<PerformanceDetailResDto> getPerformanceDetails(String performanceId) {
        KospiResponse<List<PerformanceDetailResDto>> res = kopisRepository.getPerformanceDetail(performanceId, kopisProperties.getServiceKey());
        if (checkSuccess(res)) {
            return res.getValue();
        }
        return Collections.emptyList();
    }

    private <T> boolean checkSuccess(KospiResponse<T> response) {
        return true;
    }

}
