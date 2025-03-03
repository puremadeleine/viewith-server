package com.puremadeleine.viewith.repository.client;

import com.puremadeleine.viewith.config.client.FeignConfig;
import com.puremadeleine.viewith.dto.client.kopis.PerformanceListResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kopis-api", url = "${kopis.api.uri}", configuration = FeignConfig.class)
public interface KopisRepository {

    String SERVICE = "service";
    String START_DATE = "stdate";
    String END_DATE = "eddate";
    String PAGE = "cpage";
    String SIZE = "rows";
    String GENRE_CODE = "shcate";
    String DISTRICT_CODE = "signgucodesub";
    String VENUE_CODE = "prfplccd";

    @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
    PerformanceListResDto getPerformanceList(@RequestParam(SERVICE) String serviceKey,
                               @RequestParam(START_DATE) String startDate,
                               @RequestParam(END_DATE) String endDate,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = GENRE_CODE, required = false) String genreCode,
                               @RequestParam(value = DISTRICT_CODE, required = false) String districtCode,
                               @RequestParam(value = VENUE_CODE, required = false) String venueCode
    );

}
