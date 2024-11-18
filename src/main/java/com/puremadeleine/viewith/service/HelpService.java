package com.puremadeleine.viewith.service;

import com.puremadeleine.viewith.domain.help.HelpEntity;
import com.puremadeleine.viewith.dto.help.HelpInfoResDto;
import com.puremadeleine.viewith.dto.help.HelpListResDto;
import com.puremadeleine.viewith.provider.HelpProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.puremadeleine.viewith.converter.help.HelpServiceConverter.toHelpListResDto;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HelpService {

    HelpProvider helpProvider;
    HelpServiceMapper mapper;

    public HelpInfoResDto getHelpInfo(Long helpId) {
        HelpEntity help = helpProvider.getHelp(helpId);
        return mapper.toHelpInfoResDto(help);
    }

    public HelpListResDto getHelpList(Integer page, Integer size, Boolean isSummary) {
        Page<HelpEntity> helpList = helpProvider.getHelpList(page, size);
        return toHelpListResDto(isSummary, helpList);
    }

    @Mapper(componentModel = "spring")
    public interface HelpServiceMapper {

        HelpInfoResDto toHelpInfoResDto(HelpEntity help);
    }
}
