package com.puremadeleine.viewith.dto.client.kopis;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PerformanceDetailResDto {

    @JacksonXmlProperty(localName = "mt20id")
    String id;

    @JacksonXmlProperty(localName = "prfnm")
    String name;

    @JacksonXmlProperty(localName = "prfcast")
    String artist;

    @JacksonXmlProperty(localName = "fcltynm")
    String venue;

    @JacksonXmlProperty(localName = "prfpdfrom")
    String startDate;

    @JacksonXmlProperty(localName = "prfpdto")
    String endDate;

    @JacksonXmlProperty(localName = "poster")
    String imgUrl;

}
