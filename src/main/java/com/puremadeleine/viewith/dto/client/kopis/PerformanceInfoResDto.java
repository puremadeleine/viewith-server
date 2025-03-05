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
public class PerformanceInfoResDto {

    @JacksonXmlProperty(localName = "mt20id")
    String id;

    @JacksonXmlProperty(localName = "prfnm")
    String name;

}
