package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.provider.HelpProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.converter.review.help.HelpServiceConverter.toHelpInfoResDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelpService {

    final HelpProvider helpProvider;

    public HelpInfoResDto getHelpInfo(Long helpId) {
        HelpEntity help = helpProvider.getHelp(helpId);
        return toHelpInfoResDto(help);
    }
}
