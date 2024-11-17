package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.venue.PerformanceEntity;
import com.puremadeleine.viewith.dto.performance.PerformanceSearchResDto;
import com.puremadeleine.viewith.provider.PerformanceProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PerformanceService {

    PerformanceProvider performanceProvider;
    PerformanceServiceMapper mapper;

    public List<PerformanceSearchResDto> searchPerformance(String keyword) {
        List<PerformanceEntity> searchList = performanceProvider.search(keyword);
        return mapper.toPerformanceSearchResDtoList(searchList);
    }


    @Mapper(componentModel = "spring")
    public interface PerformanceServiceMapper {

        List<PerformanceSearchResDto> toPerformanceSearchResDtoList(List<PerformanceEntity> performances);

        @Mapping(source = "title", target = "performanceTitle")
        @Mapping(source = "venue.name", target = "venueName")
        @Mapping(source = "startDate", target = "performanceStartDate")
        @Mapping(source = "endDate", target = "performanceEndDate")
        PerformanceSearchResDto ntoPerformanceSearchResDto(PerformanceEntity performance);
    }
}
