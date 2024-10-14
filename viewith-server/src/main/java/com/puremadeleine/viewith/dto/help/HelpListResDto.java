package com.puremadeleine.viewith.dto.help;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpListResDto {

    Integer page;
    Integer size;
    Integer listSize;
    Long total;
    Boolean hasNext;
    List<HelpInfoSummaryResDto> list;
}
