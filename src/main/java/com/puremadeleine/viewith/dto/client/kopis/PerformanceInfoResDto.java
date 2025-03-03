package com.puremadeleine.viewith.dto.client.kopis;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@XmlRootElement(name = "db")
public class PerformanceInfoResDto {

    String area;
    String fcltynm;
}
