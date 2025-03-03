package com.puremadeleine.viewith.dto.client.kopis;

//import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
//@XmlRootElement(name = "dbs")
public class PerformanceListResDto {

    List<PerformanceInfoResDto> performances;
}
