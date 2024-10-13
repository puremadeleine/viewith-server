package com.puremadeleine.viewith.converter.review.help;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoResDto;

public class HelpServiceConverter {

    public static HelpInfoResDto toHelpInfoResDto(HelpEntity help) {
        return HelpInfoResDto.builder()
                .title(help.getTitle())
                .content(help.getContent())
                .createTime(help.getCreateTime())
                .build();
    }
}
