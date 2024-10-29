package com.puremadeleine.viewith.dto.help;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpInfoResDto {

    String title;
    String content;
    LocalDateTime createTime;

}
