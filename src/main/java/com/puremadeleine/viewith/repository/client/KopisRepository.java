package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignLoggerConfig;
import com.puremadeleine.viewith.dto.client.kopis.KospiResponse;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceDetailResDto;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <a href="https://www.kopis.or.kr/por/cs/openapi/openApiInfo.do?menuId=MNU_00074">...</a>
 */
@FeignClient(name = "kopis-api", url = "${kopis.api.uri}", configuration = FeignLoggerConfig.class)
public interface KopisRepository {

    String SERVICE = "service";
    String START_DATE = "stdate";
    String END_DATE = "eddate";
    String PAGE = "cpage";
    String SIZE = "rows";
    String GENRE_CODE = "shcate";
    String DISTRICT_CODE = "signgucodesub";
    String VENUE_CODE = "prfplccd";

    @GetMapping(value = "")
    KospiResponse<List<PerformanceInfoResDto>> getPerformanceList(@RequestParam(SERVICE) String serviceKey,
                                                                  @RequestParam(START_DATE) String startDate,
                                                                  @RequestParam(END_DATE) String endDate,
                                                                  @RequestParam(PAGE) Integer page,
                                                                  @RequestParam(SIZE) Integer size,
                                                                  @RequestParam(value = GENRE_CODE, required = false) String genreCode,
                                                                  @RequestParam(value = VENUE_CODE, required = false) String venueCode
    );

    @GetMapping(value = "/{performance_id}")
    KospiResponse<List<PerformanceDetailResDto>> getPerformanceDetail(@PathVariable(name = "performance_id") String performanceId,
                                                                      @RequestParam(SERVICE) String serviceKey);

}
