package com.puremadeleine.viewith.converter.help;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoSummaryResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.util.HtmlUtils;
import org.springframework.data.domain.Page;

import static com.puremadeleine.viewith.util.HtmlUtils.removeHtml;
import static java.lang.Math.min;

public class HelpServiceConverter {

    public static HelpListResDto toHelpListResDto(Boolean isSummary, Page<HelpEntity> helpList) {
        return HelpListResDto.builder()
                .page(helpList.getPageable().getPageNumber() + 1)
                .size(helpList.getSize())
                .listSize(helpList.getContent().size())
                .total(helpList.getTotalElements())
                .hasNext(helpList.hasNext())
                .list(helpList.stream().map(h -> toHelpInfoSummaryResDto(isSummary, h)).toList())
                .build();
    }

    public static HelpInfoSummaryResDto toHelpInfoSummaryResDto(Boolean isSummary, HelpEntity help) {

        String pureContent = removeHtml(help.getContent());
        if (Boolean.TRUE.equals(isSummary)) {
            return HelpInfoSummaryResDto.builder()
                    .title(help.getTitle())
                    .summary(pureContent.substring(0, min(pureContent.length(), 30)))
                    .build();
        }
        return HelpInfoSummaryResDto.builder()
                .title(help.getTitle())
                .content(pureContent)
                .build();
    }
}
