package com.puremadeleine.viewith.provider;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.repository.PerformanceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerformanceProvider {

    PerformanceRepository performanceRepository;

    public List<PerformanceEntity> search(String keyword) {
        return performanceRepository.findByTitleContainsIgnoreCase(keyword);
    }
}
