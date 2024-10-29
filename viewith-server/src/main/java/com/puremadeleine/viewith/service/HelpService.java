package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.provider.HelpProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.converter.help.HelpServiceConverter.toHelpInfoResDto;
import static com.puremadeleine.viewith.converter.help.HelpServiceConverter.toHelpListResDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpService {

    final HelpProvider helpProvider;

    public HelpInfoResDto getHelpInfo(Long helpId) {
        HelpEntity help = helpProvider.getHelp(helpId);
        return toHelpInfoResDto(help);
    }

    public HelpListResDto getHelpList(Integer page, Integer size, Boolean isSummary) {
        Page<HelpEntity> helpList = helpProvider.getHelpList(page - 1, size);
        return toHelpListResDto(page, isSummary, helpList);
    }
}
